package com.Job.Application.Controllers;

import com.Job.Application.Model.Companies;
import com.Job.Application.Service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyControllers {

    @Autowired
    private CompanyService service;

    @GetMapping("/")
    public ResponseEntity<List<Companies>> getAllCompanies() {
        return new ResponseEntity<>(service.getAllCompanies(), HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Companies> getCompanyById(@PathVariable Long id){
        Companies company = service.getCompanyById(id);
        if (company != null)
            return new ResponseEntity<>(company, HttpStatus.FOUND);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<?> postCompany(@Valid @RequestBody Companies company){
        try{
            return new ResponseEntity<>(service.postCompany(company), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCompany(@PathVariable long id){
        Companies company = service.getCompanyById(id);
        if (company != null){
            service.deleteCompany(id);
            return new ResponseEntity<>(HttpStatus.FOUND);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCompany(@Valid @RequestPart Companies company, @RequestPart Long id){
        try {
            Companies company1 = service.getCompanyById(id);
            if (company1 != null)
                return new ResponseEntity<>(service.updateCompany(company, id), HttpStatus.FOUND);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
