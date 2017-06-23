package com.example.skajkut.chatapp.util;

import android.util.Log;

import com.example.skajkut.chatapp.data.model.Conversation;
import com.example.skajkut.chatapp.data.model.Message;
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
        user1.setId("-KnIiEneW8DHOqdWGoUC");
        user1.setFirstname("Stefan");
        user1.setLastname("Kajkut");
        user1.setUsername("stek");
        user1.setPassword("123");

        User user2 = new User();
        user2.setId("PBVN0k54vZe4MOk2DpXEOPlgvHI3");
        user2.setFirstname("Nikola");
        user2.setLastname("Sofronovic");
        user2.setUsername("nikola");
        user2.setPassword("123");
        user2.setId("PBVN0k54vZe4MOk2DpXEOPlgvHI3");

        User user3 = new User();
        user3.setId("-KnIiEnhsL4W8944e5xq");
        user3.setFirstname("Djordje");
        user3.setLastname("Djolenece");
        user3.setUsername("djoks");
        user3.setPassword("123");

        Conversation c1 = new Conversation();
        c1.getUsers().put(user1.getId(), user1.getUsername());
        c1.getUsers().put(user2.getId(), user2.getUsername());

        Message m1 = new Message();
        m1.getDateTime();
        m1.setText("Hello!");
        m1.setUser(user1);

        Message m2 = new Message();
        m2.getDateTime();
        m2.setText("Hello from user2");
        m2.setUser(user2);

        c1.getMessageList().add(m1);
        c1.getMessageList().add(m2);

  /*      FriendList friendList1 = new FriendList();
        friendList1.getFriends().put(user2.getUsername(), user2);
        user1.setFriendList(friendList1);*/
/*
        FriendList friendList2 = new FriendList();
        friendList2.getFriends().put(user3.getUsername(), user3);
        user2.setFriendList(friendList2);

        FriendList friendList3 = new FriendList();
        friendList3.getFriends().put(user1.getUsername(), user1);
        user3.setFriendList(friendList3);*/

        mDatabase = FirebaseDatabase.getInstance().getReference();
        String id = mDatabase.push().getKey();
        mDatabase.child("conversations").child(id).setValue(c1);

        //user1.setId(mDatabase.push().getKey());
        //      String user1ID = mDatabase.push().getKey();
        //String user2ID = mDatabase.push().getKey();
//        String user3ID = mDatabase.push().getKey();

        //user2.getFriendList().put(user1ID, user1.getUsername());
        //user2.getFriendList().put(user3ID, user3.getUsername());

/*        user2.getFriendList().add(user1);
        user2.getFriendList().add(user3);*/
        //mDatabase.setValue("users");
        //mDatabase.child("users").child(user1ID).setValue(user1);
        //for(Map.Entry<String, String> user : user2.getFriendList().entrySet()) {
          //  mDatabase.child("friendlist").child(user2.getId()).child(user.getKey()).setValue(user.getValue());
/*        for (User i : user2.getFriendList()) {
             mDatabase.child("friendlist").child(user2.getId()).child(i.getId()).setValue(i);
        }*/


        }

        //mDatabase.child("users").child(user1ID).setValue(user1);
       // mDatabase.child("users").child(user3ID).setValue(user3);
  //  }
}
