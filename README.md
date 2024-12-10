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

This Real-Time Event Ticketing System is designed to manage concurrent ticket releases by vendors and purchases by customers, leveraging multi-threading and the Producer-Consumer pattern. The system maintains data integrity in a dynamic, real-time environment and provides essential reporting features, making it ideal for handling high-demand events with concurrent ticketing needs.
This system demonstrates it power of handling concurrent requests using multithreading

---

## 👾 Features

- Concurrency Management: Simultaneous handling of ticket releases and purchases.
- Data Integrity: Thread-safe operations using synchronization for consistent ticket availability.
- Multi-threaded Environment: Employs producer-consumer threading to manage multiple vendors and customers.
- Real-Time Updates: Provides a UI with real-time status on ticket availability and transactions.
- Basic Logging: Tracks and records system activities for audit and troubleshooting.
- Websocket Usage : using websockets for realtime updates
- Api Usage : Using api to connect front end and backend

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
    │   │         ├── config
    │   │         │    └── WebSocketConfig.java
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
       ├── tailwind.config.js
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
       │         │    └── configuration.ts
       │         ├── services
       │         │    └── LogService.java
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
**Tailwind CSS** [![TailwindCSS](https://img.shields.io/badge/Tailwind%20CSS-%2338B2AC.svg?logo=tailwind-css&logoColor=white)](#): `Version: >= 3.x.x` </br>
**TypeScript** [![TypeScript](https://img.shields.io/badge/TypeScript-3178C6?logo=typescript&logoColor=fff)](#): `Version: >= 4.x.x` </br>'
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

### 🧪 Tests

Execute the test suite using the following command:

#### Run frontend unit tests:
```sh
❯ ng test
```
#### Run backend tests:
```sh
❯ ./mvnw test
```

---

## 📌 Project Roadmap

- [X] **` Project Setup and Planning`**: Plan project structure, set up folders and files, install prerequisites, review requirements, choose tech stack, initialize Git repository, and draft architecture diagrams
- [X] **`Configuration Module and Core Classes`**: Build the configuration module, implement TicketPool with synchronization, create Vendor and Customer classes with threading, and test multi-threading with sample threads.
- [X] **`Multi-threading and Synchronization`**: Enhance thread safety, add vendor ticket release and customer purchase logic, set up logging, and add error handling.
- [X] **`User Interface (UI) Development`**: Design and implement UI layout and controls, connect UI to backend, and test real-time updates.
- [X] **`Ticket Management and Logging Enhancements`**: Enhance TicketPool for edge cases, improve logging with timestamps, strengthen error handling, start documentation, and test concurrency.
- [X] **`Dynamic Vendor/Customer Management`**: Implement functionality to start or stop vendor and customer threads dynamically, and ensure UI support and backend synchronization
- [X] **`Real-Time Analytics`**: Develop a real-time analytics dashboard to display ticket sales, integrate with data sources, and test live data updates.

---

## 🤝 API Documentation

The Real-Time Ticketing System API provides endpoints to configure the system, manage vendors and customers, monitor system status, and manage ticket pool capacity.
For additional details on using each endpoint, refer to the full API documentation on Postman.

---

#### Configuration

1. **Get Configuration**
    - **Endpoint**: `GET /api/v1/system-config`
    - **Description**: Retrieves the current configuration settings.
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
    - **Endpoint**: `POST /api//v1/system-config`
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

1. **Get System Status**
    - **Endpoint**: `GET /api/v1/system/status`
    - **Description**: Retrieves the system's current status (e.g., NOT_CONFIGURED, STARTED, STOPPED, PAUSED).

2. **Start System**
    - **Endpoint**: `POST /api/v1/system/start`
    - **Description**: Starts the system.
    - **Response**: "System started successfully."

3. **Pause System**
    - **Endpoint**: `POST /api/v1/system/pause`
    - **Description**: Pauses the system.
    - **Response**: "System paused successfully."

4. **Stop and Reset System**
    - **Endpoint**: `POST /api/v1/system/stop-reset`
    - **Description**: Stops and resets the system, clearing all actions.
    - **Response**: "System stopped and reset successfully."

---

#### Customer Controller

1. **Retrieve Customer Count**
    - **Endpoint**: `GET /api/v1/customers/count`
    - **Description**: Gets the count of active customers.

2. **Add Customer**
    - **Endpoint**: `POST /api/v1/customers/add`
    - **Description**: Adds a new customer to the system.

3. **Remove Customer**
    - **Endpoint**: `POST /api/v1/customers/remove`
    - **Description**: Removes a customer from the system.

4. **Pause Customer**
    - **Endpoint**: `POST /api/v1/customers/pause`
    - **Description**: Pauses actions for a specific customer.

5. **Resume Customer**
    - **Endpoint**: `POST /api/v1/customers/resume`
    - **Description**: Resumes actions for a specific customer.

---

#### Vendor Controlller

1. **Retrieve Vendor Count**
    - **Endpoint**: `GET /api/v1/vendors/count`
    - **Description**: Retrieves the count of active vendors.

2. **Add Vendor**
    - **Endpoint**: `POST /api/v1/vendors/add`
    - **Description**: Adds a new vendor to the system.

3. **Remove Vendor**
    - **Endpoint**: `POST /api/v1/vendors/remove`
    - **Description**: Removes a vendor from the system.

4. **Pause Vendor**
    - **Endpoint**: `POST /api/v1/vendors/pause`
    - **Description**: Pauses actions for a vendor.

5. **Resume Vendor**
    - **Endpoint**: `POST /api/v1/vendors/resume`
    - **Description**: Resumes actions for a vendor.

---


## 🎗 License

This project is protected under the [MIT License](#) License.

---

## 🙌 Acknowledgments

I would like to express my sincere gratitude to the lecturers at the Institute of Information Technology (IIT) for their invaluable guidance and support throughout this project. Special thanks to the Object-Oriented Programming (OOP) module team, whose insights and teaching laid the foundation for this project. Their dedication to fostering a deep understanding of OOP principles and real-world applications has been instrumental in my development. Thank you for inspiring and empowering me to take on this challenge..

---
