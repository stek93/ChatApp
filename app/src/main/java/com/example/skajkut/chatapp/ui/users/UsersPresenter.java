package com.example.skajkut.chatapp.ui.users;

import com.example.skajkut.chatapp.data.model.User;
import com.example.skajkut.chatapp.data.remote.DataSource;
import com.example.skajkut.chatapp.data.remote.RemoteDataSource;
import com.example.skajkut.chatapp.util.mvp.BasePresenter;

import java.util.List;

/**
 * Created by Stefan Kajkut on 7/3/17.
 * For more information contact me on stefan.kajkutsf@gmail.com .
 */


public class UsersPresenter extends BasePresenter<UsersContract.View>
        implements UsersContract.Presenter {

    private RemoteDataSource remoteDataSource;

    public UsersPresenter(RemoteDataSource remoteDataSource,
                          UsersContract.View view) {
        super();
        this.remoteDataSource = remoteDataSource;
        this.view = view;
    }

    @Override
    public void getUsersByParams(String searchValue, String searchQuery) {

        if(view == null) {
            return;
        }

        remoteDataSource.searchUsers(searchValue, searchQuery,
                new DataSource.SearchUsersCallback() {
            @Override
            public void onSuccess(List<User> users) {
                if(view != null) {
                    view.setProgressBar(false);
                    view.onSearchResults(users);
                }
            }

            @Override
            public void onEmptyList() {
                if(view != null) {
                    view.setProgressBar(false);
                    view.onSearchResults(null);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                if(view != null) {
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
