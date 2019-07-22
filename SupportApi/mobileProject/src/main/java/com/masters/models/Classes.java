package com.masters.models;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Classes {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    @Column(name="id")
    private long id;
    
    private int duration; 

	//Long subject_id;
    //Long room_id;
    Date date;
    @Temporal(TemporalType.DATE)
    Date dateToCompare;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	private Rooms rooms;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subject_id", insertable = false, updatable = false)
	@Fetch(FetchMode.JOIN)
	
	private Subjects subjects; 

    
    
    public Classes() {

    }
    
    public Classes(Subjects subjects,Rooms rooms, Date   date, int duration ) {
		super();
		this.id = id; 
		this.subjects = subjects;
		this.date = date; 
		this.rooms = rooms; 
		this.duration = duration;
	}

	public Subjects getSubjects() {
		return subjects;
	}
	
    public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	public void setSubjects(Subjects subjects) {
		subjects.setClasses(null);
		this.subjects = subjects;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Rooms getRooms() {
		return rooms;
	}

	public void setRooms(Rooms rooms) {
		rooms.setClasses(null);
		this.rooms = rooms;
	}

	public Date   getDate() {
		return date;
	}
	public void setDate(Date   date) {
		this.date = date;
	}

	public Date getDateToCompare() {
		return dateToCompare;
	}

	public void setDateToCompare(Date dateToCompare) {
		this.dateToCompare = dateToCompare;
	}
    
}
