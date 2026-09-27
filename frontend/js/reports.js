// Reports Integration

async function loadReports() {
    await Promise.all([
        loadZoneReport(),
        loadBudgetReportSummary(),
        loadFinancialSummary(),
        loadBalanceSheet()
    ]);
}

async function loadZoneReport() {
    const tbody = document.getElementById('report-zone-table-body');
    if (!tbody) return;
    tbody.innerHTML = '<tr><td colspan="6">Loading zone report...</td></tr>';
    try {
        const zoneReports = await getData('/api/report/zone');
        tbody.innerHTML = '';
        if (!Array.isArray(zoneReports) || zoneReports.length === 0) {
            tbody.innerHTML = '<tr><td colspan="6" style="text-align: center;">No zone reports available.</td></tr>';
            return;
        }

        zoneReports.forEach(r => {
            const tr = document.createElement('tr');
            const zone = r.zone || r.Zone || '';
            const sCount = r.sensorCount !== undefined ? r.sensorCount : (r.SensorCount || 0);
            const tCount = r.telemetryCount !== undefined ? r.telemetryCount : (r.TelemetryCount || 0);
            const vCount = r.violationCount !== undefined ? r.violationCount : (r.ViolationCount || 0);
            const avgPm = (r.averagePm25 !== undefined && r.averagePm25 !== null) ? r.averagePm25 : ((r.AveragePm25 !== undefined && r.AveragePm25 !== null) ? r.AveragePm25 : 0);
            const maxPm = (r.maxPm25 !== undefined && r.maxPm25 !== null) ? r.maxPm25 : ((r.MaxPm25 !== undefined && r.MaxPm25 !== null) ? r.MaxPm25 : 0);

            tr.innerHTML = `
                <td><strong>${zone}</strong></td>
                <td>${sCount}</td>
                <td>${tCount}</td>
                <td><strong style="color: ${vCount > 0 ? 'var(--danger-color)' : 'inherit'};">${vCount}</strong></td>
                <td>${Number(avgPm).toFixed(1)} µg/m³</td>
                <td>${Number(maxPm).toFixed(1)} µg/m³</td>
            `;
            tbody.appendChild(tr);
        });
    } catch (err) {
        tbody.innerHTML = '<tr><td colspan="6" style="color: var(--danger-color);">Error loading zone report.</td></tr>';
    }
}

async function loadBudgetReportSummary() {
    const tbody = document.getElementById('report-budget-table-body');
    if (!tbody) return;
    tbody.innerHTML = '<tr><td colspan="6">Loading budget report...</td></tr>';
    try {
        const budgetReports = await getData('/api/report/budget');
        tbody.innerHTML = '';
        if (!Array.isArray(budgetReports) || budgetReports.length === 0) {
            tbody.innerHTML = '<tr><td colspan="6" style="text-align: center;">No budget summaries available.</td></tr>';
            return;
        }

        budgetReports.forEach(b => {
            const zone = b.zone || b.EnvironmentalZone || b.Zone || '';
            const name = b.budgetName || b.BudgetName || '';
            const year = b.fiscalYear || b.FiscalYear || '';
            const budgetAmt = (b.budgetAmount !== undefined && b.budgetAmount !== null) ? b.budgetAmount : ((b.BudgetAmount !== undefined && b.BudgetAmount !== null) ? b.BudgetAmount : 0);
            const actualExp = (b.actualExpenditure !== undefined && b.actualExpenditure !== null) ? b.actualExpenditure : ((b.ActualExpenditure !== undefined && b.ActualExpenditure !== null) ? b.ActualExpenditure : 0);
            const fineColl = (b.fineCollections !== undefined && b.fineCollections !== null) ? b.fineCollections : ((b.FineCollections !== undefined && b.FineCollections !== null) ? b.FineCollections : 0);
            const variance = b.variance !== undefined ? b.variance : (b.Variance !== undefined ? b.Variance : (budgetAmt - actualExp));

            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td><strong>${zone}</strong></td>
                <td>${name} (${year})</td>
                <td>₹${Number(budgetAmt).toLocaleString()}</td>
                <td>₹${Number(actualExp).toLocaleString()}</td>
                <td>₹${Number(fineColl).toLocaleString()}</td>
                <td><strong style="color: ${variance >= 0 ? 'var(--success-color)' : 'var(--danger-color)'};">₹${Number(variance).toLocaleString()}</strong></td>
            `;
            tbody.appendChild(tr);
        });
    } catch (err) {
        tbody.innerHTML = '<tr><td colspan="6" style="color: var(--danger-color);">Error loading budget summaries.</td></tr>';
    }
}

async function loadFinancialSummary() {
    try {
        const fin = await getData('/api/report/financial');
        const fineRev = fin.environmentalFineRevenue !== undefined ? fin.environmentalFineRevenue : (fin.EnvironmentalFineRevenue || 0);
        const licRev = fin.licensingRevenue !== undefined ? fin.licensingRevenue : (fin.LicensingRevenue || 0);
        const calExp = fin.calibrationExpenses !== undefined ? fin.calibrationExpenses : (fin.CalibrationExpenses || 0);
        const gridExp = fin.gridCommunicationExpenses !== undefined ? fin.gridCommunicationExpenses : (fin.GridCommunicationExpenses || 0);

        document.getElementById('fin-fine-rev').innerText = `₹${Number(fineRev).toLocaleString()}`;
        document.getElementById('fin-license-rev').innerText = `₹${Number(licRev).toLocaleString()}`;
        document.getElementById('fin-calib-exp').innerText = `₹${Number(calExp).toLocaleString()}`;
        document.getElementById('fin-grid-exp').innerText = `₹${Number(gridExp).toLocaleString()}`;
    } catch (err) {
        console.error('Failed to load financial summary:', err);
    }
}

async function loadBalanceSheet() {
    try {
        const bs = await getData('/api/report/balancesheet');
        const assets = bs.totalAssets !== undefined ? bs.totalAssets : (bs.TotalAssets || 0);
        const liab = bs.totalLiabilities !== undefined ? bs.totalLiabilities : (bs.TotalLiabilities || 0);
        const inc = bs.totalIncome !== undefined ? bs.totalIncome : (bs.TotalIncome || 0);
        const exp = bs.totalExpenses !== undefined ? bs.totalExpenses : (bs.TotalExpenses || 0);
        const netPos = bs.netPosition !== undefined ? bs.netPosition : (bs.NetPosition !== undefined ? bs.NetPosition : (assets - liab));

        document.getElementById('bs-assets').innerText = `₹${Number(assets).toLocaleString()}`;
        document.getElementById('bs-liabilities').innerText = `₹${Number(liab).toLocaleString()}`;
        document.getElementById('bs-income').innerText = `₹${Number(inc).toLocaleString()}`;
        document.getElementById('bs-expenses').innerText = `₹${Number(exp).toLocaleString()}`;
        const netEl = document.getElementById('bs-net-position');
        netEl.innerText = `₹${Number(netPos).toLocaleString()}`;
        netEl.style.color = netPos >= 0 ? 'var(--success-color)' : 'var(--danger-color)';
    } catch (err) {
        console.error('Failed to load balance sheet:', err);
    }
}

