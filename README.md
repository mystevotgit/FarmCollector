# FarmCollector API

## Introduction

FarmCollector is a Spring Boot application that helps farmers collect and report data on crops planted and harvested for each field in their farm across different seasons. The application includes endpoints for managing farms, fields, crops, and seasons, and provides reports comparing expected vs actual crop yields.

## Features

- Manage farms, fields, crops, and seasons
- Collect data on planting and harvesting
- Generate reports on expected vs actual crop yields
- Simple text-based reports accessible via browser
- API documentation using Swagger

## Prerequisites

- Java 17
- Maven

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/mystevotgit/FarmCollector.git
cd farmcollector
```

### Build and Run the Application
Use Maven to build and run the application:
```bash
mvn clean install
mvn spring-boot:run

```

The application will be available at http://localhost:8080.

## API Endpoints
### Farm Endpoints

- Get All Farms
  - URL: /api/farms
  - Method: GET
  - Description: Retrieve all farms.

- Create Farm
  -   URL: /api/farms
  - Method: POST
  - Description: Create a new farm.
  - Request Body:
  ```agsl
    {
       "name": "MyFarm"
    }
  ```
  Field Endpoints
  Get All Fields

URL: /api/fields
Method: GET
Description: Retrieve all fields.
Create Field

URL: /api/fields
Method: POST
Description: Create a new field.
Request Body:
    ```bash
    {
    "name": "MyFarm"
    }
    ```


