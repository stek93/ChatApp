package com.example.skajkut.chatapp.data.model;

import android.support.annotation.Nullable;

import com.google.firebase.database.Exclude;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Stefan Kajkut on 6/20/2017.
 * Contact me on stefan.kajkutsf@gmail.com.
 */

public class Conversation {

    private String id;

    private String title;
    private Map<String, String> users;

    private List<Message> messageList;

    @Exclude
    private Message lastMessage;

    public Conversation() {
        super();
        this.users = new HashMap<>();
        this.messageList = new ArrayList<>();
    }

    public Conversation(Map<String, String> users, List<Message> messages, String title) {
        super();
        this.users = users;
        this.messageList = messages;
        this.title = title;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    public Map<String, String> getUsers() {
        return this.users;
    }

    @Exclude
    public void setUsers(Map<String, String> users) {
        this.users = users;
    }

    public List<Message> getMessageList() {
        return this.messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }
}
