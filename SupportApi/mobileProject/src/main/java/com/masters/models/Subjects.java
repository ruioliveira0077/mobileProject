package com.masters.models;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Subjects")

public class Subjects {

    String title;
    @Id
    Long id;
    
    public String gettitle() {
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
}
