package com.masters.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Rooms {
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name; 
    @JsonIgnore 
    @OneToMany(targetEntity = Classes.class, mappedBy = "rooms", orphanRemoval = false, fetch = FetchType.LAZY)
    
	private Set<Classes> classes;

    public Rooms()
    {
        super();
    }

    public Rooms(String name,Set<Classes> classes)
    {
        super();
        this.id = id;
        this.name = name;
        this.classes = classes;
    }


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<Classes> getClasses() {
		return classes;
	}

	public void setClasses(Set<Classes> classes) {
		this.classes = classes;
	}
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
