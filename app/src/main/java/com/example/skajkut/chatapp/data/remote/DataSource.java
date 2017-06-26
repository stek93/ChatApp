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

    public interface GetConversationListCallback {
        void onSuccess(List<String> conversations);

        void onFailure(Throwable throwable);

        void onNetworkFailure();
    }

    public interface GetConversationCallback {
        void onSuccess(Conversation conversation);

        void onFailure(Throwable throwable);

        void onNetworkFailure();
    }

    public interface GetFriendListCallback {
        void onSuccess(List<User> users);

        void onFailure(Throwable throwable);

        void onNetworkFailure();
    }

    public interface GetFavoriteListCallback {
        void onSuccess(List<User> users);

        void onFailure(Throwable throwable);

        void onNetworkFailure();
    }

    public interface GetUserCallback {
        void onSuccess(User user);

        void onFailure(Throwable throwable);

        void onNetworkFailure();
    }

    public interface GetUsersCallback {
        void onSuccess(List<User> users);

        void onFailure(Throwable throwable);

        void onNetworkFailure();
    }

}
