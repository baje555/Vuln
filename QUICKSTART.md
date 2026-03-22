# VulnLab Tech - Quick Start Guide

## 5-Minute Setup

### Step 1: Install MySQL (if not already installed)

```bash
# Ubuntu/Debian
sudo apt-get install mysql-server

# macOS
brew install mysql

# Windows - Download from https://dev.mysql.com/downloads/mysql/
```

### Step 2: Create Database

```bash
mysql -u root -p
```

Then paste this SQL:

```sql
CREATE DATABASE vulnlab_db;
USE vulnlab_db;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'user',
    bio TEXT
);

CREATE TABLE flags (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    level INT NOT NULL UNIQUE,
    flag VARCHAR(255) NOT NULL
);

CREATE TABLE files (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    filename VARCHAR(255) NOT NULL,
    owner_id BIGINT NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES users(id)
);

INSERT INTO users (username, password, role, bio) VALUES
('admin', 'admin123', 'admin', 'Administrator account'),
('user1', 'password1', 'user', 'Test user 1'),
('user2', 'password2', 'user', 'Test user 2'),
('user3', 'password3', 'user', 'Test user 3');

INSERT INTO flags (level, flag) VALUES
(1, 'flag{sql_injection_success}'),
(2, 'flag{xss_reflected}'),
(3, 'flag{stored_xss}'),
(4, 'flag{idor_access}'),
(5, 'flag{file_upload_rce}'),
(6, 'flag{command_injection}'),
(7, 'flag{admin_access}'),
(8, 'flag{csrf_success}'),
(9, 'flag{ssti_rce}'),
(10, 'flag{final_root}');
```

### Step 3: Update Database Credentials

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/vulnlab_db
spring.datasource.username=root
spring.datasource.password=your_mysql_password
```

### Step 4: Build and Run

```bash
# Build
mvn clean package

# Run
mvn spring-boot:run
```

### Step 5: Access the Application

Open your browser and go to: **http://localhost:8080**

## Default Login Credentials

```
Username: admin
Password: admin123
```

Or:

```
Username: user1
Password: password1
```

## Solving Challenges

### Level 1 - SQL Injection
- Go to `/level1/login`
- Try username: `admin' --` with any password
- You should see the flag

### Level 2 - Reflected XSS
- Go to `/level2/search?q=<script>alert('XSS')</script>`
- The flag will appear

### Level 3 - Stored XSS
- Login first
- Go to `/level3/profile`
- Update bio with: `<script>alert('XSS')</script>`
- The flag will appear

### Level 4 - IDOR
- Go to `/level4/user?id=1` (or any user ID)
- You can access any user's profile
- The flag will appear

### Level 5 - File Upload
- Login first
- Go to `/level5/upload`
- Try uploading a `.jsp` or `.exe` file
- The flag will appear

### Level 6 - Command Injection
- Go to `/level6/ping`
- Try: `127.0.0.1; id`
- The flag will appear

### Level 7 - Broken Authentication
- Login as any user
- Go to `/level7/admin`
- The flag will appear (no admin role check!)

### Level 8 - CSRF
- Login first
- Go to `/level8/password`
- Change password (no CSRF token validation)
- The flag will appear

### Level 9 - SSTI
- Go to `/level9/welcome?name=${7*7}`
- The flag will appear

### Level 10 - Final Challenge
- Login first
- Go to `/level10/final`
- Try promoting a user to admin
- The flag will appear

## Troubleshooting

### MySQL Connection Error
- Make sure MySQL is running: `sudo systemctl start mysql`
- Check credentials in `application.properties`
- Verify database exists: `mysql -u root -p -e "SHOW DATABASES;"`

### Port 8080 Already in Use
- Change port in `application.properties`: `server.port=8081`

### Build Fails
- Make sure Java 17+ is installed: `java -version`
- Clear Maven cache: `mvn clean`

## Next Steps

1. Study each vulnerability in detail
2. Try to find alternative exploitation methods
3. Understand how to prevent each vulnerability
4. Read OWASP Top 10 documentation
5. Practice on other CTF platforms

## Resources

- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [OWASP Testing Guide](https://owasp.org/www-project-web-security-testing-guide/)
- [PortSwigger Web Security Academy](https://portswigger.net/web-security)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

---

Happy learning! 🎓
