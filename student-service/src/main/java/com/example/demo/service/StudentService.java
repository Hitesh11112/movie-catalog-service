package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.Student;

public interface StudentService {
	Student register(RegisterRequest request);
    String login(LoginRequest request);
    Optional<Student> getStudentById(Long id);
    List<Student> getAllStudents();
}
