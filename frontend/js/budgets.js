// Environmental Budget Management

async function loadBudgets() {
    const tbody = document.getElementById('budgets-table-body');
    if (!tbody) return;
    tbody.innerHTML = '<tr><td colspan="8">Loading environmental budgets...</td></tr>';
    try {
        const budgets = await getData('/api/budget/getall');
        tbody.innerHTML = '';
        if (!Array.isArray(budgets) || budgets.length === 0) {
            tbody.innerHTML = '<tr><td colspan="8" style="text-align: center;">No environmental budgets found.</td></tr>';
            return;
        }

        budgets.forEach(b => {
            const bId = b.id !== undefined ? b.id : b.Id;
            const name = b.budgetName || b.BudgetName || '';
            const zone = b.environmentalZone || b.EnvironmentalZone || '';
            const budgetAmt = (b.budgetAmount !== undefined && b.budgetAmount !== null) ? b.budgetAmount : ((b.BudgetAmount !== undefined && b.BudgetAmount !== null) ? b.BudgetAmount : 0);
            const actualExp = (b.actualExpenditure !== undefined && b.actualExpenditure !== null) ? b.actualExpenditure : ((b.ActualExpenditure !== undefined && b.ActualExpenditure !== null) ? b.ActualExpenditure : 0);
            const fineColl = (b.fineCollections !== undefined && b.fineCollections !== null) ? b.fineCollections : ((b.FineCollections !== undefined && b.FineCollections !== null) ? b.FineCollections : 0);
            const variance = budgetAmt - actualExp;
            const fiscalYear = b.fiscalYear || b.FiscalYear || '2026-2027';

            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td><strong>${name}</strong></td>
                <td>${zone}</td>
                <td>₹${Number(budgetAmt).toLocaleString()}</td>
                <td>₹${Number(actualExp).toLocaleString()}</td>
                <td>₹${Number(fineColl).toLocaleString()}</td>
                <td><strong style="color: ${variance >= 0 ? 'var(--success-color)' : 'var(--danger-color)'};">₹${Number(variance).toLocaleString()}</strong></td>
                <td>${fiscalYear}</td>
                <td>
                    <button class="btn btn-sm btn-secondary" onclick="viewBudgetVarianceReport(${bId})">Report</button>
                    <button class="btn btn-sm btn-danger" onclick="deleteBudget(${bId})">Delete</button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    } catch (err) {
        tbody.innerHTML = '<tr><td colspan="8" style="color: var(--danger-color);">Error loading budgets.</td></tr>';
    }
}

async function saveBudget(e) {
    e.preventDefault();
    const data = {
        budgetName: document.getElementById('budget-name').value.trim(),
        environmentalZone: document.getElementById('budget-zone').value.trim(),
        budgetAmount: parseFloat(document.getElementById('budget-amount').value) || 0.0,
        actualExpenditure: parseFloat(document.getElementById('budget-expenditure').value) || 0.0,
        fineCollections: parseFloat(document.getElementById('budget-fines').value) || 0.0,
        fiscalYear: document.getElementById('budget-year').value.trim()
    };

    try {
        await postData('/api/budget/create', data);
        showAlert('Environmental Budget created successfully.');
        document.getElementById('budget-form').reset();
        loadBudgets();
        if (typeof loadReports === 'function') loadReports();
        if (typeof loadDashboard === 'function') loadDashboard();
    } catch (err) {
        showAlert('Failed to save budget: ' + err.message, 'error');
    }
}

async function viewBudgetVarianceReport(id) {
    const reportBox = document.getElementById('budget-report-modal');
    try {
        const rep = await getData(`/api/budget/report/${id}`);
        const bName = rep.budgetName || rep.BudgetName || '';
        const bZone = rep.environmentalZone || rep.EnvironmentalZone || '';
        const bYear = rep.fiscalYear || rep.FiscalYear || '';
        const bAlloc = (rep.budgetAmount !== undefined && rep.budgetAmount !== null) ? rep.budgetAmount : ((rep.BudgetAmount !== undefined && rep.BudgetAmount !== null) ? rep.BudgetAmount : 0);
        const bExp = (rep.actualExpenditure !== undefined && rep.actualExpenditure !== null) ? rep.actualExpenditure : ((rep.ActualExpenditure !== undefined && rep.ActualExpenditure !== null) ? rep.ActualExpenditure : 0);
        const bFine = (rep.fineCollections !== undefined && rep.fineCollections !== null) ? rep.fineCollections : ((rep.FineCollections !== undefined && rep.FineCollections !== null) ? rep.FineCollections : 0);
        const bVar = rep.variance !== undefined ? rep.variance : (bAlloc - bExp);

        reportBox.className = 'outcome-box outcome-safe';
        reportBox.innerHTML = `
            <h4>📊 Budget Variance Report — ${bName}</h4>
            <p><strong>Environmental Zone:</strong> ${bZone} (${bYear})</p>
            <div style="margin-top: 10px; display: grid; grid-template-columns: repeat(2, 1fr); gap: 8px;">
                <p><strong>Allocated Budget:</strong> ₹${Number(bAlloc).toLocaleString()}</p>
                <p><strong>Actual Expenditure:</strong> ₹${Number(bExp).toLocaleString()}</p>
                <p><strong>Fine Collections:</strong> ₹${Number(bFine).toLocaleString()}</p>
                <p><strong>Net Variance (Surplus/Deficit):</strong> <strong style="color: ${bVar >= 0 ? '#15803d' : '#b91c1c'};">₹${Number(bVar).toLocaleString()}</strong></p>
            </div>
            <button class="btn btn-sm btn-secondary" style="margin-top: 10px;" onclick="document.getElementById('budget-report-modal').style.display='none'">Close</button>
        `;
        reportBox.style.display = 'block';
    } catch (err) {
        showAlert('Failed to fetch budget report: ' + err.message, 'error');
    }
}

async function deleteBudget(id) {
    if (!confirm('Are you sure you want to delete this budget record?')) return;
    try {
        await deleteData(`/api/budget/deletebyid/${id}`);
        showAlert('Budget record deleted successfully.');
        loadBudgets();
        if (typeof loadReports === 'function') loadReports();
        if (typeof loadDashboard === 'function') loadDashboard();
    } catch (err) {
        showAlert('Failed to delete budget: ' + err.message, 'error');
    }
}

