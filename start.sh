#!/bin/bash

echo "🚀 Starting Spring AI Chat Application..."
echo ""

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Error: Java is not installed"
    echo "Please install Java 17 or higher"
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Error: Maven is not installed"
    echo "Please install Maven 3.6+"
    exit 1
fi

# Check if Node is installed
if ! command -v node &> /dev/null; then
    echo "❌ Error: Node.js is not installed"
    echo "Please install Node.js 16+"
    exit 1
fi

echo "✅ All prerequisites found"
echo ""

# Check if OpenAI API key is set
if [ -z "$OPENAI_API_KEY" ]; then
    echo "⚠️  Warning: OPENAI_API_KEY environment variable is not set"
    echo "Please set it or add it to application.properties"
    echo ""
fi

# Start backend
echo "📦 Starting Backend..."
mvn spring-boot:run &
BACKEND_PID=$!

# Wait for backend to start
echo "⏳ Waiting for backend to start (30 seconds)..."
sleep 30

# Start frontend
echo "🎨 Starting Frontend..."
cd frontend
npm install
npm start &
FRONTEND_PID=$!

echo ""
echo "✨ Application started successfully!"
echo "📝 Backend running on: http://localhost:8080"
echo "🌐 Frontend running on: http://localhost:3000"
echo ""
echo "Press Ctrl+C to stop all services"

# Wait for user to stop
wait
