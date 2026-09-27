// Master Data Management

function initMasterDataTabs() {
    const tabBtns = document.querySelectorAll('.tab-btn[data-master-tab]');
    tabBtns.forEach(btn => {
        btn.addEventListener('click', () => {
            const route = btn.getAttribute('data-route');
            if (route && window.router) {
                window.router.navigate(route);
                return;
            }
            tabBtns.forEach(b => b.classList.remove('active'));
            document.querySelectorAll('.master-tab-content').forEach(c => c.classList.remove('active'));

            btn.classList.add('active');
            const targetId = btn.getAttribute('data-master-tab');
            const targetContent = document.getElementById(targetId);
            if (targetContent) {
                targetContent.classList.add('active');
            }
        });
    });
}

// 1. Industrial Plants
async function loadIndustrialPlants() {
    const tbody = document.getElementById('plants-table-body');
    if (!tbody) return;
    tbody.innerHTML = '<tr><td colspan="7">Loading...</td></tr>';
    try {
        const plants = await getData('/api/industrialplant/getall');
        tbody.innerHTML = '';
        if (!Array.isArray(plants) || plants.length === 0) {
            tbody.innerHTML = '<tr><td colspan="7" style="text-align: center;">No plants registered yet.</td></tr>';
            return;
        }
        plants.forEach(p => {
            const tr = document.createElement('tr');
            const pId = p.id !== undefined ? p.id : p.Id;
            const name = p.name || p.Name || '';
            const code = p.plantCode || p.PlantCode || '';
            const type = p.industryType || p.IndustryType || '';
            const zone = p.environmentalZone || p.EnvironmentalZone || '';
            const contact = p.contactPerson || p.ContactPerson || '';
            const phone = p.phone || p.Phone || '';

            tr.innerHTML = `
                <td>${pId}</td>
                <td><strong>${name}</strong></td>
                <td>${code}</td>
                <td>${type}</td>
                <td>${zone}</td>
                <td>${contact}${phone ? ` (${phone})` : ''}</td>
                <td>
                    <button class="btn btn-sm btn-danger" onclick="deleteIndustrialPlant(${pId})">Delete</button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    } catch (err) {
        tbody.innerHTML = '<tr><td colspan="7" style="color: var(--danger-color);">Error loading plants.</td></tr>';
    }
}

async function saveIndustrialPlant(e) {
    e.preventDefault();
    const data = {
        name: document.getElementById('plant-name').value.trim(),
        plantCode: document.getElementById('plant-code').value.trim(),
        industryType: document.getElementById('plant-type').value.trim(),
        address: document.getElementById('plant-address').value.trim(),
        contactPerson: document.getElementById('plant-contact').value.trim(),
        phone: document.getElementById('plant-phone').value.trim(),
        email: document.getElementById('plant-email').value.trim(),
        environmentalZone: document.getElementById('plant-zone').value.trim(),
        active: document.getElementById('plant-active').checked
    };
    try {
        await postData('/api/industrialplant/create', data);
        showAlert('Industrial Plant created successfully.');
        document.getElementById('plant-form').reset();
        loadIndustrialPlants();
        loadDropdowns();
        if (typeof loadDashboard === 'function') loadDashboard();
    } catch (err) {
        showAlert('Failed to create Industrial Plant: ' + err.message, 'error');
    }
}

async function deleteIndustrialPlant(id) {
    if (!confirm('Are you sure you want to delete this plant?')) return;
    try {
        await deleteData(`/api/industrialplant/deletebyid/${id}`);
        showAlert('Industrial Plant deleted successfully.');
        loadIndustrialPlants();
        loadDropdowns();
        if (typeof loadDashboard === 'function') loadDashboard();
    } catch (err) {
        showAlert('Failed to delete Industrial Plant: ' + err.message, 'error');
    }
}

// 2. Sensor Vendors
async function loadSensorVendors() {
    const tbody = document.getElementById('vendors-table-body');
    if (!tbody) return;
    tbody.innerHTML = '<tr><td colspan="6">Loading...</td></tr>';
    try {
        const vendors = await getData('/api/sensorvendor/getall');
        tbody.innerHTML = '';
        if (!Array.isArray(vendors) || vendors.length === 0) {
            tbody.innerHTML = '<tr><td colspan="6" style="text-align: center;">No vendors registered yet.</td></tr>';
            return;
        }
        vendors.forEach(v => {
            const tr = document.createElement('tr');
            const vId = v.id !== undefined ? v.id : v.Id;
            const name = v.name || v.Name || '';
            const code = v.vendorCode || v.VendorCode || '';
            const contact = v.contactPerson || v.ContactPerson || '';
            const email = v.email || v.Email || '';
            const phone = v.phone || v.Phone || '';

            tr.innerHTML = `
                <td>${vId}</td>
                <td><strong>${name}</strong></td>
                <td>${code}</td>
                <td>${contact}${email ? ` (${email})` : ''}</td>
                <td>${phone}</td>
                <td>
                    <button class="btn btn-sm btn-danger" onclick="deleteSensorVendor(${vId})">Delete</button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    } catch (err) {
        tbody.innerHTML = '<tr><td colspan="6" style="color: var(--danger-color);">Error loading vendors.</td></tr>';
    }
}

async function saveSensorVendor(e) {
    e.preventDefault();
    const data = {
        name: document.getElementById('vendor-name').value.trim(),
        vendorCode: document.getElementById('vendor-code').value.trim(),
        contactPerson: document.getElementById('vendor-contact').value.trim(),
        phone: document.getElementById('vendor-phone').value.trim(),
        email: document.getElementById('vendor-email').value.trim(),
        address: document.getElementById('vendor-address').value.trim(),
        active: document.getElementById('vendor-active').checked
    };

    try {
        await postData('/api/sensorvendor/create', data);
        showAlert('Sensor Vendor created successfully.');
        document.getElementById('vendor-form').reset();
        loadSensorVendors();
        loadDropdowns();
    } catch (err) {
        showAlert('Failed to create Sensor Vendor: ' + err.message, 'error');
    }
}

async function deleteSensorVendor(id) {
    if (!confirm('Are you sure you want to delete this vendor?')) return;
    try {
        await deleteData(`/api/sensorvendor/deletebyid/${id}`);
        showAlert('Sensor Vendor deleted successfully.');
        loadSensorVendors();
        loadDropdowns();
    } catch (err) {
        showAlert('Failed to delete vendor: ' + err.message, 'error');
    }
}

// 3. Compliance Products
async function loadComplianceProducts() {
    const tbody = document.getElementById('products-table-body');
    if (!tbody) return;
    tbody.innerHTML = '<tr><td colspan="6">Loading...</td></tr>';
    try {
        const products = await getData('/api/product/getall');
        tbody.innerHTML = '';
        if (!Array.isArray(products) || products.length === 0) {
            tbody.innerHTML = '<tr><td colspan="6" style="text-align: center;">No compliance products registered yet.</td></tr>';
            return;
        }
        products.forEach(pr => {
            const tr = document.createElement('tr');
            const prId = pr.id !== undefined ? pr.id : pr.Id;
            const name = pr.name || pr.Name || '';
            const code = pr.code || pr.Code || '';
            const type = pr.productType || pr.ProductType || '';
            const price = (pr.unitPrice !== undefined && pr.unitPrice !== null) ? pr.unitPrice : ((pr.UnitPrice !== undefined && pr.UnitPrice !== null) ? pr.UnitPrice : 0);

            tr.innerHTML = `
                <td>${prId}</td>
                <td><strong>${name}</strong></td>
                <td>${code}</td>
                <td>${type}</td>
                <td>₹${Number(price).toLocaleString()}</td>
                <td>
                    <button class="btn btn-sm btn-danger" onclick="deleteProduct(${prId})">Delete</button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    } catch (err) {
        tbody.innerHTML = '<tr><td colspan="6" style="color: var(--danger-color);">Error loading products.</td></tr>';
    }
}

async function saveComplianceProduct(e) {
    e.preventDefault();
    const data = {
        name: document.getElementById('product-name').value.trim(),
        code: document.getElementById('product-code').value.trim(),
        productType: document.getElementById('product-type').value,
        description: document.getElementById('product-description').value.trim(),
        unitPrice: parseFloat(document.getElementById('product-price').value) || 0.0,
        active: document.getElementById('product-active').checked
    };

    try {
        await postData('/api/product/create', data);
        showAlert('Compliance Product created successfully.');
        document.getElementById('product-form').reset();
        loadComplianceProducts();
        loadDropdowns();
    } catch (err) {
        showAlert('Failed to create Compliance Product: ' + err.message, 'error');
    }
}

async function deleteProduct(id) {
    if (!confirm('Are you sure you want to delete this product?')) return;
    try {
        await deleteData(`/api/product/deletebyid/${id}`);
        showAlert('Product deleted successfully.');
        loadComplianceProducts();
        loadDropdowns();
    } catch (err) {
        showAlert('Failed to delete product: ' + err.message, 'error');
    }
}

// 4. Accounts
async function loadAccounts() {
    const tbody = document.getElementById('accounts-table-body');
    if (!tbody) return;
    tbody.innerHTML = '<tr><td colspan="6">Loading...</td></tr>';
    try {
        const accounts = await getData('/api/account/getall');
        tbody.innerHTML = '';
        if (!Array.isArray(accounts) || accounts.length === 0) {
            tbody.innerHTML = '<tr><td colspan="6" style="text-align: center;">No accounts configured yet.</td></tr>';
            return;
        }
        accounts.forEach(acc => {
            const tr = document.createElement('tr');
            const accId = acc.id !== undefined ? acc.id : acc.Id;
            const code = acc.accountCode || acc.AccountCode || '';
            const name = acc.accountName || acc.AccountName || '';
            const type = acc.accountType || acc.AccountType || '';
            const bal = (acc.balance !== undefined && acc.balance !== null) ? acc.balance : ((acc.Balance !== undefined && acc.Balance !== null) ? acc.Balance : 0);

            tr.innerHTML = `
                <td>${accId}</td>
                <td><strong>${code}</strong></td>
                <td>${name}</td>
                <td><span class="badge badge-info">${type}</span></td>
                <td>₹${Number(bal).toLocaleString()}</td>
                <td>
                    <button class="btn btn-sm btn-danger" onclick="deleteAccount(${accId})">Delete</button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    } catch (err) {
        tbody.innerHTML = '<tr><td colspan="6" style="color: var(--danger-color);">Error loading accounts.</td></tr>';
    }
}

async function saveAccount(e) {
    e.preventDefault();
    const data = {
        accountCode: document.getElementById('acc-code').value.trim(),
        accountName: document.getElementById('acc-name').value.trim(),
        accountType: document.getElementById('acc-type').value,
        description: document.getElementById('acc-desc').value.trim(),
        balance: parseFloat(document.getElementById('acc-balance').value) || 0.0,
        active: document.getElementById('acc-active').checked
    };

    try {
        await postData('/api/account/create', data);
        showAlert('Account created successfully.');
        document.getElementById('account-form').reset();
        loadAccounts();
    } catch (err) {
        showAlert('Failed to create Account: ' + err.message, 'error');
    }
}

async function deleteAccount(id) {
    if (!confirm('Are you sure you want to delete this account?')) return;
    try {
        await deleteData(`/api/account/deletebyid/${id}`);
        showAlert('Account deleted successfully.');
        loadAccounts();
    } catch (err) {
        showAlert('Failed to delete account: ' + err.message, 'error');
    }
}

// 5. Environmental Zones
async function loadEnvironmentalZones() {
    const tbody = document.getElementById('zones-table-body');
    if (!tbody) return;
    tbody.innerHTML = '<tr><td colspan="6">Loading...</td></tr>';
    try {
        const zones = await getData('/api/zone/getall');
        tbody.innerHTML = '';
        if (!Array.isArray(zones) || zones.length === 0) {
            tbody.innerHTML = '<tr><td colspan="6" style="text-align: center;">No zones registered yet.</td></tr>';
            return;
        }
        zones.forEach(z => {
            const tr = document.createElement('tr');
            const zId = z.id !== undefined ? z.id : z.Id;
            const name = z.zoneName || z.ZoneName || '';
            const code = z.zoneCode || z.ZoneCode || '';
            const pm25 = (z.pm25Limit !== undefined && z.pm25Limit !== null) ? z.pm25Limit : ((z.Pm25Limit !== undefined && z.Pm25Limit !== null) ? z.Pm25Limit : 0);
            const co2 = (z.co2Limit !== undefined && z.co2Limit !== null) ? z.co2Limit : ((z.Co2Limit !== undefined && z.Co2Limit !== null) ? z.Co2Limit : 0);

            tr.innerHTML = `
                <td>${zId}</td>
                <td><strong>${name}</strong></td>
                <td>${code}</td>
                <td>${pm25} µg/m³</td>
                <td>${co2} ppm</td>
                <td>
                    <button class="btn btn-sm btn-danger" onclick="deleteEnvironmentalZone(${zId})">Delete</button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    } catch (err) {
        tbody.innerHTML = '<tr><td colspan="6" style="color: var(--danger-color);">Error loading zones.</td></tr>';
    }
}

async function saveEnvironmentalZone(e) {
    e.preventDefault();

    const data = {
        ZoneName: document.getElementById("zone-name").value.trim(),
        ZoneCode: document.getElementById("zone-code").value.trim(),
        Pm25Limit: parseFloat(document.getElementById("zone-pm25").value) || 0,
        Co2Limit: parseFloat(document.getElementById("zone-co2").value) || 0,
        Description: document.getElementById("zone-desc").value.trim(),
        Active: document.getElementById("zone-active").checked
    };

    try {
        await postData('/api/zone/create', data);
        showAlert('Environmental Zone created successfully.');
        document.getElementById('zone-form').reset();
        loadEnvironmentalZones();
        loadDropdowns();
    } catch (err) {
        showAlert('Failed to create Zone: ' + err.message, 'error');
    }
}

async function deleteEnvironmentalZone(id) {
    if (!confirm('Are you sure you want to delete this zone?')) return;
    try {
        await deleteData(`/api/zone/deletebyid/${id}`);
        showAlert('Zone deleted successfully.');
        loadEnvironmentalZones();
        loadDropdowns();
    } catch (err) {
        showAlert('Failed to delete zone: ' + err.message, 'error');
    }
}

// 6. Air Sensors
async function loadAirSensors() {
    const tbody = document.getElementById('sensors-table-body');
    if (!tbody) return;
    tbody.innerHTML = '<tr><td colspan="7">Loading...</td></tr>';
    try {
        const [sensors, plants] = await Promise.all([
            getData('/api/air/sensors/getall'),
            getData('/api/industrialplant/getall').catch(() => [])
        ]);

        const plantMap = {};
        if (Array.isArray(plants)) {
            plants.forEach(p => {
                const pId = p.id !== undefined ? p.id : p.Id;
                const pName = p.name || p.Name || ('Plant #' + pId);
                plantMap[pId] = pName;
            });
        }

        tbody.innerHTML = '';
        if (!Array.isArray(sensors) || sensors.length === 0) {
            tbody.innerHTML = '<tr><td colspan="7" style="text-align: center;">No sensors registered yet.</td></tr>';
            return;
        }
        sensors.forEach(s => {
            const tr = document.createElement('tr');
            const sId = s.id !== undefined ? s.id : s.Id;
            const code = s.sensorCode || s.SensorCode || '';
            const name = s.sensorName || s.SensorName || '';
            const loc = s.location || s.Location || '';
            const zone = s.environmentalZone || s.EnvironmentalZone || '';
            const plantId = s.industrialPlantId || s.IndustrialPlantId;
            const plantName = plantId ? (plantMap[plantId] || ('Plant #' + plantId)) : '';
            const threshold = (s.pm25Threshold !== undefined && s.pm25Threshold !== null) ? s.pm25Threshold : ((s.Pm25Threshold !== undefined && s.Pm25Threshold !== null) ? s.Pm25Threshold : 0);
            const status = s.status || s.Status || 'ACTIVE';

            tr.innerHTML = `
                <td>${sId}</td>
                <td><strong>${code}</strong></td>
                <td>${name}${plantName ? `<br><small style="color: var(--text-secondary);">${plantName}</small>` : ''}</td>
                <td>${loc}${zone ? ` (${zone})` : ''}</td>
                <td>${threshold} µg/m³</td>
                <td><span class="badge badge-success">${status}</span></td>
                <td>
                    <button class="btn btn-sm btn-danger" onclick="deleteAirSensor(${sId})">Delete</button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    } catch (err) {
        tbody.innerHTML = '<tr><td colspan="7" style="color: var(--danger-color);">Error loading sensors.</td></tr>';
    }
}

async function saveAirSensor(e) {
    e.preventDefault();
    const plantSelect = document.getElementById('sensor-plant');
    const plantId = plantSelect && plantSelect.value ? parseInt(plantSelect.value) : null;

    const data = {
        sensorCode: document.getElementById('sensor-code').value.trim(),
        sensorName: document.getElementById('sensor-name').value.trim(),
        industrialPlantId: plantId,
        location: document.getElementById('sensor-location').value.trim(),
        environmentalZone: document.getElementById('sensor-zone').value.trim(),
        manufacturer: document.getElementById('sensor-vendor').value.trim(),
        pm25Threshold: parseFloat(document.getElementById('sensor-pm25').value) || 0.0,
        co2Threshold: parseFloat(document.getElementById('sensor-co2').value) || 0.0,
        status: document.getElementById('sensor-status').value,
        installationDate: document.getElementById('sensor-date').value,
        active: document.getElementById('sensor-active').checked
    };

    try {
        await postData('/api/air/sensors/create', data);
        showAlert('Air Sensor registered successfully.');
        document.getElementById('sensor-form').reset();
        loadAirSensors();
        loadDropdowns();
        if (typeof loadDashboard === 'function') loadDashboard();
    } catch (err) {
        showAlert('Failed to register Sensor: ' + err.message, 'error');
    }
}

async function deleteAirSensor(id) {
    if (!confirm('Are you sure you want to delete this sensor?')) return;
    try {
        await deleteData(`/api/air/sensors/deletebyid/${id}`);
        showAlert('Sensor deleted successfully.');
        loadAirSensors();
        loadDropdowns();
        if (typeof loadDashboard === 'function') loadDashboard();
    } catch (err) {
        showAlert('Failed to delete sensor: ' + err.message, 'error');
    }
}

// Master Dropdown Population helper
async function loadDropdowns() {
    try {
        const [zones, vendors, plants, sensors, products] = await Promise.all([
            getData('/api/zone/getall').catch(() => []),
            getData('/api/sensorvendor/getall').catch(() => []),
            getData('/api/industrialplant/getall').catch(() => []),
            getData('/api/air/sensors/getall').catch(() => []),
            getData('/api/product/getall').catch(() => [])
        ]);

        const zonesList = Array.isArray(zones) ? zones : [];
        const vendorsList = Array.isArray(vendors) ? vendors : [];
        const plantsList = Array.isArray(plants) ? plants : [];
        const sensorsList = Array.isArray(sensors) ? sensors : [];
        const productsList = Array.isArray(products) ? products : [];

        // Zone dropdowns
        const zoneSelects = document.querySelectorAll('.populate-zones');
        zoneSelects.forEach(sel => {
            const current = sel.value;
            sel.innerHTML = '<option value="">Select Zone...</option>';
            zonesList.forEach(z => {
                const opt = document.createElement('option');
                const zName = z.zoneName || z.ZoneName || '';
                const zCode = z.zoneCode || z.ZoneCode || '';
                opt.value = zName;
                opt.textContent = `${zName}${zCode ? ` (${zCode})` : ''}`;
                sel.appendChild(opt);
            });
            if (current) sel.value = current;
        });

        // Vendor dropdowns
        const vendorSelects = document.querySelectorAll('.populate-vendors');
        vendorSelects.forEach(sel => {
            const current = sel.value;
            sel.innerHTML = '<option value="">Select Vendor...</option>';
            vendorsList.forEach(v => {
                const opt = document.createElement('option');
                const vId = v.id !== undefined ? v.id : v.Id;
                const vName = v.name || v.Name || '';
                const vCode = v.vendorCode || v.VendorCode || '';
                opt.value = sel.classList.contains('use-id') ? vId : vName;
                opt.textContent = `${vName}${vCode ? ` (${vCode})` : ''}`;
                sel.appendChild(opt);
            });
            if (current) sel.value = current;
        });

        // Telemetry sensor dropdown
        const telemetrySensorSelect = document.getElementById('telemetry-sensor-id');
        if (telemetrySensorSelect) {
            const current = telemetrySensorSelect.value;
            telemetrySensorSelect.innerHTML = '<option value="">Select Registered Sensor...</option>';
            sensorsList.forEach(s => {
                const opt = document.createElement('option');
                const sId = s.id !== undefined ? s.id : s.Id;
                const sCode = s.sensorCode || s.SensorCode || '';
                const sName = s.sensorName || s.SensorName || '';
                const sZone = s.environmentalZone || s.EnvironmentalZone || '';
                const sThresh = (s.pm25Threshold !== undefined && s.pm25Threshold !== null) ? s.pm25Threshold : ((s.Pm25Threshold !== undefined && s.Pm25Threshold !== null) ? s.Pm25Threshold : 100);

                opt.value = sId;
                opt.setAttribute('data-zone', sZone);
                opt.setAttribute('data-threshold', sThresh);
                opt.textContent = `${sCode} - ${sName} (${sZone || 'No Zone'})`;
                telemetrySensorSelect.appendChild(opt);
            });
            if (current) telemetrySensorSelect.value = current;
        }

        // Product dropdowns for purchase & invoicing
        const productSelects = document.querySelectorAll('.populate-products');
        productSelects.forEach(sel => {
            const current = sel.value;
            sel.innerHTML = '<option value="">Select Product...</option>';
            productsList.forEach(p => {
                const opt = document.createElement('option');
                const pId = p.id !== undefined ? p.id : p.Id;
                const pName = p.name || p.Name || '';
                const pPrice = (p.unitPrice !== undefined && p.unitPrice !== null) ? p.unitPrice : ((p.UnitPrice !== undefined && p.UnitPrice !== null) ? p.UnitPrice : 0);
                opt.value = pId;
                opt.setAttribute('data-price', pPrice);
                opt.textContent = `${pName} - ₹${Number(pPrice).toLocaleString()}`;
                sel.appendChild(opt);
            });
            if (current) sel.value = current;
        });

        // Plants dropdowns
        const plantSelects = document.querySelectorAll('.populate-plants');
        plantSelects.forEach(sel => {
            const current = sel.value;
            sel.innerHTML = '<option value="">Select Industrial Plant...</option>';
            plantsList.forEach(p => {
                const opt = document.createElement('option');
                const pId = p.id !== undefined ? p.id : p.Id;
                const pName = p.name || p.Name || '';
                const pCode = p.plantCode || p.PlantCode || '';
                opt.value = pId;
                opt.textContent = `${pName}${pCode ? ` (${pCode})` : ''}`;
                sel.appendChild(opt);
            });
            if (current) sel.value = current;
        });

    } catch (err) {
        console.error('Error loading dropdown data:', err);
    }
}

