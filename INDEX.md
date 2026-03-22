# VulnLab Tech - Documentation Index

Welcome to VulnLab Tech! This is your guide to all available documentation.

## 📚 Documentation Files

### Getting Started
1. **[QUICKSTART.md](QUICKSTART.md)** ⭐ START HERE
   - 5-minute setup guide
   - Step-by-step instructions
   - Default credentials
   - Quick challenge solutions

2. **[README.md](README.md)**
   - Complete project overview
   - Full setup instructions
   - All 10 vulnerabilities explained
   - Technology stack details
   - Routes and endpoints

### Project Information
3. **[PROJECT_SUMMARY.md](PROJECT_SUMMARY.md)**
   - Project completion status
   - File structure overview
   - Technology stack
   - Learning outcomes
   - Statistics

### Troubleshooting
4. **[TROUBLESHOOTING.md](TROUBLESHOOTING.md)**
   - Common issues and solutions
   - Error messages explained
   - Performance optimization
   - Debugging tips
   - Quick checklist

### This File
5. **[INDEX.md](INDEX.md)** (You are here)
   - Documentation navigation
   - Quick reference

---

## 🚀 Quick Start (5 Minutes)

```bash
# 1. Create database
mysql -u root -p < src/main/resources/schema.sql

# 2. Update credentials in src/main/resources/application.properties

# 3. Build
mvn clean package

# 4. Run
mvn spring-boot:run

# 5. Open browser
# http://localhost:8080
```

**Default Login:** `admin` / `admin123`

---

## 📖 Documentation by Purpose

### "I want to set up the application"
→ Read [QUICKSTART.md](QUICKSTART.md)

### "I want to understand the project"
→ Read [README.md](README.md)

### "I want to know what was built"
→ Read [PROJECT_SUMMARY.md](PROJECT_SUMMARY.md)

### "Something is broken"
→ Read [TROUBLESHOOTING.md](TROUBLESHOOTING.md)

### "I want to learn about vulnerabilities"
→ Read [README.md](README.md) - Vulnerabilities section

### "I want to solve the challenges"
→ Read [QUICKSTART.md](QUICKSTART.md) - Solving Challenges section

---

## 🎯 Vulnerability Quick Reference

| Level | Vulnerability | URL | Difficulty |
|-------|---|---|---|
| 1 | SQL Injection | `/level1/login` | Easy |
| 2 | Reflected XSS | `/level2/search` | Easy |
| 3 | Stored XSS | `/level3/profile` | Easy |
| 4 | IDOR | `/level4/user` | Easy |
| 5 | File Upload | `/level5/upload` | Medium |
| 6 | Command Injection | `/level6/ping` | Medium |
| 7 | Broken Authentication | `/level7/admin` | Medium |
| 8 | CSRF | `/level8/password` | Medium |
| 9 | SSTI | `/level9/welcome` | Hard |
| 10 | Final Challenge | `/level10/final` | Hard |

---

## 🔑 Default Credentials

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

---

## 📋 Prerequisites Checklist

Before starting, make sure you have:

- [ ] Java 17 or higher
- [ ] Maven 3.6 or higher
- [ ] MySQL 8.0 or higher
- [ ] 100MB free disk space
- [ ] Port 8080 available

Check versions:
```bash
java -version
mvn --version
mysql --version
```

---

## 🛠️ Common Commands

### Setup
```bash
# Create database
mysql -u root -p < src/main/resources/schema.sql

# Build application
mvn clean package

# Run application
mvn spring-boot:run
```

### Database
```bash
# Connect to database
mysql -u root -p vulnlab_db

# View users
SELECT * FROM users;

# View flags
SELECT * FROM flags;

# View tables
SHOW TABLES;
```

### Troubleshooting
```bash
# Check if MySQL is running
mysql -u root -p -e "SELECT 1;"

# Check if port 8080 is in use
lsof -i :8080

# Check Java version
java -version

# View application logs
tail -f logs/application.log
```

---

## 📁 Project Structure

```
vulnlab-tech/
├── src/main/java/com/vulnlab/
│   ├── controller/          # 11 controllers
│   ├── entity/              # 3 entity classes
│   └── repository/          # 3 repositories
├── src/main/resources/
│   ├── templates/           # 11 HTML templates
│   ├── application.properties
│   └── schema.sql
├── pom.xml                  # Maven configuration
├── README.md                # Full documentation
├── QUICKSTART.md            # Quick setup guide
├── PROJECT_SUMMARY.md       # Project overview
├── TROUBLESHOOTING.md       # Problem solving
├── INDEX.md                 # This file
└── .gitignore
```

---

## 🎓 Learning Path

### Beginner (Easy Levels)
1. Start with Level 1 - SQL Injection
2. Move to Level 2 - Reflected XSS
3. Try Level 3 - Stored XSS
4. Attempt Level 4 - IDOR

### Intermediate (Medium Levels)
5. Level 5 - File Upload
6. Level 6 - Command Injection
7. Level 7 - Broken Authentication
8. Level 8 - CSRF

### Advanced (Hard Levels)
9. Level 9 - SSTI
10. Level 10 - Final Challenge

---

## 🔗 External Resources

### Security Learning
- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [OWASP Testing Guide](https://owasp.org/www-project-web-security-testing-guide/)
- [PortSwigger Web Security Academy](https://portswigger.net/web-security)

### CTF Platforms
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)
- [PicoCTF](https://picoctf.org/)

### Documentation
- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [MySQL Docs](https://dev.mysql.com/doc/)
- [Java Docs](https://docs.oracle.com/en/java/)

---

## ❓ FAQ

### Q: Is this safe to run?
**A:** Yes, if run locally. Never expose to the internet without proper isolation.

### Q: Can I modify the vulnerabilities?
**A:** Yes! That's part of learning. Try to fix them and understand the patches.

### Q: How long does setup take?
**A:** About 5 minutes with the QUICKSTART guide.

### Q: What if I get stuck?
**A:** Check TROUBLESHOOTING.md first, then review the relevant documentation.

### Q: Can I use this for teaching?
**A:** Yes! It's designed for educational purposes. Perfect for security courses.

### Q: What if I find a real vulnerability?
**A:** This is intentional! That's the whole point of the lab.

---

## 📞 Support

### If You Need Help

1. **Check Documentation**
   - QUICKSTART.md for setup
   - TROUBLESHOOTING.md for errors
   - README.md for details

2. **Review Code Comments**
   - Controllers have inline comments
   - Vulnerabilities are clearly marked

3. **Check Logs**
   - Application logs show errors
   - Database logs show connection issues

4. **Verify Prerequisites**
   - Java 17+
   - MySQL 8.0+
   - Maven 3.6+

---

## 📝 Notes

- This application is **intentionally vulnerable**
- Use only for **educational purposes**
- Do **NOT** deploy to production
- Do **NOT** expose to the internet
- Always use in **isolated environments**

---

## 🎯 Next Steps

1. **Read QUICKSTART.md** - Get the app running
2. **Access http://localhost:8080** - See the homepage
3. **Login with admin/admin123** - Access your dashboard
4. **Start with Level 1** - Begin learning
5. **Progress through levels** - Build your skills
6. **Read OWASP resources** - Deepen your knowledge

---

## 📊 Project Statistics

- **Total Files:** 25+
- **Lines of Code:** 3,000+
- **Controllers:** 11
- **Templates:** 11
- **Vulnerabilities:** 10
- **Setup Time:** 5 minutes
- **Learning Time:** 2-4 hours

---

## 📅 Version Information

- **Version:** 1.0.0
- **Last Updated:** 2026-03-22
- **Status:** ✅ Complete and Ready

---

## 📄 License

Educational Use Only - Not for Production

---

**Happy Learning! 🎓**

Start with [QUICKSTART.md](QUICKSTART.md) →
