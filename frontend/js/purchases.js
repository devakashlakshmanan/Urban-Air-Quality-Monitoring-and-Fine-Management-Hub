// Purchases and Vendor Bills

async function loadPurchases() {
    await Promise.all([loadPurchaseOrders(), loadVendorBills()]);
}

async function loadPurchaseOrders() {
    const tbody = document.getElementById('po-table-body');
    if (!tbody) return;
    tbody.innerHTML = '<tr><td colspan="7">Loading purchase orders...</td></tr>';
    try {
        const [orders, vendors] = await Promise.all([
            getData('/api/purchase/getall'),
            getData('/api/sensorvendor/getall').catch(() => [])
        ]);

        const vendorMap = {};
        if (Array.isArray(vendors)) {
            vendors.forEach(v => {
                const vId = v.id !== undefined ? v.id : v.Id;
                const vName = v.name || v.Name || ('Vendor #' + vId);
                const vCode = v.vendorCode || v.VendorCode || '';
                vendorMap[vId] = vCode ? `${vName} (${vCode})` : vName;
            });
        }

        tbody.innerHTML = '';
        if (!Array.isArray(orders) || orders.length === 0) {
            tbody.innerHTML = '<tr><td colspan="7" style="text-align: center;">No purchase orders recorded yet.</td></tr>';
            return;
        }

        orders.forEach(po => {
            const tr = document.createElement('tr');
            const poId = po.id !== undefined ? po.id : po.Id;
            const poNum = po.purchaseOrderNumber || po.PurchaseOrderNumber || ('PO-' + poId);
            const vendorId = po.vendorId !== undefined ? po.vendorId : po.VendorId;
            const vendorLabel = vendorId ? (vendorMap[vendorId] || `Vendor #${vendorId}`) : 'N/A';
            const orderDate = po.orderDate || po.OrderDate || '';
            const qty = po.quantity !== undefined ? po.quantity : (po.Quantity !== undefined ? po.Quantity : 1);
            const amt = (po.amount !== undefined && po.amount !== null) ? po.amount : ((po.Amount !== undefined && po.Amount !== null) ? po.Amount : 0);
            const status = po.status || po.Status || 'CREATED';
            const statusClass = status.toLowerCase();
            const actionBtn = (status.toUpperCase() === 'CREATED')
                ? `<button class="btn btn-sm btn-primary" onclick="createBillFromPO(${poId}, ${vendorId || 1}, ${amt})">Generate Bill</button>`
                : `<span style="color: var(--text-light); font-size: 0.8rem;">${status}</span>`;

            tr.innerHTML = `
                <td><strong>${poNum}</strong></td>
                <td>${vendorLabel}</td>
                <td>${orderDate}</td>
                <td>${qty}</td>
                <td><strong>₹${Number(amt).toLocaleString()}</strong></td>
                <td><span class="badge badge-${statusClass}">${status}</span></td>
                <td>${actionBtn}</td>
            `;
            tbody.appendChild(tr);
        });
    } catch (err) {
        tbody.innerHTML = '<tr><td colspan="7" style="color: var(--danger-color);">Error loading purchase orders.</td></tr>';
    }
}

async function savePurchaseOrder(e) {
    e.preventDefault();
    const vendorId = parseInt(document.getElementById('po-vendor-id').value);
    const productId = parseInt(document.getElementById('po-product-id').value);
    const quantity = parseInt(document.getElementById('po-quantity').value) || 1;
    const amount = parseFloat(document.getElementById('po-amount').value) || 0.0;
    const orderDate = document.getElementById('po-date').value || new Date().toISOString().split('T')[0];

    const data = {
        vendorId: vendorId,
        productId: productId,
        quantity: quantity,
        amount: amount,
        orderDate: orderDate,
        status: "CREATED"
    };

    try {
        await postData('/api/purchase/create', data);
        showAlert('Purchase Order created successfully.');
        document.getElementById('po-form').reset();
        loadPurchaseOrders();
        if (typeof loadDashboard === 'function') loadDashboard();
    } catch (err) {
        showAlert('Failed to create Purchase Order: ' + err.message, 'error');
    }
}

function handlePOProductChange() {
    const productSelect = document.getElementById('po-product-id');
    const selectedOpt = productSelect.options[productSelect.selectedIndex];
    const qty = parseInt(document.getElementById('po-quantity').value) || 1;
    if (selectedOpt && selectedOpt.getAttribute('data-price')) {
        const unitPrice = parseFloat(selectedOpt.getAttribute('data-price')) || 0;
        document.getElementById('po-amount').value = unitPrice * qty;
    }
}

async function loadVendorBills() {
    const tbody = document.getElementById('bills-table-body');
    if (!tbody) return;
    tbody.innerHTML = '<tr><td colspan="7">Loading vendor bills...</td></tr>';
    try {
        const [bills, vendors] = await Promise.all([
            getData('/api/vendorbill/getall'),
            getData('/api/sensorvendor/getall').catch(() => [])
        ]);

        const vendorMap = {};
        if (Array.isArray(vendors)) {
            vendors.forEach(v => {
                const vId = v.id !== undefined ? v.id : v.Id;
                const vName = v.name || v.Name || ('Vendor #' + vId);
                const vCode = v.vendorCode || v.VendorCode || '';
                vendorMap[vId] = vCode ? `${vName} (${vCode})` : vName;
            });
        }

        tbody.innerHTML = '';
        if (!Array.isArray(bills) || bills.length === 0) {
            tbody.innerHTML = '<tr><td colspan="7" style="text-align: center;">No vendor bills recorded yet.</td></tr>';
            return;
        }

        bills.forEach(b => {
            const tr = document.createElement('tr');
            const bId = b.id !== undefined ? b.id : b.Id;
            const bNum = b.billNumber || b.BillNumber || ('BILL-' + bId);
            const poId = b.purchaseOrderId !== undefined ? b.purchaseOrderId : b.PurchaseOrderId;
            const vendorId = b.vendorId !== undefined ? b.vendorId : b.VendorId;
            const vendorLabel = vendorId ? (vendorMap[vendorId] || `Vendor #${vendorId}`) : 'N/A';
            const billDate = b.billDate || b.BillDate || '';
            const amt = (b.amount !== undefined && b.amount !== null) ? b.amount : ((b.Amount !== undefined && b.Amount !== null) ? b.Amount : 0);
            const status = b.status || b.Status || 'BILLED';
            const actionBtn = (status.toUpperCase() !== 'PAID')
                ? `<button class="btn btn-sm btn-primary" onclick="payVendorBillDemo(${bId})">Pay via Bank (Demo)</button>`
                : `<span class="badge badge-success">PAID</span>`;
            tr.innerHTML = `
                <td><strong>${bNum}</strong></td>
                <td>${poId ? ('PO #' + poId) : 'Direct'}</td>
                <td>${vendorLabel}</td>
                <td>${billDate}</td>
                <td><strong>₹${Number(amt).toLocaleString()}</strong></td>
                <td><span class="badge badge-info">${status}</span></td>
                <td>${actionBtn}</td>
            `;
            tbody.appendChild(tr);
        });
    } catch (err) {
        tbody.innerHTML = '<tr><td colspan="7" style="color: var(--danger-color);">Error loading vendor bills.</td></tr>';
    }
}

async function createBillFromPO(poId, vendorId, amount) {
    if (!confirm(`Generate Vendor Bill for PO #${poId}?`)) return;
    try {
        const billData = {
            purchaseOrderId: poId,
            vendorId: vendorId,
            amount: amount,
            billDate: new Date().toISOString().split('T')[0],
            status: "BILLED"
        };
        const savedBill = await postData('/api/vendorbill/create', billData);
        showAlert(`Vendor Bill ${savedBill.billNumber || savedBill.BillNumber || ('BILL-' + (savedBill.id || savedBill.Id))} generated successfully.`);
        loadPurchases();
        if (typeof loadDashboard === 'function') loadDashboard();
    } catch (err) {
        showAlert('Failed to generate vendor bill: ' + err.message, 'error');
    }
}

async function payVendorBillDemo(id) {
    if (!confirm(`Simulate bank payment for Vendor Bill #${id}? (Demo Mode)`)) return;
    try {
        const res = await postData(`/api/vendorbill/pay/${id}`, {});
        const bNum = (res && typeof res === 'object') ? (res.billNumber || res.BillNumber || ('BILL-' + (res.id || res.Id || id))) : ('BILL-' + id);
        showAlert(`Vendor Bill ${bNum} paid successfully via Bank (Demo).`);
        loadVendorBills();
        if (typeof loadDashboard === 'function') loadDashboard();
        if (typeof loadReports === 'function') loadReports();
    } catch (err) {
        showAlert('Failed to process vendor bill payment: ' + err.message, 'error');
    }
}

