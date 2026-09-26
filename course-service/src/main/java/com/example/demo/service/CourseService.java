package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.CourseWithStudentDto;
import com.example.demo.entity.Course;

public interface CourseService {
	Course addCourse(Course course);
    List<Course> getAllCourses();
    CourseWithStudentDto getCourseWithStudent(Long courseId);
    List<Course> getCoursesByStudent(Long studentId);
    boolean deleteCourse(Long id);
}
