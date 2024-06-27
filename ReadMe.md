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
      - [Docker Configuration](#docker-configuration)
  - [Usage](#usage)
    - [Endpoints](#endpoints)
    - [Examples](#examples)
  - [Testing](#testing)
    - [Unit Tests](#unit-tests)
  - [Future Enhancements](#future-enhancements)
  - [Authors](#authors)
  - [Side Notes](#Side-Notes)

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

The project includes unit tests to ensure the correctness of the core functionalities. Below are the descriptions of the key unit tests:

#### FxDealController Tests

These tests verify the behavior of the `FxDealController` class, which handles HTTP requests and responses.

- `testCreateDealsSuccess`: This test verifies that the `createDeals` method successfully creates and saves multiple FX deals. It mocks the service layer to simulate the creation of deals without any duplicates and checks that the correct status code and response body are returned.

- `testCreateDealsPartialSuccess`: This test checks the behavior of the `createDeals` method when some of the deals are duplicates. It ensures that the method returns a partial success status code and correctly identifies which deals were not saved due to duplication.

#### FxDealService Tests

These tests validate the business logic implemented in the `FxDealService` class, which manages the core functionalities of saving and retrieving FX deals.

- `testSaveDealSuccess`: This test ensures that a new FX deal is saved successfully when there are no duplicates. It mocks the repository layer to simulate the saving process and verifies that the deal is saved correctly.

- `testSaveDealDuplicate`: This test verifies that an exception is thrown when attempting to save a duplicate FX deal. It ensures that the service correctly identifies and handles duplicate entries, preventing them from being saved.

These unit tests, written using JUnit and Mockito, provide robust coverage for the critical components of the application, ensuring that the core functionalities work as expected and handle edge cases appropriately.

## Future Enhancements

### - Writing a MakeFile for the application

### - Enhanced Error Handling

- **Detailed Error Responses:** Implement more detailed error responses to provide users with specific information about why a request failed.

- **Custom Exception Handling:** Develop custom exceptions for various error scenarios to improve code readability and maintainability.

### - More Enhanced Logging 

- **Detailed Process Logging:** Implementing more detailed and advanced logging for each process and making sure all logs are in sync with the exceptions to give more debugging useful information.

### - User Interface

- **Admin Dashboard:** Develop an admin dashboard for managing FX deals and monitoring system status.

### Other Possible Future Enhancements

- **Authentication & Authorization**: Implement security features to restrict access to the endpoints only to authorized users.
- **Advanced Search**: Add endpoints for searching deals based on different values.
- **Reporting**: Generate reports based on the stored FX deals data.
- **Integration with Other Systems**: Connect with other financial systems for data exchange.

## Authors
**- Abdullah Helal**

## Side Notes

- I added the default CURRENT_TIMESTAMP value constraint to the timestamp column in the db to get a default value for it in the table.
- At first, I struggled with running the application and the error turned out to be the sql dialect, I was using 5 instead  of 8 which was set on my db.
- I came across the error that when trying to retrieve any deal based on its ID (or any other column) it would always return 404 not found, and wasted a significant amount of time trying to solve it, i ended up realizing that it was not a required feature.
- While trying to solve this I figured out that when sending the request through postman it doesn’t even go into the method, because the error handlings and logging techniques I used in the method were not triggered, so this helped me thoroughly understands the significance and benifits of having a proper logging and proper error/exception handling.
- I faced this inconsistency while developing code with help of different ai tools (e.g. chatgpt, github copilot ) or different online sources, which is that a lot of them still consider java 11 the default version.
- Throughout these 2 days, i learned a lot of new concepts. I learned what springboot is and what it does, how to write docker files and how to use docker compose.
- Learned the logic and relationships between springboot services and controllers with the JPA repository to implement an api.
- Learned what postman is and the benifits of using it to initiate HTTP requests.
- While researching i learned what are different types of testing and understood why only unit tests were the ones required for this assignment.
- Learned documentation using MarkDown and the understood different features it has. I aim to learn more about as i believe a proper documentation is a crucial part of a successful project.
- Learned what is REST and used a REST controller instead of a normal one to implement web and http features without having to write html files.
- I started this assignment thinking i could never get it done in two days because a huge part of the concepts needed to do it were not familiar to me. Getting it done "kinda" made me understand that when put enough effort in, nothing is impossible.
- This assignment also made me understand the power of connections and the importance of networking. I have a colleague who is experienced with similar projects who helped me by telling me some best practices and guided me to some references to learn from.