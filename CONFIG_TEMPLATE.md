# VulnLab Tech - Configuration Template

This file provides templates for common configuration changes.

## Database Configuration

### application.properties

```properties
# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/vulnlab_db
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Thymeleaf Configuration
spring.thymeleaf.cache=false
spring.thymeleaf.mode=HTML

# Session Configuration
server.servlet.session.timeout=30m

# Server Configuration
server.port=8080
spring.application.name=vulnlab-tech
```

## Custom Database Configuration

### For Different MySQL Host

```properties
# Remote MySQL server
spring.datasource.url=jdbc:mysql://192.168.1.100:3306/vulnlab_db
spring.datasource.username=vulnlab_user
spring.datasource.password=secure_password
```

### For Different Port

```properties
# MySQL on port 3307
spring.datasource.url=jdbc:mysql://localhost:3307/vulnlab_db
```

### For MariaDB

```properties
# MariaDB instead of MySQL
spring.datasource.url=jdbc:mariadb://localhost:3306/vulnlab_db
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDB103Dialect
```

## Server Configuration

### Change Application Port

```properties
# Run on port 8081 instead of 8080
server.port=8081
```

### Change Session Timeout

```properties
# Session expires after 1 hour
server.servlet.session.timeout=60m

# Session expires after 15 minutes
server.servlet.session.timeout=15m
```

### Enable HTTPS

```properties
# HTTPS Configuration
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=password
server.ssl.key-store-type=PKCS12
server.ssl.key-alias=tomcat
```

## Logging Configuration

### Enable Debug Logging

```properties
# Root logger
logging.level.root=DEBUG

# Application logger
logging.level.com.vulnlab=DEBUG

# Spring Web logger
logging.level.org.springframework.web=DEBUG

# Hibernate SQL logger
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

### Log to File

```properties
# Log to file
logging.file.name=logs/application.log
logging.file.max-size=10MB
logging.file.max-history=10

# Log pattern
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss} - %msg%n
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %msg%n
```

## Performance Configuration

### Increase Heap Size

```bash
# Set environment variable before running
export MAVEN_OPTS="-Xmx2048m -Xms1024m"
mvn spring-boot:run
```

### Database Connection Pool

```properties
# HikariCP Configuration
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=20000
spring.datasource.hikari.idle-timeout=300000
spring.datasource.hikari.max-lifetime=1200000
```

### Hibernate Configuration

```properties
# Batch processing
spring.jpa.properties.hibernate.jdbc.batch_size=20
spring.jpa.properties.hibernate.order_inserts=true
spring.jpa.properties.hibernate.order_updates=true

# Query optimization
spring.jpa.properties.hibernate.generate_statistics=false
spring.jpa.properties.hibernate.use_sql_comments=true
```

## Security Configuration

### Disable Thymeleaf Cache (Development)

```properties
spring.thymeleaf.cache=false
```

### Enable Thymeleaf Cache (Production - Not Recommended)

```properties
spring.thymeleaf.cache=true
```

### Session Security

```properties
# Secure session cookies
server.servlet.session.cookie.secure=true
server.servlet.session.cookie.http-only=true
server.servlet.session.cookie.same-site=strict
```

## Custom Flags

### Modify Flag Values

Edit `src/main/resources/schema.sql`:

```sql
INSERT INTO flags (level, flag) VALUES
(1, 'flag{custom_sql_injection}'),
(2, 'flag{custom_xss_reflected}'),
(3, 'flag{custom_stored_xss}'),
(4, 'flag{custom_idor}'),
(5, 'flag{custom_file_upload}'),
(6, 'flag{custom_command_injection}'),
(7, 'flag{custom_admin_access}'),
(8, 'flag{custom_csrf}'),
(9, 'flag{custom_ssti}'),
(10, 'flag{custom_final}');
```

## Custom Users

### Add More Test Users

Edit `src/main/resources/schema.sql`:

```sql
INSERT INTO users (username, password, role, bio) VALUES
('admin', 'admin123', 'admin', 'Administrator'),
('user1', 'password1', 'user', 'Test User 1'),
('user2', 'password2', 'user', 'Test User 2'),
('user3', 'password3', 'user', 'Test User 3'),
('testuser', 'testpass', 'user', 'Additional Test User'),
('hacker', 'hacker123', 'user', 'Security Researcher');
```

## Docker Configuration (Optional)

### Dockerfile

```dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/vulnlab-tech-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

### docker-compose.yml

```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: vulnlab_db
    ports:
      - "3306:3306"
    volumes:
      - ./src/main/resources/schema.sql:/docker-entrypoint-initdb.d/schema.sql

  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/vulnlab_db
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: root
    depends_on:
      - mysql
```

## Environment Variables

### Using Environment Variables

```bash
# Set environment variables
export DB_URL=jdbc:mysql://localhost:3306/vulnlab_db
export DB_USER=root
export DB_PASSWORD=root
export SERVER_PORT=8080

# Run application
mvn spring-boot:run
```

### application.properties with Variables

```properties
spring.datasource.url=${DB_URL:jdbc:mysql://localhost:3306/vulnlab_db}
spring.datasource.username=${DB_USER:root}
spring.datasource.password=${DB_PASSWORD:root}
server.port=${SERVER_PORT:8080}
```

## Profile-Based Configuration

### application-dev.properties

```properties
# Development configuration
spring.jpa.show-sql=true
logging.level.root=DEBUG
spring.thymeleaf.cache=false
server.port=8080
```

### application-prod.properties

```properties
# Production configuration (NOT RECOMMENDED FOR THIS APP)
spring.jpa.show-sql=false
logging.level.root=INFO
spring.thymeleaf.cache=true
server.port=8080
server.ssl.enabled=true
```

### Run with Profile

```bash
# Development
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"

# Production
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=prod"
```

## Maven Configuration

### pom.xml - Custom Dependencies

```xml
<!-- Add additional dependencies as needed -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

### Maven Build Properties

```xml
<properties>
    <java.version>17</java.version>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>
```

## Customization Examples

### Change Application Name

In `pom.xml`:
```xml
<name>My Custom VulnLab</name>
<description>Custom Vulnerable Web Application</description>
```

In `application.properties`:
```properties
spring.application.name=my-custom-vulnlab
```

### Change Upload Directory

In `Level5Controller.java`:
```java
String uploadDir = "/var/uploads/vulnlab/";
```

### Change Session Timeout

In `application.properties`:
```properties
server.servlet.session.timeout=60m
```

### Add Custom Vulnerability

1. Create new controller: `Level11Controller.java`
2. Create new template: `level11.html`
3. Add route to homepage
4. Add flag to database

## Troubleshooting Configuration

### If Application Won't Start

1. Check `application.properties` syntax
2. Verify database connection
3. Check port availability
4. Review logs for errors

### If Database Connection Fails

1. Verify MySQL is running
2. Check credentials
3. Verify database exists
4. Check firewall settings

### If Templates Don't Load

1. Verify files in `src/main/resources/templates/`
2. Check Thymeleaf configuration
3. Verify file names match controller returns
4. Check for typos in template names

---

## Quick Reference

| Configuration | Default | Purpose |
|---|---|---|
| Database URL | localhost:3306 | MySQL connection |
| Database Name | vulnlab_db | Database name |
| Server Port | 8080 | Application port |
| Session Timeout | 30m | Session duration |
| Thymeleaf Cache | false | Template caching |
| Log Level | INFO | Logging verbosity |

---

**Last Updated:** 2026-03-22

**Version:** 1.0.0
