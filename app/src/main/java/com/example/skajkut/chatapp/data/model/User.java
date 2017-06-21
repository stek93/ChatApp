package com.example.skajkut.chatapp.data.model;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stefan Kajkut on 6/20/2017.
 * Contact me on stefan.kajkutsf@gmail.com.
 */

public class User {

    private String mFirstname;
    private String mLastname;
    @Exclude
    private String mUsername;
    private String mPassword;

    @Exclude
    private FriendList mFriendList;
    private List<Conversation> mConversationList;

    public User() {
        super();
        this.mConversationList = new ArrayList<>();
    }

    public User(FriendList friendList, List<Conversation> conversations,
                String firstname, String lastname, String username,
                String password) {
        super();
        this.mFriendList = friendList;
        this.mConversationList = conversations;
        this.mFirstname = firstname;
        this.mLastname = lastname;
        this.mUsername = username;
        this.mPassword = password;
    }

    public FriendList getFriendList() {
        return mFriendList;
    }

    public void setFriendList(FriendList friendList) {
        mFriendList = friendList;
    }

    public List<Conversation> getConversationList() {
        return mConversationList;
    }

    public void setConversationList(List<Conversation> conversationList) {
        mConversationList = conversationList;
    }

    public String getFirstname() {
        return mFirstname;
    }

    public void setFirstname(String firstname) {
        mFirstname = firstname;
    }

    public String getLastname() {
        return mLastname;
    }

    public void setLastname(String lastname) {
        mLastname = lastname;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }
}
