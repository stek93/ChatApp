package com.example.skajkut.chatapp.ui.conversation;

import com.example.skajkut.chatapp.data.model.Message;
import com.example.skajkut.chatapp.data.model.User;
import com.example.skajkut.chatapp.data.remote.DataSource;
import com.example.skajkut.chatapp.data.remote.RemoteDataSource;
import com.example.skajkut.chatapp.util.mvp.BasePresenter;

import java.util.List;

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
    public void getMessages() {
        if (view == null){
            return;
        }

        view.setProgressBar(true);
        String id = remoteDataSource.getCurrentUserID();

        remoteDataSource.getMessages(new DataSource.GetMessagesCallback() {
            @Override
            public void onSuccess(List<Message> messages) {

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
    public void sendNewMessage(String message, String recieverID) {
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

        remoteDataSource.sendMessage(message, new DataSource.SendMessageCallback() {
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
}
