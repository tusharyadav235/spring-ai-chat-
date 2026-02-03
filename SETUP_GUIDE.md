# Complete Setup Guide - Spring Boot AI Chat Application

## Step-by-Step Installation Guide

### Prerequisites Checklist

Before starting, ensure you have:
- ✅ Java 17 or higher installed
- ✅ Maven 3.6+ installed
- ✅ Node.js 16+ and npm installed
- ✅ MySQL 8.0+ installed and running
- ✅ OpenAI API Key (get one from https://platform.openai.com/)

### Step 1: Verify Java Installation

```bash
java -version
# Should show Java 17 or higher

mvn -version
# Should show Maven 3.6+
```

If not installed:
- **Windows**: Download from https://adoptium.net/
- **Mac**: `brew install openjdk@17`
- **Linux**: `sudo apt install openjdk-17-jdk maven`

### Step 2: Verify Node.js Installation

```bash
node -v
# Should show v16 or higher

npm -v
# Should show npm version
```

If not installed:
- Download from https://nodejs.org/
- Or use nvm: `nvm install --lts`

### Step 3: Setup MySQL Database

1. **Start MySQL**:
```bash
# Windows
net start mysql

# Mac
brew services start mysql

# Linux
sudo systemctl start mysql
```

2. **Login to MySQL**:
```bash
mysql -u root -p
```

3. **Create Database**:
```sql
CREATE DATABASE ai_chat_db;

-- Verify database creation
SHOW DATABASES;

-- Exit MySQL
EXIT;
```

### Step 4: Get OpenAI API Key

1. Go to https://platform.openai.com/
2. Sign up or log in
3. Navigate to API Keys section
4. Click "Create new secret key"
5. Copy and save the key securely

### Step 5: Configure Backend

1. **Navigate to application.properties**:
```
spring-ai-chat/src/main/resources/application.properties
```

2. **Update Database Credentials**:
```properties
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
```

3. **Set OpenAI API Key** (Choose Option A or B):

**Option A: Environment Variable (Recommended)**
```bash
# Windows (CMD)
set OPENAI_API_KEY=sk-your-key-here

# Windows (PowerShell)
$env:OPENAI_API_KEY="sk-your-key-here"

# Mac/Linux
export OPENAI_API_KEY=sk-your-key-here

# To make it permanent, add to your shell profile
# Mac/Linux: Add to ~/.bashrc or ~/.zshrc
echo 'export OPENAI_API_KEY=sk-your-key-here' >> ~/.bashrc
source ~/.bashrc
```

**Option B: Direct Configuration**
```properties
# In application.properties
spring.ai.openai.api-key=sk-your-key-here
```

⚠️ **Important**: If using Option B, never commit this file to Git!

### Step 6: Build and Run Backend

1. **Navigate to project root**:
```bash
cd spring-ai-chat
```

2. **Build the project**:
```bash
mvn clean install
```

This will:
- Download all dependencies
- Compile the code
- Run tests
- Package the application

3. **Run the application**:
```bash
mvn spring-boot:run
```

4. **Verify backend is running**:
- Open browser: http://localhost:8080/api/chat/health
- Should see: `{"status":"UP","service":"AI Chat API"}`

### Step 7: Setup and Run Frontend

1. **Open a NEW terminal** (keep backend running)

2. **Navigate to frontend directory**:
```bash
cd spring-ai-chat/frontend
```

3. **Install dependencies**:
```bash
npm install
```

This will install:
- React
- Axios
- All other dependencies

4. **Start frontend**:
```bash
npm start
```

5. **Verify frontend is running**:
- Browser should automatically open: http://localhost:3000
- You should see the AI Chat interface

### Step 8: Test the Application

1. **Test Chat Feature**:
   - Type a message: "Hello, how are you?"
   - Click "Send"
   - You should receive an AI response

2. **Test Summarization**:
   - Click the "Summarize" tab
   - Paste some text
   - Click "Generate Summary"
   - You should see a summary

### Step 9: Verify Database Storage

1. **Check MySQL**:
```bash
mysql -u root -p
```

2. **Query chat messages**:
```sql
USE ai_chat_db;

-- View all tables
SHOW TABLES;

-- View chat messages
SELECT * FROM chat_messages ORDER BY timestamp DESC LIMIT 10;
```

You should see your chat messages stored!

## Common Setup Issues & Solutions

### Issue 1: Port Already in Use

**Backend (8080)**:
```bash
# Find process using port 8080
# Windows
netstat -ano | findstr :8080

# Mac/Linux
lsof -i :8080

# Kill the process or change port in application.properties
server.port=8081
```

**Frontend (3000)**:
```bash
# Change port when starting
PORT=3001 npm start
```

### Issue 2: MySQL Connection Failed

**Check MySQL is running**:
```bash
# Windows
sc query mysql

# Mac
brew services list | grep mysql

# Linux
sudo systemctl status mysql
```

**Reset MySQL password** if forgotten:
```sql
ALTER USER 'root'@'localhost' IDENTIFIED BY 'new_password';
FLUSH PRIVILEGES;
```

### Issue 3: Maven Build Fails

**Clear Maven cache**:
```bash
mvn clean
rm -rf ~/.m2/repository
mvn install
```

**Check Java version**:
```bash
java -version
# Must be Java 17+
```

### Issue 4: OpenAI API Errors

**Verify API key**:
- Check key starts with `sk-`
- Ensure no extra spaces
- Verify you have credits: https://platform.openai.com/usage

**Test API key**:
```bash
curl https://api.openai.com/v1/models \
  -H "Authorization: Bearer sk-your-key-here"
```

### Issue 5: Frontend Can't Connect to Backend

**Verify backend URL in frontend**:
```javascript
// frontend/src/App.js
const API_BASE_URL = 'http://localhost:8080/api/chat';
```

**Check CORS configuration**:
```properties
# application.properties
cors.allowed-origins=http://localhost:3000
```

## Production Deployment Tips

### Backend Deployment

1. **Build production JAR**:
```bash
mvn clean package -DskipTests
```

2. **Run with production profile**:
```bash
java -jar target/spring-ai-chat-1.0.0.jar --spring.profiles.active=prod
```

3. **Use environment variables**:
```bash
export SPRING_DATASOURCE_URL=jdbc:mysql://prod-db:3306/ai_chat_db
export SPRING_DATASOURCE_USERNAME=prod_user
export SPRING_DATASOURCE_PASSWORD=prod_password
export OPENAI_API_KEY=sk-your-prod-key
```

### Frontend Deployment

1. **Update API URL**:
```javascript
// App.js - Change to your backend URL
const API_BASE_URL = 'https://your-backend.com/api/chat';
```

2. **Build production bundle**:
```bash
cd frontend
npm run build
```

3. **Deploy build folder** to:
- Netlify
- Vercel
- AWS S3 + CloudFront
- GitHub Pages

## Monitoring & Maintenance

### Check Application Logs

**Backend**:
```bash
# View logs in terminal
# Or check log file if configured
tail -f logs/spring-boot-app.log
```

**Frontend**:
- Check browser console (F12)

### Database Maintenance

**Regular backups**:
```bash
mysqldump -u root -p ai_chat_db > backup_$(date +%Y%m%d).sql
```

**Monitor table size**:
```sql
SELECT 
    table_name,
    ROUND(((data_length + index_length) / 1024 / 1024), 2) AS 'Size (MB)'
FROM information_schema.TABLES
WHERE table_schema = 'ai_chat_db';
```

### Performance Optimization

1. **Enable caching** for frequently accessed data
2. **Add database indexes**:
```sql
CREATE INDEX idx_session_timestamp ON chat_messages(session_id, timestamp);
```
3. **Configure connection pooling** in application.properties
4. **Monitor OpenAI API usage** to control costs

## Next Steps

Now that your application is running:

1. ✅ Explore different chat prompts
2. ✅ Test summarization with various texts
3. ✅ Check the database to see stored messages
4. ✅ Customize the UI in App.css
5. ✅ Add new features (see README for ideas)

## Getting Help

If you encounter issues:

1. Check this guide's troubleshooting section
2. Review application logs
3. Verify all prerequisites are met
4. Check Spring AI documentation
5. Review OpenAI API status

## Useful Commands Cheat Sheet

```bash
# Backend
mvn clean install          # Build project
mvn spring-boot:run       # Run backend
mvn test                  # Run tests
mvn package              # Create JAR

# Frontend
npm install              # Install dependencies
npm start               # Run dev server
npm run build          # Build for production
npm test              # Run tests

# Database
mysql -u root -p                    # Login
CREATE DATABASE ai_chat_db;        # Create DB
SHOW TABLES;                       # List tables
SELECT * FROM chat_messages;       # View data

# Git
git init                   # Initialize repo
git add .                 # Stage changes
git commit -m "message"  # Commit
git push                # Push to remote
```

Happy coding! 🚀
