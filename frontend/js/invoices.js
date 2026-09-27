// Customer Invoices Management

async function loadInvoices() {
    const tbody = document.getElementById('invoices-table-body');
    if (!tbody) return;
    tbody.innerHTML = '<tr><td colspan="8">Loading customer invoices...</td></tr>';
    try {
        const [invoices, plants] = await Promise.all([
            getData('/api/invoice/getall'),
            getData('/api/industrialplant/getall').catch(() => [])
        ]);

        const plantMap = {};
        if (Array.isArray(plants)) {
            plants.forEach(p => {
                const pId = p.id !== undefined ? p.id : p.Id;
                const pName = p.name || p.Name || ('Plant #' + pId);
                const pCode = p.plantCode || p.PlantCode || '';
                plantMap[pId] = pCode ? `${pName} (${pCode})` : pName;
            });
        }

        tbody.innerHTML = '';
        if (!Array.isArray(invoices) || invoices.length === 0) {
            tbody.innerHTML = '<tr><td colspan="8" style="text-align: center;">No invoices generated yet.</td></tr>';
            return;
        }

        invoices.forEach(inv => {
            const tr = document.createElement('tr');
            const invId = inv.id !== undefined ? inv.id : inv.Id;
            const invNum = inv.invoiceNumber || inv.InvoiceNumber || ('INV-' + invId);
            const plantId = inv.industrialPlantId || inv.IndustrialPlantId || '1';
            const plantLabel = plantMap[plantId] || `Plant #${plantId}`;
            const ticketId = inv.violationTicketId || inv.ViolationTicketId;
            const invDate = inv.invoiceDate || inv.InvoiceDate || '';
            const dueDate = inv.dueDate || inv.DueDate || '';
            const amt = (inv.amount !== undefined && inv.amount !== null) ? inv.amount : ((inv.Amount !== undefined && inv.Amount !== null) ? inv.Amount : 0);
            const status = inv.status || inv.Status || 'UNPAID';
            const statusClass = status.toLowerCase();
            const actionBtn = (status.toUpperCase() !== 'PAID')
                ? `<button class="btn btn-sm btn-primary" onclick="payFineDirect(${invId}, ${amt})">Pay Fine (Demo)</button>`
                : `<span class="badge badge-success">PAID</span>`;

            tr.innerHTML = `
                <td><strong>${invNum}</strong></td>
                <td>${plantLabel}</td>
                <td>${ticketId ? ('Ticket #' + ticketId) : 'Standard'}</td>
                <td>${invDate}</td>
                <td>${dueDate}</td>
                <td><strong>₹${Number(amt).toLocaleString()}</strong></td>
                <td><span class="badge badge-${statusClass}">${status}</span></td>
                <td>${actionBtn}</td>
            `;
            tbody.appendChild(tr);
        });
    } catch (err) {
        tbody.innerHTML = '<tr><td colspan="8" style="color: var(--danger-color);">Error loading invoices.</td></tr>';
    }
}

async function payFineDirect(invoiceId, amount) {
    if (!confirm(`Simulate fine payment of ₹${Number(amount).toLocaleString()} for Invoice #${invoiceId}? (Demo Mode)`)) return;
    try {
        const paymentData = {
            customerInvoiceId: invoiceId,
            amount: amount,
            paymentMode: "BANK_TRANSFER"
        };
        const paymentRes = await postData('/api/payment/create', paymentData);
        showAlert(`Demo Payment Successful! Ref: ${paymentRes.paymentReference || paymentRes.PaymentReference || 'DEMO-PAY'}`);
        
        loadInvoices();
        if (typeof loadPayments === 'function') loadPayments();
        if (typeof loadViolations === 'function') loadViolations();
        if (typeof populateUnpaidInvoicesDropdown === 'function') populateUnpaidInvoicesDropdown();
        if (typeof loadDashboard === 'function') loadDashboard();
        if (typeof loadReports === 'function') loadReports();
    } catch (err) {
        showAlert('Demo payment failed: ' + err.message, 'error');
    }
}

