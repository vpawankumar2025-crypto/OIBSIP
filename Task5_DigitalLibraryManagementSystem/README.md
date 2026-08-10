# Digital Library Management System

A web-based library system built with **Spring Boot + Thymeleaf + Spring Data
JPA + H2**, with separate Admin and User (member) roles.

## Tech Stack
- Java 17, Spring Boot 3.2
- Spring MVC + Thymeleaf (server-rendered HTML)
- Spring Data JPA + H2 (file-based, no external DB server needed)
- Plain session-based auth (`HttpSession` + a `HandlerInterceptor`) — no
  Spring Security dependency required

## Project Structure
```
src/main/java/com/library/
  model/        Book, User, Loan, Reservation, ContactMessage
  repository/   Spring Data JPA repositories
  service/      LibraryService – all business logic
  controller/   AuthController, HomeController, UserController, AdminController
  config/       AuthInterceptor + WebConfig (route protection)
src/main/resources/
  templates/    Thymeleaf HTML pages
  static/css/   stylesheet
  application.properties
```

## Features

### Admin Module
- Admin login with access to all system features
- Add / edit / delete book records (title, author, ISBN, category, quantity)
- View all issued books and their due dates (with overdue flag)
- View all registered members
- Fine management: mark fines as paid
- Review active advance-booking reservations
- Review contact/query messages and mark them resolved

### User Module
- User registration and login
- Browse the book catalogue, filter by category or search by title/author
- Issue a book (decrements available quantity, records a 14-day due date)
- Return a book (increments available quantity)
- Automatic fine calculation on overdue returns (₹5/day, configurable in `Loan.FINE_PER_DAY`)
- Advance booking: reserve a book that's currently fully issued
- Contact/query form (stored in the DB, visible to admins)

## Requirements
- JDK 17+
- Maven 3.8+ (or use an IDE with Maven support — IntelliJ IDEA, Eclipse, VS Code)

## How to Run
```bash
mvn spring-boot:run
```
Then open **http://localhost:8080** in your browser.

(First build will download dependencies from Maven Central — an internet
connection is required for that one-time step.)

## Demo Accounts
| Role  | Username | Password  |
|-------|----------|-----------|
| Admin | admin    | admin123  |
| Member| member   | member123 |

Demo books (Clean Code, Effective Java, etc.) and both accounts are seeded
automatically on first startup via `LibraryService.seedData()`.

## Database
Data is stored in a local H2 file database at `./data/librarydb` (created
automatically). You can inspect it live at **http://localhost:8080/h2-console**
using JDBC URL `jdbc:h2:file:./data/librarydb`, user `sa`, blank password.

The `data/` folder is git-ignored so the repository stays clean — a fresh
copy will reseed automatically on first run.
