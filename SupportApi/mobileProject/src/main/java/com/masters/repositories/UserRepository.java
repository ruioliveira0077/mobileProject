package com.masters.repositories;

import org.springframework.data.repository.CrudRepository;

import com.masters.models.User;

public interface UserRepository  extends CrudRepository<User, Long>{
	Iterable<User> findAll();
	
	User findByEmailAndPassword(String email, String password);
}
