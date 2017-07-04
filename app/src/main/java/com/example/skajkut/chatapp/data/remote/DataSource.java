package com.example.skajkut.chatapp.data.remote;

import android.net.Uri;

import com.example.skajkut.chatapp.data.model.Conversation;
import com.example.skajkut.chatapp.data.model.Message;
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
    public abstract void createUser(String firstname, String lastname, String username,
                                    String email, String password, AddUserCallback callback);
    public abstract void createUserFromProvider(String firstname, String lastname, String email, Uri photoUrl, AddUserFromProviderCallback callback);
    public abstract void searchUsers(String searchValue, String searchQuery,
                                     SearchUsersCallback callback);
    public abstract void addFavorite(User user, AddFavoriteCallback callback);
    public abstract void  removeFavorite(User user, RemoveFavoriteCallback callback);
    public abstract void getMessages(GetMessagesCallback callback);
    public abstract void sendMessage(String message, SendMessageCallback callback);

    public interface GetEntityCallback<T> {
        void onSuccess(T classType);

        void onFailure(Throwable throwable);

        void onNetworkFailure();
    }

    public interface GetConversationListCallback extends GetEntityCallback<List<String>> {

        @Override
        void onSuccess(List<String> conversations);

        void onEmptyList();
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

        void onEmptyList();
    }

    public interface GetUserCallback extends GetEntityCallback<User> {

        @Override
        void onSuccess(User user);
    }

    public interface GetUsersCallback extends GetEntityCallback<List<User>> {

        @Override
        void onSuccess(List<User> users);
    }

    public interface AddUserCallback extends GetEntityCallback<User>{

        @Override
        void onSuccess(User user);
    }

     public interface AddUserFromProviderCallback extends GetEntityCallback<User>{

         @Override
         void onSuccess(User user);
     }

     public interface SearchUsersCallback extends GetEntityCallback<List<User>> {

         @Override
         void onSuccess(List<User> users);

         void onEmptyList();

     }

     public interface AddFavoriteCallback extends  GetEntityCallback<User>{

         @Override
         void onSuccess(User user);
     }

     public interface RemoveFavoriteCallback extends GetEntityCallback<User>{

         @Override
         void onSuccess(User user);
     }

     public interface GetMessagesCallback extends GetEntityCallback<List<Message>>{

         @Override
         void onSuccess(List<Message> message);
     }

     public interface SendMessageCallback extends GetEntityCallback<Message>{

         @Override
         void onSuccess(Message message);
     }

}
