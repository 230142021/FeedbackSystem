# 🚀 Feedback System 

## 📌 Project Overview
Final OOP project assignment. A professional desktop application for managing user feedback with role-based access and database persistence.

## 🛠 Features
- **Full CRUD:** Create, Read, Update, and Soft-Delete feedback.
- **Role System:** Authorization for `ADMIN` and `USER`.
- **Trash Bin:** Restore deleted records.
- **Data Persistence:** Uses PostgreSQL and CSV Export/Import.
- **Mac-Style GUI:** Modern, responsive interface.

## 🧬 OOP Principles Demonstrated
1. **Encapsulation:** All model fields are private with public getters/setters.
2. **Inheritance:** `Admin` class extends `User`.
3. **Polymorphism:** Method `showMenu()` is overridden in `Admin`.
4. **Abstraction:** Logic is separated into Services and Models.

## ⚙️ Setup & Requirements
- **Java:** JDK 17+
- **Database:** PostgreSQL (port 5433)
- **Library:** [PostgreSQL JDBC Driver](https://jdbc.postgresql.org/download/)

### SQL Setup:
```sql
CREATE TABLE feedback (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    message TEXT,
    deleted BOOLEAN DEFAULT FALSE
);
