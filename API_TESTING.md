# API Testing Guide

## Using cURL

### 1. Health Check
```bash
curl http://localhost:8080/api/chat/health
```

Expected Response:
```json
{
  "status": "UP",
  "service": "AI Chat API"
}
```

### 2. Send Chat Message (New Session)
```bash
curl -X POST http://localhost:8080/api/chat/send \
  -H "Content-Type: application/json" \
  -d '{
    "message": "Hello, can you help me understand Spring Boot?"
  }'
```

Expected Response:
```json
{
  "response": "Of course! Spring Boot is...",
  "sessionId": "550e8400-e29b-41d4-a716-446655440000",
  "messageId": 1
}
```

### 3. Continue Conversation (Existing Session)
```bash
curl -X POST http://localhost:8080/api/chat/send \
  -H "Content-Type: application/json" \
  -d '{
    "message": "Can you give me an example?",
    "sessionId": "550e8400-e29b-41d4-a716-446655440000"
  }'
```

### 4. Summarize Text
```bash
curl -X POST http://localhost:8080/api/chat/summarize \
  -H "Content-Type: application/json" \
  -d '{
    "text": "Spring Boot is an open-source Java framework used for programming standalone, production-grade Spring-based applications with minimal effort. It is developed by Pivotal Team and provides a good platform for Java developers to develop a stand-alone and production-grade spring application. Spring Boot makes it easy to create Spring-powered, production-grade applications and services with absolute minimum fuss."
  }'
```

Expected Response:
```json
{
  "summary": "Spring Boot is an open-source Java framework that simplifies creating production-grade applications..."
}
```

### 5. Get Chat History
```bash
curl http://localhost:8080/api/chat/history/550e8400-e29b-41d4-a716-446655440000
```

Expected Response:
```json
[
  {
    "id": 1,
    "sessionId": "550e8400-e29b-41d4-a716-446655440000",
    "role": "user",
    "content": "Hello, can you help me understand Spring Boot?",
    "timestamp": "2024-01-28T10:30:00"
  },
  {
    "id": 2,
    "sessionId": "550e8400-e29b-41d4-a716-446655440000",
    "role": "assistant",
    "content": "Of course! Spring Boot is...",
    "timestamp": "2024-01-28T10:30:05"
  }
]
```

### 6. Get Recent Chats
```bash
curl http://localhost:8080/api/chat/recent
```

### 7. Clear Chat History
```bash
curl -X DELETE http://localhost:8080/api/chat/history/550e8400-e29b-41d4-a716-446655440000
```

Expected Response:
```json
{
  "message": "Chat history cleared successfully"
}
```

## Using JavaScript (Axios)

### Setup
```javascript
import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/chat';
```

### Send Message
```javascript
async function sendMessage(message, sessionId = null) {
  try {
    const response = await axios.post(`${API_BASE_URL}/send`, {
      message: message,
      sessionId: sessionId
    });
    
    console.log('Response:', response.data.response);
    console.log('Session ID:', response.data.sessionId);
    return response.data;
  } catch (error) {
    console.error('Error:', error.response?.data || error.message);
  }
}

// Usage
sendMessage('Hello, AI!');
```

### Summarize Text
```javascript
async function summarizeText(text) {
  try {
    const response = await axios.post(`${API_BASE_URL}/summarize`, {
      text: text
    });
    
    console.log('Summary:', response.data.summary);
    return response.data.summary;
  } catch (error) {
    console.error('Error:', error.response?.data || error.message);
  }
}

// Usage
summarizeText('Your long text here...');
```

### Get Chat History
```javascript
async function getChatHistory(sessionId) {
  try {
    const response = await axios.get(`${API_BASE_URL}/history/${sessionId}`);
    console.log('Chat History:', response.data);
    return response.data;
  } catch (error) {
    console.error('Error:', error.response?.data || error.message);
  }
}
```

## Using Python (requests)

### Setup
```python
import requests
import json

API_BASE_URL = 'http://localhost:8080/api/chat'
```

### Send Message
```python
def send_message(message, session_id=None):
    url = f'{API_BASE_URL}/send'
    payload = {
        'message': message,
        'sessionId': session_id
    }
    
    try:
        response = requests.post(url, json=payload)
        response.raise_for_status()
        data = response.json()
        
        print(f"Response: {data['response']}")
        print(f"Session ID: {data['sessionId']}")
        return data
    except requests.exceptions.RequestException as e:
        print(f"Error: {e}")

# Usage
send_message('Hello, AI!')
```

### Summarize Text
```python
def summarize_text(text):
    url = f'{API_BASE_URL}/summarize'
    payload = {'text': text}
    
    try:
        response = requests.post(url, json=payload)
        response.raise_for_status()
        data = response.json()
        
        print(f"Summary: {data['summary']}")
        return data['summary']
    except requests.exceptions.RequestException as e:
        print(f"Error: {e}")

# Usage
summarize_text('Your long text here...')
```

## Postman Collection

Import this JSON into Postman:

```json
{
  "info": {
    "name": "Spring AI Chat API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Health Check",
      "request": {
        "method": "GET",
        "header": [],
        "url": {
          "raw": "http://localhost:8080/api/chat/health",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "chat", "health"]
        }
      }
    },
    {
      "name": "Send Chat Message",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"message\": \"Hello, how are you?\",\n  \"sessionId\": \"\"\n}"
        },
        "url": {
          "raw": "http://localhost:8080/api/chat/send",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "chat", "send"]
        }
      }
    },
    {
      "name": "Summarize Text",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"text\": \"Your text to summarize here...\"\n}"
        },
        "url": {
          "raw": "http://localhost:8080/api/chat/summarize",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "chat", "summarize"]
        }
      }
    },
    {
      "name": "Get Chat History",
      "request": {
        "method": "GET",
        "header": [],
        "url": {
          "raw": "http://localhost:8080/api/chat/history/{{sessionId}}",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "chat", "history", "{{sessionId}}"]
        }
      }
    },
    {
      "name": "Get Recent Chats",
      "request": {
        "method": "GET",
        "header": [],
        "url": {
          "raw": "http://localhost:8080/api/chat/recent",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "chat", "recent"]
        }
      }
    },
    {
      "name": "Clear Chat History",
      "request": {
        "method": "DELETE",
        "header": [],
        "url": {
          "raw": "http://localhost:8080/api/chat/history/{{sessionId}}",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "chat", "history", "{{sessionId}}"]
        }
      }
    }
  ]
}
```

## Testing Checklist

- [ ] Health check returns status UP
- [ ] Can send first message (new session)
- [ ] Response contains sessionId
- [ ] Can continue conversation with sessionId
- [ ] Can summarize text
- [ ] Can retrieve chat history
- [ ] Can get recent chats
- [ ] Can clear chat history
- [ ] Invalid requests return proper error messages
- [ ] CORS allows frontend requests
