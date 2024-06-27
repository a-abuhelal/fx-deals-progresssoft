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

1. Clone the repository: `git clone <https://github.com/a-abuhelal/fx-deals-progresssoft.git>`
2. Navigate to the project directory: `cd fx-deals`
3. Build the project: `./gradlew build`
4. Run the application: `./gradlew bootRun`

## Configuration

### Database Configuration

- **Example MySQL configuration in application.properties**:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/fx_deals_db
spring.datasource.username=root
spring.datasource.password=password
```
### External Services

#### Prerequisites

Before you start, ensure you have the following software installed on your system:

- Docker
- Docker Compose

#### Docker Configuration

The project uses Docker for containerization and deployment. Follow these steps to configure and run the project using Docker.

#### Dockerfile

The Dockerfile is used to build the Docker image for the Spring Boot application. Here's the Dockerfile:

```dockerfile
# Use an official Gradle image to build the app
FROM gradle:8.8.0-jdk17 AS build
WORKDIR /app
COPY . .

# Build the application
RUN gradle clean bootJar

# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
```

#### Docker Compose

Docker Compose is used to define and run multi-container Docker applications. Here's the docker-compose.yml file:

```yaml
version: '3.8'

services:
  db:
    image: mysql:8.0
    container_name: mysqldb
    environment:
      MYSQL_ROOT_PASSWORD: 1234
      MYSQL_DATABASE: bloomberg_fx
    ports:
      - "3303:3306"
    volumes:
      - mysql-data:/var/lib/mysql
    networks:
      - mysql-spring-boot

  app:
    build: 
      context: .
      dockerfile: DockerFile
    container_name: spring-boot-app
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/bloomberg_fx
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: 1234
    depends_on:
      - db
    networks:
      - mysql-spring-boot
    restart: on-failure
volumes:
  mysql-data:
networks:
  mysql-spring-boot:
    driver: bridge
    
```

#### Running the Application

To build and run the application, follow these steps:

1. Open a terminal and navigate to the directory containing the docker-compose.yml file.
2. Build and start the containers using Docker Compose:

```sh
docker-compose up --build
```

This command will build the Docker images and start the containers as defined in the docker-compose.yml file.

#### Accessing the Application

Once the containers are up and running, the Spring Boot application will be accessible at http://localhost:8080.

#### Stopping the Application

To stop the running containers, use the following command:

```sh
docker-compose down
```

#### Cleaning Up

If you need to remove the Docker volumes to start fresh (this will delete the MySQL data), use the following command:

```sh
docker-compose down -v
```

#### Environment Variables

The following environment variables are used for the database configuration:

- `SPRING_DATASOURCE_URL`: The JDBC URL for the MySQL database.
- `SPRING_DATASOURCE_USERNAME`: The username for accessing the MySQL database.
- `SPRING_DATASOURCE_PASSWORD`: The password for accessing the MySQL database.

These variables are set in the docker-compose.yml file under the environment section of the app service.

## Usage

Describe how to use your application.

### Endpoints

- `POST /api/fx_deals` : Create new FX deals.
- `GET /api/fx_deals` : Retrieve all FX deals.
- `GET /api/fx_deals/{dealUniqueId}` : Retrieve a specific FX deal by unique ID.

### Examples

- **Create a new FX deal**:

`POST /api/fx_deals`
```
[
  {
    "dealUniqueId": "123",
    "fromCurrencyIsoCode": "USD",
    "toCurrencyIsoCode": "EUR",
    "dealTimestamp": "2024-06-28T12:00:00",
    "dealAmount": 1000.0
  }
]
```
**Note: The system deals with inserted deals as a list of "FxDeal" so even when inserting one deal it needs to be written in the list format.**
- **Retrieve all FX deals**:

`GET /api/fx_deals`
```
[
  {
    "dealUniqueId": "123",
    "fromCurrencyIsoCode": "USD",
    "toCurrencyIsoCode": "EUR",
    "dealTimestamp": "2024-06-28T12:00:00",
    "dealAmount": 1000.0
  }
]
```
## Testing

The only form of testing implemented in this system is **Unit Testing**.

### Unit Tests
Describe the unit testing strategy using frameworks like JUnit and Mockito.

## Contributing
Explain how others can contribute to your project.

## License
Specify the license under which your project is distributed.

## Authors
List the authors or contributors to the project.

## Acknowledgments
Acknowledge any individuals, organizations, or resources that you found helpful during the project.
