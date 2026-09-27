// Lightweight Vanilla Client-Side Router using HTML5 History API

const ROUTE_DEFINITIONS = {
    '/': { redirect: '/dashboard' },
    '/dashboard': {
        section: 'dashboard',
        sidebar: 'dashboard',
        title: 'Dashboard & System Overview'
    },
    '/master-data': {
        section: 'masters',
        sidebar: 'masters',
        masterTab: 'tab-plants',
        title: 'Master Data Management'
    },
    '/master-data/industrial-plants': {
        section: 'masters',
        sidebar: 'masters',
        masterTab: 'tab-plants',
        title: 'Master Data — Industrial Plants'
    },
    '/master-data/sensor-vendors': {
        section: 'masters',
        sidebar: 'masters',
        masterTab: 'tab-vendors',
        title: 'Master Data — Sensor Vendors'
    },
    '/master-data/compliance-products': {
        section: 'masters',
        sidebar: 'masters',
        masterTab: 'tab-products',
        title: 'Master Data — Compliance Products'
    },
    '/master-data/accounts': {
        section: 'masters',
        sidebar: 'masters',
        masterTab: 'tab-accounts',
        title: 'Master Data — Chart of Accounts'
    },
    '/master-data/environmental-zones': {
        section: 'masters',
        sidebar: 'masters',
        masterTab: 'tab-zones',
        title: 'Master Data — Environmental Zones'
    },
    '/master-data/air-sensors': {
        section: 'masters',
        sidebar: 'masters',
        masterTab: 'tab-sensors',
        title: 'Master Data — Air Sensors'
    },

    '/air-monitoring': {
        section: 'monitoring',
        sidebar: 'monitoring',
        subTab: 'monitoring-telemetry',
        title: 'Air Quality IoT Telemetry'
    },
    '/air-monitoring/telemetry': {
        section: 'monitoring',
        sidebar: 'monitoring',
        subTab: 'monitoring-telemetry',
        title: 'Air Monitoring — IoT Telemetry'
    },
    '/air-monitoring/sensors': {
        section: 'monitoring',
        sidebar: 'monitoring',
        subTab: 'monitoring-sensors',
        title: 'Air Monitoring — Sensors Grid'
    },

    '/violations': {
        section: 'violations',
        sidebar: 'violations',
        subTab: 'violations-tickets',
        title: 'Emission Violations & Fine Tickets'
    },
    '/violations/tickets': {
        section: 'violations',
        sidebar: 'violations',
        subTab: 'violations-tickets',
        title: 'Violations & Fines — Tickets'
    },
    '/violations/fines': {
        section: 'violations',
        sidebar: 'violations',
        subTab: 'violations-fines',
        title: 'Violations & Fines — Fine Amounts'
    },

    '/invoices': {
        section: 'invoices',
        sidebar: 'invoices',
        subTab: 'invoices-customer',
        title: 'Customer Penalty Invoices'
    },
    '/invoices/customer-invoices': {
        section: 'invoices',
        sidebar: 'invoices',
        subTab: 'invoices-customer',
        title: 'Customer Penalty Invoices'
    },

    '/payments': {
        section: 'payments',
        sidebar: 'payments',
        subTab: 'payments-demo',
        title: 'Demo Fine Payments'
    },
    '/payments/demo-payments': {
        section: 'payments',
        sidebar: 'payments',
        subTab: 'payments-demo',
        title: 'Simulate Demo Payments'
    },

    '/purchases': {
        section: 'purchases',
        sidebar: 'purchases',
        subTab: 'purchases-orders',
        title: 'Procurement & Vendor Bills'
    },
    '/purchases/orders': {
        section: 'purchases',
        sidebar: 'purchases',
        subTab: 'purchases-orders',
        title: 'Purchases & Bills — Purchase Orders'
    },
    '/purchases/vendor-bills': {
        section: 'purchases',
        sidebar: 'purchases',
        subTab: 'purchases-bills',
        title: 'Purchases & Bills — Vendor Bills'
    },

    '/budget': {
        section: 'budgets',
        sidebar: 'budgets',
        subTab: 'budget-env',
        title: 'Environmental Budgets & Variance'
    },
    '/budget/environmental-budget': {
        section: 'budgets',
        sidebar: 'budgets',
        subTab: 'budget-env',
        title: 'Environmental Zone Budget'
    },

    '/reports': {
        section: 'reports',
        sidebar: 'reports',
        subTab: 'reports-pl',
        title: 'Regulatory Environmental Reports'
    },
    '/reports/profit-loss': {
        section: 'reports',
        sidebar: 'reports',
        subTab: 'reports-pl',
        title: 'Reports — Profit & Loss Summary'
    },
    '/reports/balance-sheet': {
        section: 'reports',
        sidebar: 'reports',
        subTab: 'reports-bs',
        title: 'Reports — Balance Sheet Summary'
    },
    '/reports/budget': {
        section: 'reports',
        sidebar: 'reports',
        subTab: 'reports-budget',
        title: 'Reports — Environmental Budget Variance'
    }
};

class Router {
    constructor() {
        this.routes = ROUTE_DEFINITIONS;
        this.currentPath = null;
    }

    init() {
        // Intercept back / forward browser navigation
        window.addEventListener('popstate', () => {
            this.handleRoute(window.location.pathname, false);
        });

        // Handle initial path on page load
        let initialPath = window.location.pathname;
        if (!initialPath || initialPath === '') {
            initialPath = '/';
        }
        if (initialPath.length > 1 && initialPath.endsWith('/')) {
            initialPath = initialPath.slice(0, -1);
        }
        this.handleRoute(initialPath, false);
    }

    navigate(path, options = { replace: false }) {
        if (path.length > 1 && path.endsWith('/')) {
            path = path.slice(0, -1);
        }
        if (options.replace) {
            history.replaceState(null, '', path);
        } else {
            if (window.location.pathname !== path) {
                history.pushState(null, '', path);
            }
        }
        this.handleRoute(path, false);
    }

    handleRoute(path, shouldPush = false) {
        if (path.length > 1 && path.endsWith('/')) {
            path = path.slice(0, -1);
        }

        const routeConfig = this.routes[path];

        if (!routeConfig) {
            this.render404(path);
            return;
        }

        if (routeConfig.redirect) {
            this.navigate(routeConfig.redirect, { replace: true });
            return;
        }

        this.currentPath = path;
        this.renderRoute(routeConfig);
    }

    renderRoute(routeConfig) {
        // 1. Highlight active sidebar link
        document.querySelectorAll('.nav-link').forEach(link => link.classList.remove('active'));
        if (routeConfig.sidebar) {
            const activeLink = document.querySelector(`.nav-link[data-section="${routeConfig.sidebar}"]`);
            if (activeLink) activeLink.classList.add('active');
        }

        // 2. Activate section
        document.querySelectorAll('.app-section').forEach(section => section.classList.remove('active'));
        const targetSection = document.getElementById(`section-${routeConfig.section}`);
        if (targetSection) targetSection.classList.add('active');

        // 3. Update topbar title
        const titleElem = document.getElementById('topbar-page-title');
        if (titleElem && routeConfig.title) {
            titleElem.innerText = routeConfig.title;
        }

        // 4. Activate master tab if applicable
        if (routeConfig.masterTab) {
            const tabBtns = document.querySelectorAll('.tab-btn[data-master-tab]');
            tabBtns.forEach(b => b.classList.remove('active'));
            const activeBtn = document.querySelector(`.tab-btn[data-master-tab="${routeConfig.masterTab}"]`);
            if (activeBtn) activeBtn.classList.add('active');

            document.querySelectorAll('.master-tab-content').forEach(c => c.classList.remove('active'));
            const targetContent = document.getElementById(routeConfig.masterTab);
            if (targetContent) targetContent.classList.add('active');
        }

        // 5. Activate section sub-tab if applicable
        if (routeConfig.subTab) {
            const subTabBtns = document.querySelectorAll('.tab-btn[data-sub-tab]');
            subTabBtns.forEach(b => {
                if (b.getAttribute('data-sub-tab') === routeConfig.subTab) {
                    b.classList.add('active');
                } else if (b.closest(`#section-${routeConfig.section}`)) {
                    b.classList.remove('active');
                }
            });

            const sectionElem = document.getElementById(`section-${routeConfig.section}`);
            if (sectionElem) {
                sectionElem.querySelectorAll('.sub-tab-content').forEach(c => c.classList.remove('active'));
                const targetSub = document.getElementById(`sub-${routeConfig.subTab}`);
                if (targetSub) targetSub.classList.add('active');
            }
        }

        // 6. Refresh data for section
        if (typeof window.onRouteActivated === 'function') {
            window.onRouteActivated(routeConfig.section, routeConfig);
        }
    }

    render404(path) {
        this.currentPath = path;

        // De-highlight sidebar
        document.querySelectorAll('.nav-link').forEach(link => link.classList.remove('active'));

        // Hide all sections, show 404
        document.querySelectorAll('.app-section').forEach(section => section.classList.remove('active'));
        const sec404 = document.getElementById('section-404');
        if (sec404) sec404.classList.add('active');

        // Update topbar title
        const titleElem = document.getElementById('topbar-page-title');
        if (titleElem) titleElem.innerText = '404 — Page Not Found';

        // Update not found text
        const pathElem = document.getElementById('notfound-path');
        if (pathElem) pathElem.innerText = path;
    }
}

const router = new Router();
window.router = router;
