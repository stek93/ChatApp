package com.example.skajkut.chatapp.ui.friend;

import com.example.skajkut.chatapp.data.model.User;
import com.example.skajkut.chatapp.data.remote.DataSource;
import com.example.skajkut.chatapp.data.remote.RemoteDataSource;
import com.example.skajkut.chatapp.util.mvp.BasePresenter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by internship007 on 6/30/17.
 */

public class FriendPresenter  extends BasePresenter<FriendContract.View> implements FriendContract.Presenter{

    private RemoteDataSource remoteDataSource;
    private User currentUser;

    public FriendPresenter(RemoteDataSource remoteDataSource,
                           FriendContract.View view) {
        this.remoteDataSource = remoteDataSource;
        this.view = view;
        currentUser = new User();
        currentUser.setId(remoteDataSource.getCurrentUserID());

    }


    public void getFriends() {
        if (view == null){
            return;
        }

        view.setProgressBar(true);
        String id = remoteDataSource.getCurrentUserID();
        remoteDataSource.getFriendList(id, new DataSource.GetFriendListCallback() {

            @Override
            public void onSuccess(List<User> users) {
                if(view != null){
                    view.setProgressBar(false);
                    currentUser.setFriendList(convertListToMap(users));
                    view.showFriendList(users, currentUser);

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
        if (view==null){
            return;
        }

        view.setProgressBar(true);
        String id = remoteDataSource.getCurrentUserID();

        remoteDataSource.getFavoriteList(id, new DataSource.GetFavoriteListCallback() {
            @Override
            public void onSuccess(List<User> users) {
                if (view!=null){
                    view.setProgressBar(false);
                    currentUser.setFavoriteList(convertListToMap(users));
                    view.showFavoriteFriends(users);

                }
            }

            @Override
            public void onEmptyList() {
                view.setProgressBar(false);
                view.showFavoriteFriends(null);
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

    private Map<String, User> convertListToMap(List<User> users) {
        Map<String, User> usersMap = new HashMap<String, User>();
        for (User u : users) usersMap.put(u.getId(), u);

        return usersMap;

    }

    @Override
    public void favoriteFriendsListener(){
        if (view==null){
            return;
        }

        view.setProgressBar(true);
        String id = remoteDataSource.getCurrentUserID();

        remoteDataSource.getFavoriteList(id, new DataSource.GetFavoriteListCallback() {
            @Override
            public void onSuccess(List<User> users) {
                if (view!=null){
                    view.setProgressBar(false);

                }
            }

            @Override
            public void onEmptyList() {
                view.setProgressBar(false);
                view.showFavoriteFriends(null);
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

}
