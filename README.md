# ☕ Java Development Track — Completed Projects

All 5 tasks from the Java Development track, fully implemented. Each project
lives in its own folder with its own `README.md` covering setup and how to run it.

| # | Project | Folder | Tech |
|---|---------|--------|------|
| 1 | Online Reservation System | `Task1_OnlineReservationSystem/` | Java Swing, JDBC, SQLite |
| 2 | Number Guessing Game | `Task2_NumberGuessingGame/` | Java console |
| 3 | ATM Interface | `Task3_ATMInterface/` | Java console, OOP |
| 4 | Online Examination System | `Task4_OnlineExaminationSystem/` | Java Swing |
| 5 | Digital Library Management System | `Task5_DigitalLibraryManagementSystem/` | Spring Boot, Thymeleaf, JPA, H2 |

## Quick Start Per Project

**Task 2 — Number Guessing Game** (no dependencies)
```bash
cd Task2_NumberGuessingGame/src
javac NumberGuessingGame.java && java NumberGuessingGame
```

**Task 3 — ATM Interface** (no dependencies)
```bash
cd Task3_ATMInterface/src
javac *.java && java Main
```

**Task 4 — Online Examination System** (no dependencies)
```bash
cd Task4_OnlineExaminationSystem/src
javac *.java && java ExamApp
```

**Task 1 — Online Reservation System** (needs the sqlite-jdbc driver jar — see its README)
```bash
cd Task1_OnlineReservationSystem/src
javac -cp .:sqlite-jdbc-3.45.0.0.jar *.java
java -cp .:sqlite-jdbc-3.45.0.0.jar LoginForm
```

**Task 5 — Digital Library Management System** (needs Maven + internet for first build)
```bash
cd Task5_DigitalLibraryManagementSystem
mvn spring-boot:run
# then open http://localhost:8080
```

## Uploading to GitHub

This whole folder is ready to push as-is. From inside the extracted folder:
```bash
git init
git add .
git commit -m "Java Development Track: all 5 projects"
git branch -M main
git remote add origin https://github.com/<your-username>/<your-repo>.git
git push -u origin main
```

Each subfolder is self-contained, so you can also split them into 5 separate
repos later if you prefer — just move each `TaskN_*` folder into its own repo.

## Notes
- Tasks 1, 3, and 4 use `PreparedStatement` / input validation to follow the
  original feature checklists closely (SQL injection prevention, no empty
  fields, numeric/date format checks, etc.).
- Task 1 and Task 5 were **not compiled/tested in this environment** because
  their dependencies (SQLite JDBC driver, Maven Central) require network
  access that wasn't available while building this — double-check they
  compile cleanly once you have that access, and open an issue/fix any typos
  you spot.
- Task 5 is intentionally a simplified but functional Spring Boot app rather
  than a full Servlet/JSP stack, since it's easier to run with zero manual
  server setup (`mvn spring-boot:run` handles everything, including an
  embedded Tomcat and H2 database).
