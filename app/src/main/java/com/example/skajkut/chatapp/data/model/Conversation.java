package com.example.skajkut.chatapp.data.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Stefan Kajkut on 6/20/2017.
 * Contact me on stefan.kajkutsf@gmail.com.
 */

public class Conversation {

    private Map<String, String> users;
    private List<Message> messageList;

    public Conversation() {
        super();
        this.users = new HashMap();
        this.messageList = new ArrayList<>();
    }

    public Conversation(Map<String, String> users, List<Message> messages) {
        super();
        this.users = users;
        this.messageList = messages;
    }

    public Map<String, String> getUsers() {
        return this.users;
    }

    public void setUsers(Map<String, String>users) {
        this.users = users;
    }

    public List<Message> getMessageList() {
        return this.messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }
}
