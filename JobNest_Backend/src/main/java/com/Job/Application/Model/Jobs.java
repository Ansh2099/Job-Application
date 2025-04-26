package com.Job.Application.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.Job.Application.Constants.JobsConstants;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "jobs")
@NamedQueries({
    @NamedQuery(name = JobsConstants.FindByCompanyId, query = "SELECT j FROM Jobs j WHERE j.company.id = :companyId")
})
public class Jobs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String title;

    @NotBlank
    @Size(max = 1000)
    private String description;

    @NotBlank
    private String minSalary;

    @NotBlank
    private String maxSalary;

    @NotBlank
    @Size(max = 100)
    private String location;

    @JsonIgnore
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<JobSubmission> applications;
    
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Companies company;
}