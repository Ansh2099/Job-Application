I'm working on a Spring Boot REST API project for a Job Application Platform. Here's the technical overview:

Tech Stack:
- Java 21
- Spring Boot 3.4.1
- Spring Data JPA
- Spring Security
- H2 Database
- Lombok
- Jakarta Validation
- Maven

Core Features:
1. Company Management
- CRUD operations for companies
- Company profiles with username and description
- One-to-many relationships with jobs and reviews

2. Job Postings
- Nested under companies (/companies/{companyId}/jobs)
- Job details include title, description, salary range, location
- Many-to-one relationship with companies
- Full CRUD operations

3. Review System
- Nested under companies (/companies/{companyId}/reviews)
- Review details include title, description, rating
- Many-to-one relationship with companies
- Full CRUD operations

Entity Relationships:
- Companies: OneToMany → Jobs and Reviews
- Jobs: ManyToOne → Companies
- Reviews: ManyToOne → Companies

API Structure:
1. Companies API (/companies)
2. Jobs API (/companies/{companyId}/jobs)
3. Reviews API (/companies/{companyId}/reviews)

Key Implementation Details:
- Proper input validation using Jakarta Validation
- RESTful architecture with nested resources
- H2 in-memory database with JPA
- Exception handling and proper HTTP status codes
- Lombok for reducing boilerplate
- Cascading relationships
- Lazy loading for better performance

The project follows a standard layered architecture:
- Controllers (REST APIs)
- Services (Business Logic)
- Repositories (Data Access)
- Models (Entities)

This serves as a foundation for a job posting and company review platform, similar to LinkedIn or Glassdoor but on a smaller scale.
