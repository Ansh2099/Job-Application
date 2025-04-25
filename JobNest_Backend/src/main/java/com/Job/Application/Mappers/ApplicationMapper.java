package com.Job.Application.Mappers;

import com.Job.Application.Model.JobSubmission;
import com.Job.Application.Response.ApplicationResponse;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ApplicationMapper {

    private final UserMapper userMapper;
    private final JobMapper jobMapper;

    @Autowired
    public ApplicationMapper(UserMapper userMapper, JobMapper jobMapper) {
        this.userMapper = userMapper;
        this.jobMapper = jobMapper;
    }

    public ApplicationResponse toApplicationResponse(JobSubmission submission) {
        return ApplicationResponse.builder()
                .id(submission.getId())
                .user(userMapper.toUserResponse(submission.getUser()))
                .applicantName(submission.getApplicantName())
                .email(submission.getEmail())
                .resume(submission.getResume())
                .coverLetter(submission.getCoverLetter())
                .status(submission.getStatus())
                .submissionDate(submission.getSubmissionDate())
                .job(jobMapper.toJobResponse(submission.getJob()))
                .build();
    }
}
