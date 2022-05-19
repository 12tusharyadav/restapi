package com.springboot.restApi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.restApi.model.Course;
import com.springboot.restApi.repository.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService{

	@Autowired
	private CourseRepository courseRepository;
	
	@Override
	public List<Course> getCourses() {
		
		return courseRepository.findAll();
	}

	@Override
	public Course getCourse(long courseId) {
		Course getCourse = courseRepository.findById(courseId).get();
		return getCourse;
	}

	@Override
	public Course addCourse(Course course) {
		
	 Course newCourse = courseRepository.save(course);
	 return newCourse;
	}

	@Override
	public Course updateCourse(Course course) {
		Course newCourse = courseRepository.save(course);
		return newCourse;
	}

	@Override
	public void deleteCourse(long courseId) {
		Course c = courseRepository.getById(courseId);
		courseRepository.delete(c);	
	}

}
