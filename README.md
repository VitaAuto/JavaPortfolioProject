# Portfolio_Java_Project
## Overview
This is a multi-module Java application, featuring a RESTful API, UI and automated tests.
Parts of this project (such as templates, API controller) were generated using AI tools and code generators.
All automated test scenarios, integration tests, Allure reporting, and test infrastructure were implemented by me and reflect my real commercial experience.
This project demonstrates both the use of modern AI-assisted development and my hands-on skills in building robust test automation.

The project includes:

### API module: 
- Spring Boot REST API controller
- Tests module: Automated tests with Allure reporting
- PostgreSQL: For data storage
- Docker & Docker Compose: For easy local deployment

#### Technologies Used
- Java 17
- Spring Boot 3.2
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
git clone https://gitlab.com/vitalitalipski1/portfolio_java_project.git
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

#### Build and Run Manually
#### Build API

cd api

./gradlew build

#### Run API
java -jar build/libs/api.jar

### Automated Testing modules:
The "api-tests" module contains automated integration and API tests for the project.
The "ui-tests" module contains automated ui tests for the project.

#### Key features:

- BDD-style scenarios using Cucumber (feature files in src/test/resources/features)
- JUnit 5 as the test runner
- RestAssured for HTTP API testing
- WireMock for mocking external dependencies (scenarios with @mock tag)
- Selenide for UI testing
- Allure for test reporting

#### Running Tests
- ./gradlew :api-tests:test - run all tests from API-module
- ./gradlew :api-tests:smokeTest - run only smoke tests from API-module
- ./gradlew :ui-tests:test - run all tests from UI-module
- ./gradlew :ui-tests:smokeTest - run only smoke tests from UI-module

#### Generating Allure Report
After running tests, generate and open the Allure report (You need Allure CLI installed):
- allure generate tests/allure-results -o allure-report --clean
- allure serve tests/allure-results
- https://github.com/VitaAuto/PortfolioJavaProject - to see results on GitHub Pages

#### Example Features
Feature files are written in Gherkin syntax.

Example: order.feature describes scenarios for getting, creating, updating, and deleting orders.

#### Environment Variables (API)
- SPRING_DATASOURCE_URL
- SPRING_DATASOURCE_USERNAME
- SPRING_DATASOURCE_PASSWORD
- SERVER_PORT

#### Environment Variables (UI)
- LOCKED_OUT_PASSWORD
- LOCKED_OUT_USER
- STANDARD_LOGIN_PASSWORD
- STANDARD_LOGIN_USER

All variables are set in docker-compose.yml for the API container.