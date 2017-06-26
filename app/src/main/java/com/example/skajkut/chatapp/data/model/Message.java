package com.example.skajkut.chatapp.data.model;

import java.util.Date;

/**
 * Created by Stefan Kajkut on 6/20/2017.
 * Contact me on stefan.kajkutsf@gmail.com.
 */

public class Message {

    private String text;
    private Date dateTime;
    private String sender;

    public Message() {
        super();
        this.dateTime = new Date();
    }

    public Message(String text, Date dateTime, String sender) {
        super();
        this.text = text;
        this.dateTime = dateTime;
        this.sender = sender;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getSender() {
        return this.sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
