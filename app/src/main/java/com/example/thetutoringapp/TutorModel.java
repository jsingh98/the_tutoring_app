package com.example.thetutoringapp;

public class TutorModel {

    private String first;
    private String email;
    private String subject;
    private String price;
    private String num;
    private String total;

    private TutorModel(){}

    private TutorModel(String first, String email, String subject, String price, String num, String total){
        this.first = first;
        this.email = email;
        this.subject = subject;
        this.price = price;
        this.num = num;
        this.total = total;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}


