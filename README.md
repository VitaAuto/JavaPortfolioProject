# JavaPortfolioProject
## Overview
This is a multi-module Java application, featuring a RESTful API controller, API and UI automated tests.
Parts of this project (such as templates, API controller) were generated using AI tools and code generators as a base for automated tests.
All automated test scenarios, integration tests, Allure reporting, and test infrastructure were implemented by me and reflect my real commercial experience.
This project demonstrates both the use of modern AI-assisted development and my hands-on skills in building robust test automation.

The project includes:

### API module: 
- Spring Boot REST API controller
- Tests module: Automated tests with Allure reporting
- PostgreSQL: For data storage
- Docker & Docker Compose: For easy local deployment

### UI module:
- Selenide framework
- Tests module: Automated tests with Allure reporting and parallelization

#### Project technologies used
- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Lombok
- JUnit 5
- Selenide
- Cucumber
- RestAssured
- WireMock
- Allure
- Docker, Docker Compose
- OpenAPI (Swagger UI)

#### Getting Started
1. Clone the repository: 
git clone https://github.com/VitaAuto/JavaPortfolioProject.git
2. Start with Docker Compose to activate the database and api-controller (or use @mock tag for running tests with prepared stubs and without starting real database and api-controller):

docker-compose up --build
- The API will be available at: http://localhost:8082
- PostgreSQL will be available on port: 5454

3. API Documentation (Swagger UI)
After starting the API, documentation is available at: http://localhost:8082/swagger-ui.html

#### API Endpoints
- GET /api/orders — Get all orders
- GET /api/orders/{id} — Get order by ID
- POST /api/orders — Create a new order
- PUT /api/orders/{id} — Update an order completely
- PATCH /api/orders/{id} — Partially update an order
- DELETE /api/orders/{id} — Mark order as deleted
- DELETE /api/orders/hard/{id} — Physically delete order

#### Database
- Uses PostgreSQL (via Docker Compose)
- Initialization script: db-init/init.sql
Data is persisted in the pgdata Docker volume

#### Build and run manually
#### Build API

cd api

./gradlew build

#### Run API
java -jar build/libs/api.jar

### Automated testing modules:
The "api-tests" module contains automated integration and API tests for the project.
The "ui-tests" module contains automated UI tests for the project.

#### Key features:

- BDD-style scenarios using Cucumber (feature files in src/test/resources/features)
- JUnit 5 as the test runner
- RestAssured for HTTP API testing
- WireMock for mocking external dependencies (scenarios with @mock tag)
- Selenide for UI testing and parallel running
- Allure for test reporting

#### Running tests
- ./gradlew :api-tests:test - run all tests from API-module
- ./gradlew :api-tests:smokeTest - run only smoke tests from API-module
- ./gradlew :ui-tests:test - run all tests from UI-module
- ./gradlew :ui-tests:smokeTest - run only smoke tests from UI-module

#### Generating allure report
After running tests, generate and open the Allure report (You need Allure CLI installed):
- allure generate tests/allure-results -o allure-report --clean
- allure serve tests/allure-results
- https://vitaauto.github.io/JavaPortfolioProject/ - to see results on GitHub Pages

#### Example features
Feature files are written in Gherkin syntax.

Example: order.feature describes scenarios for getting, creating, updating, and deleting orders.

#### Environment variables (API)
- SPRING_DATASOURCE_URL
- SPRING_DATASOURCE_USERNAME
- SPRING_DATASOURCE_PASSWORD
- JWT_SECRET
- AUTH_USERNAME
- AUTH_PASSWORD
- POSTGRES_DB
- POSTGRES_USER
- POSTGRES_PASSWORD
- VALID_API_LOGIN
- VALID_API_PASSWORD
- INVALID_API_LOGIN
- INVALID_API_PASSWORD

#### Environment variables (UI)
- LOCKED_OUT_PASSWORD
- LOCKED_OUT_USER
- STANDARD_LOGIN_PASSWORD
- STANDARD_LOGIN_USER

All variables are set in .env file (for local running) and GitHub secrets (for CI/CD).