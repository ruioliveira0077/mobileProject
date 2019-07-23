package com.example.sheduleorganizer;

public class Classes {

    private Long id_subject;
    private Long id;
    private String date;
    private Rooms room;
    private Subjects subjects;

    public Subjects getSubjects() {
        return subjects;
    }

    public void setSubjects(Subjects subjects) {
        this.subjects = subjects;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    private int duration;

    public Classes(Subjects subjects, String date, Rooms room, int duration) {
        this.id_subject = id_subject;
        this.date = date;
        this.room = room;
        this.subjects=subjects;
        this.duration = duration;
    }

    /*
    public Long getId_subject() {
        return id_subject;
    }

    public void setId_subject(Long id_subject) {
        this.id_subject = id_subject;
    }
    */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Rooms getRoom() {
        return room;
    }

    public void setRoom(Rooms room) {
        this.room = room;
    }
}
