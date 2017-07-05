package com.example.skajkut.chatapp.ui.conversation;

import com.example.skajkut.chatapp.data.model.Conversation;
import com.example.skajkut.chatapp.data.model.Message;
import com.example.skajkut.chatapp.data.model.User;
import com.example.skajkut.chatapp.data.remote.DataSource;
import com.example.skajkut.chatapp.data.remote.RemoteDataSource;
import com.example.skajkut.chatapp.util.mvp.BasePresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by internship007 on 7/4/17.
 */

public class ChatPresenter extends BasePresenter<ChatContract.View> implements ChatContract.Presenter{

    private RemoteDataSource remoteDataSource;
    private User currentUser;

    public ChatPresenter(RemoteDataSource remoteDataSource, ChatContract.View view) {
        this.remoteDataSource = remoteDataSource;
        this.currentUser = new User();
        this.view = view;
        currentUser.setId(remoteDataSource.getCurrentUserID());
    }

    @Override
    public void getMessages(Conversation conversation) {
        if (view == null){
            return;
        }

        view.setProgressBar(true);
        String id = remoteDataSource.getCurrentUserID();



        remoteDataSource.getMessages(conversation, new DataSource.GetMessagesCallback() {
            @Override
            public void onSuccess(List<Message> messages) {
                view.showMessages(messages);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onNetworkFailure() {

            }
        });


    }

    @Override
    public void sendNewMessage(String message, Conversation conversation) {
        if (view == null){
            return;
        }

        String id = remoteDataSource.getCurrentUserID();

        remoteDataSource.getUserByID(id, new DataSource.GetUserCallback() {
            @Override
            public void onSuccess(User user) {
                currentUser = user;
            }

            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onNetworkFailure() {

            }
        });

        remoteDataSource.sendMessage(message, conversation, new DataSource.SendMessageCallback() {
            @Override
            public void onSuccess(Message message) {

            }

            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onNetworkFailure() {

            }
        });
    }

    @Override
    public void createConversation(String recieverID) {

        Conversation c = new Conversation();

        String[] split = recieverID.split(" ");
        Map<String, String> userMap = new HashMap<>();
        userMap.put(split[0], split[1]);

        String username = remoteDataSource.getCurrentUser().getDisplayName();
        String id = remoteDataSource.getCurrentUserID();
        userMap.put(id, username);

        c.getUsers().put(split[0], split[1]);
        c.getUsers().put(id, username);

        c.setTitle("Chat");

        remoteDataSource.createConversation(c, new DataSource.CreateConversationCallback() {
            @Override
            public void onSuccess(Conversation conversation) {
                remoteDataSource.createConversationForUser(conversation);
                view.onConversationCreated(conversation);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onNetworkFailure() {

            }
        });
    }
}
