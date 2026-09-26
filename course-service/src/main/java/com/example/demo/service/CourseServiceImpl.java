package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.CourseWithStudentDto;
import com.example.demo.dto.StudentDto;
import com.example.demo.entity.Course;
import com.example.demo.feign.StudentClient;
import com.example.demo.respository.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService{
	
	private final CourseRepository courseRepository;
    private final StudentClient studentClient;

    CourseServiceImpl(CourseRepository courseRepository, StudentClient studentClient) {
        this.courseRepository = courseRepository;
        this.studentClient = studentClient;
    } // OpenFeign client -> calls student-service

    @Override
    public Course addCourse(Course course) {
        // Confirms the student exists in student-service before enrolling them
        StudentDto student = studentClient.getStudentById(course.getStudentId());
        if (student == null) {
            throw new RuntimeException("Student not found: " + course.getStudentId());
        }
        return courseRepository.save(course);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public CourseWithStudentDto getCourseWithStudent(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found: " + courseId));

        StudentDto student = studentClient.getStudentById(course.getStudentId());
        return new CourseWithStudentDto(course, student);
    }

    @Override
    public List<Course> getCoursesByStudent(Long studentId) {
        return courseRepository.findByStudentId(studentId);
    }

    @Override
    public boolean deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            return false;
        }
        courseRepository.deleteById(id);
        return true;
    }
}
