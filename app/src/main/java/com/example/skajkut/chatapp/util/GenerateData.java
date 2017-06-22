package com.example.skajkut.chatapp.util;

import com.example.skajkut.chatapp.data.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        user2.setId("PBVN0k54vZe4MOk2DpXEOPlgvHI3");

        User user3 = new User();
        user3.setFirstname("Djordje");
        user3.setLastname("Djolenece");
        user3.setUsername("djoks");
        user3.setPassword("123");

        /*FriendList friendList1 = new FriendList();
        friendList1.getFriends().put(user2.getUsername(), user2);
        user1.setFriendList(friendList1);

        FriendList friendList2 = new FriendList();
        friendList2.getFriends().put(user3.getUsername(), user3);
        user2.setFriendList(friendList2);

        FriendList friendList3 = new FriendList();
        friendList3.getFriends().put(user1.getUsername(), user1);
        user3.setFriendList(friendList3);*/

        mDatabase = FirebaseDatabase.getInstance().getReference();
        //user1.setId(mDatabase.push().getKey());
        String user1ID = mDatabase.push().getKey();
        //String user2ID = mDatabase.push().getKey();
        String user3ID = mDatabase.push().getKey();

        user2.getFriendList().put(user1ID, user1.getUsername());
        user2.getFriendList().put(user3ID, user3.getUsername());
/*
        user2.getFriendList().put(user1ID, user1.getUsername());
        user3.getFriendList().put(user1ID, user1.getUsername());*/
        //mDatabase.setValue("users");
        //mDatabase.child("users").child(user1ID).setValue(user1);
        for(Map.Entry<String, String> user : user2.getFriendList().entrySet()) {
            mDatabase.child("friendlist").child(user2.getId()).child(user.getKey()).setValue(user.getValue());
        }
        mDatabase.child("users").child(user1ID).setValue(user1);
        mDatabase.child("users").child(user3ID).setValue(user3);
    }
}
