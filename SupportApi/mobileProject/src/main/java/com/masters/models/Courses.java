package com.masters.models;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Courses")

public class Courses {

    String title;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) long id;
    Long user_id;
    
    public Courses() {
    	
    }
    
	public Courses(Long user_id, String title) {
		super();
		this.id = id; 
		this.user_id = user_id;
		this.title = title; 
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getID() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	} 
}