package com.example.skajkut.chatapp.data.model;

import java.util.Date;

/**
 * Created by Stefan Kajkut on 6/20/2017.
 * Contact me on stefan.kajkutsf@gmail.com.
 */

public class Message {

    private String mText;
    private Date mDateTime;
    private User mUser;

    public Message() {
        super();
        this.mDateTime = new Date();
    }

    public Message(String text, Date dateTime, User user) {
        super();
        this.mText = text;
        this.mDateTime = dateTime;
        this.mUser = user;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public Date getDateTime() {
        return mDateTime;
    }

    public void setDateTime(Date dateTime) {
        mDateTime = dateTime;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }
}
