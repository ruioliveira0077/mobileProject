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

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Classes {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    @Column(name="id")
    private long id;
    
    private int duration; 

	Long subject_id;
    Long room_id;
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
    
    public Classes(Subjects subjects,Rooms rooms, Date   date, Date dateToCompare, int duration ) {
		super();
		this.id = id; 
		this.subjects = subjects;
		this.date = date; 
		this.rooms = rooms; 
		this.duration = duration;
		this.dateToCompare = dateToCompare; 
		this.subject_id = subjects.getId(); 
		this.room_id=rooms.getId(); 
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

	public Long getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(Long subject_id) {
		this.subject_id = subject_id;
	}

	public Long getRoom_id() {
		return room_id;
	}

	public void setRoom_id(Long room_id) {
		this.room_id = room_id;
	}
    
}
