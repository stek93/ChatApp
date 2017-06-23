package com.example.skajkut.chatapp.data.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Stefan Kajkut on 6/20/2017.
 * Contact me on stefan.kajkutsf@gmail.com.
 */
@IgnoreExtraProperties
public class User {

    private String id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;

    @Exclude
    private List<Conversation> conversationList;
    private Map<String, String> friendList;
    private Map<String, String> favoriteList;

    public User() {
        super();
        this.friendList = new HashMap<>();
        this.conversationList = new ArrayList<>();
        this.favoriteList = new HashMap<>();
    }

    public User(String firstname, String lastname, String username,
                String password, List<Conversation> conversations) {
        super();
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.conversationList = conversations;
    }

    public User(String id, String firstname, String lastname, String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public User(String firstname, String lastname, String username, String password, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
    }


    @Exclude
    public String getId() {
        return this.id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    public Map<String, String> getFriendList() {
        return this.friendList;
    }

    @Exclude
    public void setFriendList(Map<String, String> friendList) {
        this.friendList = friendList;
    }

    public List<Conversation> getConversationList() {
        return this.conversationList;
    }

    public void setConversationList(List<Conversation> conversationList) {
        this.conversationList = conversationList;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, String> getFavoriteList() {
        return this.favoriteList;
    }

    public void setFavoriteList(Map<String, String> favoriteList) {
        this.favoriteList = favoriteList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", conversationList=" + conversationList +
                ", friendList=" + friendList +
                '}';
    }
}
