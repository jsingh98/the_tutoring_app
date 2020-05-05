package com.example.thetutoringapp;

public class TutorMessageModel {


    private String tutorEmail;
    private String subject;


    private TutorMessageModel(){}

    private TutorMessageModel( String email, String subject){

        this.tutorEmail = email;
        this.subject = subject;
    }

    public String getTutorEmail() {
        return tutorEmail;
    }

    public void setTutorEmail(String tutorEmail) {
        this.tutorEmail = tutorEmail;
    }


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
