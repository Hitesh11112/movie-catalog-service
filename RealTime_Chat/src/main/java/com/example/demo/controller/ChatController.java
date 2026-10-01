package com.example.demo.controller;

import java.security.Principal;
import java.time.LocalDateTime;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.example.demo.entity.ChatMessage;
import com.example.demo.repository.ChatMessageRepository;

@Controller
public class ChatController {

    private static final int MAX_LENGTH = 255; // matches the default varchar(255) column

    private final ChatMessageRepository repo;

    ChatController(ChatMessageRepository repo) {
        this.repo = repo;
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage message, Principal principal) {
        if (message.getContent() == null || message.getContent().isBlank()) {
            return null; // nothing is broadcast
        }

        String content = message.getContent().trim();
        if (content.length() > MAX_LENGTH) {
            content = content.substring(0, MAX_LENGTH);
        }

        ChatMessage toSave = new ChatMessage();          // fresh object: client can't set id/sender
        toSave.setSender(principal.getName());           // taken from the verified JWT
        toSave.setContent(content);
        toSave.setTimestamp(LocalDateTime.now());
        return repo.save(toSave);
    }
}