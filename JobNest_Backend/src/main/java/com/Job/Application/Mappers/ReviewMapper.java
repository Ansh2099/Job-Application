package com.Job.Application.Mappers;

import com.Job.Application.Model.Reviews;
import com.Job.Application.Response.ReviewResponse;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ReviewMapper {

    private final UserMapper userMapper;
    private final CompanyMapper companyMapper;

    @Autowired
    public ReviewMapper(UserMapper userMapper, CompanyMapper companyMapper) {
        this.userMapper = userMapper;
        this.companyMapper = companyMapper;
    }

    public ReviewResponse toReviewResponse(Reviews review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .title(review.getTitle())
                .description(review.getDescription())
                .rating(review.getRating())
                .user(userMapper.toUserResponse(review.getUser()))
                .company(companyMapper.toCompanyResponse(review.getCompany()))
                .build();
    }
}
