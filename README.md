# Real-Time Event Ticketing System
Event Ticketing System with Producer-Consumer Pattern

# `REAL-TIME-EVENT-TICKETING-SYSTEM-W2051922`


<p align="left">
		<em>Built with the following tools and technologies:</em>
</p>
<p align="center">
	<img src="https://img.shields.io/badge/Chart.js-FF6384.svg?style=plastic&logo=chartdotjs&logoColor=white" alt="Chart.js">
	<img src="https://img.shields.io/badge/JavaScript-F7DF1E.svg?style=plastic&logo=JavaScript&logoColor=black" alt="JavaScript">
	<img src="https://img.shields.io/badge/HTML5-E34F26.svg?style=plastic&logo=HTML5&logoColor=white" alt="HTML5">
	<img src="https://img.shields.io/badge/TypeScript-3178C6.svg?style=plastic&logo=TypeScript&logoColor=white" alt="TypeScript">
	<img src="https://img.shields.io/badge/JSON-000000.svg?style=plastic&logo=JSON&logoColor=white" alt="JSON">
	<img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=plastic&logo=openjdk&logoColor=white" alt="java">
	<img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=fff" alt="spring">
	<img src="https://img.shields.io/badge/Angular-red?logo=angular&logoColor=fff" alt="spring">
    <img src="https://img.shields.io/badge/PrimeNG-%2338B2AC.svg" alt="PrimeNg">
</p>

<hr>

## 📍 Overview

This Real-Time Event Ticketing System employs the Producer-Consumer model along with multi-threading to manage simultaneous vendor ticket releases and customer purchases. It's an ideal solution for overseeing high-demand events where ticketing needs arise concurrently, as it maintains data integrity in a dynamic, real-time environment and provides essential reporting capabilities. 

This system demonstrates its proficiency in utilizing multithreading to address multiple requests simultaneously. The system's real-time updates and basic logging capabilities make it an excellent choice for managing ticket sales and availability. The system's web interface provides a user-friendly experience, allowing users to monitor ticket availability and transactions in real-time. 

---

## 👾 Features

- \*Concurrency Management\*: Simultaneous handling of ticket releases and purchases.
- \*Data Integrity\*: Thread-safe operations using synchronization for consistent ticket availability.
- \*Multi-threaded Environment\*: Employs producer-consumer threading to manage multiple vendors and customers.
- \*Real-Time Updates\*: Provides a UI with real-time status on ticket availability and transactions.
- \*Basic Logging\*: Tracks and records system activities for audit and troubleshooting.
- \*Polling Usage\*: Utilizes Periodic Polling for real-time updates.
- \*API Usage\*: Connects front end and backend using APIs.

---

## 📂 Repository Structure

```sh
└── ticketing-system/
    ├── README.md
    │── systemConfiguration.json
    ├── backend
    │   ├── .gitattributes
    │   ├── .gitignore
    │   ├── .mvn
    │   ├── Dockerfile
    │   ├── mvnw
    │   ├── mvnw.cmd
    │   ├── pom.xml
    │   ├── src
    │   │     └── main
    │   │         ├── controller
    │   │         │    ├── CustomerController.java
    │   │         │    ├── VendorController.java
    │   │         │    ├── ConfigController.java
    │   │         │    ├── LogController.java
    │   │         │    └── SystemController.java
    │   │         ├── model
    │   │         │    ├── SystemConfiguration.java
    │   │         │    ├── Customer.java
    │   │         │    ├── Vendor.java
    │   │         │    ├── Ticket.java
    │   │         │    └── Log.java
    │   │         ├── repository
    │   │         │    ├── CustomerRepository.java
    │   │         │    ├── LogRepository.java
    │   │         │    ├── SystemConfigRepository.java
    │   │         │    └── VendorRepository.java
    │   │         ├── service
    │   │         │    ├── impl
    │   │         │    │   ├── ConfigServiceImpl.java
    │   │         │    │   ├── CustomerServiceImpl.java
    │   │         │    │   ├── VendorServiceImpl.java
    │   │         │    │   ├── TicketPoolServiceImpl.java
    │   │         │    │   ├── SystemServiceImpl.java
    │   │         │    │   └── LogServiceImpl.java
    │   │         │    ├── runnable
    │   │         │    │   ├── CustomerRunnable.java
    │   │         │    │   └── VendorRunnable.java
    │   │         │    ├── ConfigService.java
    │   │         │    ├── CustomerService.java
    │   │         │    ├── SystemService.java
    │   │         │    ├── TicketPoolService.java
    │   │         │    ├── VendorService.java
    │   │         │    └── LogService.java
    │   │         └──TicketingSystemBackendApplication.java
    │   └── resources
    │          ├── application.properties
    │          ├── log4j2.properties
    │          └── system.log
    └── frontend
       ├── .angular
       ├── .editorconfig
       ├── .gitignore
       ├── README.md
       ├── angular.json
       ├── desktop.ini
       ├── package-lock.json
       ├── package.json
       ├── postcss.config.js
       ├── public
       ├── src
       ├── tsconfig.app.json
       ├── tsconfig.json
       └── tsconfig.spec.json
       ├── src
       │     └── app
       │         ├── components
       │         │    ├── configuration-form
       │         │    │   ├── configuration-form.component.css
       │         │    │   ├── configuration-form.component.html
       │         │    │   ├── configuration-form.component.spec.ts
       │         │    │   └── configuration-form.component.ts
       │         │    ├── control-panel
       │         │    │   ├── control-panel.component.css
       │         │    │   ├── control-panel.component.html
       │         │    │   ├── control-panel.component.spec.ts
       │         │    │   └── control-panel.component.ts
       │         │    ├── count-display
       │         │    │   ├── count-display.component.css
       │         │    │   ├── count-display.component.html
       │         │    │   ├── count-display.component.spec.ts
       │         │    │   └── count-display.component.ts
       │         │    ├── line-chart
       │         │    │   ├── line-chart.component.css
       │         │    │   ├── line-chartm.component.html
       │         │    │   ├── line-chart.component.spec.ts
       │         │    │   └── line-chart.component.ts
       │         │    ├── log-display
       │         │    │   ├── log-display.component.css
       │         │    │   ├── log-display.component.html
       │         │    │   ├── log-display.component.spec.ts
       │         │    │   └── log-display.component.ts
       │         │    └── ticket-availability
       │         │        ├── ticket-availability.component.css
       │         │        ├── ticket-availability.component.html
       │         │        ├── ticket-availability.component.spec.ts
       │         │        └── ticket-availability.component.ts
       │         ├── models
       │         │    └── configuration.model.ts
       │         │── services
       │         │     │── count-display.service.ts
       │         │     │── line-chart.service.ts
       │         │     └── ticket-availability.service.ts
       │         ├── app.component.css
       │         ├── app.component.html
       │         ├── app.component.spec.ts
       │         ├── app.component.ts
       │         ├── app.module.ts
       │         │── app.routes.ts
       │         └── app.config.ts
       ├── assets
       ├── index.html
       ├── main.ts
       └── styles.css
```

---

## 🚀 Getting Started

### 🔖 Prerequisites

**Java Development Kit (JDK)** 	[![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white)](#) : `version Java 17 or higher` </br>
**Node.js** [![NodeJS](https://img.shields.io/badge/Node.js-6DA55F?logo=node.js&logoColor=white)](#): `Version: >= 16.x.x` </br>
**npm** 	[![npm](https://img.shields.io/badge/npm-CB3837?logo=npm&logoColor=fff)](#) : `Version: >= 8.x.x` </br>
**Angular CLI** [![Angular](https://img.shields.io/badge/Angular-%23DD0031.svg?logo=angular&logoColor=white)](#): `Version: ^18.2.8` </br>
**TypeScript** [![TypeScript](https://img.shields.io/badge/TypeScript-3178C6?logo=typescript&logoColor=fff)](#): `Version: >= 4.x.x` </br>
**Maven**: `Version: 3.9.9` </br>

### 📦 Installation

#### Build the project from source:

1. Clone the Real-Time-Event-Ticketing-System-W2051760 repository:
```sh
❯ git clone https://github.com/Chamod07/ticketing-system.git
```

2. Navigate to the project directory:
```sh
❯ cd ticketing-system
```

#### Backend Installation and Usage

1. Navigate to the backend directory:
```sh
❯ cd backend
```
2. Install dependencies and build the project:
```sh
❯ ./mvnw clean install
```

#### Frontend Installation and Usage

1. Navigate to the frontend directory:
```sh
❯ cd frontend
```
2. Install dependencies:
```sh
❯ npm install
```



### 🤖 Usage

To run the project, execute the following command:

#### Backend Usage

1. Navigate to the backend directory:
```sh
❯ cd backend
```
2. Run the backend server:
```sh
❯ ./mvnw spring-boot:run
```

#### Frontend Usage

1. Navigate to the frontend directory:
```sh
❯ cd frontend
```

2. Run the frontend development server:

```sh
❯ ng serve
```

---

## 🤝 API Documentation

The Real-Time Ticketing System API provides endpoints to configure the system, manage vendors and customers, monitor system status, and manage ticket pool capacity.
For additional details on using each endpoint, refer to the full API documentation on Postman.

---

#### Configuration Controller

1. **Get Configuration**
    - **Endpoint**: `GET /api/v1/system-config`
    - **Description**: Retrieves current configuration settings.
    - **Response**:
      ```json
      {
        "totalTickets": 0,
        "ticketReleaseRate": 0,
        "customerRetrievalRate": 0,
        "maxTicketCapacity": 0
      }
      ```

2. **Set Configuration**
    - **Endpoint**: `POST /api/v1/system-config`
    - **Description**: Updates system configuration.
    - **Request Body**:
      ```json
      {
        "totalTickets": 50,
        "ticketReleaseRate": 5,
        "customerRetrievalRate": 4,
        "maxTicketCapacity": 100
      }
      ```

---

#### System Controller

1. **Get System State**
    - **Endpoint**: `GET /api/v1/system/state`
    - **Description**: Retrieves the system's current state (e.g., Started, Stopped, Paused).

2. **Start System**
    - **Endpoint**: `POST /api/v1/system/start`
    - **Description**: Starts the system.
    - **Response**: "System started successfully."

3. **Pause System**
   - **Endpoint**: `POST /api/v1/system/pause`
   - **Description**: Pauses the system.
   - **Response**: "System paused successfully."

4. **Pause System**
   - **Endpoint**: `POST /api/v1/system/pause`
   - **Description**: Pauses the system.
   - **Response**: "System paused successfully."

5. **Stop and Reset System**
    - **Endpoint**: `POST /api/v1/system/stop`
    - **Description**: Stops and resets the system.
    - **Response**: "System stopped successfully."

---

#### Customer Controller

1. **Retrieve Customer Count**
    - **Endpoint**: `GET /api/v1/customer/count`
    - **Description**: Gets the count of active customers.

2. **Retrieve VIP Customer Count**
   - **Endpoint**: `GET /api/v1/customer/count-vip`
   - **Description**: Gets the count of active VIP customers.

3. **Add Customer**
    - **Endpoint**: `POST /api/v1/customer/add`
    - **Description**: Adds a new customer to the system.

4. **Add VIP Customer**
   - **Endpoint**: `POST /api/v1/customer/add-vip`
   - **Description**: Adds a new VIP customer to the system.

5. **Remove Customer**
    - **Endpoint**: `POST /api/v1/customer/remove`
    - **Description**: Removes a customer from the system.

6. **Remove VIP Customer**
   - **Endpoint**: `POST /api/v1/customer/remove-vip`
   - **Description**: Removes a VIP customer from the system.

---

#### Vendor Controller

1. **Retrieve Vendor Count**
    - **Endpoint**: `GET /api/v1/vendor/count`
    - **Description**: Retrieves the count of active vendors.

2. **Add Vendor**
    - **Endpoint**: `POST /api/v1/vendor/add`
    - **Description**: Adds a new vendor to the system.

3. **Remove Vendor**
    - **Endpoint**: `POST /api/v1/vendor/remove`
    - **Description**: Removes a vendor from the system.

---

#### Ticket Controller

1. **Retrieve Available Tickets**
   - **Endpoint**: `GET /api/v1/tickets/availability`
   - **Description**: Retrieves the number of available tickets.

2. **Retrieve Purchased Tickets**
   - **Endpoint**: `GET /api/v1/tickets/purchased`
   - **Description**: Retrieves the total number of tickets purchased.

3. **Retrieve Released Tickets**
   - **Endpoint**: `GET /api/v1/tickets/released`
   - **Description**: Retrieves the total number of tickets released.

4. **Retrieve Maximum Capacity of Tickets**
   - **Endpoint**: `GET /api/v1/tickets/max-capacity`
   - **Description**: Retrieves the maximum capacity of the system.

---

#### Log Controller

1. **Retrieve System Logs**
   - **Endpoint**: `GET /api/v1/log/latest`
   - **Description**: Retrieves the latest logs of the system.
   - **Response**:
     ```json
     {
        "id": 1110,
        "action": "[Vendor-1] Added 5 tickets. (Pool size-25)",
        "timestamp": "2024-12-12T02:00:47.9287035"
     }
     ```

---

## 🎗 License

This project is protected under the [MIT License](#) License.

---

## 🙌 Acknowledgments

I'd like to thank the instructors at the Institute of Information Technology (IIT) for their crucial assistance and support throughout this project. Special thanks to the Object-Oriented Programming (OOP) module team for their thoughts and training that laid the groundwork for this effort. Their commitment to promoting a thorough understanding of OOP principles and real-world applications has been extremely beneficial to my progress. Thank you for motivating and empowering me to face this issue.


---
