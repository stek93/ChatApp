package com.example.skajkut.chatapp.ui.friend;


import android.content.Context;

import com.example.skajkut.chatapp.data.model.User;
import com.example.skajkut.chatapp.util.mvp.IBasePresenter;
import com.example.skajkut.chatapp.util.mvp.IBaseView;

import java.util.List;

/**
 * Created by internship007 on 6/30/17.
 */

public interface FriendContract {

    interface View extends IBaseView {

        void showFriendList(List<User> users, User currentUser);

        void showFavoriteFriends(List<User> users);


        void showNoFriendMessage(List<User> users);


    }

    interface Presenter extends IBasePresenter<View> {

        //void checkFriends();

        void getFriends();

        void getFavoriteFreinds();

        void favoriteFriendsListener();

    }

}
