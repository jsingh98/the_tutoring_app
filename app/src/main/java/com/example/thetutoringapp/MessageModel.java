package com.example.thetutoringapp;

public class MessageModel {

    private String from;
    private String message;
    private String date;

    private MessageModel(String from, String message) {
        this.from = from;
        this.message = message;
    }

    private MessageModel(){}

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
