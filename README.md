# DeployWatch — Deployment Monitoring Dashboard

A beginner-friendly Java + Spring Boot project that records deployments, tracks their status, and raises dashboard incidents when a deployment fails. Built to demonstrate REST APIs, role-based access, MySQL, exception handling, layered architecture, and CI/CD integration concepts.

## Features

- Dashboard counters for successful, failed, running deployments, and open incidents.
- Recent deployment table with application, environment, version, status, and last update.
- Automatic HIGH-severity incident creation when a deployment is registered or updated as `FAILED`.
- REST APIs to create and inspect deployments, update status, list incidents, and resolve incidents.
- Spring Security HTTP Basic + form login with `ADMIN`, `ENGINEER`, and `VIEWER` roles.
- Spring Data JPA + MySQL persistence.
- Request validation and consistent JSON error responses.
- GitHub Actions workflow that builds and tests the project with Maven.

> This starter project tracks deployment events sent to its API. It does **not** execute real deployments or automatically connect to Jenkins/GitHub Actions yet. A pipeline can call these APIs to report deployment status.

## Tech stack

Java 17, Spring Boot 3.4, Spring Web, Spring Security, Spring Data JPA, Thymeleaf, MySQL, Maven, GitHub Actions.

## Requirements

- JDK 17+
- Maven 3.9+ (or use the Maven wrapper if you add one)
- MySQL 8+

## Run locally

1. Create the database (or let the configured JDBC URL create it if your MySQL user has permission):

   ```sql
   CREATE DATABASE deploywatch;
   ```

2. Configure environment variables if your local MySQL credentials differ:

   **Windows PowerShell**
   ```powershell
   $env:DB_URL="jdbc:mysql://localhost:3306/deploywatch"
   $env:DB_USERNAME="root"
   $env:DB_PASSWORD="your_mysql_password"
   ```

   **macOS/Linux**
   ```bash
   export DB_URL="jdbc:mysql://localhost:3306/deploywatch"
   export DB_USERNAME="root"
   export DB_PASSWORD="your_mysql_password"
   ```

3. Start the app from the project root:

   ```bash
   mvn spring-boot:run
   ```

4. Open `http://localhost:8080` and sign in.

### Demo accounts

| Username | Password | Role |
|---|---|---|
| `admin` | `Admin@123` | ADMIN, ENGINEER, VIEWER |
| `engineer` | `Engineer@123` | ENGINEER, VIEWER |
| `viewer` | `Viewer@123` | VIEWER |

**Demo only:** These credentials are hard-coded for local demonstration. Before deploying publicly, move users to a database or identity provider, configure secrets through environment variables, use HTTPS, and review CSRF/CORS/API security.

## REST API

All API routes require authentication. Use HTTP Basic for quick local API testing.

### Create a deployment

`POST /api/deployments`

```json
{
  "applicationName": "Inventory API",
  "environment": "staging",
  "version": "1.2.0",
  "commitId": "a1b2c3d",
  "triggeredBy": "github-actions",
  "status": "RUNNING",
  "message": "Deployment started"
}
```

### List recent deployments

`GET /api/deployments`

### Get one deployment

`GET /api/deployments/1`

### Update deployment status

`PATCH /api/deployments/1/status`

```json
{
  "status": "FAILED",
  "message": "Health check failed with HTTP 503"
}
```

A `FAILED` status creates an incident shown on the dashboard.

### List incidents

`GET /api/incidents`

### Resolve an incident

`PATCH /api/incidents/1/resolve`

`ADMIN` and `ENGINEER` roles can create/update deployment events and resolve incidents. A `VIEWER` can view the dashboard and GET endpoints only.

## Example cURL

```bash
curl -u engineer:Engineer@123 -X POST http://localhost:8080/api/deployments   -H "Content-Type: application/json"   -d '{"applicationName":"Inventory API","environment":"staging","version":"1.2.0","commitId":"a1b2c3d","triggeredBy":"github-actions","status":"FAILED","message":"Health check returned 503"}'
```

## Architecture

`Controller → Service → Repository → MySQL`

- **Controller:** HTTP routes and request/response handling.
- **Service:** deployment rules and automatic incident creation.
- **Repository:** persistence through Spring Data JPA.
- **Model:** JPA entities and deployment status enum.
- **Config:** Spring Security and centralized API exception handling.
- **Templates:** Thymeleaf monitoring dashboard.

## CI/CD integration concept

A GitHub Actions/Jenkins pipeline can call `POST /api/deployments` when a release starts, then call `PATCH /api/deployments/{id}/status` after build, deployment, and health checks. For production, use a dedicated API token or service identity and never put real credentials in source control.

## Push to GitHub

1. Create a new empty GitHub repository named `deploywatch`.
2. In this project folder, run:

   ```bash
   git init
   git add .
   git commit -m "Build DeployWatch deployment monitoring dashboard"
   git branch -M main
   git remote add origin https://github.com/YOUR-USERNAME/deploywatch.git
   git push -u origin main
   ```

Replace `YOUR-USERNAME` with your GitHub username.

## Next improvements

- Persist users and roles in MySQL rather than in-memory demo accounts.
- Add JWT authentication and audit history.
- Connect real GitHub Actions/Jenkins webhooks.
- Add service health-check URLs and scheduled health checks.
- Add tests for service rules and API endpoints.
- Add Docker Compose for app + MySQL.
