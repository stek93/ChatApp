package com.example.skajkut.chatapp.data.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Stefan Kajkut on 6/20/2017.
 * Contact me on stefan.kajkutsf@gmail.com.
 */

public class Conversation {

    private List<User> users;
    private List<Message> messageList;

    public Conversation() {
        super();
        this.users = new ArrayList<>();
        this.messageList = new ArrayList<>();
    }

    public Conversation(List<User> users, List<Message> messages) {
        super();
        this.users = users;
        this.messageList = messages;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Message> getMessageList() {
        return this.messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }
}
