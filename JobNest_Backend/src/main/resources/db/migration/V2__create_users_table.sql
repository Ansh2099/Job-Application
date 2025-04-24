CREATE TABLE users(
id BIGSERIAL PRIMARY KEY,
username VARCHAR(255) NOT NULL,
firstname VARCHAR(255) NOT NULL,
lastname VARCHAR(255) NOT NULL,
email VARCHAR(255) NOT NULL,
roles VARCHAR(255),
phoneNumber VARCHAR(20),
resume VARCHAR(255),
skills VARCHAR(255),
experience VARCHAR(255),
company_id BIGINT,
position VARCHAR(255),

FOREIGN KEY (company_id) REFERENCES companies(id)
);
