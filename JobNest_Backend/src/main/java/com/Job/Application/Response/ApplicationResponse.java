package com.Job.Application.Response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import com.Job.Application.Constants.ApplicationStatus;

@Data
@Builder
public class ApplicationResponse {
    private Long id;
    private UserResponse user;
    private String applicantName;
    private String email;
    private String resume;
    private String coverLetter;
    private ApplicationStatus status;
    private LocalDateTime submissionDate;
    private JobResponse job;
}
