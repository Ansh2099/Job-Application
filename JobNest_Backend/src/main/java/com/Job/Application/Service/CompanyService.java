package com.Job.Application.Service;

import com.Job.Application.Model.Companies;
import com.Job.Application.Repo.CompanyRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepo repo;

    public List<Companies> getAllCompanies() {
        return repo.findAll();
    }

    public Companies postCompany(Companies company) {
        return repo.save(company);
    }

    public void deleteCompany(Long id) {
        repo.deleteById(id);
    }

    public Companies getCompanyById(long id) {
        return repo.findById(id).orElse(null);
    }

    public Object updateCompany(@Valid Companies company, Long id) {
        return repo.save(company);
    }
}