package com.masters.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masters.models.Courses;
@RestController
@RequestMapping("/getCourses")
public class CoursesController {
	@Autowired // This means to get the bean called userRepository
	private com.masters.repositories.CoursesRepository coursesRepository;
	
	@GetMapping("/courses")
    public List<Courses> getCoursesById(@RequestParam("user_id") Long user_id) {
        return (List<Courses>) coursesRepository.getCoursesById(user_id);
    }
		  
}