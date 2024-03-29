package com.masters.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.masters.models.Courses;
import com.masters.models.User;
@RestController
@RequestMapping("/getCourses")
public class CoursesController {
	@Autowired // This means to get the bean called userRepository
	private com.masters.repositories.CoursesRepository coursesRepository;
	
	@GetMapping("/courses")
    public List<Courses> getCoursesById(@RequestParam("user_id") Long user_id) {
        return (List<Courses>) coursesRepository.getCoursesById(user_id);
    }
	
	@GetMapping("/courseByTitle")
    public List<Courses> getCoursesById(@RequestParam("user_id") Long user_id, @RequestParam("title") String title) {
        return (List<Courses>) coursesRepository.getCourseByTitle(user_id, title);
    }
	
	@PostMapping("/createCourse")
	@ResponseBody
	public Courses CreateCourse( @RequestParam Long user_id, @RequestParam String title) { 
		return coursesRepository.save(new Courses(user_id, title, null));
	}
	
	@DeleteMapping("/deleteCourse/{id}")
	public void deleteCourse(@PathVariable long id) {
		coursesRepository.deleteCourseById(id);
	}
	
	@PutMapping("/editCourse")
	public void editCourse( @RequestParam Long course_id, @RequestParam String title) { 
		 coursesRepository.editCourse(course_id, title);
	}
}