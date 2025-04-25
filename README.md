# JobNest - Job Application Platform

## Live Demo
- Backend API: [Insert Render Backend URL here]
- Frontend: [Coming Soon]

## Project Overview
JobNest is a comprehensive job application platform that connects job seekers with recruiters. The platform provides functionality for managing company profiles, job postings, applications, and company reviews, similar to platforms like LinkedIn and Glassdoor.

## Technical Stack

### Backend
- Java 21
- Spring Boot 3.4.2
- Spring Data JPA
- Spring Security with OAuth2/JWT
- PostgreSQL
- Flyway for database migrations
- Lombok
- SpringDoc OpenAPI (Swagger)

### Frontend
- Angular (separate repository)

### Infrastructure
- Docker for containerization
- Render for cloud hosting
- Supabase for PostgreSQL hosting
- Keycloak for authentication and authorization

## Core Features

### 1. User Management
- Authentication using OAuth2/JWT via Keycloak
- Role-based access control (Admin, Recruiter, Job Seeker)
- User profile management

### 2. Company Management
- CRUD operations for companies
- Company profiles with details like name, description, industry, etc.
- One-to-many relationships with jobs and reviews

### 3. Job Postings
- Complete job listing capabilities
- Detailed job information including title, description, requirements, salary, location
- Job search and filtering

### 4. Job Applications
- Apply to jobs with resume and cover letter
- Track application status
- Manage applications as a recruiter

### 5. Review System
- Company reviews with ratings
- Insight into company culture and work environment

## Architecture

The project follows a standard layered architecture:

```
├── Controllers (REST APIs)
├── Services (Business Logic)
├── Repositories (Data Access)
├── Models (Entities)
├── Mappers (DTO Conversions)
├── Response Objects (API Responses)
├── Exceptions (Error Handling)
```

### Entity Relationships:
- Users: OneToMany → Job Applications
- Companies: OneToMany → Jobs, Reviews
- Jobs: ManyToOne → Companies, OneToMany → Applications
- Reviews: ManyToOne → Companies, ManyToOne → Users
- JobApplications: ManyToOne → Jobs, ManyToOne → Users

## API Structure

1. Users API (`/api/v1/users`)
2. Companies API (`/api/v1/companies`)
3. Jobs API (`/api/v1/companies/{companyId}/jobs`)
4. Applications API (`/api/v1/jobs/{jobId}/applications`)
5. Reviews API (`/api/v1/companies/{companyId}/reviews`)
6. Health API (`/api/v1/health`)

## Setup and Installation

### Prerequisites
- Java 21 or higher
- Maven
- Docker (optional for containerization)
- PostgreSQL (or Docker for PostgreSQL)

### Local Development Setup

1. Clone the repository
```bash
git clone https://github.com/yourusername/JobNest.git
cd JobNest
```

2. Configure environment variables
Create a `.env` file in the root directory with the following variables:
```
DATABASE_URL=jdbc:postgresql://localhost:5432/jobnest
POSTGRES_USER=postgres
POSTGRES_PASSWORD=yourpassword
KEYCLOAK_ISSUER_URI=http://localhost:8080/realms/JobNest
ALLOWED_ORIGINS=http://localhost:4200
```

3. Run the application
```bash
# Using Maven
./mvnw spring-boot:run

# Using Docker
docker build -t jobnest-backend .
docker run -p 8080:8080 --env-file .env jobnest-backend
```

4. Access the application
- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html

## Deployment

### Deploying to Render

1. Create a new Web Service on Render
2. Connect your GitHub repository
3. Configure the service:
   - Build Command: `./mvnw clean package -DskipTests`
   - Start Command: `java -jar target/Job-Application-0.0.1-SNAPSHOT.jar`
4. Add the required environment variables:
   - `DATABASE_URL`
   - `KEYCLOAK_ISSUER_URI`
   - `ALLOWED_ORIGINS`
   - Any other environment-specific variables

## Environment Variables

| Variable | Description | Example |
|----------|-------------|---------|
| `DATABASE_URL` | PostgreSQL connection URL | `jdbc:postgresql://db.example.com:5432/postgres` |
| `POSTGRES_USER` | Database username | `postgres` |
| `POSTGRES_PASSWORD` | Database password | `password` |
| `KEYCLOAK_ISSUER_URI` | Keycloak realm URL | `https://auth.example.com/realms/JobNest` |
| `ALLOWED_ORIGINS` | CORS allowed origins | `http://localhost:4200,https://example.com` |
| `CLOUDINARY_CLOUD_NAME` | Cloudinary cloud name | `mycloudname` |
| `CLOUDINARY_API_KEY` | Cloudinary API key | `123456789012345` |
| `CLOUDINARY_API_SECRET` | Cloudinary API secret | `abcdefghijklmnopqrstuvwxyz` |
| `CLOUDINARY_UPLOAD_FOLDER` | Cloudinary folder for uploads | `JobNest/uploads` |

## API Documentation

Interactive API documentation is available via Swagger UI when the application is running:
- Local: http://localhost:8080/swagger-ui.html
- Production: [Insert Render Backend URL here]/swagger-ui.html

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

[MIT License](LICENSE)

## Contact

Email Id:- anshpreetsingh1009@gmail.com

Project Link: [https://github.com/yourusername/JobNest](https://github.com/yourusername/JobNest)
