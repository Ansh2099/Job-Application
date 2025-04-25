package com.Job.Application.Mappers;

import com.Job.Application.Model.Jobs;
import com.Job.Application.Response.JobResponse;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class JobMapper {

    private final CompanyMapper companyMapper;

    @Autowired
    public JobMapper(CompanyMapper companyMapper) {
        this.companyMapper = companyMapper;
    }

    public JobResponse toJobResponse(Jobs job) {
        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .minSalary(job.getMinSalary())
                .maxSalary(job.getMaxSalary())
                .location(job.getLocation())
                .company(companyMapper.toCompanyResponse(job.getCompany()))
                .build();
    }
}
