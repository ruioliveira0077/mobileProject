package com.masters.controllers;


import java.text.DateFormat;
import java.text.ParseException;
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
	private com.masters.repositories.SubjectRepository subjectsRepository;
	private com.masters.repositories.RoomsRepository roomsRepository;
	
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
	public Classes CreateClass( @RequestParam long subject_id, @RequestParam long room_id, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") String date,  @RequestParam String dateToCompare,@RequestParam int duration) throws ParseException { 
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		Date strDate = dateFormat.parse(dateToCompare);  
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date strDate2 = sdf.parse(date);
		
		System.out.println("my id is"+subject_id);
		
		List<Subjects> subject = (List<Subjects>) subjectsRepository.subjectById(subject_id);
		List<Rooms> room = (List<Rooms>) roomsRepository.roomById(room_id);
		
		return classesRepository.save(new Classes(subject.get(0),room.get(0), strDate2, strDate,duration));
	}
}

