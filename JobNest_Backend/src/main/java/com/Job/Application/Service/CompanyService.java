package com.Job.Application.Service;

import com.Job.Application.Model.Companies;
import com.Job.Application.Repo.CompanyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CompanyService {
    
    private final CompanyRepo companyRepo;
    
    /**
     * Get all companies
     */
    public List<Companies> getAllCompanies() {
        return companyRepo.findAll();
    }
    
    /**
     * Get company by ID
     */
    public Companies getCompanyById(Long id) {
        return companyRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Company not found with ID: " + id));
    }
    
    /**
     * Create a new company
     */
    @Transactional
    public Companies createCompany(Companies company) {
        return companyRepo.save(company);
    }
    
    /**
     * Update an existing company
     */
    @Transactional
    public Companies updateCompany(Long id, Companies companyDetails) {
        Companies company = getCompanyById(id);
        
        // Update company details
        company.setName(companyDetails.getName());
        company.setDescription(companyDetails.getDescription());
        
        return companyRepo.save(company);
    }
    
    /**
     * Delete a company
     */
    @Transactional
    public void deleteCompany(Long id) {
        Companies company = getCompanyById(id);
        companyRepo.delete(company);
    }
}