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
    private String photo;

    private List<Conversation> conversationList;
    private Map<String, User> friendList;
    private Map<String, User> favoriteList;

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


    public User(String firstname, String lastname, String username, String password, String email) {
        super();
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String firstname, String lastname, String email, String photoUrl) {
        super();
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.photo = photoUrl;
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
    public Map<String, User> getFriendList() {
        return this.friendList;
    }

    @Exclude
    public void setFriendList(Map<String, User> friendList) {
        this.friendList = friendList;
    }

    @Exclude
    public List<Conversation> getConversationList() {
        return this.conversationList;
    }

    @Exclude
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Exclude
    public Map<String, User> getFavoriteList() {
        return this.favoriteList;
    }

    @Exclude
    public void setFavoriteList(Map<String, User> favoriteList) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        return username.equals(user.username);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + username.hashCode();
        return result;
    }


}
