# 🚑 Resque PH (RQPH) – Desktop Application

## 📌 Overview

**Resque PH (RQPH)** is a desktop-based emergency response and rescue coordination system developed using **Java** and **JavaFX**. The application is designed to improve communication, incident reporting, and rescue operation management within the Philippines.

The system aims to provide an efficient, reliable, and user-friendly platform that helps emergency responders coordinate rescue efforts, monitor incidents, and manage critical information in real time.

---

## ✨ Features

- 📍 Emergency incident reporting
- 🚑 Rescue operation management
- 👥 User account management
- 📊 Incident monitoring and tracking
- 🗄️ Database-driven information storage
- 🔍 Search and filtering functionalities
- 🖥️ User-friendly graphical interface using JavaFX

---

## 🛠️ Technologies Used

### Programming Languages
- Java
- SQL

### Frameworks & Libraries
- JavaFX

### Database
- MySQL

### Development Tools
- NetBeans IDE / IntelliJ IDEA
- MySQL Workbench
- Git & GitHub

---

## 📂 Project Structure

```text
rqph-desktop-app/
│
├── RQPH/
│   ├── src/
│   │   ├── controller/
│   │   ├── model/
│   │   ├── view/
│   │   └── database/
│   │
│   ├── resources/
│   └── build/
│
├── rqph.sql
├── README.md
└── .gitignore
```

> Note: The actual structure may vary depending on the IDE and project configuration.

---

## ⚙️ Installation Guide

### Prerequisites

Before running the project, ensure you have:

- Java JDK 8 or higher
- JavaFX SDK
- MySQL Server
- Git

### Clone the Repository

```bash
git clone https://github.com/yourusername/rqph-desktop-app.git
cd rqph-desktop-app
```

### Configure the Database

1. Open MySQL Workbench.
2. Create a new database.

```sql
CREATE DATABASE rqph_db;
```

3. Import the provided SQL file:

```text
rqph.sql
```

### Configure Database Connection

Update the database credentials in the database configuration file:

```java
String url = "jdbc:mysql://localhost:3306/rqph_db";
String username = "root";
String password = "your_password";
```

### Run the Application

1. Open the project in NetBeans or IntelliJ IDEA.
2. Configure the JavaFX SDK if necessary.
3. Build and run the project.

---

## 🖥️ Screenshots

Add screenshots of the following pages:

- Login Screen
- Dashboard
- Incident Report Form
- Rescue Management Panel
- User Management Module

Example:

```md
![Dashboard](screenshots/dashboard.png)
```

---

## 🎯 Objectives

The primary objectives of Resque PH are:

- Improve emergency response coordination.
- Centralize incident information.
- Provide accurate and timely rescue management.
- Enhance communication among responders.
- Reduce response time during emergencies.

---

## 👨‍💻 Developer

**Railech**  
GitHub: https://github.com/Rai567245

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome.

1. Fork the repository
2. Create a feature branch

```bash
git checkout -b feature/NewFeature
```

3. Commit your changes

```bash
git commit -m "Add new feature"
```

4. Push to the branch

```bash
git push origin feature/NewFeature
```

5. Open a Pull Request

---

## 📜 License

This project is intended for educational and research purposes.

---

## 🇵🇭 About Resque PH

Resque PH is a desktop application built to support rescue and emergency coordination efforts in the Philippines through a streamlined and accessible system. By leveraging modern software technologies, the project seeks to contribute to safer and more effective emergency response operations.
