package com.example.demo.controller;

import java.time.LocalDateTime;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.example.demo.entity.ChatMessage;
import com.example.demo.repository.ChatMessageRepository;

@Controller
public class ChatController {
	
	private final ChatMessageRepository repo;

    ChatController(ChatMessageRepository repo) {
        this.repo = repo;
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage message) {
        message.setTimestamp(LocalDateTime.now());
        return repo.save(message);
    }
}
