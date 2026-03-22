# VulnLab Tech - Deliberately Vulnerable Web Application for CTF Training

A deliberately vulnerable web application designed for cybersecurity learning and Capture The Flag (CTF) practice. This application demonstrates common web vulnerabilities in an isolated, educational environment.

## ⚠️ WARNING

**This application is intentionally vulnerable and should ONLY be used for:**
- Educational purposes
- Security training and learning
- CTF competitions
- Authorized penetration testing

**DO NOT deploy this application on production servers or expose it to the internet without proper security measures.**

## Project Structure

```
vulnlab-tech/
├── src/
│   ├── main/
│   │   ├── java/com/vulnlab/
│   │   │   ├── VulnLabApplication.java
│   │   │   ├── controller/
│   │   │   │   ├── MainController.java
│   │   │   │   ├── Level1Controller.java (SQL Injection)
│   │   │   │   ├── Level2Controller.java (Reflected XSS)
│   │   │   │   ├── Level3Controller.java (Stored XSS)
│   │   │   │   ├── Level4Controller.java (IDOR)
│   │   │   │   ├── Level5Controller.java (File Upload)
│   │   │   │   ├── Level6Controller.java (Command Injection)
│   │   │   │   ├── Level7Controller.java (Broken Authentication)
│   │   │   │   ├── Level8Controller.java (CSRF)
│   │   │   │   ├── Level9Controller.java (SSTI)
│   │   │   │   └── Level10Controller.java (Final Challenge)
│   │   │   ├── entity/
│   │   │   │   ├── User.java
│   │   │   │   ├── Flag.java
│   │   │   │   └── UploadedFile.java
│   │   │   └── repository/
│   │   │       ├── UserRepository.java
│   │   │       ├── FlagRepository.java
│   │   │       └── FileRepository.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── schema.sql
│   │       └── templates/
│   │           ├── index.html
│   │           ├── register.html
│   │           ├── login.html
│   │           ├── dashboard.html
│   │           ├── level1.html through level10.html
│   │           └── layout.html
├── pom.xml
└── README.md
```

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+

## Setup Instructions

### 1. Install MySQL

```bash
# On Ubuntu/Debian
sudo apt-get install mysql-server

# On macOS
brew install mysql

# On Windows
# Download from https://dev.mysql.com/downloads/mysql/
```

### 2. Create Database

```bash
mysql -u root -p < src/main/resources/schema.sql
```

Or manually:

```sql
CREATE DATABASE vulnlab_db;
USE vulnlab_db;

-- Run the SQL from src/main/resources/schema.sql
```

### 3. Update Database Credentials

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/vulnlab_db
spring.datasource.username=root
spring.datasource.password=your_password
```

### 4. Build the Application

```bash
mvn clean package
```

### 5. Run the Application

```bash
mvn spring-boot:run
```

Or:

```bash
java -jar target/vulnlab-tech-1.0.0.jar
```

The application will start on `http://localhost:8080`

## Vulnerabilities Included

### Level 1 - SQL Injection (Easy)
- **URL:** `/level1/login`
- **Vulnerability:** Unprotected SQL query concatenation
- **Hint:** Try using SQL operators like `' OR '1'='1` or `' --`
- **Flag:** `flag{sql_injection_success}`

### Level 2 - Reflected XSS (Easy)
- **URL:** `/level2/search?q=`
- **Vulnerability:** User input reflected without sanitization
- **Hint:** Try injecting `<script>alert('XSS')</script>`
- **Flag:** `flag{xss_reflected}`

### Level 3 - Stored XSS (Easy)
- **URL:** `/level3/profile`
- **Vulnerability:** User input stored and displayed without sanitization
- **Hint:** Update your bio with XSS payload
- **Flag:** `flag{stored_xss}`

### Level 4 - IDOR (Easy)
- **URL:** `/level4/user?id=`
- **Vulnerability:** No access control on user profiles
- **Hint:** Try different user IDs (1, 2, 3, etc.)
- **Flag:** `flag{idor_access}`

### Level 5 - File Upload (Medium)
- **URL:** `/level5/upload`
- **Vulnerability:** No file type validation
- **Hint:** Try uploading `.jsp`, `.exe`, or `.sh` files
- **Flag:** `flag{file_upload_rce}`

### Level 6 - Command Injection (Medium)
- **URL:** `/level6/ping`
- **Vulnerability:** User input passed to system commands
- **Hint:** Try `127.0.0.1; id` or `127.0.0.1 | whoami`
- **Flag:** `flag{command_injection}`

### Level 7 - Broken Authentication (Medium)
- **URL:** `/level7/admin`
- **Vulnerability:** No proper role verification
- **Hint:** Login as any user and access the admin panel
- **Flag:** `flag{admin_access}`

### Level 8 - CSRF (Medium)
- **URL:** `/level8/password`
- **Vulnerability:** No CSRF token validation
- **Hint:** Create a form on another page to change password
- **Flag:** `flag{csrf_success}`

### Level 9 - SSTI (Hard)
- **URL:** `/level9/welcome?name=`
- **Vulnerability:** Server-side template injection
- **Hint:** Try `${7*7}` or `#{7*7}`
- **Flag:** `flag{ssti_rce}`

### Level 10 - Final Challenge (Hard)
- **URL:** `/level10/final`
- **Vulnerability:** Chaining multiple vulnerabilities
- **Hint:** Combine techniques from previous levels
- **Flag:** `flag{final_root}`

## Default Credentials

```
Username: admin
Password: admin123

Username: user1
Password: password1

Username: user2
Password: password2

Username: user3
Password: password3
```

## Routes

| Route | Purpose |
|-------|---------|
| `/` | Homepage with level list |
| `/register` | User registration |
| `/login` | User login |
| `/dashboard` | User dashboard |
| `/logout` | Logout |
| `/level1/login` | SQL Injection challenge |
| `/level2/search` | Reflected XSS challenge |
| `/level3/profile` | Stored XSS challenge |
| `/level4/user` | IDOR challenge |
| `/level5/upload` | File Upload challenge |
| `/level6/ping` | Command Injection challenge |
| `/level7/admin` | Broken Authentication challenge |
| `/level8/password` | CSRF challenge |
| `/level9/welcome` | SSTI challenge |
| `/level10/final` | Final Challenge |

## Technology Stack

- **Backend:** Spring Boot 3.1.5
- **Frontend:** HTML5, CSS3, Vanilla JavaScript
- **Database:** MySQL 8.0
- **Build Tool:** Maven
- **Java Version:** 17+

## Learning Objectives

This application teaches:

1. **SQL Injection** - How to exploit unprotected database queries
2. **Cross-Site Scripting (XSS)** - Both reflected and stored variants
3. **Insecure Direct Object Reference (IDOR)** - Access control vulnerabilities
4. **File Upload Exploitation** - Uploading malicious files
5. **Command Injection** - Executing arbitrary system commands
6. **Broken Authentication** - Bypassing authentication mechanisms
7. **Cross-Site Request Forgery (CSRF)** - Forging requests on behalf of users
8. **Server-Side Template Injection (SSTI)** - Injecting template expressions
9. **Access Control Vulnerabilities** - Improper authorization checks
10. **Vulnerability Chaining** - Combining multiple vulnerabilities

## Important Notes

- Each vulnerability is intentionally left unpatched for learning purposes
- The application uses simple, clear code to demonstrate vulnerabilities
- Flags are displayed when vulnerabilities are successfully exploited
- The application runs on port 8080 by default
- Session-based authentication is used (not production-ready)

## Disclaimer

This project is for educational purposes only. Users are responsible for:
- Using this application only in authorized environments
- Not deploying it on public-facing servers
- Understanding the security implications of the vulnerabilities
- Following all applicable laws and regulations

## License

Educational Use Only - Not for Production

## Support

For questions or issues, refer to the OWASP Top 10 and security best practices documentation.

---

**Remember:** The goal is to learn how vulnerabilities work so you can prevent them in real applications!
