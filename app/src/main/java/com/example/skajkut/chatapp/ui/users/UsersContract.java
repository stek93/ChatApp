package com.example.skajkut.chatapp.ui.users;

import com.example.skajkut.chatapp.data.model.User;
import com.example.skajkut.chatapp.util.mvp.IBasePresenter;
import com.example.skajkut.chatapp.util.mvp.IBaseView;

import java.util.List;

/**
 * Created by Stefan Kajkut on 7/3/17.
 * For more information contact me on stefan.kajkutsf@gmail.com .
 */

public interface UsersContract {

    interface View extends IBaseView {

        void onFindUsers(String searchValue, String searchQuery);

        void onSearchResults(List<User> users);
    }

    interface Presenter extends IBasePresenter<View> {

        void getUsersByParams(String searchValue, String searchQuery);

    }
}
