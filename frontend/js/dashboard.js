// Dashboard Logic
async function loadDashboard() {
    try {
        const [plants, sensors, telemetries, violations, invoices, payments] = await Promise.all([
            getData('/api/industrialplant/getall').catch(() => []),
            getData('/api/air/sensors/getall').catch(() => []),
            getData('/api/air/telemetry/getall').catch(() => []),
            getData('/api/violation/getall').catch(() => []),
            getData('/api/invoice/getall').catch(() => []),
            getData('/api/payment/getall').catch(() => [])
        ]);

        const plantsList = Array.isArray(plants) ? plants : [];
        const sensorsList = Array.isArray(sensors) ? sensors : [];
        const telemetriesList = Array.isArray(telemetries) ? telemetries : [];
        const violationsList = Array.isArray(violations) ? violations : [];
        const invoicesList = Array.isArray(invoices) ? invoices : [];
        const paymentsList = Array.isArray(payments) ? payments : [];

        document.getElementById('dash-plants-count').innerText = plantsList.length;
        document.getElementById('dash-sensors-count').innerText = sensorsList.length;
        document.getElementById('dash-telemetry-count').innerText = telemetriesList.length;
        document.getElementById('dash-violations-count').innerText = violationsList.length;
        
        const unpaidInvoices = invoicesList.filter(inv => {
            const st = (inv.status || inv.Status || '').toUpperCase();
            return st !== 'PAID';
        }).length;
        document.getElementById('dash-unpaid-invoices').innerText = unpaidInvoices;

        const totalFineCollected = paymentsList
            .filter(p => {
                const st = (p.status || p.Status || '').toUpperCase();
                return st === 'SUCCESS' || st === 'PAID';
            })
            .reduce((sum, p) => {
                const amt = (p.amount !== undefined && p.amount !== null) ? p.amount : ((p.Amount !== undefined && p.Amount !== null) ? p.Amount : 0);
                return sum + amt;
            }, 0);
        document.getElementById('dash-fine-collections').innerText = `₹${Number(totalFineCollected).toLocaleString()}`;

        // Populate recent violations in dashboard
        const recentViolationsTbody = document.getElementById('dash-recent-violations');
        if (recentViolationsTbody) {
            recentViolationsTbody.innerHTML = '';
            const recent = violationsList.slice(-5).reverse();
            if (recent.length === 0) {
                recentViolationsTbody.innerHTML = '<tr><td colspan="5" style="text-align: center; color: var(--text-secondary);">No violations recorded yet.</td></tr>';
            } else {
                recent.forEach(v => {
                    const row = document.createElement('tr');
                    const vId = v.id !== undefined ? v.id : v.Id;
                    const ticketNo = v.ticketNumber || v.TicketNumber || ('VIOL-' + vId);
                    const zone = v.environmentalZone || v.EnvironmentalZone || 'Industrial Sector Air Zone 5';
                    const param = v.parameter || v.Parameter || 'PM2.5';
                    const measured = (v.measuredValue !== undefined && v.measuredValue !== null) ? v.measuredValue : ((v.MeasuredValue !== undefined && v.MeasuredValue !== null) ? v.MeasuredValue : 0);
                    const threshold = (v.thresholdValue !== undefined && v.thresholdValue !== null) ? v.thresholdValue : ((v.ThresholdValue !== undefined && v.ThresholdValue !== null) ? v.ThresholdValue : 0);
                    const penalty = (v.penaltyAmount !== undefined && v.penaltyAmount !== null) ? v.penaltyAmount : ((v.PenaltyAmount !== undefined && v.PenaltyAmount !== null) ? v.PenaltyAmount : 0);
                    const status = v.status || v.Status || 'OPEN';
                    const statusClass = status.toLowerCase();

                    row.innerHTML = `
                        <td><strong>${ticketNo}</strong></td>
                        <td>${zone}</td>
                        <td>${param}: ${measured} µg/m³ (Limit: ${threshold})</td>
                        <td>₹${Number(penalty).toLocaleString()}</td>
                        <td><span class="badge badge-${statusClass}">${status}</span></td>
                    `;
                    recentViolationsTbody.appendChild(row);
                });
            }
        }
    } catch (error) {
        console.error('Failed to load dashboard metrics:', error);
    }
}

