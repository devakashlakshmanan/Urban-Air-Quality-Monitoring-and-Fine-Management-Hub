// Violations & Fines Management

async function loadViolations() {
    const tbody = document.getElementById('violations-table-body');
    if (!tbody) return;
    tbody.innerHTML = '<tr><td colspan="9">Loading violations...</td></tr>';
    try {
        const [violations, plants] = await Promise.all([
            getData('/api/violation/getall'),
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
        if (!Array.isArray(violations) || violations.length === 0) {
            tbody.innerHTML = '<tr><td colspan="9" style="text-align: center;">No environmental violations recorded.</td></tr>';
            return;
        }

        violations.forEach(v => {
            const tr = document.createElement('tr');
            const vId = v.id !== undefined ? v.id : v.Id;
            const tNum = v.ticketNumber || v.TicketNumber || ('VIOL-' + vId);
            const plantId = v.industrialPlantId || v.IndustrialPlantId || '1';
            const plantLabel = plantMap[plantId] || `Plant #${plantId}`;
            const zone = v.environmentalZone || v.EnvironmentalZone || 'Zone';
            const param = v.parameter || v.Parameter || 'PM2.5';
            const measured = (v.measuredValue !== undefined && v.measuredValue !== null) ? v.measuredValue : ((v.MeasuredValue !== undefined && v.MeasuredValue !== null) ? v.MeasuredValue : 0);
            const threshold = (v.thresholdValue !== undefined && v.thresholdValue !== null) ? v.thresholdValue : ((v.ThresholdValue !== undefined && v.ThresholdValue !== null) ? v.ThresholdValue : 0);
            const penalty = (v.penaltyAmount !== undefined && v.penaltyAmount !== null) ? v.penaltyAmount : ((v.PenaltyAmount !== undefined && v.PenaltyAmount !== null) ? v.PenaltyAmount : 0);
            const date = v.violationDate || v.ViolationDate || '';
            const status = v.status || v.Status || 'OPEN';
            const statusClass = status.toLowerCase();
            const actionBtn = (status.toUpperCase() === 'OPEN') 
                ? `<button class="btn btn-sm btn-primary" onclick="createInvoiceFromViolation(${vId}, ${plantId}, ${penalty})">Generate Invoice</button>`
                : `<span style="color: var(--text-light); font-size: 0.8rem;">${status}</span>`;

            tr.innerHTML = `
                <td><strong>${tNum}</strong></td>
                <td>${plantLabel}</td>
                <td>${zone}</td>
                <td>${param}</td>
                <td><strong>${measured}</strong> (Limit: ${threshold})</td>
                <td>₹${Number(penalty).toLocaleString()}</td>
                <td>${date}</td>
                <td><span class="badge badge-${statusClass}">${status}</span></td>
                <td>${actionBtn}</td>
            `;
            tbody.appendChild(tr);
        });

        // Also populate fines and penalties overview table
        const finesTbody = document.getElementById('fines-table-body');
        if (finesTbody) {
            finesTbody.innerHTML = '';
            if (!Array.isArray(violations) || violations.length === 0) {
                finesTbody.innerHTML = '<tr><td colspan="6" style="text-align: center;">No fines recorded.</td></tr>';
            } else {
                violations.forEach(v => {
                    const tr = document.createElement('tr');
                    const vId = v.id !== undefined ? v.id : v.Id;
                    const tNum = v.ticketNumber || v.TicketNumber || ('VIOL-' + vId);
                    const plantId = v.industrialPlantId || v.IndustrialPlantId || '1';
                    const plantLabel = plantMap[plantId] || `Plant #${plantId}`;
                    const zone = v.environmentalZone || v.EnvironmentalZone || 'Zone';
                    const penalty = (v.penaltyAmount !== undefined && v.penaltyAmount !== null) ? v.penaltyAmount : ((v.PenaltyAmount !== undefined && v.PenaltyAmount !== null) ? v.PenaltyAmount : 0);
                    const date = v.violationDate || v.ViolationDate || '';
                    const status = v.status || v.Status || 'OPEN';
                    const statusClass = status.toLowerCase();

                    tr.innerHTML = `
                        <td><strong>${tNum}</strong></td>
                        <td>${plantLabel}</td>
                        <td>${zone}</td>
                        <td><strong>₹${Number(penalty).toLocaleString()}</strong></td>
                        <td>${date}</td>
                        <td><span class="badge badge-${statusClass}">${status}</span></td>
                    `;
                    finesTbody.appendChild(tr);
                });
            }
        }
    } catch (err) {
        tbody.innerHTML = '<tr><td colspan="9" style="color: var(--danger-color);">Error loading violations.</td></tr>';
    }
}

async function createInvoiceFromViolation(violationId, plantId, amount) {
    if (!confirm('Generate customer penalty invoice for this violation ticket?')) return;
    try {
        // Fetch compliance products to find Penalty product id
        const products = await getData('/api/product/getall').catch(() => []);
        const penaltyProduct = (Array.isArray(products) ? products : []).find(p => {
            const name = p.name || p.Name || '';
            return name.toLowerCase().includes('penalty');
        }) || products[0];
        const productId = penaltyProduct ? (penaltyProduct.id !== undefined ? penaltyProduct.id : penaltyProduct.Id) : 1;

        const invoiceData = {
            industrialPlantId: plantId,
            violationTicketId: violationId,
            productId: productId,
            amount: amount,
            status: "UNPAID"
        };

        const savedInvoice = await postData('/api/invoice/create', invoiceData);
        showAlert(`Invoice ${savedInvoice.invoiceNumber || savedInvoice.InvoiceNumber || ('INV-' + (savedInvoice.id || savedInvoice.Id))} created successfully.`);
        loadViolations();
        if (typeof loadInvoices === 'function') loadInvoices();
        if (typeof populateUnpaidInvoicesDropdown === 'function') populateUnpaidInvoicesDropdown();
        if (typeof loadDashboard === 'function') loadDashboard();
        if (typeof loadReports === 'function') loadReports();
    } catch (err) {
        showAlert('Failed to generate customer invoice: ' + err.message, 'error');
    }
}

