package com.masters.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masters.models.Rooms;
import com.masters.models.Subjects;

@RestController
@RequestMapping("/getRooms")
public class RoomsController {
	@Autowired // This means to get the bean called userRepository
	private com.masters.repositories.RoomsRepository roomsRepository;
	
	@GetMapping("/allRooms")
    public List<Rooms> allRooms() {
        return (List<Rooms>) roomsRepository.allRooms();
    }
	
	@GetMapping("/roomByTitle")
    public List<Rooms> subjectByTitle(@RequestParam("title") String title) {
        return (List<Rooms>) roomsRepository.roomByTitle(title);
    }
}