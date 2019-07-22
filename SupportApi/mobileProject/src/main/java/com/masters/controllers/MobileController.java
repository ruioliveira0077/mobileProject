package com.masters.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.masters.models.Courses;
import com.masters.models.Subjects;

@RestController
@RequestMapping("/getSubjects")
public class MobileController {
	@Autowired // This means to get the bean called userRepository
	private com.masters.repositories.SubjectRepository subjectRepository;
	
	@GetMapping("/subjects")
    public List<com.masters.models.Subjects> getAllSubjects() {
        return (List<Subjects>) subjectRepository.findAll();
    }
	
	@GetMapping("/subjectByCourse")
    public List<Subjects> subjectByCourse(@RequestParam("course_id") Long course_id) {
        return (List<Subjects>) subjectRepository.subjectByCourse(course_id);
    }
	
	@GetMapping("/subjectByTitle")
    public List<Subjects> subjectByTitle(@RequestParam("title") String title) {
        return (List<Subjects>) subjectRepository.subjectByTitle(title);
    }
	  
	@PostMapping("/createSubject")
	@ResponseBody
	public Subjects CreateSubject( @RequestParam Long course_id, @RequestParam String title) { 
		return subjectRepository.save(new Subjects(null, course_id, title, null));
	}
	
	@DeleteMapping("/deleteSubject/{id}")
	public void deleteSubject(@PathVariable long id) {
		subjectRepository.deleteSubject(id);
	}
	
}
