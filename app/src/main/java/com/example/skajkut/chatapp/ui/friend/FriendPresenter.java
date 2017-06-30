package com.example.skajkut.chatapp.ui.friend;

import android.content.Context;

import com.example.skajkut.chatapp.data.model.User;
import com.example.skajkut.chatapp.data.remote.DataSource;
import com.example.skajkut.chatapp.data.remote.FirebaseUserService;
import com.example.skajkut.chatapp.data.remote.RemoteDataSource;
import com.example.skajkut.chatapp.util.mvp.BasePresenter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

/**
 * Created by internship007 on 6/30/17.
 */

public class FriendPresenter  extends BasePresenter<FriendContract.View> implements FriendContract.Presenter{

    private RemoteDataSource remoteDataSource;
    private FirebaseAuth firebaseAuth;

    public FriendPresenter(RemoteDataSource remoteDataSource,
                           FriendContract.View view) {
        this.remoteDataSource = remoteDataSource;
        this.view = view;
    }

    @Override
    public void getFriends() {
        if (view == null){
            return;
        }

        view.setProgressBar(true);
        firebaseAuth = FirebaseAuth.getInstance();
        String userID = firebaseAuth.getCurrentUser().getUid();
        remoteDataSource.getFriendList(userID, new DataSource.GetFriendListCallback() {

            @Override
            public void onSuccess(List<User> users) {
                if(view != null){
                    view.setProgressBar(false);
                    view.showFriendList(users);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                if (view != null){
                    view.setProgressBar(false);
                    view.showToastMessage("Something went wrong!");
                }
            }

            @Override
            public void onNetworkFailure() {
                if(view != null) {
                    view.setProgressBar(false);
                    view.showNetworkFailureMessage(true);
                }
            }
        });

    }

    @Override
    public void getFavoriteFreinds() {
        if (view == null){
            return;
        }



        view.setProgressBar(true);
        firebaseAuth = FirebaseAuth.getInstance();
        String userID = firebaseAuth.getCurrentUser().getUid();
        remoteDataSource.getFavoriteList(userID, new DataSource.GetFavoriteListCallback() {
            @Override
            public void onSuccess(List<User> users) {
                if (view!=null){
                    view.setProgressBar(false);
                    view.showFavoriteFriends(users);
                }
            }

            @Override
            public void onListEmpty() {
                view.showFavoriteFriends(null);
            }

            @Override
            public void onFailure(Throwable throwable) {
                if (view != null){
                    view.setProgressBar(false);
                    view.showToastMessage("Something went wrong!");
                    view.showFavoriteFriends(null);
                }
            }

            @Override
            public void onNetworkFailure() {
                if(view != null) {
                    view.setProgressBar(false);
                    view.showNetworkFailureMessage(true);
                }
            }
        });
    }



}
