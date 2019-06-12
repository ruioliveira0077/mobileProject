package com.example.sheduleorganizer;

public class Courses {

    private String title;
    private Long user_id;
    private Long id;


    public Courses(String title, Long user_id) {
        this.user_id = user_id;
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
