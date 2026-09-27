# Urban Air Quality Monitoring & Industrial Emissions Fine Management Hub

## Overview

The **Urban Air Quality Monitoring & Industrial Emissions Fine Management Hub** is a Spring Boot-based web application that bridges industrial IoT telemetry with environmental governance, regulatory compliance, automated violation ticketing, fine invoicing, demo payment processing, double-entry journal accounting, environmental budgeting, and regulatory financial reporting.

The system continuously monitors critical industrial air quality parameters—specifically **PM2.5** (Particulate Matter) and **CO₂** (Carbon Dioxide)—collected via deployed air sensor nodes. Sensor readings are automatically evaluated against established environmental regulatory safety thresholds. When an emission reading exceeds safe limits, the platform creates an automated **Violation Ticket**, calculates environmental penalties, and initiates the automated customer fine invoicing and simulated payment reconciliation lifecycle.

> [!NOTE]
> The payment processing module in this project is a **DEMO / SIMULATION** designed strictly for academic, educational, and workflow demonstration purposes. It does **NOT** connect to any real-world banking network or live payment gateway.

---

## Key Features

### Dashboard
- **Real-Time Environmental Overview**: Visual KPIs displaying total industrial plants, active air sensors, IoT telemetry streams, detected violations, unpaid penalty invoices, and collected fines.
- **Recent Violation Alerts**: Live feed of active violations, affected zones, exceeded thresholds, and current enforcement status.
- **Environmental Activity Summary**: High-level operational awareness for environmental protection authorities.

### Master Data Management
- **Environmental Zones**: Definition of regulatory districts, geographic boundaries, and regional pollution risk classifications.
- **Industrial Plants**: Industrial facilities registry with plant codes, operational capacity, zone assignments, and compliance statuses.
- **Sensor Vendors**: Directory of certified environmental monitoring equipment manufacturers and suppliers.
- **Compliance Products**: Registry of compliance equipment, sensor hardware, calibration devices, and associated standard costs.
- **Chart of Accounts**: Regulatory General Ledger accounts (Assets, Liabilities, Equity, Revenue, Expenses) enabling automated journal entries.
- **Air Sensors**: IoT sensor nodes assigned to industrial plants and environmental zones, tracking operational statuses and firmware/calibration versions.

### Air Monitoring & Telemetry
- **Air Sensor Management**: Registry and status tracking of IoT air monitoring devices.
- **Telemetry Ingestion**: Real-time logging of ambient atmospheric telemetry (`PM2.5` in µg/m³ and `CO₂` in ppm).
- **Automated Threshold Evaluation**: Dynamic comparison of live readings against regulatory limits (`PM2.5` > 60 µg/m³, `CO₂` > 1000 ppm).
- **Sensor-to-Plant Association**: Every reading is mapped back to the originating industrial plant and environmental zone.

### Violations & Environmental Fines
- **Automated Violation Detection**: Telemetry readings exceeding thresholds automatically generate a violation record.
- **Violation Tickets**: Unique ticket numbers, timestamps, measured vs. allowed threshold values, and assigned penalty amounts.
- **Penalty Management**: Fine calculation engine enforcing regulatory financial liabilities on polluting facilities.

### Customer Invoices
- **Automated Penalty Invoices**: Direct generation of penalty invoices from violation tickets.
- **Status Tracking**: Complete lifecycle tracking (`ISSUED`, `PAID`, `CANCELLED`).
- **Violation Traceability**: Direct bidirectional linkage between violation tickets and customer billing documents.

### Demo Payments
- **Simulated Payment Gateway**: Safe demonstration environment to simulate penalty fine settlements.
- **Audit Records**: Transaction logging with simulated payment references and payment methods.
- **State Reconciliation**: Automatically transitions customer invoice status to `PAID` and updates associated violation tickets to `SETTLED`/`CLOSED`.
- **No Real Gateway**: Completely isolated simulation with zero external financial integrations.

### Purchases & Bills
- **Purchase Orders (PO)**: Creation and management of procurement orders for sensor hardware and compliance equipment.
- **Sensor Vendor Procurement**: Associating purchase orders with registered equipment suppliers.
- **Vendor Bills**: Recording accounts payable liabilities upon receipt of equipment and services.
- **Bill Settlement**: Settling vendor bills with automated journal ledger entries.

### Environmental Budgeting
- **Zone Budgets**: Allocation of environmental management and monitoring budgets per zone and fiscal year.
- **Budget Tracking**: Tracking allocated budget amounts, actual expenditures, and fine revenue collections.
- **Variance Analysis**: Automated calculation of budget vs. actual variances for environmental oversight.

### Financial & Environmental Reports
- **Profit & Loss (P&L)**: Regulatory revenue from fines and licensing vs. operational/calibration expenditures.
- **Balance Sheet**: Real-time aggregation of regulatory assets, liabilities, income, and expenses across chart of accounts.
- **Environmental Budget Report**: Fiscal year variance analysis comparing allocated municipal budgets against operational spending and fine collections.
- **Zone Air Quality Report**: Summary of environmental conditions, telemetry counts, and violation frequencies across zones.

---

## System Workflow

### Environmental Violation & Fine Workflow
```text
Environmental Zone
       ↓
Industrial Plant
       ↓
   Air Sensor
       ↓
 Air Telemetry (PM2.5, CO₂)
       ↓
Threshold Evaluation (Exceeds Limit?)
       ↓
Violation Ticket Generated
       ↓
Environmental Fine Computed
       ↓
Customer Invoice Issued
       ↓
 Demo Payment Executed
       ↓
  Invoice Marked PAID
       ↓
Violation Ticket SETTLED / CLOSED
       ↓
Journal Entry Posted (Debit Cash/Receivable, Credit Fine Revenue)
       ↓
Budget Tracking & Financial Reports Updated
```

### Procurement & Vendor Accounting Workflow
```text
   Sensor Vendor
         ↓
  Purchase Order
         ↓
    Vendor Bill
         ↓
Bill Payment Executed
         ↓
Journal Entry Posted (Debit Expense/Asset, Credit Cash/Accounts Payable)
         ↓
Budget & Financial Reports Updated
```

---

## Technology Stack

| Layer | Technology |
|---|---|
| **Frontend** | HTML5, Vanilla CSS3, Modern Vanilla JavaScript (ES6+) |
| **Routing** | Client-Side Vanilla SPA Router (HTML5 History API) |
| **Backend** | Java 17, Spring Boot 4.1.1 |
| **Persistence / ORM** | Spring Data JPA, Hibernate |
| **Database** | MySQL 8.x |
| **Build Tool** | Apache Maven (with Maven Wrapper `mvnw`) |
| **API Architecture** | RESTful JSON APIs |

---

## Project Structure

```text
Urban-Air-Quality-Monitoring-and-Fine-Management-Hub/
├── .gitignore                                 # Root Git ignore rules
├── README.md                                  # Project documentation
├── frontend/                                  # Frontend Single-Page Application
│   ├── index.html                             # Main application shell and UI views
│   ├── css/
│   │   └── style.css                          # Application styles and responsive design
│   └── js/
│       ├── api.js                             # Fetch API client and global notification handlers
│       ├── app.js                             # Application initializer and event wiring
│       ├── budgets.js                         # Environmental budget management logic
│       ├── dashboard.js                       # Dashboard metrics and summary statistics
│       ├── invoices.js                        # Penalty invoice management logic
│       ├── masters.js                         # Master data CRUD (plants, vendors, zones, accounts, products)
│       ├── monitoring.js                      # Air sensor and telemetry ingestion logic
│       ├── payments.js                        # Demo payment simulation logic
│       ├── purchases.js                       # Purchase orders and vendor bills logic
│       ├── reports.js                         # Financial reports (P&L, Balance Sheet, Budget)
│       ├── router.js                          # Client-side HTML5 History API routing
│       └── violations.js                      # Violation tickets and fine management logic
└── project/                                   # Spring Boot backend application
    ├── pom.xml                                # Maven build and dependency configuration
    ├── mvnw / mvnw.cmd                        # Maven wrapper scripts
    └── src/
        ├── main/
        │   ├── java/
        │   │   └── ProjectLeap34/project/
        │   │       ├── ProjectApplication.java # Spring Boot main entrypoint
        │   │       ├── CorsConfig.java         # Cross-Origin Resource Sharing configuration
        │   │       ├── Controller/             # Spring MVC REST Controllers
        │   │       │   ├── AccountController.java
        │   │       │   ├── AirSensorController.java
        │   │       │   ├── AirTelemetryController.java
        │   │       │   ├── ComplianceProductController.java
        │   │       │   ├── CustomerInvoiceController.java
        │   │       │   ├── EnvironmentalBudgetController.java
        │   │       │   ├── EnvironmentalZoneController.java
        │   │       │   ├── IndustrialPlantController.java
        │   │       │   ├── JournalEntryController.java
        │   │       │   ├── PaymentController.java
        │   │       │   ├── PurchaseOrderController.java
        │   │       │   ├── ReportController.java
        │   │       │   ├── SensorVendorController.java
        │   │       │   ├── VendorBillController.java
        │   │       │   └── ViolationTicketController.java
        │   │       ├── DTO/                    # Data Transfer Objects (Requests & Responses)
        │   │       ├── Models/                 # JPA Entities
        │   │       ├── Repository/             # Spring Data JPA Repositories
        │   │       └── Services/               # Business Logic and Service Layer
        │   └── resources/
        │       └── application.properties     # Spring Boot application configuration
        └── test/
            ├── java/ProjectLeap34/project/
            │   └── ProjectApplicationTests.java
            └── resources/
                └── application.properties     # In-memory H2 test configuration
```

---

## Backend API

The backend exposes RESTful endpoints on `http://localhost:8082`:

| Controller Group | Base Route | Supported Endpoints | Description |
|---|---|---|---|
| **Environmental Zones** | `/api/zone/` | `GET getall`, `GET getbyid/{id}`, `POST create`, `PUT update`, `DELETE deletebyid/{id}` | Manage environmental zones |
| **Industrial Plants** | `/api/industrialplant/` | `GET getall`, `GET getbyid/{id}`, `POST create`, `PUT update`, `DELETE deletebyid/{id}` | Manage industrial plant facilities |
| **Sensor Vendors** | `/api/sensorvendor/` | `GET getall`, `GET getbyid/{id}`, `POST create`, `PUT update`, `DELETE deletebyid/{id}` | Equipment vendors registry |
| **Compliance Products** | `/api/product/` | `GET getall`, `GET getbyid/{id}`, `POST create`, `PUT update`, `DELETE deletebyid/{id}` | Products & compliance hardware |
| **Chart of Accounts** | `/api/account/` | `GET getall`, `GET getbyid/{id}`, `POST create`, `PUT update`, `DELETE deletebyid/{id}` | Financial ledger accounts |
| **Air Sensors** | `/api/air/sensors/` | `GET getall`, `GET getbyid/{id}`, `POST create`, `PUT update`, `DELETE deletebyid/{id}` | IoT air sensor management |
| **Air Telemetry** | `/api/air/telemetry/` | `GET getall`, `GET getbyid/{id}`, `POST create`, `DELETE deletebyid/{id}` | Atmospheric telemetry readings & auto-violation triggers |
| **Violation Tickets** | `/api/violation/` | `GET getall`, `GET getbyid/{id}`, `POST create`, `PUT update`, `DELETE deletebyid/{id}` | Violations & penalty enforcement |
| **Customer Invoices** | `/api/invoice/` | `GET getall`, `GET getbyid/{id}`, `POST create`, `PUT update`, `DELETE deletebyid/{id}` | Environmental fine customer invoices |
| **Demo Payments** | `/api/payment/` | `GET getall`, `GET getbyid/{id}`, `POST create`, `DELETE deletebyid/{id}` | Simulated fine payment processing |
| **Purchase Orders** | `/api/purchase/` | `GET getall`, `GET getbyid/{id}`, `POST create`, `PUT update`, `DELETE deletebyid/{id}` | Equipment purchase orders |
| **Vendor Bills** | `/api/vendorbill/` | `GET getall`, `GET getbyid/{id}`, `POST create`, `PUT update`, `DELETE deletebyid/{id}`, `POST pay/{id}` | Vendor billing & bill settlements |
| **Environmental Budgets** | `/api/budget/` | `GET getall`, `GET getbyid/{id}`, `POST create`, `PUT update`, `DELETE deletebyid/{id}`, `GET report/{id}` | Zone-level environmental budgets |
| **Journal Entries** | `/api/journal/` | `GET getall`, `GET getbyid/{id}`, `POST create` | Double-entry accounting records |
| **Reports** | `/api/report/` | `GET zone`, `GET budget`, `GET financial`, `GET balancesheet` | P&L, Balance Sheet, Budget variance, Zone reports |

---

## Database

The project uses **MySQL** for persistent relational data storage. The database schema includes the following primary entities:

- `EnvironmentalZone`: Name, code, description, and risk classification.
- `IndustrialPlant`: Facility name, registration code, zone reference, address, and status.
- `SensorVendor`: Supplier company name, contact info, email, and address.
- `ComplianceProduct`: Product name, SKU, price, and category.
- `Account`: Chart of accounts entity with account number, name, and classification (Asset, Liability, Equity, Income, Expense).
- `AirSensor`: Device serial, model, zone, industrial plant association, and status.
- `AirTelemetry`: Timestamp, sensor ID, zone, plant, PM2.5 reading, and CO₂ reading.
- `ViolationTicket`: Ticket number, timestamp, sensor ID, plant, zone, parameter breached, measured value, limit value, fine amount, and violation status.
- `CustomerInvoice`: Invoice number, violation ticket reference, industrial plant, amount, due date, and status (`ISSUED`, `PAID`, `CANCELLED`).
- `Payment`: Payment reference, invoice ID, amount paid, payment method, date, and status.
- `PurchaseOrder`: Order number, vendor reference, total amount, status, and order date.
- `VendorBill`: Bill number, purchase order ID, vendor, amount, due date, and payment status.
- `EnvironmentalBudget`: Fiscal year, budget name, zone, allocated budget amount, actual expenditure, and fine collections.
- `JournalEntry`: Entry date, reference, description, account code, debit amount, and credit amount.

---

## Installation & Setup

### Prerequisites
- **Java Development Kit (JDK) 17** or higher
- **Apache Maven 3.8+** (or use included `mvnw`)
- **MySQL Server 8.0+**
- **Git**

---

### Step 1: Clone the Repository
```bash
git clone https://github.com/devakashlakshmanan/Urban-Air-Quality-Monitoring-and-Fine-Management-Hub.git
cd Urban-Air-Quality-Monitoring-and-Fine-Management-Hub
```

### Step 2: Create the MySQL Database
Open your MySQL terminal or client (e.g., MySQL Workbench) and create the database schema configured by the project:

```sql
CREATE DATABASE ProjectLeap34;
```

### Step 3: Configure Database Password
The backend application accesses MySQL using the `DB_PASSWORD` environment variable.

Configure the environment variable on your machine:

**Windows (PowerShell):**
```powershell
$env:DB_PASSWORD="your_mysql_password"
```

**Windows (Command Prompt):**
```cmd
set DB_PASSWORD=your_mysql_password
```

**Linux / macOS:**
```bash
export DB_PASSWORD=your_mysql_password
```

*(Optional: If your MySQL username is different from `root`, adjust `spring.datasource.username` in `project/src/main/resources/application.properties` accordingly).*

### Step 4: Run Spring Boot Backend
Navigate to the `project` directory and start the application:

```bash
cd project
./mvnw spring-boot:run
```
*(On Windows Command Prompt, use `mvnw.cmd spring-boot:run`, or open the project in an IDE and run `ProjectApplication.java`).*

### Step 5: Backend URL
The backend server will start and listen on:
```text
http://localhost:8082
```

### Step 6: Serve the Frontend
The frontend consists of static HTML, CSS, and vanilla JavaScript and is served on:
```text
http://localhost:3000
```

You can serve the `frontend/` folder using any static HTTP web server or IDE extension. For example:

**Option A — Using Node `serve`:**
```bash
npx serve -s frontend -l 3000
```

**Option B — Using VS Code / IDE Live Server:**
- Open the project in VS Code.
- Right-click `frontend/index.html` and choose **"Open with Live Server"** (configured to port 3000).

**Option C — Using `http-server`:**
```bash
npx http-server frontend -p 3000 --cors
```

---

## Application Routes

The client-side single-page router (`frontend/js/router.js`) uses the HTML5 History API to support seamless in-browser navigation:

- `/` → Automatically redirects to `/dashboard`
- `/dashboard` → Dashboard & System Overview
- `/master-data` → Master Data Hub
- `/master-data/industrial-plants` → Industrial Plants Directory
- `/master-data/sensor-vendors` → Sensor Vendors Directory
- `/master-data/compliance-products` → Compliance Products & Equipment
- `/master-data/accounts` → General Ledger Chart of Accounts
- `/master-data/environmental-zones` → Environmental Zones Directory
- `/master-data/air-sensors` → Air Monitoring Sensors
- `/air-monitoring` → Air Quality IoT Telemetry
- `/air-monitoring/telemetry` → Ingest & View Air Telemetry
- `/air-monitoring/sensors` → Deployed Sensors Grid
- `/violations` → Emission Violations Hub
- `/violations/tickets` → Violation Tickets Management
- `/violations/fines` → Environmental Penalties & Fines
- `/invoices` → Penalty Invoices Hub
- `/invoices/customer-invoices` → Customer Penalty Invoices
- `/payments` → Demo Payments Simulation
- `/payments/demo-payments` → Process Simulated Penalty Settlements
- `/purchases` → Procurement & Vendor Bills
- `/purchases/orders` → Purchase Orders Management
- `/purchases/vendor-bills` → Vendor Bills Management
- `/budget` → Environmental Budgets & Variance
- `/budget/environmental-budget` → Zone Environmental Budget Management
- `/reports` → Regulatory Environmental Reports
- `/reports/profit-loss` → Profit & Loss Statement
- `/reports/balance-sheet` → Balance Sheet Summary
- `/reports/budget` → Environmental Budget Variance Report

---

## Demo Payment Notice

> [!IMPORTANT]
> **This project uses a simulated/demo payment workflow for demonstration purposes. It does not integrate with Razorpay, Stripe, PayPal, Cashfree, PhonePe, Google Pay, UPI gateways, or banking APIs.**
> All payment transactions, references, and balance adjustments are handled internally within the application database to model regulatory compliance settlement flows without real money transfers.

---

## Security / Configuration

- **Environment-Based Credentials**: Database passwords are provided through the `DB_PASSWORD` environment variable. Never hardcode credentials into source control.
- **Secret Protection**: `.gitignore` is configured to prevent committing `.env`, build artifacts, IDE metadata, or sensitive files.
- **Academic / Demo Purpose**: This system is designed as an educational, architectural demonstration and academic capstone project.

---

## Testing Workflow

To verify the end-to-end operational flow of the system:

1. **Create Environmental Zone**: Add a zone (e.g., "Industrial Sector Air Zone 5", code `IND-ZONE-5`).
2. **Create Industrial Plant**: Register a facility (e.g., "Apex Steel Works") located within the created zone.
3. **Create Sensor Vendor**: Add an approved monitoring equipment vendor.
4. **Create Compliance Product**: Add standard monitoring/filtering equipment.
5. **Create Air Sensor**: Register an IoT air sensor node assigned to the plant and zone.
6. **Submit Normal Telemetry**: Record telemetry within acceptable limits (`PM2.5` = 35 µg/m³, `CO₂` = 600 ppm) and observe normal status.
7. **Submit Telemetry Exceeding PM2.5 Threshold**: Record telemetry with elevated levels (`PM2.5` = 95 µg/m³, `CO₂` = 1200 ppm).
8. **Verify Violation Ticket**: Confirm that a violation ticket is automatically generated with measured vs. limit values.
9. **Verify Environmental Fine**: Review the penalty amount assigned to the breach.
10. **Generate Customer Invoice**: Create a customer penalty invoice linked to the violation ticket.
11. **Perform Demo Payment**: Execute a simulated settlement for the invoice via Demo Payments.
12. **Verify Invoice Status**: Confirm the invoice status transitions to `PAID`.
13. **Verify Violation Status**: Confirm the violation ticket status updates to settled/closed.
14. **Verify Journal Entry**: Inspect the General Ledger for the corresponding debit and credit entries.
15. **Verify Environmental Budget**: Review how collected fines and actual expenditures affect zone budget variance.
16. **Verify Reports**: Inspect the updated Profit & Loss, Balance Sheet, and Zone Air Quality reports.
17. **Verify Dashboard**: Check the updated aggregate statistics, telemetry counts, and violation indicators on the dashboard.

---

## Important Notes

- **MySQL Must Be Running**: Ensure your MySQL server service is active before launching the Spring Boot backend.
- **Configure `DB_PASSWORD`**: Ensure the `DB_PASSWORD` environment variable is exported or set in your environment.
- **Start Backend First**: Launch the Spring Boot application on port `8082` before initiating API-dependent frontend interactions.
- **Demo Payments Are Simulated**: All payment processing is strictly internal and simulated.

---

## License

Academic/educational project.