package com.masters.controllers;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.masters.models.Classes;
import com.masters.models.Courses;
import com.masters.models.Rooms;
import com.masters.models.Subjects;


@RestController
@RequestMapping("/getClasses")
public class ClassesController {
	@Autowired // This means to get the bean called userRepository
	private com.masters.repositories.ClassesRepository classesRepository;
	
	@GetMapping("/classesBySubject")
    public List<Classes> classesBySubject(@RequestParam("subject_id") Long subject_id) {
        return (List<Classes>) classesRepository.classesBySubject(subject_id);
    }
	
	@GetMapping("/classesByDate")
    public List<Classes> classesByDate(@RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date,@RequestParam("user_id") Long user_id) {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		String strDate = dateFormat.format(date);  
		
		System.out.println("my stufff "+strDate);
        return (List<Classes>) classesRepository.classesByDate(strDate, user_id);
    }
	
	@GetMapping("/classesByDateAndSubject")
    public List<Classes> classesByDateAndSubject(@RequestParam("subject_id") Long subject_id,@RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date,@RequestParam("user_id") Long user_id) {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		String strDate = dateFormat.format(date);  
		
		System.out.println("my stufff "+strDate);
        return (List<Classes>) classesRepository.classesByDateAndSubject(subject_id, strDate, user_id);
    }
	
	@DeleteMapping("/deleteClass/{id}")
	public void deleteClass(@PathVariable long id) {
		classesRepository.deleteClass(id);
	}
	
	@PostMapping("/createClass")
	@ResponseBody
	public Classes CreateCourse( @RequestParam Subjects subject, @RequestParam Rooms rooms, @RequestParam Date date,  @RequestParam Date dateToCompare,@RequestParam int duration) { 
		return classesRepository.save(new Classes(subject,rooms, date, dateToCompare,duration));
	}
}

