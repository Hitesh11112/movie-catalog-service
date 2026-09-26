package com.example.demo.dto;

import com.example.demo.entity.Course;

public class CourseWithStudentDto {
	private Course course;
    private StudentDto student;

    public CourseWithStudentDto(Course course, StudentDto student) {
        this.course = course;
        this.student = student;
    }

    public Course getCourse() { return course; }
    public StudentDto getStudent() { return student; }
}
