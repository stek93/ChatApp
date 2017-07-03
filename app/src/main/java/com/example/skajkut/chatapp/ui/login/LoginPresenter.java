package com.example.skajkut.chatapp.ui.login;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.skajkut.chatapp.data.local.LocalDataSource;
import com.example.skajkut.chatapp.data.model.User;
import com.example.skajkut.chatapp.data.remote.DataSource;
import com.example.skajkut.chatapp.data.remote.FirebaseUserService;
import com.example.skajkut.chatapp.data.remote.RemoteDataSource;
import com.example.skajkut.chatapp.util.mvp.BasePresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;


/**
 * Created by Stefan Kajkut on 6/26/2017.
 * Contact me on stefan.kajkutsf@gmail.com.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    private RemoteDataSource mRemoteDataSource;
    private FirebaseUserService firebaseUserService;
    private LocalDataSource mLocalDataSource;

    public LoginPresenter(RemoteDataSource mRemoteDataSource,
                          LoginContract.View view, FirebaseUserService firebaseUserService, LocalDataSource mLocalDataSource) {
        this.mRemoteDataSource = mRemoteDataSource;
        this.view = view;
        this.firebaseUserService = firebaseUserService;
        this.mLocalDataSource = mLocalDataSource;
    }

    @Override
    public void onRegistrationClicked(boolean show) {
        if (show){
            view.showRegistrationLayout(true);
        }
    }

    @Override
    public void onLoginWithEmailAndPasswordClicked(boolean show) {
        if (show){
            view.showLoginWithEmailAndPassword(true);
        }
    }

    @Override
    public void onFacebokLoginClicked(boolean show) {
        if (show){
            view.showFacebookLoginLayout(true);
        }
    }

    @Override
    public void loginViaEmail(final String email,final String password) {
        firebaseUserService.getUserWithEmail(email, password)
                .addOnCompleteListener((Activity) view.getPermission(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()) {
                            view.showLoginFailed();
                        } else {
                            view.startNewActivity();
                        }
                    }
                });
    }

    @Override
    public void registration(final String firstname, final String lastname, final String username, final String password, final String email) {
        firebaseUserService.registerUserWithEmail(email, password)
                .addOnCompleteListener((Activity) view.getPermission(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            view.showLoginFailed();
                        }else{
                            createUser(firstname, lastname, username, password, email);
                            view.startNewActivity();
                        }
                    }
                });

    }

    private void createUser(String firstname, String lastname, String username, String password, String email){
        if (view == null){
            return;
        }

        view.setProgressBar(true);

        mRemoteDataSource.createUser(firstname, lastname, username, password, email, new DataSource.AddUserCallback() {
            @Override
            public void onSuccess(User user) {
                if(view != null){
                    view.startNewActivity();
                    view.setProgressBar(false);
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
    public void checkUserRemote() {

        if (view == null){
            return;
        }

        view.setProgressBar(true);

        final String userID = mRemoteDataSource.getCurrentUserID();
        final FirebaseUser firebaseUser = mRemoteDataSource.getCurrentUser();

        mRemoteDataSource.getUserByID(userID, new DataSource.GetUserCallback() {

            @Override
            public void onSuccess(User user) {
                view.setProgressBar(false);
                if (user != null) {
                    view.startNewActivity();
                }else{
                    mLocalDataSource.writeSharedPreferences(view.getPermission(), firebaseUser);
                    createUserFromProvider(firebaseUser.getDisplayName(), firebaseUser.getEmail(), firebaseUser.getPhotoUrl());
                    view.startNewActivity();
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

    private void createUserFromProvider(String displayName, String email, Uri photoUrl){
        if (view == null){
            return;
        }

        view.setProgressBar(true);

        String[] split = new String[0];
        if (displayName != null) {
            split = displayName.split("\\s+");
        }
        String firstname = split[0];
        String lastname = split[1];

        mRemoteDataSource.createUserFromProvider(firstname, lastname, email,
                new DataSource.AddUserFromProviderCallback() {
            @Override
            public void onSuccess(User user) {
                if (view != null){
                    view.setProgressBar(false);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                if(view != null) {
                    view.setProgressBar(false);
                    view.showNetworkFailureMessage(true);
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
