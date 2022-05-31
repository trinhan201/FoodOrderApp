package com.example.foodorderapp.observer;

import java.io.Serializable;

public class EmailData implements Serializable {
    private String address;
    private String subject;
    private String message;

    public EmailData() {

    }

    public void setAddress(String emailAddress) {
        this.address = emailAddress;
    }

    public String getAddress() {
        return this.address;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
