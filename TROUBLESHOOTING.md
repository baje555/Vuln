# VulnLab Tech - Troubleshooting Guide

## Common Issues and Solutions

### 1. MySQL Connection Error

**Error Message:**
```
com.mysql.cj.jdbc.exceptions.CommunicationsException: Communications link failure
```

**Solutions:**

a) **MySQL is not running**
```bash
# Ubuntu/Debian
sudo systemctl start mysql

# macOS
brew services start mysql

# Windows
# Start MySQL from Services or use MySQL Command Line Client
```

b) **Wrong credentials in application.properties**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/vulnlab_db
spring.datasource.username=root
spring.datasource.password=your_actual_password
```

c) **Database doesn't exist**
```bash
mysql -u root -p < src/main/resources/schema.sql
```

d) **Port 3306 is blocked**
```bash
# Check if MySQL is listening
netstat -an | grep 3306

# Or use lsof
lsof -i :3306
```

---

### 2. Port 8080 Already in Use

**Error Message:**
```
Address already in use: bind
```

**Solutions:**

a) **Change the port in application.properties**
```properties
server.port=8081
```

b) **Kill the process using port 8080**
```bash
# Linux/macOS
lsof -i :8080
kill -9 <PID>

# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

c) **Find what's using the port**
```bash
# Linux
sudo netstat -tlnp | grep 8080

# macOS
lsof -i :8080

# Windows
netstat -ano | findstr :8080
```

---

### 3. Java Version Error

**Error Message:**
```
Unsupported class version 61.0
```

**Solution:**

Install Java 17 or higher:

```bash
# Ubuntu/Debian
sudo apt-get install openjdk-17-jdk

# macOS
brew install openjdk@17

# Windows
# Download from https://www.oracle.com/java/technologies/downloads/

# Verify installation
java -version
```

---

### 4. Maven Build Fails

**Error Message:**
```
[ERROR] COMPILATION ERROR
```

**Solutions:**

a) **Clear Maven cache**
```bash
mvn clean
rm -rf ~/.m2/repository
mvn clean package
```

b) **Check Java version**
```bash
java -version
# Should be 17 or higher
```

c) **Update Maven**
```bash
mvn --version
# Should be 3.6 or higher
```

d) **Check internet connection**
```bash
# Maven needs to download dependencies
ping maven.apache.org
```

---

### 5. Application Starts but Pages Show 404

**Error Message:**
```
Whitelabel Error Page
404 - Not Found
```

**Solutions:**

a) **Check if application started correctly**
```
Look for: "Started VulnLabApplication in X seconds"
```

b) **Verify templates are in correct location**
```
src/main/resources/templates/
```

c) **Check application.properties**
```properties
spring.thymeleaf.cache=false
spring.thymeleaf.mode=HTML
```

d) **Restart the application**
```bash
# Stop (Ctrl+C)
# Then run again
mvn spring-boot:run
```

---

### 6. Database Tables Not Created

**Error Message:**
```
Table 'vulnlab_db.users' doesn't exist
```

**Solutions:**

a) **Run schema.sql manually**
```bash
mysql -u root -p vulnlab_db < src/main/resources/schema.sql
```

b) **Verify database exists**
```bash
mysql -u root -p -e "SHOW DATABASES;"
```

c) **Check if tables were created**
```bash
mysql -u root -p vulnlab_db -e "SHOW TABLES;"
```

d) **Recreate database**
```bash
mysql -u root -p
DROP DATABASE vulnlab_db;
CREATE DATABASE vulnlab_db;
USE vulnlab_db;
# Paste schema.sql content
```

---

### 7. Login Not Working

**Issue:** Cannot login with default credentials

**Solutions:**

a) **Verify users exist in database**
```bash
mysql -u root -p vulnlab_db -e "SELECT * FROM users;"
```

b) **Check password is correct**
```
Default: admin / admin123
```

c) **Clear browser cache**
```
Ctrl+Shift+Delete (or Cmd+Shift+Delete on Mac)
```

d) **Check session configuration**
```properties
server.servlet.session.timeout=30m
```

---

### 8. File Upload Not Working

**Issue:** Cannot upload files

**Solutions:**

a) **Create uploads directory**
```bash
mkdir -p uploads
chmod 755 uploads
```

b) **Check file permissions**
```bash
ls -la uploads/
```

c) **Verify upload path in Level5Controller**
```java
String uploadDir = "uploads/";
new File(uploadDir).mkdirs();
```

d) **Check disk space**
```bash
df -h
```

---

### 9. Command Injection Not Working

**Issue:** Ping command returns error

**Solutions:**

a) **Check if ping is available**
```bash
which ping
ping -c 1 127.0.0.1
```

b) **On Windows, use different command**
```
Modify Level6Controller to use: ping -n 1
```

c) **Check firewall settings**
```bash
# Linux
sudo ufw status
```

---

### 10. XSS Payloads Not Executing

**Issue:** JavaScript doesn't execute

**Solutions:**

a) **Check browser console for errors**
```
F12 → Console tab
```

b) **Verify Thymeleaf is using th:utext**
```html
<!-- Correct (allows HTML)
<span th:utext="${result}"></span>

<!-- Wrong (escapes HTML)
<span th:text="${result}"></span>
```

c) **Check browser security settings**
```
Some browsers block inline scripts
```

---

### 11. SSTI Not Working

**Issue:** Template expressions not evaluated

**Solutions:**

a) **Verify Thymeleaf is configured**
```properties
spring.thymeleaf.mode=HTML
```

b) **Check if expression is in template**
```html
<span th:text="${message}"></span>
```

c) **Use correct syntax**
```
Thymeleaf: ${expression}
Not: <%= expression %>
```

---

### 12. Application Crashes on Startup

**Error Message:**
```
Exception in thread "main"
```

**Solutions:**

a) **Check logs for detailed error**
```bash
mvn spring-boot:run 2>&1 | tail -50
```

b) **Verify all dependencies are installed**
```bash
mvn dependency:resolve
```

c) **Check for port conflicts**
```bash
lsof -i :8080
```

d) **Verify database connection**
```bash
mysql -u root -p -e "SELECT 1;"
```

---

### 13. Slow Application Performance

**Issue:** Application is slow or unresponsive

**Solutions:**

a) **Check system resources**
```bash
# CPU and Memory
top

# Disk I/O
iostat -x 1
```

b) **Check database performance**
```bash
mysql -u root -p vulnlab_db -e "SHOW PROCESSLIST;"
```

c) **Enable query logging**
```properties
spring.jpa.show-sql=true
```

d) **Increase heap size**
```bash
export MAVEN_OPTS="-Xmx1024m"
mvn spring-boot:run
```

---

### 14. CSRF Challenge Not Working

**Issue:** Password change doesn't show flag

**Solutions:**

a) **Verify you're logged in**
```
Check session in browser
```

b) **Check form is POST method**
```html
<form method="post">
```

c) **Verify controller is receiving data**
```java
@PostMapping("/level8/password")
```

---

### 15. IDOR Challenge Not Working

**Issue:** Cannot access other user profiles

**Solutions:**

a) **Verify user IDs exist**
```bash
mysql -u root -p vulnlab_db -e "SELECT id FROM users;"
```

b) **Check URL format**
```
/level4/user?id=1
/level4/user?id=2
```

c) **Verify controller is not checking authorization**
```java
// Should NOT check if user owns profile
```

---

## Performance Optimization

### For Better Performance

1. **Increase MySQL buffer pool**
```sql
SET GLOBAL innodb_buffer_pool_size = 1073741824;
```

2. **Enable query caching**
```properties
spring.jpa.properties.hibernate.generate_statistics=false
```

3. **Increase Java heap**
```bash
export MAVEN_OPTS="-Xmx2048m -Xms1024m"
```

---

## Debugging Tips

### Enable Debug Logging

```properties
# application.properties
logging.level.root=INFO
logging.level.com.vulnlab=DEBUG
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate.SQL=DEBUG
```

### Check Application Logs

```bash
# View logs while running
tail -f logs/application.log

# Or in console output
mvn spring-boot:run
```

### Database Debugging

```bash
# Connect to database
mysql -u root -p vulnlab_db

# Check tables
SHOW TABLES;

# Check data
SELECT * FROM users;
SELECT * FROM flags;

# Check table structure
DESCRIBE users;
```

---

## Getting Help

### If You're Still Stuck

1. **Check the logs** - Most errors are in the console output
2. **Verify prerequisites** - Java 17+, MySQL 8.0+, Maven 3.6+
3. **Review documentation** - README.md and QUICKSTART.md
4. **Check OWASP resources** - For vulnerability-specific questions
5. **Search online** - Spring Boot and MySQL error messages

### Common Error Patterns

| Error | Likely Cause |
|-------|---|
| `Connection refused` | MySQL not running |
| `Address already in use` | Port 8080 in use |
| `Table doesn't exist` | Schema not imported |
| `404 Not Found` | Wrong URL or template missing |
| `Unsupported class version` | Java version too old |
| `BUILD FAILURE` | Missing dependencies or Java issue |

---

## Quick Checklist

Before asking for help, verify:

- [ ] Java 17+ installed: `java -version`
- [ ] Maven 3.6+ installed: `mvn --version`
- [ ] MySQL 8.0+ running: `mysql -u root -p -e "SELECT 1;"`
- [ ] Database created: `mysql -u root -p -e "SHOW DATABASES;"`
- [ ] Tables created: `mysql -u root -p vulnlab_db -e "SHOW TABLES;"`
- [ ] Credentials correct in `application.properties`
- [ ] Port 8080 available: `lsof -i :8080`
- [ ] Application starts: `mvn spring-boot:run`
- [ ] Can access http://localhost:8080

---

**Last Updated:** 2026-03-22

**Version:** 1.0.0
