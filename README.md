
# OrangeHRM Selenium Automation Project

## 📌 Project Overview

This project is a Selenium WebDriver automation testing project developed using **Java, Selenium WebDriver, TestNG, Maven, and Page Object Model (POM)**.

The project focuses on automating functional test scenarios for the OrangeHRM demo web application. It demonstrates the use of a structured automation framework to validate web application functionality, interact with web elements, and execute automated test cases.

The objective is to improve test efficiency, reduce repetitive manual effort, and develop reusable and maintainable automation scripts.

---

## 🌐 Application Under Test

- **Application Name:** OrangeHRM
- **Application Type:** Human Resource Management Web Application
- **Application URL:** https://opensource-demo.orangehrmlive.com/
- **Testing Approach:** Selenium UI Automation Testing
- **Automation Language:** Java

---

## 👩‍💻 My Role & Responsibilities

**Automation Tester**

- Analyzed application functionality and identified automation scenarios.
- Designed and developed Selenium WebDriver automation scripts.
- Used Java to implement test automation logic.
- Used TestNG for test execution and test organization.
- Implemented Page Object Model (POM) for reusable and maintainable code.
- Created and used Selenium locators for web element identification.
- Applied explicit waits for handling dynamic web elements.
- Used assertions to validate expected and actual results.
- Organized test cases using TestNG annotations.
- Executed automation scripts and reviewed test results.
- Maintained the automation project using Git and GitHub.

---

## 🎯 Project Objectives

- Automate selected OrangeHRM functional workflows.
- Validate application behavior through automated test cases.
- Reduce repetitive manual testing effort.
- Improve code reusability and maintainability.
- Practice Selenium WebDriver automation using Java.
- Implement a structured test automation framework.
- Demonstrate knowledge of TestNG and Page Object Model.

---

## 🧪 Automation Testing Scope

The project covers selected OrangeHRM modules and functional workflows.

### Modules Covered

- Login
- Leave
- PIM
- Assign Leave
- Recruitment
- Candidates
- Directory
- Maintenance

### Testing Activities

- Login validation
- Navigation and menu interactions
- Form field interactions
- Positive and negative test scenarios
- Web element identification and interaction
- Validation of page elements and messages
- Functional UI automation
---

## 🛠️ Tools & Technologies

| Tool / Technology | Purpose |
|---|---|
| Java | Programming language for automation |
| Selenium WebDriver | Browser automation |
| TestNG | Test execution and test organization |
| Maven | Build and dependency management |
| Page Object Model | Framework design pattern |
| Eclipse IDE | Development environment |
| Google Chrome | Browser testing |
| Git | Version control |
| GitHub | Source code repository |

---

## 🏗️ Automation Framework

The framework is developed using:

- Java
- Selenium WebDriver
- TestNG
- Maven
- Page Object Model (POM)

### Page Object Model (POM)

The Page Object Model design pattern is used to separate page elements and page actions from test classes.

This approach helps improve:

- Code reusability
- Maintainability
- Readability
- Separation of responsibilities
- Ease of updating locators

Page classes contain web element locators and reusable methods, while test classes contain test execution and validation logic.

---

## 🔍 Selenium Concepts Used

- WebDriver initialization
- Browser management
- Selenium locators
- ID, Name, Class Name, CSS Selector, and XPath
- WebElement interactions
- Explicit Wait
- ExpectedConditions
- Assertions
- TestNG annotations
- TestNG test execution
- Page Object Model
- Encapsulation
- Java methods and reusable utilities

---

## 🧠 OOP Concepts Applied

The project supports practice and application of Java Object-Oriented Programming concepts.

### Encapsulation

Page elements and related actions can be organized inside page classes to protect implementation details and provide reusable methods.

### Inheritance

Common setup or utility functionality may be shared through inheritance, where implemented in the framework.

### Polymorphism

Java method overloading and overriding concepts can be applied where appropriate in the automation framework.
---

## 🔄 Sample Automation Workflow

```text
Launch Browser
      ↓
Open OrangeHRM Application
      ↓
Login
      ↓
Navigate to Required Module
      ↓
Perform User Action
      ↓
Validate Expected Result
      ↓
Capture Test Result
      ↓
Logout / Close Browser
```

---

## 🧪 TestNG Framework

TestNG is used for organizing and executing automated test cases.

### TestNG Features

- Test annotations
- Assertions
- Test execution
- Test prioritization
- DataProvider
- Test suite execution

TestNG helps organize test methods and manage the execution of automated test scenarios.

---

## 📊 Test Execution Summary

| Test Metric | Result |
|---|---|
| Total Test Cases | 174|
| Executed | 174 |
| Passed | 164 |
| Failed | 10 |
| Pass Percentage | 92% |

---

## 📁 Project Structure

```text
OrangeHRM-Selenium-Automation
│
├── src
│   ├── main
│   │   └── java
│   │       ├── pages
│   │       └── utilities
│   │
│   └── test
│       └── java
│           └── tests
│
├── pom.xml
├── testng.xml
└── README.md
```


## ▶️ How to Run the Project

### Prerequisites

- Java JDK installed
- Eclipse IDE or another Java IDE
- Maven installed or configured through the IDE
- Google Chrome browser
- Git

### Execution Steps

1. Clone the GitHub repository.
2. Import the project into Eclipse as a Maven project.
3. Verify the Java and Maven configuration.
4. Check the browser and test configuration.
5. Update the test data or configuration if required.
6. Run the TestNG test classes or test suite.
7. Review the execution results.

### Clone Repository

```bash
git clone https://github.com/jalvann/OrangeHRM-Selenium-Automation.git
```

---
## Project Deliverables & Testing Documents
> *Access the complete project documentation below:*

* 📄 **[Test Plan](https://drive.google.com/file/d/1XYNrsom0X1Z4YiDpDawK8kdT3McNo6Gi/view?usp=sharing)** — *Strategy, entry/exit criteria, and automated testing scope.
* 📄 **[Test Cases & Execution Matrix](https://docs.google.com/spreadsheets/d/1XW3U7DbqsnxPg07ntrl9Og4fSdPi_OD3/edit?usp=sharing&ouid=101004487928191801159&rtpof=true&sd=true)** — *Detailed 174 test cases with automated script mappings.
* 📄 **[Defect / Bug Report](https://docs.google.com/spreadsheets/d/1XW3U7DbqsnxPg07ntrl9Og4fSdPi_OD3/edit?usp=sharing&ouid=101004487928191801159&rtpof=true&sd=true)** — *Logs for the 10 failed tests (e.g., BUG_001 to BUG_010).
* 📄 **[Test Summary Report](https://drive.google.com/file/d/1x_wIpZzIYDhYe5AnfchyBxrY1ebEbzVF/view?usp=sharing)** — *Complete executive test summary report.
* 📄 **[Test Sign-off](https://drive.google.com/file/d/1MRwyIuJue3hIZYV1RVVCK8j0Qdi_c7kp/view?usp=sharing)** 
---

## ⭐ Key Highlights

- Developed UI automation scripts using Java and Selenium WebDriver.
- Used TestNG for test execution and test organization.
- Implemented the Page Object Model for maintainability.
- Practiced Selenium locators and explicit waits.
- Applied reusable methods and Java programming concepts.
- Automated selected OrangeHRM functional workflows.
- Maintained the project using Git and GitHub.

---

## 🔗 Project Repository

[OrangeHRM Selenium Automation Project](https://github.com/jalvann/OrangeHRM-Selenium-Automation.git)

---

## 👤 Author

**Jalva NN**

Software Testing | Selenium Automation | Java | TestNG | Manual Testing
