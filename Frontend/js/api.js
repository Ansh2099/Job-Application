const API_BASE_URL = 'http://localhost:9000';

const api = {
    async getAllCompanies() {
        const response = await fetch(`${API_BASE_URL}/companies`);
        return response.json();
    },

    async getCompanyJobs(companyId) {
        const response = await fetch(`${API_BASE_URL}/companies/${companyId}/jobs`);
        return response.json();
    },

    async submitApplication(companyId, jobId, application) {
        // This is a mock implementation since the backend doesn't have this endpoint yet
        return new Promise((resolve) => {
            setTimeout(() => resolve({ success: true }), 1500);
        });
    }
};
