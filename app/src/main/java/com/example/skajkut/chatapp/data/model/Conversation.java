package com.example.skajkut.chatapp.data.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by Stefan Kajkut on 6/20/2017.
 * Contact me on stefan.kajkutsf@gmail.com.
 */

public class Conversation {

    private String id;
    private Map<String, Object> users;
    private List<Message> messageList;

    public Conversation() {
        super();
        this.users = new HashMap<>();
        this.messageList = new ArrayList<>();
    }

    public Conversation(Map<String, Object> users, List<Message> messages) {
        super();
        this.users = users;
        this.messageList = messages;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getUsers() {
        return this.users;
    }

    public void setUsers(Map<String, Object> users) {
        this.users = users;
    }

    public List<Message> getMessageList() {
        return this.messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

}
