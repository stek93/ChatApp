package com.example.skajkut.chatapp.data.model;

import java.util.Date;

/**
 * Created by Stefan Kajkut on 6/20/2017.
 * Contact me on stefan.kajkutsf@gmail.com.
 */

public class Message {

    private String text;
    private Date dateTime;
    private User user;

    public Message() {
        super();
        this.dateTime = new Date();
    }

    public Message(String text, Date dateTime, User user) {
        super();
        this.text = text;
        this.dateTime = dateTime;
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        text = text;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        dateTime = dateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        user = user;
    }
}
