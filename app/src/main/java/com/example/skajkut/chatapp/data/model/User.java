package com.example.skajkut.chatapp.data.model;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stefan Kajkut on 6/20/2017.
 * Contact me on stefan.kajkutsf@gmail.com.
 */

public class User {

    private String firstname;
    private String lastname;
    private String username;
    private String password;

    private List<String> friendList;

    @Exclude
    private List<Conversation> conversationList;

    public User() {
        super();
        this.conversationList = new ArrayList<>();
    }

    public User(List<String> friendList, List<Conversation> conversations,
                String firstname, String lastname, String username,
                String password) {
        super();
        this.friendList = friendList;
        this.conversationList = conversations;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
    }

    public List<String> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<String> friendList) {
        friendList = friendList;
    }

    public List<Conversation> getConversationList() {
        return conversationList;
    }

    public void setConversationList(List<Conversation> conversationList) {
        conversationList = conversationList;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        password = password;
    }
}
