# SebastianMutukuSpringTest

This is a Spring Boot application that provides project and task management functionality, including the ability to
manage tasks within projects, fetch project details, and view project summaries.

## Table of Contents

- [Project Overview](#project-overview)
- [Technologies Used](#technologies-used)
- [Endpoints](#endpoints)
    - [Project Endpoints](#project-endpoints)
    - [Task Endpoints](#task-endpoints)
- [Request & Response Format](#request--response-format)
    - [Project Summary Response](#project-summary-response)
- [Setup and Configuration](#setup-and-configuration)
    - [Running with Docker Compose](#running-with-docker-compose)

## Project Overview

The **SebastianMutukuSpringTest** project is designed to demonstrate the management of projects and tasks using a
RESTful API. Key functionalities include:

- **Create projects** and **assign tasks** to projects.
- **Retrieve project details** with associated tasks.
- **Fetch project summaries**, including task counts grouped by status.

## Technologies Used

- **Spring Boot** (3.4.0)
- **Spring Web MVC** (6.2.0)
- **Spring Data JPA** for database interactions
- **H2 Database** for runtime storage
- **Swagger UI** for API documentation
- **Jakarta Validation API** (3.1.0) for input validation
- **Lombok** for reducing boilerplate code

## Endpoints

### Project Endpoints

#### 1. **Create Project**

- **URL**: `/api/v1/projects/create`
- **Method**: `POST`
- **Request Body**: JSON containing project details
- **Response**: Returns the created project details.

Example Request:



#### 2. **Fetch All Projects**
- **URL**: `/api/v1/projects/findAll`
- **Method**: `GET`
- **Request Params**:
  - `page`: The page number for pagination.
  - `size`: The number of items per page for pagination.
- **Response**: Returns a paginated list of all projects.

#### 3. **Get Project Details by ID**
- **URL**: `/api/v1/projects/{projectId}`
- **Method**: `GET`
- **Request Params**:
  - `projectId`: (Path variable) The unique identifier of the project.
- **Response**: Returns the details of a specific project by its ID.

#### 4. **Add Tasks to Project**
- **URL**: `/api/v1/projects/{projectId}/tasks`
- **Method**: `POST`
- **Request Body**: JSON containing task details (e.g., title, description, dueDate).
- **Response**: Returns the created task details.

#### 5. **Fetch Tasks by Project ID**
- **URL**: `/api/v1/projects/{projectId}/tasks`
- **Method**: `GET`
- **Request Params**:
  - `status`: (Optional) The status of the tasks to filter by (e.g., OPEN, IN_PROGRESS, COMPLETED).
  - `dueDate`: (Optional) The due date to filter tasks by. Must be in `yyyy-MM-dd` format.
  - `page`: The page number for pagination.
  - `size`: The number of items per page for pagination.
- **Response**: Returns a paginated list of tasks associated with the specified project.

#### 6. **Project Summary**
- **URL**: `/api/v1/projects/projects/summary`
- **Method**: `GET`
- **Request Params**:
  - `page`: The page number for pagination.
  - `size`: The number of items per page for pagination.
- **Response**: Returns a paginated summary of all projects with a count of tasks grouped by status.
