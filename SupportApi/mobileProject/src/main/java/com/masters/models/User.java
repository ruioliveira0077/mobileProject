package com.masters.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="User")
public class User {
	
    @Column(name="email")
    String email;
    String password;
    String username;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) long id;
    
    public User() {
    	
    }
    
	public User(String username, String password, String email) {
		super();
		this.id = id; 
		this.username = username;
		this.password = password;
		this.email = email; 
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
    
    
}
