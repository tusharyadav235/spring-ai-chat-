@echo off
echo Starting Spring AI Chat Application...
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java is not installed
    echo Please install Java 17 or higher
    pause
    exit /b 1
)

REM Check if Maven is installed
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Maven is not installed
    echo Please install Maven 3.6+
    pause
    exit /b 1
)

REM Check if Node is installed
node -v >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Node.js is not installed
    echo Please install Node.js 16+
    pause
    exit /b 1
)

echo All prerequisites found
echo.

REM Check if OpenAI API key is set
if "%OPENAI_API_KEY%"=="" (
    echo Warning: OPENAI_API_KEY environment variable is not set
    echo Please set it or add it to application.properties
    echo.
)

REM Start backend in new window
echo Starting Backend...
start "Spring Boot Backend" cmd /k mvn spring-boot:run

REM Wait for backend to start
echo Waiting for backend to start (30 seconds)...
timeout /t 30 /nobreak >nul

REM Start frontend in new window
echo Starting Frontend...
cd frontend
start "React Frontend" cmd /k "npm install && npm start"

echo.
echo Application started successfully!
echo Backend running on: http://localhost:8080
echo Frontend running on: http://localhost:3000
echo.
echo Close the command windows to stop the services
pause
