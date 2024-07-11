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
cd farmCollector
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
    ```json
      {
        "name": "MyFarm"
      }
    ```
### Field Endpoints
- Get All Fields
  - URL: /api/fields
  - Method: GET
  - Description: Retrieve all fields.
  
- Create Field
  - URL: /api/fields
  - Method: POST
  - Description: Create a new field.
  - Request Body:
      ```json
        {
          "name": "Field1",
          "plantingArea": 100.0,
          "farm": {
                    "id": 1
                  }
        }
      ```
### Crop Endpoints

- Get All Crops
  - URL: /api/crops
  - Method: GET
  - Description: Retrieve all crops.
  
- Create Crop
  - URL: /api/crops
  - Method: POST
  - Description: Create a new crop.
  - Request Body:
    ```json
     {
       "type": "Corn",
       "expectedProduct": 50.0,
       "actualProduct": 45.0,
       "field": {
                  "id": 1
                },
       "season": {
                   "id": 1
                 }
     }
    ```
### Season Endpoints

- Get All Seasons
  - URL: /api/seasons
  - Method: GET
  - Description: Retrieve all seasons.
  
- Create Season
  - URL: /api/seasons
  - Method: POST
  - Description: Create a new season.
  - Request Body:
    ```json
     {
       "name": "Spring 2024"
     }
    ```

### Report Endpoints

- Get Report for Farm by Season
  - URL: /api/reports/farm/{farmId}/season/{seasonId}
  - Method: GET
  - Description: Generate a report comparing expected vs actual crop yields for a specific farm and season.
  
- Get Report for Crops by Season
  - URL: /api/reports/crops/season/{seasonId}
  - Method: GET
  - Description: Generate a report comparing expected vs actual crop yields for all farms by crop type for a specific season.

## API Documentation
Swagger UI is available at http://localhost:8080/swagger-ui/ for detailed API documentation.

Testing
Unit tests are included for the API endpoints. To run the tests, use:
```bash
   mvn test
```

