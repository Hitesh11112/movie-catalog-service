package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final StudentService studentService;

    AuthController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public Student register(@RequestBody RegisterRequest request) {
        return studentService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        String token = studentService.login(request);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
