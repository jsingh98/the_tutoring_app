package com.example.thetutoringapp;

public class TutorModel {

    private String first;
    private String email;
    private String subject;

    private TutorModel(){}

    private TutorModel(String first, String last, String subject){
        this.first = first;
        this.email = last;
        this.subject = subject;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
