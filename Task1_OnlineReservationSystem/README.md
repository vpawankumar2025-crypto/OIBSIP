# Online Reservation System (Train Ticketing)

A Swing GUI application for booking and cancelling train tickets, backed by
a local SQLite database via JDBC.

## Classes
- `LoginForm` — entry point; username + password login
- `MainDashboard` — hub linking to booking / cancellation
- `ReservationForm` — booking form with auto-fill and PNR generation
- `CancellationForm` — fetch a booking by PNR and cancel it
- `DBConnection` — JDBC connection + schema bootstrap (SQLite)

## Features
- Login form with access denied on invalid credentials
- Reservation form: passenger name, train number, train name
  (auto-populated via "Auto-fill Train Name" button), class, date,
  source, destination
- Book button saves to DB and generates a unique PNR
- Confirmation dialog with full booking details
- Cancellation form: PNR + Fetch button shows booking details
- Confirm cancellation with an "Are you sure?" dialog; removes the record
- Input validation: required fields, numeric train number, `yyyy-MM-dd` date format
- Uses `PreparedStatement` throughout to prevent SQL injection

## Requirements
- JDK 11+
- [sqlite-jdbc driver](https://github.com/xerial/sqlite-jdbc) on the classpath
  (Maven coordinate: `org.xerial:sqlite-jdbc:3.45.0.0`, or download the jar
  directly from the Maven Central / GitHub releases page)

## How to Run

### Option A — plain javac (with the driver jar downloaded locally)
```bash
cd src
javac -cp .:sqlite-jdbc-3.45.0.0.jar *.java
java -cp .:sqlite-jdbc-3.45.0.0.jar LoginForm
```

### Option B — Maven
Create a `pom.xml` alongside `src/` with:
```xml
<dependency>
    <groupId>org.xerial</groupId>
    <artifactId>sqlite-jdbc</artifactId>
    <version>3.45.0.0</version>
</dependency>
```
then run via your IDE (IntelliJ / Eclipse / VS Code) with the dependency resolved.

## Demo Login
- Username: `admin`
- Password: `admin123`

The database file `reservation.db` is created automatically on first run,
along with demo train records (e.g. train number `12951`).
