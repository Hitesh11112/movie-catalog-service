package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CourseWithStudentDto;
import com.example.demo.entity.Course;
import com.example.demo.service.CourseService;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
	private final CourseService courseService;

    CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public Course addCourse(@RequestBody Course course) {
        return courseService.addCourse(course);
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    // Returns the course WITH live student details, fetched via OpenFeign
    @GetMapping("/{id}")
    public CourseWithStudentDto getCourseWithStudent(@PathVariable Long id) {
        return courseService.getCourseWithStudent(id);
    }

    @GetMapping("/student/{studentId}")
    public List<Course> getCoursesByStudent(@PathVariable Long studentId) {
        return courseService.getCoursesByStudent(studentId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        if (!courseService.deleteCourse(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
