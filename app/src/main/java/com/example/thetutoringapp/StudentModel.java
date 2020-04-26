package com.example.thetutoringapp;

public class StudentModel {

    private String studentEmail;


    private StudentModel(){}

    private StudentModel( String email){

        this.studentEmail = email;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }
}
