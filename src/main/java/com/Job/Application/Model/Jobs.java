package com.Job.Application.Model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Component
@ToString
public class Jobs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(max = 100)
    private String title;

    @NotBlank
    @Size(max = 1000)
    private String desc;

    @NotBlank
    private String minSalary;

    @NotBlank
    private String maxSalary;

    @NotBlank
    @Size(max = 100)
    private String location;

    // Add this field to the existing Jobs class
    @JsonIgnore
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<JobSubmission> applications;
    
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Companies company;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Companies getCompany() {
        return company;
    }

    public void setCompany(Companies company) {
        this.company = company;
    }
}
