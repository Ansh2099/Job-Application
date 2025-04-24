CREATE TABLE job_submission (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    applicant_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    resume VARCHAR(255) NOT NULL,
    cover_letter VARCHAR(1000),
    job_id BIGINT,
    FOREIGN KEY (job_id) REFERENCES jobs(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);