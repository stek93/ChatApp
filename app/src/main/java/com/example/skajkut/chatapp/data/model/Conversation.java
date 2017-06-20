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

    private Set<User> mUsers;
    private List<Message> mMessageList;

    public Conversation() {
        super();
        this.mUsers = new HashSet<>();
        this.mMessageList = new ArrayList<>();
    }

    public Conversation(Set<User> users, List<Message> messages) {
        super();
        this.mUsers = users;
        this.mMessageList = messages;
    }

    public Set<User> getUsers() {
        return mUsers;
    }

    public void setUsers(Set<User> users) {
        mUsers = users;
    }

    public List<Message> getMessageList() {
        return mMessageList;
    }

    public void setMessageList(List<Message> messageList) {
        mMessageList = messageList;
    }
}
