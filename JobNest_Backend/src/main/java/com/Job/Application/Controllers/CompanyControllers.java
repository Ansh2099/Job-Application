package com.Job.Application.Controllers;

import com.Job.Application.Model.Companies;
import com.Job.Application.Service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController

@RequestMapping("/companies")
public class CompanyControllers {

    @Autowired
    private CompanyService service;

    @GetMapping("/")
    public ResponseEntity<List<Companies>> getAllCompanies() {
        return new ResponseEntity<>(service.getAllCompanies(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Companies> getCompanyById(@PathVariable Long id) {
        Companies company = service.getCompanyById(id);
        if (company != null) {
            return new ResponseEntity<>(company, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<?> postCompany(@Valid @RequestBody Companies company) {
        try {
            return new ResponseEntity<>(service.postCompany(company), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id) {
        Companies company = service.getCompanyById(id);
        if (company != null) {
            service.deleteCompany(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable Long id, @Valid @RequestBody Companies company) {
        try {
            Companies existingCompany = service.getCompanyById(id);
            if (existingCompany != null) {
                company.setId(id);
                return new ResponseEntity<>(service.updateCompany(company, id), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
