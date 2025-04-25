package com.Job.Application.Mappers;

import com.Job.Application.Model.Companies;
import com.Job.Application.Response.CompanyResponse;
import org.springframework.stereotype.Service;

@Service
public class CompanyMapper {

    public CompanyResponse toCompanyResponse(Companies company) {
        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .description(company.getDescription())
                .build();
    }
}
