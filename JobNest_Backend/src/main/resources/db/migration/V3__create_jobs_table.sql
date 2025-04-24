CREATE TABLE jobs (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    minSalary VARCHAR(255) NOT NULL,
    maxSalary VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    applications VARCHAR(255),
    company BIGINT,
    FOREIGN KEY (company) REFERENCES companies(id)
);