
# LOMS - Loan Origination & Management System

Lightweight Spring Boot application for loan origination, credit evaluation, disbursal and repayment tracking.

## Quick Start

Prerequisites:
- JDK 17+
- Maven (or use included wrappers: `./mvnw` / `mvnw.cmd`)

## Build and run

1. Clone the project:
```powershell
git clone https://your.git.repo.url/your-repo.git
cd new_loan_management_system
```

2. Change database credentials:
- Open application properties files (e.g. `src/main/resources/application.properties` and any profile files like `src/main/resources/application-*.properties`) and update:
    - spring.datasource.url (change database name)
    - spring.datasource.username
    - spring.datasource.password

## File Structure
```
C:.
├───.idea
├───.mvn
│   └───wrapper
├───src
│   ├───main
│   │   ├───java
│   │   │   └───com
│   │   │       └───genc
│   │   │           └───loms
│   │   │               ├───controller
│   │   │               ├───dto
│   │   │               ├───entity
│   │   │               ├───repository
│   │   │               └───service
│   │   └───resources
│   │       └───static
│   └───test
│       └───java
│           └───com
│               └───genc
│                   └───loms
```



## API highlights

Key REST controllers:
- [`com.genc.loms.controller.LoanApplicationController`](src/main/java/com/genc/loms/controller/LoanApplicationController.java) — submit and query loan applications (endpoints: `/submitloanapplication`, `/getCustomerLoans`, `/getPendingLoanApplication`, `/getAllApplication`)
- [`com.genc.loms.controller.LoanDisbursalController`](src/main/java/com/genc/loms/controller/LoanDisbursalController.java) — approve/reject loans (`/approveLoan/{id}`, `/reject/{id}`, `/getDisbursalById/{id}`)
- [`com.genc.loms.controller.LoanRepaymentController`](src/main/java/com/genc/loms/controller/LoanRepaymentController.java) — repayment endpoints (`/loan-repayment/{applicationId}`, `/set-DueDate/{applicationId}`, `/payment-history/{id}`)
- [`com.genc.loms.controller.CreditEvaluationController`](src/main/java/com/genc/loms/controller/CreditEvaluationController.java) — credit score evaluation endpoints
- [`com.genc.loms.controller.CustomerController`](src/main/java/com/genc/loms/controller/CustomerController.java) — customer register/login (`/customer/register`, `/customer/login`)
- [`com.genc.loms.controller.UserController`](src/main/java/com/genc/loms/controller/UserController.java) — employee/user register/login

Important services:
- [`com.genc.loms.service.LoanApplicationService`](src/main/java/com/genc/loms/service/LoanApplicationService.java)
- [`com.genc.loms.service.LoanDisbursalService`](src/main/java/com/genc/loms/service/LoanDisbursalService.java)
- [`com.genc.loms.service.LoanRepaymentService`](src/main/java/com/genc/loms/service/LoanRepaymentService.java)
- [`com.genc.loms.service.CreditEvaluationService`](src/main/java/com/genc/loms/service/CreditEvaluationService.java)
- [`com.genc.loms.service.CustomerService`](src/main/java/com/genc/loms/service/CustomerService.java)
- [`com.genc.loms.service.UserService`](src/main/java/com/genc/loms/service/UserService.java)

Repositories:
- [`com.genc.loms.repository.CustomerRepo`](src/main/java/com/genc/loms/repository/CustomerRepo.java)
- [`com.genc.loms.repository.LoanApplicationRepo`](src/main/java/com/genc/loms/repository/LoanApplicationRepo.java)
- [`com.genc.loms.repository.LoanDisbursalRepo`](src/main/java/com/genc/loms/repository/LoanDisbursalRepo.java)
- [`com.genc.loms.repository.CreditScoreRepo`](src/main/java/com/genc/loms/repository/CreditScoreRepo.java)
- [`com.genc.loms.repository.LoanRepaymentRepo`](src/main/java/com/genc/loms/repository/LoanRepaymentRepo.java)
- [`com.genc.loms.repository.UserRepo`](src/main/java/com/genc/loms/repository/UserRepo.java)

## Data model (key entities)

- [`com.genc.loms.entity.Customer`](src/main/java/com/genc/loms/entity/Customer.java) — customer profile
- [`com.genc.loms.entity.LoanApplication`](src/main/java/com/genc/loms/entity/LoanApplication.java) — application, loanType/status enums
- [`com.genc.loms.entity.CreditScore`](src/main/java/com/genc/loms/entity/CreditScore.java) — credit score records
- [`com.genc.loms.entity.LoanRepayment`](src/main/java/com/genc/loms/entity/LoanRepayment.java) — repayment history
- [`com.genc.loms.entity.LoanDisbursal`](src/main/java/com/genc/loms/entity/LoanDisbursal.java) — disbursal records
- [`com.genc.loms.entity.User`](src/main/java/com/genc/loms/entity/User.java) — employee user

## Frontend / Static pages

Static UI pages (served from classpath):
- [src/main/resources/static/index.html](src/main/resources/static/index.html)
- [src/main/resources/static/Home.html](src/main/resources/static/Home.html)
- [src/main/resources/static/loanApplicationForm.html](src/main/resources/static/loanApplicationForm.html)
- [src/main/resources/static/credit-evaluation.html](src/main/resources/static/credit-evaluation.html)
- [src/main/resources/static/status.html](src/main/resources/static/status.html)
- [src/main/resources/static/ApproveRejectLoan.html](src/main/resources/static/ApproveRejectLoan.html)
- [src/main/resources/static/employeeHome.html](src/main/resources/static/employeeHome.html)
- [src/main/resources/static/dashboard-home.html](src/main/resources/static/dashboard-home.html)

These pages call the API endpoints above (see inline JS in each file).

## How pieces fit

- Controllers expose REST endpoints -> delegate to Service classes -> Services use Repository interfaces -> JPA persists Entities.
- Credit evaluation is implemented in [`com.genc.loms.service.CreditEvaluationService`](src/main/java/com/genc/loms/service/CreditEvaluationService.java) and persists [`com.genc.loms.entity.CreditScore`](src/main/java/com/genc/loms/entity/CreditScore.java).
- Loan lifecycle: submit via `LoanApplicationController` -> employees approve via `LoanDisbursalController` -> `LoanDisbursalService` updates statuses and creates `LoanDisbursal` record. Repayments managed via `LoanRepaymentController` and [`com.genc.loms.service.LoanRepaymentService`](src/main/java/com/genc/loms/service/LoanRepaymentService.java).

## Useful files

- Application bootstrap: [`src/main/java/com/genc/loms/LomsApplication.java`](src/main/java/com/genc/loms/LomsApplication.java)
- Main config: [`src/main/resources/application.properties`](src/main/resources/application.properties)
- Build: [`pom.xml`](pom.xml)
- Tests: [`src/test/java/com/genc/loms/LomsApplicationTests.java`](src/test/java/com/genc/loms/LomsApplicationTests.java)

## Development notes & tips

- Session-handling between static pages and controllers is used for customer context (see `CustomerController` and how `LoanApplicationController` expects session data).
- Repositories contain custom queries (see [`LoanApplicationRepo.findAmountRequestedByApplicationId`](src/main/java/com/genc/loms/repository/LoanApplicationRepo.java) and methods in [`CreditScoreRepo`](src/main/java/com/genc/loms/repository/CreditScoreRepo.java)).
- Passwords in entities are base64-encoded in setters (see [`Customer.setPassword`](src/main/java/com/genc/loms/entity/Customer.java) and [`User.setPassword`](src/main/java/com/genc/loms/entity/User.java)). Consider using a secure hash (BCrypt) before production.

## Running locally with static UI

1. Start the application (see Quick Start).
2. Open browser to: http://localhost:8082/index.html (static files are served from `/static`).

## Contributing

- Follow existing service/repository/controller patterns.
- Add unit tests under `src/test/java`.
- Run `./mvnw -DskipTests=false verify` before PR.

