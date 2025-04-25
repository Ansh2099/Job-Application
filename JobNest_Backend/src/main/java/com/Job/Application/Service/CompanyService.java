package com.Job.Application.Service;

import com.Job.Application.Mappers.CompanyMapper;
import com.Job.Application.Model.Companies;
import com.Job.Application.Repo.CompanyRepo;
import com.Job.Application.Response.CompanyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {
    
    private final CompanyRepo companyRepo;
    private final CompanyMapper companyMapper;
    
    /**
     * Get all companies
     */
    public List<Companies> getAllCompanies() {
        return companyRepo.findAll();
    }
    
    /**
     * Get all companies as CompanyResponse objects
     */
    public List<CompanyResponse> getAllCompanyResponses() {
        return getAllCompanies().stream()
                .map(companyMapper::toCompanyResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Get company by ID
     */
    public Companies getCompanyById(Long id) {
        return companyRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Company not found with ID: " + id));
    }
    
    /**
     * Get company by ID as CompanyResponse
     */
    public CompanyResponse getCompanyResponseById(Long id) {
        return companyMapper.toCompanyResponse(getCompanyById(id));
    }
    
    /**
     * Create a new company
     */
    @Transactional
    public Companies createCompany(Companies company) {
        return companyRepo.save(company);
    }
    
    /**
     * Create a new company and return CompanyResponse
     */
    @Transactional
    public CompanyResponse createCompanyAndGetResponse(Companies company) {
        return companyMapper.toCompanyResponse(createCompany(company));
    }
    
    /**
     * Update an existing company
     */
    @Transactional
    public Companies updateCompany(Long id, Companies companyDetails) {
        Companies company = getCompanyById(id);
        
        // Update fields
        company.setName(companyDetails.getName());
        company.setDescription(companyDetails.getDescription());
        
        return companyRepo.save(company);
    }
    
    /**
     * Update an existing company and return CompanyResponse
     */
    @Transactional
    public CompanyResponse updateCompanyAndGetResponse(Long id, Companies companyDetails) {
        return companyMapper.toCompanyResponse(updateCompany(id, companyDetails));
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