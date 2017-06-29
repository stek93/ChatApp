package com.example.skajkut.chatapp.ui.login;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.skajkut.chatapp.data.model.User;
import com.example.skajkut.chatapp.data.remote.DataSource;
import com.example.skajkut.chatapp.data.remote.FirebaseUserService;
import com.example.skajkut.chatapp.data.remote.RemoteDataSource;
import com.example.skajkut.chatapp.util.mvp.BasePresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;


/**
 * Created by Stefan Kajkut on 6/26/2017.
 * Contact me on stefan.kajkutsf@gmail.com.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    private RemoteDataSource mRemoteDataSource;
    private FirebaseUserService firebaseUserService;

    public LoginPresenter(RemoteDataSource mRemoteDataSource,
                          LoginContract.View view, FirebaseUserService firebaseUserService) {
        this.mRemoteDataSource = mRemoteDataSource;
        this.view = view;
        this.firebaseUserService = firebaseUserService;
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
                            //checkSharedPref(email);
                        }
                    }
                });
    }

    @Override
    public void registration(final String firstname, final String lastname, final String username, final String password, final String email) {
        try{firebaseUserService.registerUserWithEmail(email, password)
                .addOnCompleteListener((Activity) view.getPermission(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(view.getPermission(), "Registration failed!", Toast.LENGTH_SHORT).show();
                        }else{
                            createUser(firstname, lastname, username, password, email);
                        }
                    }
                });
    }catch (Exception e){
            e.printStackTrace();
        }

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
                    //Uspesna registracija
                    //Prosledi u main activity
                    view.setProgressBar(false);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                if (view != null){
                    view.setProgressBar(false);
                    view.showToastMessage("Something went wrong");
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
