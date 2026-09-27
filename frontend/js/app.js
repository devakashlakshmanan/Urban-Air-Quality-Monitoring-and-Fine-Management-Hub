// Main Application Coordinator & Navigation Logic

document.addEventListener('DOMContentLoaded', () => {
    initNavigation();
    initMasterDataTabs();
    initSubTabs();
    attachFormListeners();
    
    // Initial router load based on address bar path
    if (window.router) {
        window.router.init();
    } else {
        navigateTo('dashboard');
    }
});

function initNavigation() {
    const navLinks = document.querySelectorAll('.nav-link[data-section]');
    navLinks.forEach(link => {
        link.addEventListener('click', (e) => {
            e.preventDefault();
            const route = link.getAttribute('href');
            if (route && window.router) {
                window.router.navigate(route);
            } else {
                const section = link.getAttribute('data-section');
                navigateTo(section);
            }
        });
    });
}

function initSubTabs() {
    const subTabBtns = document.querySelectorAll('.tab-btn[data-sub-tab]');
    subTabBtns.forEach(btn => {
        btn.addEventListener('click', () => {
            const route = btn.getAttribute('data-route');
            if (route && window.router) {
                window.router.navigate(route);
            }
        });
    });
}

const SECTION_PATH_MAP = {
    'dashboard': '/dashboard',
    'masters': '/master-data',
    'monitoring': '/air-monitoring',
    'violations': '/violations',
    'invoices': '/invoices',
    'payments': '/payments',
    'purchases': '/purchases',
    'budgets': '/budget',
    'reports': '/reports'
};

function navigateTo(sectionName) {
    if (window.router && SECTION_PATH_MAP[sectionName]) {
        window.router.navigate(SECTION_PATH_MAP[sectionName]);
        return;
    }

    document.querySelectorAll('.nav-link').forEach(l => l.classList.remove('active'));
    const activeLink = document.querySelector(`.nav-link[data-section="${sectionName}"]`);
    if (activeLink) activeLink.classList.add('active');

    document.querySelectorAll('.app-section').forEach(s => s.classList.remove('active'));
    const targetSection = document.getElementById(`section-${sectionName}`);
    if (targetSection) targetSection.classList.add('active');

    if (typeof window.onRouteActivated === 'function') {
        window.onRouteActivated(sectionName);
    }
}

window.onRouteActivated = function(sectionName) {
    loadDropdowns();
    switch (sectionName) {
        case 'dashboard':
            loadDashboard();
            break;
        case 'masters':
            loadIndustrialPlants();
            loadSensorVendors();
            loadComplianceProducts();
            loadAccounts();
            loadEnvironmentalZones();
            loadAirSensors();
            break;
        case 'monitoring':
            loadTelemetryHistory();
            break;
        case 'violations':
            loadViolations();
            break;
        case 'invoices':
            loadInvoices();
            break;
        case 'payments':
            loadPayments();
            populateUnpaidInvoicesDropdown();
            break;
        case 'purchases':
            loadPurchases();
            break;
        case 'budgets':
            loadBudgets();
            break;
        case 'reports':
            loadReports();
            break;
    }
};

function attachFormListeners() {
    // Master data forms
    const plantForm = document.getElementById('plant-form');
    if (plantForm) plantForm.addEventListener('submit', saveIndustrialPlant);

    const vendorForm = document.getElementById('vendor-form');
    if (vendorForm) vendorForm.addEventListener('submit', saveSensorVendor);

    const productForm = document.getElementById('product-form');
    if (productForm) productForm.addEventListener('submit', saveComplianceProduct);

    const accountForm = document.getElementById('account-form');
    if (accountForm) accountForm.addEventListener('submit', saveAccount);

    const zoneForm = document.getElementById('zone-form');
    if (zoneForm) zoneForm.addEventListener('submit', saveEnvironmentalZone);

    const sensorForm = document.getElementById('sensor-form');
    if (sensorForm) sensorForm.addEventListener('submit', saveAirSensor);

    // Telemetry form
    const telemetryForm = document.getElementById('telemetry-form');
    if (telemetryForm) telemetryForm.addEventListener('submit', submitTelemetry);

    const telemetrySensor = document.getElementById('telemetry-sensor-id');
    if (telemetrySensor) telemetrySensor.addEventListener('change', handleTelemetrySensorChange);

    // Payment form
    const paymentForm = document.getElementById('payment-form');
    if (paymentForm) paymentForm.addEventListener('submit', processDemoPayment);

    const paymentInvoiceSelect = document.getElementById('payment-invoice-id');
    if (paymentInvoiceSelect) paymentInvoiceSelect.addEventListener('change', handlePaymentInvoiceSelectChange);

    // Purchase & Bill forms
    const poForm = document.getElementById('po-form');
    if (poForm) poForm.addEventListener('submit', savePurchaseOrder);

    const poProductSelect = document.getElementById('po-product-id');
    if (poProductSelect) poProductSelect.addEventListener('change', handlePOProductChange);

    const poQty = document.getElementById('po-quantity');
    if (poQty) poQty.addEventListener('input', handlePOProductChange);

    // Budget form
    const budgetForm = document.getElementById('budget-form');
    if (budgetForm) budgetForm.addEventListener('submit', saveBudget);
}
