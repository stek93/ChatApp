package com.example.skajkut.chatapp.data.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Stefan Kajkut on 6/20/2017.
 * Contact me on stefan.kajkutsf@gmail.com.
 */

public class Conversation {

    private Set<User> users;
    private List<Message> messageList;

    public Conversation() {
        super();
        this.users = new HashSet<>();
        this.messageList = new ArrayList<>();
    }

    public Conversation(Set<User> users, List<Message> messages) {
        super();
        this.users = users;
        this.messageList = messages;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        users = users;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        messageList = messageList;
    }
}
