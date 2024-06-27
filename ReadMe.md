# ClusteredData Warehouse

## Table of Contents

- [Project Title](#project-title)
  - [Overview](#overview)
  - [Table of Contents](#table-of-contents)
  - [Technologies Used](#technologies-used)
  - [Project Structure](#project-structure)
  - [Setup and Installation](#setup-and-installation)
    - [Prerequisites](#prerequisites)
    - [Steps](#steps)
  - [Configuration](#configuration)
    - [Database Configuration](#database-configuration)
    - [External Services](#external-services)
  - [Usage](#usage)
    - [Endpoints](#endpoints)
    - [Examples](#examples)
  - [Testing](#testing)
    - [Unit Tests](#unit-tests)
    - [Integration Tests](#integration-tests)
  - [Deployment](#deployment)
    - [Environment Variables](#environment-variables)
    - [Deployment Steps](#deployment-steps)
  - [Contributing](#contributing)
  - [License](#license)
  - [Authors](#authors)
  - [Acknowledgments](#acknowledgments)

## Overview

### Introduction
The FX Deals project is a Spring Boot application designed to manage and analyze foreign exchange (FX) deals. It provides RESTful endpoints for creating and validating FX deals, ensuring that duplicate deals are not processed, and integrating with a MySQL database for data persistence.

### Objectives
- **Manage FX Deals**: Create and store FX deals, ensuring data integrity and uniqueness.
- **Prevent Duplicates**: Ensure that the system does not process duplicate FX deals.
- **Data Persistence**: Use a MySQL database to store and manage FX deal data.
- **Logging**: Implement logging to track operations and assist in debugging.

### Features
- **RESTful API**: A set of endpoints for managing FX deals.
  - **Create Deals**: Endpoint to create new FX deals, with support for batch processing.
- **Data Validation**: Check for duplicate deals before saving to the database.
- **Error Handling**: Proper error messages and status codes for various scenarios (e.g., duplicates).
- **Unit Testing**: Comprehensive tests using Mockito and JUnit to ensure the robustness of the application.

### Technologies Used
- **Spring Boot**: Framework for building the application.
- **Spring Data JPA**: For interacting with the MySQL database.
- **MySQL**: Database for storing FX deal data.
- **Lombok**: To reduce boilerplate code.
- **Mockito & JUnit**: For unit testing and ensuring code quality.
- **SLF4J**: For logging.
- **Docker & Docker Compose**: For deployment.

### Repository Structure
- **Entity**: Contains the `FxDeal` entity representing the FX deal data.
- **Repository**: Provides data access methods for `FxDeal` entity.
- **Service**: Contains business logic for processing FX deals.
- **Controller**: Defines RESTful endpoints for interacting with the application.
- **Exception**: Custom exception classes for handling specific error scenarios.

### Key Classes
- **FxDeal**: Entity class representing an FX deal.
- **FxDealRepository**: Repository interface for `FxDeal` entity.
- **FxDealService**: Service class containing business logic for FX deals.
- **FxDealController**: Controller class defining RESTful endpoints.

### Key Endpoints
- **POST /api/fx_deals**: Create new FX deals.
- **GET /api/fx_deals**: Retrieve all FX deals.

### Error Handling
- **DuplicateDealException**: Thrown when a duplicate deal is detected.
- **404 Not Found**: Returned when a requested deal is not found.

### Unit Testing
- **Mockito**: Used to mock dependencies and services.
- **JUnit**: Framework for writing and running tests.

### Logging
- **SLF4J**: Used for logging application events, errors, and operations to help with debugging and monitoring.

### Future Enhancements
- **Authentication & Authorization**: Implement security features to restrict access to the endpoints.
- **Advanced Search**: Add endpoints for searching deals based on different values.
- **Reporting**: Generate reports based on the stored FX deals data.
- **Integration with Other Systems**: Connect with other financial systems for data exchange.

This overview provides a high-level understanding of the FX Deals project, its objectives, features, and the technologies used. The project is designed to be scalable, maintainable, and easily extendable to accommodate future enhancements and integrations.

## Technologies Used

- Spring Boot
- MySQL
- Gradle
- Mockito
- JUnit
- Lombok
- Docker
- Docker Compose
- JaCoCo

## Project Structure

Describe the structure of your project's source code. For example:

## Setup and Installation

### Prerequisites

- Java 17
- MySQL

### Steps

1. Clone the repository: `git clone <>`
2. Navigate to the project directory: `cd fx_deals`
3. Build the project: `./gradlew build`
4. Run the application: `./gradlew bootRun`

## Configuration

Explain any configuration settings needed for your project.

### Database Configuration

- **Example MySQL configuration in application.properties**:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/fx_deals_db
spring.datasource.username=root
spring.datasource.password=password
```
### External Services

Document any configurations for external services, if applicable.

## Usage

Describe how to use your application.

### Endpoints

POST /api/fx_deals: Create new FX deals.
GET /api/fx_deals: Retrieve all FX deals.
GET /api/fx_deals/{dealUniqueId}: Retrieve a specific FX deal by unique ID.

### Examples
Provide examples of requests and responses for each endpoint.

- **Create a new FX deal**:

POST /api/fx_deals

{
  "dealUniqueId": "123",
  "fromCurrencyIsoCode": "USD",
  "toCurrencyIsoCode": "EUR",
  "dealTimestamp": "2024-06-28T12:00:00",
  "dealAmount": 1000.0
}

- **Retrieve all FX deals**:

GET /api/fx_deals

[
  {
    "dealUniqueId": "123",
    "fromCurrencyIsoCode": "USD",
    "toCurrencyIsoCode": "EUR",
    "dealTimestamp": "2024-06-28T12:00:00",
    "dealAmount": 1000.0
  }
]

## Testing

Explain how to run tests and the types of tests included.

### Unit Tests
Describe the unit testing strategy using frameworks like JUnit and Mockito.

## Deployment
Provide instructions for deploying your application.

### Environment Variables
List any environment variables or configuration settings required for deployment.

### Deployment Steps

1. Set environment variables.
2. Build the project: ./gradlew build
3. Deploy the artifact to a server.

## Contributing
Explain how others can contribute to your project.

## License
Specify the license under which your project is distributed.

## Authors
List the authors or contributors to the project.

## Acknowledgments
Acknowledge any individuals, organizations, or resources that you found helpful during the project.
