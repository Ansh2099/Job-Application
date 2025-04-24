package com.Job.Application.Controllers;

import com.Job.Application.Model.Companies;
import com.Job.Application.Model.User;
import com.Job.Application.Service.CompanyService;
import com.Job.Application.Service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
@Tag(name = "Company Controller")
public class CompanyControllers {

    private final CompanyService companyService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<Companies>> getAllCompanies()
    {
        return ResponseEntity.ok().body(companyService.getAllCompanies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Companies> getCompanyById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(companyService.getCompanyById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('RECRUITER')")
    public ResponseEntity<Companies> createCompany(@Valid @RequestBody Companies company) {
        Companies createdCompany = companyService.createCompany(company);
        
        // If a recruiter is creating a company, associate them with it
        User currentUser = userService.getCurrentUser();
        if (currentUser.isRecruiter()) {
            currentUser.setCompany(createdCompany);
            userService.updateUser(currentUser);
        }
        
        return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('RECRUITER')")
    public ResponseEntity<Companies> updateCompany(@PathVariable("id") Long id, @Valid @RequestBody Companies company) {
        // Check if the recruiter is associated with this company
        User currentUser = userService.getCurrentUser();
        if (currentUser.isRecruiter() && 
            (currentUser.getCompany() == null || !currentUser.getCompany().getId().equals(id))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null);
        }
        
        Companies updatedCompany = companyService.updateCompany(id, company);
        return ResponseEntity.ok().body(updatedCompany);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCompany(@PathVariable("id") Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{companyId}/recruiters")
    public ResponseEntity<List<User>> getCompanyRecruiters(@PathVariable Long companyId) {
        List<User> recruiters = userService.getAllUsers().stream()
                .filter(User::isRecruiter)
                .filter(user -> user.getCompany() != null && user.getCompany().getId().equals(companyId))
                .toList();
        return ResponseEntity.ok(recruiters);
    }
}
