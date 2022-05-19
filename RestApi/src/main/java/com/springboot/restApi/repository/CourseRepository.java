package com.springboot.restApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.restApi.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
	
}
