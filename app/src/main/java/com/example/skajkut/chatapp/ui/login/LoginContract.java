package com.example.skajkut.chatapp.ui.login;

import com.example.skajkut.chatapp.data.model.User;
import com.example.skajkut.chatapp.util.mvp.IBasePresenter;
import com.example.skajkut.chatapp.util.mvp.IBaseView;
import com.facebook.CallbackManager;

/**
 * Created by Stefan Kajkut on 6/26/2017.
 * Contact me on stefan.kajkutsf@gmail.com.
 */

public interface LoginContract {

    interface View extends IBaseView {

        void showRegistrationLayout(boolean show);

        void showLoginWithEmailAndPassword(boolean show);

        void showFacebookLoginLayout(boolean show);

        void showLoginFailed();

    }

    interface Presenter extends IBasePresenter<View> {

        void onRegistrationClicked(boolean show);

        void onLoginWithEmailAndPasswordClicked(boolean show);

        void onFacebokLoginClicked(boolean show);

        void loginViaEmail(String email, String password);

        void registration(String firstname, String lastname, String username,
                          String password, String email);



    }
}
