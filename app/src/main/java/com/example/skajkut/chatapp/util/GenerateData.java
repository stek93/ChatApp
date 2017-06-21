package com.example.skajkut.chatapp.util;

import com.example.skajkut.chatapp.data.model.FriendList;
import com.example.skajkut.chatapp.data.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Stefan Kajkut on 6/20/2017.
 * Contact me on stefan.kajkutsf@gmail.com.
 */

public final class GenerateData {

    private static GenerateData instance = null;
    private DatabaseReference mDatabase;

    private GenerateData() {
        super();
    }

    public static GenerateData getInstance() {
        if(instance == null) {
            instance = new GenerateData();
        }

        return instance;
    }

    public void generateRandomData() {
        User user1 = new User();
        user1.setFirstname("Stefan");
        user1.setLastname("Kajkut");
        user1.setUsername("stek");
        user1.setPassword("123");

        User user2 = new User();
        user2.setFirstname("Nikola");
        user2.setLastname("Sofronovic");
        user2.setUsername("nikola");
        user2.setPassword("123");

        User user3 = new User();
        user3.setFirstname("Djordje");
        user3.setLastname("Djolenece");
        user3.setUsername("djoks");
        user3.setPassword("123");

        FriendList friendList1 = new FriendList();
        friendList1.getFriends().put(user2.getUsername(), user2);
        //user1.setFriendList(friendList1);

        FriendList friendList2 = new FriendList();
        friendList2.getFriends().put(user3.getUsername(), user3);
        user2.setFriendList(friendList2);

        FriendList friendList3 = new FriendList();
        friendList3.getFriends().put(user1.getUsername(), user1);
        user3.setFriendList(friendList3);
        friendList1.getFriends().put(user3.getUsername(), user3);
        user1.setFriendList(friendList1);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(user1.getUsername()).setValue(user1);
        mDatabase.child("users").child(user2.getUsername()).setValue(user2);
        mDatabase.child("users").child(user3.getUsername()).setValue(user3);

        List<User> list = new ArrayList<>(user1.getFriendList().getFriends().values());
        for(User usr : list) {
            List<User> list2 = new ArrayList<>(usr.getFriendList().getFriends().values());
            for(User usr2 : list2) {
                mDatabase.child("friendlists").child(usr.getUsername()).setValue(usr2.getUsername());
            }
        }

    }
}
