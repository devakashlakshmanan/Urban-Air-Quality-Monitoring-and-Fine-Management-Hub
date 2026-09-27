// API Configuration
const API_BASE_URL = "http://localhost:8082";

// Common API Fetch wrappers
async function getData(endpoint) {
    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`);
        if (!response.ok) {
            throw new Error(`HTTP error ${response.status}: ${response.statusText}`);
        }
        return await response.json();
    } catch (error) {
        console.error(`Error GET ${endpoint}:`, error);
        throw error;
    }
}

async function postData(endpoint, data) {
    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || `HTTP error ${response.status}`);
        }
        const contentType = response.headers.get("content-type");
        if (contentType && contentType.includes("application/json")) {
            return await response.json();
        }
        return await response.text();
    } catch (error) {
        console.error(`Error POST ${endpoint}:`, error);
        throw error;
    }
}

async function putData(endpoint, data) {
    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || `HTTP error ${response.status}`);
        }
        const contentType = response.headers.get("content-type");
        if (contentType && contentType.includes("application/json")) {
            return await response.json();
        }
        return await response.text();
    } catch (error) {
        console.error(`Error PUT ${endpoint}:`, error);
        throw error;
    }
}

async function deleteData(endpoint) {
    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, {
            method: "DELETE"
        });
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || `HTTP error ${response.status}`);
        }
        return await response.text();
    } catch (error) {
        console.error(`Error DELETE ${endpoint}:`, error);
        throw error;
    }
}

// Global Alert Notification Helper
function showAlert(message, type = "success") {
    const banner = document.getElementById("alert-banner");
    if (!banner) return;
    
    banner.className = `alert-banner alert-${type}`;
    banner.innerText = message;
    banner.style.display = "block";
    
    // Auto-hide after 5 seconds
    setTimeout(() => {
        banner.style.display = "none";
    }, 5000);
}

function hideAlert() {
    const banner = document.getElementById("alert-banner");
    if (banner) {
        banner.style.display = "none";
    }
}
