package com.Job.Application.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewResponse {
    private Long id;
    private String title;
    private String description;
    private long rating;
    private UserResponse user;
    private CompanyResponse company;
}
