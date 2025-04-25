package com.Job.Application.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobResponse {
    private Long id;
    private String title;
    private String description;
    private String minSalary;
    private String maxSalary;
    private String location;
    private CompanyResponse company;
}
