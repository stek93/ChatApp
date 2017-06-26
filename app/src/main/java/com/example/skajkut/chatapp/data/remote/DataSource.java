package com.example.skajkut.chatapp.data.remote;

import com.example.skajkut.chatapp.data.model.Conversation;
import com.example.skajkut.chatapp.data.model.User;

import java.util.List;

/**
 * Created by Stefan Kajkut on 6/26/2017.
 * Contact me on stefan.kajkutsf@gmail.com.
 */

public abstract class DataSource {

    public abstract void getConversationList(String userID, GetConversationListCallback callback);
    public abstract void getConversation(String conversationID, GetConversationCallback callback);
    public abstract void getFriendList(String userID, GetFriendListCallback callback);
    public abstract void getFavoriteList(String userID, GetFavoriteListCallback callback);
    public abstract void getUserByID(String userID, GetUserCallback callback);
    public abstract void getUserByUsername(String username, GetUserCallback callback);
    public abstract void getAllUsers(GetUsersCallback callback);

    public interface GetEntityCallback<T> {
        void onSuccess(T classType);

        void onFailure(Throwable throwable);

        void onNetworkFailure();
    }

    public interface GetConversationListCallback extends GetEntityCallback<List<String>> {

        @Override
        void onSuccess(List<String> conversations);
    }

    public interface GetConversationCallback extends GetEntityCallback<Conversation> {

        @Override
        void onSuccess(Conversation conversation);
    }

    public interface GetFriendListCallback extends GetEntityCallback<List<User>> {

        @Override
        void onSuccess(List<User> users);
    }

    public interface GetFavoriteListCallback extends GetEntityCallback<List<User>> {

        @Override
        void onSuccess(List<User> users);
    }

    public interface GetUserCallback extends GetEntityCallback<User> {

        @Override
        void onSuccess(User user);
    }

    public interface GetUsersCallback extends GetEntityCallback<List<User>> {

        @Override
        void onSuccess(List<User> users);
    }

}
