document.addEventListener('DOMContentLoaded', function() {
    loadJobs(); // Use real API instead of mock data
    setupEventListeners();
    setupFilterButtons();
});

async function loadJobs() {
    try {
        const companies = await api.getAllCompanies();
        const jobsPromises = companies.map(company => api.getCompanyJobs(company.id));
        const jobsByCompany = await Promise.all(jobsPromises);
        
        // Flatten and transform jobs array
        const allJobs = jobsByCompany.flat().map(job => ({
            id: job.id,
            title: job.title,
            company: {
                name: companies.find(c => c.id === job.companyId).name,
                id: job.companyId,
                logo: "https://via.placeholder.com/50" // Placeholder since backend doesn't have logos
            },
            desc: job.desc,
            location: job.location || 'Not specified',
            type: job.type || 'Full-Time',
            minSalary: job.minSalary,
            maxSalary: job.maxSalary,
            posted: 'Recently' // Backend doesn't have this field yet
        }));

        displayJobs(allJobs);
    } catch (error) {
        console.error('Error loading jobs:', error);
        // Fallback to mock data if API fails
        loadMockJobs();
    }
}

function displayJobs(jobs) {
    const container = document.getElementById('jobsContainer');
    container.innerHTML = '';

    jobs.forEach(job => {
        const jobCard = createJobCard(job);
        container.appendChild(jobCard);
    });
}

function createJobCard(job) {
    const card = document.createElement('div');
    card.className = 'job-card';
    card.innerHTML = `
        <div class="job-card-header">
            <img src="${job.company.logo}" alt="${job.company.name} logo" class="company-logo">
            <div class="job-card-title">
                <h2>${job.title}</h2>
                <div class="company-name">${job.company.name}</div>
            </div>
            <div class="job-posted">${job.posted}</div>
        </div>
        <div class="job-details">
            <div class="job-tags">
                <span class="job-tag"><i class="fas fa-map-marker-alt"></i> ${job.location}</span>
                <span class="job-tag"><i class="fas fa-briefcase"></i> ${job.type}</span>
                <span class="job-tag"><i class="fas fa-dollar-sign"></i> ${formatSalary(job.minSalary, job.maxSalary)}</span>
            </div>
            <p class="job-description">${job.desc}</p>
        </div>
        <div class="actions">
            <button class="btn btn-secondary" onclick="viewReviews(${job.company.id})">
                <i class="fas fa-star"></i> Company Reviews
            </button>
            <button class="btn btn-primary" onclick="openApplicationModal(${job.id})">
                <i class="fas fa-paper-plane"></i> Apply Now
            </button>
        </div>
    `;
    return card;
}

function formatSalary(min, max) {
    return `$${(min/1000).toFixed(0)}k - $${(max/1000).toFixed(0)}k`;
}

function setupEventListeners() {
    // Search functionality
    const searchInput = document.getElementById('searchInput');
    const searchBtn = document.getElementById('searchBtn');
    
    searchInput.addEventListener('keyup', (e) => {
        if (e.key === 'Enter') {
            handleSearch();
        }
    });
    searchBtn.addEventListener('click', handleSearch);

    // Modal events
    const closeBtn = document.querySelector('.close');
    closeBtn.addEventListener('click', closeApplicationModal);

    const applicationForm = document.getElementById('applicationForm');
    applicationForm.addEventListener('submit', handleApplicationSubmit);

    // Close modal when clicking outside
    window.addEventListener('click', (event) => {
        const modal = document.getElementById('applicationModal');
        if (event.target === modal) {
            closeApplicationModal();
        }
    });

    // Close modal with Escape key
    document.addEventListener('keydown', (event) => {
        if (event.key === 'Escape') {
            closeApplicationModal();
        }
    });
}

function setupFilterButtons() {
    const filterButtons = document.querySelectorAll('.filter-btn');
    filterButtons.forEach(button => {
        button.addEventListener('click', () => {
            // Remove active class from all buttons
            filterButtons.forEach(btn => btn.classList.remove('active'));
            // Add active class to clicked button
            button.classList.add('active');
            // TODO: Implement actual filtering
            handleFilter(button.textContent);
        });
    });
}

function handleFilter(filterType) {
    // This is a mock implementation
    console.log(`Filtering by: ${filterType}`);
    loadMockJobs(); // Reload all jobs for now
}

function handleSearch() {
    const searchTerm = document.getElementById('searchInput').value.toLowerCase();
    const jobCards = document.querySelectorAll('.job-card');

    jobCards.forEach(card => {
        const title = card.querySelector('h2').textContent.toLowerCase();
        const description = card.querySelector('.job-description').textContent.toLowerCase();
        const company = card.querySelector('.company-name').textContent.toLowerCase();
        
        if (title.includes(searchTerm) || 
            description.includes(searchTerm) || 
            company.includes(searchTerm)) {
            card.style.display = 'block';
        } else {
            card.style.display = 'none';
        }
    });
}

function openApplicationModal(jobId) {
    const modal = document.getElementById('applicationModal');
    document.getElementById('jobId').value = jobId;
    modal.style.display = 'block';
    document.body.style.overflow = 'hidden';
    
    // Clear form fields
    document.getElementById('applicantName').value = '';
    document.getElementById('email').value = '';
    document.getElementById('resume').value = '';
    document.getElementById('coverLetter').value = '';

    // Focus on first input
    document.getElementById('applicantName').focus();
}

function closeApplicationModal() {
    const modal = document.getElementById('applicationModal');
    modal.style.display = 'none';
    document.body.style.overflow = 'auto';
}

function handleApplicationSubmit(event) {
    event.preventDefault();
    
    // Show loading state
    const submitButton = event.target.querySelector('button[type="submit"]');
    const originalText = submitButton.innerHTML;
    submitButton.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Submitting...';
    submitButton.disabled = true;

    // Simulate API call
    setTimeout(() => {
        submitButton.innerHTML = '<i class="fas fa-check"></i> Submitted!';
        setTimeout(() => {
            closeApplicationModal();
            submitButton.innerHTML = originalText;
            submitButton.disabled = false;
            
            // Show success message
            showNotification('Application submitted successfully!');
        }, 1000);
    }, 1500);
}

function showNotification(message) {
    // Create notification element
    const notification = document.createElement('div');
    notification.className = 'notification';
    notification.innerHTML = `
        <i class="fas fa-check-circle"></i>
        ${message}
    `;
    
    // Add to document
    document.body.appendChild(notification);
    
    // Remove after animation
    setTimeout(() => {
        notification.remove();
    }, 3000);
}

function viewReviews(companyId) {
    // This is a mock implementation
    showNotification('Company reviews feature coming soon!');
}