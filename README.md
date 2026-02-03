# Spring Boot AI Chat Application

A full-stack AI-powered chat application built with Spring Boot, Spring AI, React, and MySQL.

## 🚀 Features

- **AI Chat Interface**: Real-time conversational AI powered by OpenAI
- **Text Summarization**: Intelligent text summarization capabilities
- **Chat History**: Persistent storage of conversations in MySQL
- **Session Management**: Maintains conversation context
- **Modern UI**: Clean, responsive React interface
- **REST API**: Well-structured RESTful endpoints

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- Node.js 16+ and npm
- MySQL 8.0+
- OpenAI API Key

## 🛠️ Technology Stack

### Backend
- Spring Boot 3.2.1
- Spring AI (OpenAI Integration)
- Spring Data JPA
- MySQL
- Lombok

### Frontend
- React 18
- Axios
- CSS3

## 📦 Project Structure

```
spring-ai-chat/
├── src/
│   └── main/
│       ├── java/com/example/springaichat/
│       │   ├── SpringAiChatApplication.java
│       │   ├── config/
│       │   │   └── CorsConfig.java
│       │   ├── controller/
│       │   │   └── ChatController.java
│       │   ├── dto/
│       │   │   ├── ChatRequest.java
│       │   │   ├── ChatResponse.java
│       │   │   └── SummaryRequest.java
│       │   ├── exception/
│       │   │   └── GlobalExceptionHandler.java
│       │   ├── model/
│       │   │   └── ChatMessage.java
│       │   ├── repository/
│       │   │   └── ChatMessageRepository.java
│       │   └── service/
│       │       └── AiChatService.java
│       └── resources/
│           └── application.properties
├── frontend/
│   ├── public/
│   │   └── index.html
│   ├── src/
│   │   ├── App.js
│   │   ├── App.css
│   │   ├── index.js
│   │   └── index.css
│   └── package.json
└── pom.xml
```

## 🚀 Getting Started

### 1. Database Setup

Create a MySQL database:

```sql
CREATE DATABASE ai_chat_db;
```

### 2. Backend Configuration

1. Navigate to `src/main/resources/application.properties`
2. Update database credentials:

```properties
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
```

3. Set your OpenAI API key (choose one method):

**Option A: Environment Variable (Recommended)**
```bash
export OPENAI_API_KEY=your-openai-api-key
```

**Option B: Direct in application.properties**
```properties
spring.ai.openai.api-key=your-openai-api-key
```

### 3. Run Backend

```bash
# From project root
mvn clean install
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

### 4. Run Frontend

```bash
# Navigate to frontend directory
cd frontend

# Install dependencies
npm install

# Start development server
npm start
```

The frontend will start on `http://localhost:3000`

## 📡 API Endpoints

### Chat Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/chat/send` | Send a chat message |
| POST | `/api/chat/summarize` | Summarize text |
| GET | `/api/chat/history/{sessionId}` | Get chat history |
| GET | `/api/chat/recent` | Get recent chats |
| DELETE | `/api/chat/history/{sessionId}` | Clear chat history |
| GET | `/api/chat/health` | Health check |

### Example Requests

**Send Chat Message:**
```bash
curl -X POST http://localhost:8080/api/chat/send \
  -H "Content-Type: application/json" \
  -d '{
    "message": "Hello, how are you?",
    "sessionId": "optional-session-id"
  }'
```

**Summarize Text:**
```bash
curl -X POST http://localhost:8080/api/chat/summarize \
  -H "Content-Type: application/json" \
  -d '{
    "text": "Your long text here..."
  }'
```

**Get Chat History:**
```bash
curl http://localhost:8080/api/chat/history/{sessionId}
```

## 🎨 Frontend Features

### Chat Interface
- Real-time messaging with AI
- Message history display
- Typing indicators
- Session management
- Clear chat functionality

### Summarization Interface
- Text input area
- One-click summarization
- Results display
- Clear functionality

## 🔧 Configuration Options

### application.properties

```properties
# Server Configuration
server.port=8080

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/ai_chat_db
spring.datasource.username=root
spring.datasource.password=your_password

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# OpenAI
spring.ai.openai.api-key=${OPENAI_API_KEY}
spring.ai.openai.chat.options.model=gpt-4o-mini
spring.ai.openai.chat.options.temperature=0.7
spring.ai.openai.chat.options.max-tokens=1000

# CORS
cors.allowed-origins=http://localhost:3000
```

## 🗄️ Database Schema

### chat_messages Table

| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary key (auto-increment) |
| session_id | VARCHAR(255) | Conversation session identifier |
| role | VARCHAR(10) | 'user' or 'assistant' |
| content | TEXT | Message content |
| timestamp | DATETIME | Message timestamp |

## 🔒 Security Considerations

1. **API Key Security**: Never commit API keys to version control
2. **CORS**: Configure allowed origins properly in production
3. **Input Validation**: All inputs are validated using Bean Validation
4. **SQL Injection**: Protected by JPA/Hibernate parameterized queries
5. **Error Handling**: Global exception handler prevents information leakage

## 🚀 Deployment

### Backend Deployment

1. Build JAR file:
```bash
mvn clean package
```

2. Run the JAR:
```bash
java -jar target/spring-ai-chat-1.0.0.jar
```

### Frontend Deployment

1. Build production bundle:
```bash
cd frontend
npm run build
```

2. Deploy the `build` folder to your hosting service

## 🐛 Troubleshooting

### Common Issues

**Issue: Database connection failed**
- Verify MySQL is running
- Check credentials in application.properties
- Ensure database exists

**Issue: OpenAI API errors**
- Verify API key is correct
- Check API key permissions
- Ensure you have credits available

**Issue: CORS errors**
- Verify `cors.allowed-origins` matches frontend URL
- Check if backend is running

**Issue: Frontend can't connect to backend**
- Verify backend is running on port 8080
- Check `API_BASE_URL` in App.js
- Ensure no firewall is blocking connections

## 📚 Additional Resources

- [Spring AI Documentation](https://docs.spring.io/spring-ai/reference/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [OpenAI API Documentation](https://platform.openai.com/docs)
- [React Documentation](https://react.dev/)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License.

## 👨‍💻 Author

Built with ❤️ using Spring Boot and Spring AI

## 🎯 Future Enhancements

- [ ] User authentication
- [ ] Multiple AI model support
- [ ] File upload support
- [ ] Voice input/output
- [ ] Multi-language support
- [ ] Advanced chat features (code highlighting, markdown rendering)
- [ ] Export chat history
- [ ] Real-time streaming responses
