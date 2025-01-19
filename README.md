# Business accounting app API

This is an Api for a Business accounting app that supports adding and removing clients and keeping track of orders

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Installation](#installation)

## Features

- Clients
- Orders
- Admin and regular users (in progress)


## Technologies Used

- ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white) 
- ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white) 
- ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white) 
- ![Docker](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white) 
- ![Kafka](https://img.shields.io/badge/Apache%20Kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white) 
## Installation

Follow these steps to get the project up and running on your local machine.

### Prerequisites
- **Docker**: You should have docker and docker-compose installed on your system
- **Java**: Ensure you have Java installed (`java -version` should return at least Java 21).
- **Maven**: Ensure Maven is installed (`mvn -version`).


### Steps

1. Clone the repository:
   ```sh
   git clone https://github.com/arseniyGoryagin/business-accounting-app.git
   cd my-spring-project
2. Run docker-compose
     ```sh
    docker-compose up -d --build
3. You need to enter the postgres docker container and create a User that can login to the app
     

