// Payments Section (Demo Transaction Only)

async function loadPayments() {
    const tbody = document.getElementById('payments-table-body');
    if (!tbody) return;
    tbody.innerHTML = '<tr><td colspan="6">Loading payment records...</td></tr>';
    try {
        const payments = await getData('/api/payment/getall');
        tbody.innerHTML = '';
        if (payments.length === 0) {
            tbody.innerHTML = '<tr><td colspan="6" style="text-align: center;">No payments recorded yet.</td></tr>';
            return;
        }

        payments.slice().reverse().forEach(p => {
            const tr = document.createElement('tr');
            const ref = p.paymentReference || p.PaymentReference || ('DEMO-PAY-' + (p.id || p.Id));
            const invId = p.customerInvoiceId || p.CustomerInvoiceId || 'N/A';
            const date = p.paymentDate || p.PaymentDate || '';
            const amt = (p.amount !== undefined && p.amount !== null) ? p.amount : ((p.Amount !== undefined && p.Amount !== null) ? p.Amount : 0);
            const mode = p.paymentMode || p.PaymentMode || 'BANK_TRANSFER';
            const status = p.status || p.Status || 'SUCCESS';
            tr.innerHTML = `
                <td><strong>${ref}</strong></td>
                <td>Invoice #${invId}</td>
                <td>${date}</td>
                <td><strong>₹${Number(amt).toLocaleString()}</strong></td>
                <td>${mode}</td>
                <td><span class="badge badge-success">${status}</span></td>
            `;
            tbody.appendChild(tr);
        });
    } catch (err) {
        tbody.innerHTML = '<tr><td colspan="6" style="color: var(--danger-color);">Error loading payments.</td></tr>';
    }
}

async function populateUnpaidInvoicesDropdown() {
    const select = document.getElementById('payment-invoice-id');
    if (!select) return;
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

        const unpaid = (Array.isArray(invoices) ? invoices : []).filter(i => {
            const st = (i.status || i.Status || '').toUpperCase();
            return st !== 'PAID';
        });
        select.innerHTML = '<option value="">Select Unpaid Customer Invoice...</option>';
        unpaid.forEach(inv => {
            const opt = document.createElement('option');
            const invId = inv.id !== undefined ? inv.id : inv.Id;
            const invNum = inv.invoiceNumber || inv.InvoiceNumber || ('INV-' + invId);
            const plantId = inv.industrialPlantId || inv.IndustrialPlantId || '1';
            const plantLabel = plantMap[plantId] || `Plant #${plantId}`;
            const amt = (inv.amount !== undefined && inv.amount !== null) ? inv.amount : ((inv.Amount !== undefined && inv.Amount !== null) ? inv.Amount : 0);

            opt.value = invId;
            opt.setAttribute('data-amount', amt);
            opt.textContent = `${invNum} (${plantLabel}) - ₹${Number(amt).toLocaleString()}`;
            select.appendChild(opt);
        });

        if (select.options.length > 1 && !select.value) {
            select.selectedIndex = 1;
            handlePaymentInvoiceSelectChange();
        }
    } catch (err) {
        console.error('Failed to load unpaid invoices:', err);
    }
}

function handlePaymentInvoiceSelectChange() {
    const select = document.getElementById('payment-invoice-id');
    if (!select) return;
    const selectedOpt = select.options[select.selectedIndex];
    if (selectedOpt && selectedOpt.getAttribute('data-amount')) {
        document.getElementById('payment-amount').value = selectedOpt.getAttribute('data-amount');
    }
}

async function processDemoPayment(e) {
    e.preventDefault();
    const invoiceId = parseInt(document.getElementById('payment-invoice-id').value);
    const amount = parseFloat(document.getElementById('payment-amount').value);
    const mode = document.getElementById('payment-mode').value;

    const data = {
        customerInvoiceId: invoiceId,
        amount: amount,
        paymentMode: mode
    };

    const resultBox = document.getElementById('payment-result-box');

    try {
        const res = await postData('/api/payment/create', data);
        showAlert('Demo fine payment processed successfully.');

        const ref = res.paymentReference || res.PaymentReference || ('DEMO-PAY-' + (res.id || res.Id));
        const invId = res.customerInvoiceId || res.CustomerInvoiceId || invoiceId;
        const resAmt = (res.amount !== undefined && res.amount !== null) ? res.amount : ((res.Amount !== undefined && res.Amount !== null) ? res.Amount : amount);
        const resMode = res.paymentMode || res.PaymentMode || mode;
        const resStatus = res.status || res.Status || 'SUCCESS';

        resultBox.className = 'outcome-box outcome-safe';
        resultBox.innerHTML = `
            <h4>💳 Payment Successful (Demo Transaction)</h4>
            <p><strong>Payment Reference:</strong> ${ref}</p>
            <p><strong>Customer Invoice #${invId}:</strong> <span class="badge badge-success">PAID</span></p>
            <p><strong>Amount Settled:</strong> ₹${Number(resAmt).toLocaleString()}</p>
            <p><strong>Payment Mode:</strong> ${resMode} (Simulated Bank Transfer)</p>
            <p><strong>Status:</strong> <span class="badge badge-success">${resStatus}</span></p>
            <p style="font-size: 0.8rem; margin-top: 6px; color: #166534;">* General Ledger debit (Cash/Bank) and credit (Accounts Receivable) records created automatically in the backend.</p>
        `;
        resultBox.style.display = 'block';

        document.getElementById('payment-form').reset();
        loadPayments();
        populateUnpaidInvoicesDropdown();
        if (typeof loadInvoices === 'function') loadInvoices();
        if (typeof loadViolations === 'function') loadViolations();
        if (typeof loadDashboard === 'function') loadDashboard();
        if (typeof loadReports === 'function') loadReports();
    } catch (err) {
        showAlert('Failed to execute demo payment: ' + err.message, 'error');
    }
}
