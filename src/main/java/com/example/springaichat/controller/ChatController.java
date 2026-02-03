package com.example.springaichat.controller;

import com.example.springaichat.dto.ChatRequest;
import com.example.springaichat.dto.ChatResponse;
import com.example.springaichat.dto.SummaryRequest;
import com.example.springaichat.model.ChatMessage;
import com.example.springaichat.service.AiChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@CrossOrigin(origins = "${cors.allowed-origins}")
public class ChatController {
    
    private final AiChatService aiChatService;
    
    @PostMapping("/send")
    public ResponseEntity<ChatResponse> sendMessage(@Valid @RequestBody ChatRequest request) {
        ChatResponse response = aiChatService.sendMessage(request);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/summarize")
    public ResponseEntity<Map<String, String>> summarizeText(@Valid @RequestBody SummaryRequest request) {
        String summary = aiChatService.summarizeText(request.getText());
        return ResponseEntity.ok(Map.of("summary", summary));
    }
    
    @GetMapping("/history/{sessionId}")
    public ResponseEntity<List<ChatMessage>> getChatHistory(@PathVariable String sessionId) {
        List<ChatMessage> history = aiChatService.getChatHistory(sessionId);
        return ResponseEntity.ok(history);
    }
    
    @GetMapping("/recent")
    public ResponseEntity<List<ChatMessage>> getRecentChats() {
        List<ChatMessage> recentChats = aiChatService.getRecentChats();
        return ResponseEntity.ok(recentChats);
    }
    
    @DeleteMapping("/history/{sessionId}")
    public ResponseEntity<Map<String, String>> clearHistory(@PathVariable String sessionId) {
        aiChatService.clearChatHistory(sessionId);
        return ResponseEntity.ok(Map.of("message", "Chat history cleared successfully"));
    }
    
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "AI Chat API"));
    }
}
