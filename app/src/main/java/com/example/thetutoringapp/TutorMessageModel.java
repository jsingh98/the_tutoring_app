package com.example.thetutoringapp;

public class TutorMessageModel {


    private String tutorEmail;


    private TutorMessageModel(){}

    private TutorMessageModel( String email){

        this.tutorEmail = email;
    }

    public String getTutorEmail() {
        return tutorEmail;
    }

    public void setTutorEmail(String tutorEmail) {
        this.tutorEmail = tutorEmail;
    }
}
