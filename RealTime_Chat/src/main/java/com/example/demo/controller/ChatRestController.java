package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.ChatMessage;
import com.example.demo.repository.ChatMessageRepository;

@RestController
@RequestMapping("/api")
public class ChatRestController {
	
	private final ChatMessageRepository repo;

    ChatRestController(ChatMessageRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/messages")
    public List<ChatMessage> getAllMessages() {
        return repo.findAll();
    }
    
    @DeleteMapping("/messages")
    public void deleteAllMessages() {
        repo.deleteAll();
    }

}
