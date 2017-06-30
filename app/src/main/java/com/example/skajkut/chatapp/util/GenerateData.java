package com.example.skajkut.chatapp.util;

import com.example.skajkut.chatapp.data.model.Conversation;
import com.example.skajkut.chatapp.data.model.Message;
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
        mDatabase = FirebaseDatabase.getInstance().getReference();

        User user1 = new User();
        user1.setId("-KnIiEneW8DHOqdWGoUC");
        user1.setFirstname("Stefan");
        user1.setLastname("Kajkut");
        user1.setUsername("stek");
        user1.setPassword("123");


        User user2 = new User();
        user2.setFirstname("Nikola");
        user2.setLastname("Sofronovic");
        user2.setUsername("nikola");
        user2.setPassword("123");
        user2.setId("fhj3hIqnOkQHgTEkpnVfzj2lQRQ2");

        User user3 = new User();
        user3.setId("-KnIiEnhsL4W8944e5xq");
        user3.setFirstname("Djordje");
        user3.setLastname("Djolenece");
        user3.setUsername("djoks");
        user3.setPassword("123");

        Conversation c1 = new Conversation();
        c1.setTitle("Title1");
//        c1.getUsers().put(user1.getId(), user1.getUsername());
//        c1.getUsers().put(user2.getId(), user2.getUsername());
        c1.setId(mDatabase.push().getKey());

        Message m1 = new Message();
        m1.getDateTime();
        m1.setText("Hello!");
        //m1.getUsers().put(user1.getId(), user1.getUsername());
        m1.setSender(user1.getFirstname() + " " + user1.getLastname());
        //m1.setUser(user1);

        Message m2 = new Message();
        m2.getDateTime();
        m2.setText("Hello from user2");
        //m2.getUsers().put(user2.getId(), user2.getUsername());
        m2.setSender(user2.getFirstname() + " " + user2.getLastname());

        c1.getMessageList().add(m1);
        c1.getMessageList().add(m2);

        user1.getConversationList().add(c1);
/*

        String c_id = mDatabase.push().getKey();
        mDatabase.child("conversations").child(c_id).setValue(c1);*/

        //user1.setId(mDatabase.push().getKey());
        //String user1ID = mDatabase.push().getKey();
        //String user2ID = mDatabase.push().getKey();
        //String user3ID = mDatabase.push().getKey();

        user2.getFriendList().put(user1.getId(), user1.getUsername());
        user2.getFriendList().put(user3.getId(), user3.getUsername());


        user2.getFavoriteList().put(user1.getId(), user1.getUsername());
        user2.getFavoriteList().put(user3.getId(), user3.getUsername());
        //mDatabase.setValue("users");



        //mDatabase.child("users").child(user1ID).setValue(user1);
/*        for(Map.Entry<String, String> user : user2.getFavoriteList().entrySet()) {
            mDatabase.child("favoritefriends").child(user2.getId()).child(user.getKey()).setValue(user.getValue());
        }
        for (Map.Entry<String, String> user : user2.getFriendList().entrySet()) {
             mDatabase.child("friendlist").child(user2.getId()).child(user.getKey()).setValue(user.getValue());
        }*/

       /* for (User i : user2.getFavoriteList()){
            mDatabase.child("favoritefriends").child(user2.getId()).child(i.getId()).setValue(i);
        }*/
/*

        mDatabase.child("users").child(user1.getId()).setValue(user1);
        mDatabase.child("users").child(user2.getId()).setValue(user2);
        mDatabase.child("users").child(user3.getId()).setValue(user3);
*/


        for(Message m : c1.getMessageList()) {
            mDatabase.child("conversations").child(c1.getId()).child("messages").setValue(m);
            //mDatabase.child("conversations").child(c1.getId()).child("users").updateChildren(c1.getUsers());
            mDatabase.child("conversations").child(c1.getId()).child("lastMessage").setValue(m);
        }

        mDatabase.child("conversations").child(c1.getId()).child("title").setValue(c1.getTitle());
        List<String> c = new ArrayList<>();
        c.add(c1.getId());
        mDatabase.child("conversationlist").child("fhj3hIqnOkQHgTEkpnVfzj2lQRQ2").setValue(c);

    }
}
