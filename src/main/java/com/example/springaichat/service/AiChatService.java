package com.example.springaichat.service;

import com.example.springaichat.dto.ChatRequest;
import com.example.springaichat.dto.ChatResponse;
import com.example.springaichat.model.ChatMessage;
import com.example.springaichat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiChatService {
    
    private final ChatClient.Builder chatClientBuilder;
    private final ChatMessageRepository chatMessageRepository;
    
    @Transactional
    public ChatResponse sendMessage(ChatRequest request) {
        String sessionId = request.getSessionId();
        if (sessionId == null || sessionId.isEmpty()) {
            sessionId = UUID.randomUUID().toString();
        }
        
        // Save user message
        ChatMessage userMessage = new ChatMessage();
        userMessage.setSessionId(sessionId);
        userMessage.setRole("user");
        userMessage.setContent(request.getMessage());
        chatMessageRepository.save(userMessage);
        
        // Get conversation history
        List<ChatMessage> history = chatMessageRepository.findBySessionIdOrderByTimestampAsc(sessionId);
        
        // Build message history for context
        List<Message> messages = new ArrayList<>();
        for (ChatMessage msg : history) {
            if ("user".equals(msg.getRole())) {
                messages.add(new UserMessage(msg.getContent()));
            } else if ("assistant".equals(msg.getRole())) {
                messages.add(new AssistantMessage(msg.getContent()));
            }
        }
        
        // Get AI response
        ChatClient chatClient = chatClientBuilder.build();
        String aiResponse = chatClient.prompt()
            .messages(messages)
            .call()
            .content();
        
        // Save AI response
        ChatMessage assistantMessage = new ChatMessage();
        assistantMessage.setSessionId(sessionId);
        assistantMessage.setRole("assistant");
        assistantMessage.setContent(aiResponse);
        ChatMessage savedMessage = chatMessageRepository.save(assistantMessage);
        
        log.info("Chat processed for session: {}", sessionId);
        
        return new ChatResponse(aiResponse, sessionId, savedMessage.getId());
    }
    
    public String summarizeText(String text) {
        String prompt = String.format(
            "Please provide a concise summary of the following text in 2-3 sentences:\n\n%s",
            text
        );
        
        ChatClient chatClient = chatClientBuilder.build();
        String summary = chatClient.prompt()
            .user(prompt)
            .call()
            .content();
        
        log.info("Text summarization completed");
        return summary;
    }
    
    public List<ChatMessage> getChatHistory(String sessionId) {
        return chatMessageRepository.findBySessionIdOrderByTimestampAsc(sessionId);
    }
    
    public List<ChatMessage> getRecentChats() {
        return chatMessageRepository.findTop10ByOrderByTimestampDesc();
    }
    
    @Transactional
    public void clearChatHistory(String sessionId) {
        chatMessageRepository.deleteBySessionId(sessionId);
        log.info("Chat history cleared for session: {}", sessionId);
    }
}
