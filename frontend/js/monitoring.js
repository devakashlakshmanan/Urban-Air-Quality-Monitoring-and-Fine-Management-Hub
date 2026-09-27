// Air Quality Monitoring & IoT Telemetry Logic

async function loadTelemetryHistory() {
    const tbody = document.getElementById('telemetry-table-body');
    if (!tbody) return;
    tbody.innerHTML = '<tr><td colspan="6">Loading...</td></tr>';
    try {
        const [telemetryList, sensors] = await Promise.all([
            getData('/api/air/telemetry/getall'),
            getData('/api/air/sensors/getall').catch(() => [])
        ]);

        const sensorMap = {};
        if (Array.isArray(sensors)) {
            sensors.forEach(s => {
                const sId = s.id !== undefined ? s.id : s.Id;
                const sName = s.sensorName || s.SensorName || ('Sensor #' + sId);
                const sCode = s.sensorCode || s.SensorCode || '';
                sensorMap[sId] = sCode ? `${sCode} - ${sName}` : sName;
            });
        }

        tbody.innerHTML = '';
        if (!Array.isArray(telemetryList) || telemetryList.length === 0) {
            tbody.innerHTML = '<tr><td colspan="6" style="text-align: center;">No telemetry records yet.</td></tr>';
            return;
        }
        telemetryList.slice(-15).reverse().forEach(t => {
            const tr = document.createElement('tr');
            const tId = t.id !== undefined ? t.id : t.Id;
            const sensorId = t.sensorId !== undefined ? t.sensorId : t.SensorId;
            const sensorLabel = sensorId ? (sensorMap[sensorId] || `Sensor #${sensorId}`) : 'N/A';
            const zone = t.environmentalZone || t.EnvironmentalZone || 'N/A';
            const pm25 = (t.pm25 !== undefined && t.pm25 !== null) ? t.pm25 : ((t.Pm25 !== undefined && t.Pm25 !== null) ? t.Pm25 : 0);
            const co2 = (t.co2 !== undefined && t.co2 !== null) ? t.co2 : ((t.Co2 !== undefined && t.Co2 !== null) ? t.Co2 : 0);
            const recordedAt = t.recordedAt || t.RecordedAt || '';

            tr.innerHTML = `
                <td>${tId}</td>
                <td><strong>${sensorLabel}</strong></td>
                <td>${zone}</td>
                <td><strong>${pm25} µg/m³</strong></td>
                <td>${co2} ppm</td>
                <td>${recordedAt}</td>
            `;
            tbody.appendChild(tr);
        });

        // Also populate active air sensors grid under Air Monitoring section
        const mSensorsTbody = document.getElementById('monitoring-sensors-table-body');
        if (mSensorsTbody) {
            mSensorsTbody.innerHTML = '';
            if (!Array.isArray(sensors) || sensors.length === 0) {
                mSensorsTbody.innerHTML = '<tr><td colspan="6" style="text-align: center;">No sensors deployed yet.</td></tr>';
            } else {
                sensors.forEach(s => {
                    const tr = document.createElement('tr');
                    const sId = s.id !== undefined ? s.id : s.Id;
                    const sCode = s.sensorCode || s.SensorCode || ('SENS-' + sId);
                    const sName = s.sensorName || s.SensorName || '';
                    const sLoc = s.location || s.Location || '';
                    const sZone = s.environmentalZone || s.EnvironmentalZone || '';
                    const locLabel = sZone ? `${sLoc} (${sZone})` : sLoc;
                    const pm25 = s.pm25Threshold !== undefined ? s.pm25Threshold : (s.Pm25Threshold || 100);
                    const status = s.status || s.Status || 'ACTIVE';
                    const statusBadge = (status.toUpperCase() === 'ACTIVE')
                        ? `<span class="badge badge-success">ACTIVE</span>`
                        : `<span class="badge badge-warning">${status}</span>`;

                    tr.innerHTML = `
                        <td>${sId}</td>
                        <td><strong>${sCode}</strong></td>
                        <td>${sName}</td>
                        <td>${locLabel}</td>
                        <td>${pm25} µg/m³</td>
                        <td>${statusBadge}</td>
                    `;
                    mSensorsTbody.appendChild(tr);
                });
            }
        }
    } catch (err) {
        tbody.innerHTML = '<tr><td colspan="6" style="color: var(--danger-color);">Error loading telemetry.</td></tr>';
    }
}

async function submitTelemetry(e) {
    e.preventDefault();
    const sensorSelect = document.getElementById('telemetry-sensor-id');
    const sensorId = sensorSelect.value ? parseInt(sensorSelect.value) : null;
    const pm25 = parseFloat(document.getElementById('telemetry-pm25').value);
    const co2 = parseFloat(document.getElementById('telemetry-co2').value);
    const zone = document.getElementById('telemetry-zone').value;
    const recordedAt = document.getElementById('telemetry-time').value || new Date().toISOString();

    const data = {
        sensorId: sensorId,
        pm25: pm25,
        co2: co2,
        environmentalZone: zone,
        recordedAt: recordedAt
    };

    const outcomeBox = document.getElementById('telemetry-outcome');

    try {
        const savedTelemetry = await postData('/api/air/telemetry/create', data);
        showAlert('Telemetry submitted successfully.');

        const savedId = savedTelemetry.id !== undefined ? savedTelemetry.id : savedTelemetry.Id;

        // Check backend violation record corresponding to this telemetry
        const violations = await getData('/api/violation/getall').catch(() => []);
        const match = Array.isArray(violations) ? violations.find(v => {
            const telId = v.telemetryId !== undefined ? v.telemetryId : v.TelemetryId;
            return telId === savedId;
        }) : null;

        if (match) {
            const mTicket = match.ticketNumber || match.TicketNumber || ('VIOL-' + (match.id || match.Id));
            const mMeasured = match.measuredValue !== undefined ? match.measuredValue : match.MeasuredValue;
            const mThreshold = match.thresholdValue !== undefined ? match.thresholdValue : match.ThresholdValue;
            const mPenalty = (match.penaltyAmount !== undefined && match.penaltyAmount !== null) ? match.penaltyAmount : ((match.PenaltyAmount !== undefined && match.PenaltyAmount !== null) ? match.PenaltyAmount : 5000);
            const mStatus = match.status || match.Status || 'OPEN';

            outcomeBox.className = 'outcome-box outcome-violation';
            outcomeBox.innerHTML = `
                <h4>⚠️ Emission Violation Detected!</h4>
                <p><strong>Violation Ticket:</strong> ${mTicket}</p>
                <p><strong>Parameter:</strong> PM2.5</p>
                <p><strong>Measured Value:</strong> ${mMeasured} µg/m³</p>
                <p><strong>Allowed Limit:</strong> ${mThreshold} µg/m³</p>
                <p><strong>Regulatory Penalty Amount:</strong> ₹${Number(mPenalty).toLocaleString()}</p>
                <p><strong>Status:</strong> <span class="badge badge-danger">${mStatus}</span></p>
                <p style="font-size: 0.8rem; margin-top: 6px; color: #7f1d1d;">* An automatic fine violation record and accounting journal entry were generated by the backend.</p>
            `;
            outcomeBox.style.display = 'block';
        } else {
            outcomeBox.className = 'outcome-box outcome-safe';
            outcomeBox.innerHTML = `
                <h4>✅ Clean Air Compliance Normal</h4>
                <p>Telemetry recorded successfully.</p>
                <p><strong>PM2.5:</strong> ${pm25} µg/m³ | <strong>CO2:</strong> ${co2} ppm</p>
                <p>No threshold violation detected for this reading.</p>
            `;
            outcomeBox.style.display = 'block';
        }

        loadTelemetryHistory();
        if (typeof loadViolations === 'function') loadViolations();
        if (typeof loadInvoices === 'function') loadInvoices();
        if (typeof populateUnpaidInvoicesDropdown === 'function') populateUnpaidInvoicesDropdown();
        if (typeof loadDashboard === 'function') loadDashboard();
        if (typeof loadReports === 'function') loadReports();
    } catch (err) {
        showAlert('Failed to submit telemetry: ' + err.message, 'error');
    }
}

function handleTelemetrySensorChange() {
    const sensorSelect = document.getElementById('telemetry-sensor-id');
    const selectedOpt = sensorSelect.options[sensorSelect.selectedIndex];
    if (selectedOpt && selectedOpt.getAttribute('data-zone')) {
        document.getElementById('telemetry-zone').value = selectedOpt.getAttribute('data-zone');
    }
}

