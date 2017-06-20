package com.example.skajkut.chatapp.data.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stefan Kajkut on 6/20/2017.
 * Contact me on stefan.kajkutsf@gmail.com.
 */

public class FriendList {

    private Map<String, User> mFriends;

    public FriendList() {
        super();
        this.mFriends = new HashMap<>();
    }

    public FriendList(Map<String, User> friends) {
        super();
        this.mFriends = friends;
    }

    public Map<String, User> getFriends() {
        return mFriends;
    }

    public void setFriends(Map<String, User> friends) {
        mFriends = friends;
    }
}
