package com.masters.models;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Subjects")

public class Subjects {

    String title;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) long id;
    
    Long course_id; 

	@JsonIgnore 
    @OneToMany(targetEntity = Classes.class, mappedBy = "subjects", orphanRemoval = false, fetch = FetchType.LAZY)
    private Set<Classes> classes;
    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Courses courses; 
	
    public Subjects() {

    }

	public Subjects(Courses courses, Long course_id, String title, Set<Classes> classes) {
		super();
		this.id = id; 
		this.course_id = course_id; 
		this.courses = courses;
		this.title = title; 
		this.classes= classes; 
	}
    
	public Courses getCourses() {
		return courses;
	}

	public void setCourses(Courses courses) {
		courses.setSubjects(null);
		this.courses = courses;
	}

	public String getTitle() {
    return title;
    }
    public void setTitle(String title) {
    this.title = title;
    }
    
	public Long getId() {
    return id;
    }
    public void setId(Long id) {
    this.id = id;
    }
    public Set<Classes> getClasses() {
		return classes;
	}

	public void setClasses(Set<Classes> classes) {
		this.classes = classes;
	}
	
    public Long getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Long course_id) {
		this.course_id = course_id;
	}
}
