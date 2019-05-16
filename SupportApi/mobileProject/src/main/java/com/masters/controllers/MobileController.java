package com.masters.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	  
}
