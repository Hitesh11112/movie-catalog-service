package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import com.example.demo.security.JwtUtil;

@Service
public class StudentServiceImpl implements StudentService{
	private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    StudentServiceImpl(StudentRepository studentRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Student register(RegisterRequest request) {
        Student student = new Student(
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );
        return studentRepository.save(student);
    }

    @Override
    public String login(LoginRequest request) {
        Student student = studentRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), student.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return jwtUtil.generateToken(student.getEmail());
    }

    @Override
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
