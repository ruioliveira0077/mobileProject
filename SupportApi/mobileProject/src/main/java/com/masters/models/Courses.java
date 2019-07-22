package com.masters.models;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Courses")

public class Courses {

    String title;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) long id;
    Long user_id;
    
    @JsonIgnore 
    @OneToMany(targetEntity = Subjects.class, mappedBy = "courses", orphanRemoval = false, fetch = FetchType.LAZY)
    private Set<Subjects> subjects;
    
    public Courses() {
    	
    }
    
	public Courses(Long user_id, String title, Set<Subjects> subjects) {
		super();
		this.id = id; 
		this.user_id = user_id;
		this.title = title; 
		this.subjects = subjects; 
	}
	
	public Set<Subjects> getSubjects() {
		return subjects;
	}

	public void setSubjects(Set<Subjects> subjects) {
		this.subjects = subjects;
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