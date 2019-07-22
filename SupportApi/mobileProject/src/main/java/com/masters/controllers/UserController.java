package com.masters.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.masters.models.User;
import com.masters.repositories.UserRepository;


@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired // This means to get the bean called userRepository
	private UserRepository userRepository;
	
	
	@GetMapping("/users")
    public List<User> getAllSubjects() {
        return (List<User>) userRepository.findAll();
    }
	
	@PostMapping("/createUser")
	@ResponseBody
	public User CreateUser( @RequestParam String username, @RequestParam String password, @RequestParam String email) { 
		return userRepository.save(new User(username, password, email));
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
		User user = userRepository.findByEmailAndPassword(email, password);
		
		if(user != null) return new ResponseEntity<>(true, HttpStatus.OK);
		else return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
	}
}