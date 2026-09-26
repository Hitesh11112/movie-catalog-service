package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.StudentDto;
import com.example.demo.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {
	
	private final StudentService studentService;

    StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Secured — requires a valid JWT. Called directly by a logged-in student,
    // or by course-service via OpenFeign (which forwards the same JWT)
    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id)
                .map(s -> ResponseEntity.ok(new StudentDto(s.getId(), s.getName(), s.getEmail())))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<StudentDto> getAllStudents() {
        return studentService.getAllStudents().stream()
                .map(s -> new StudentDto(s.getId(), s.getName(), s.getEmail()))
                .collect(Collectors.toList());
    }
}
