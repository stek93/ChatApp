package com.example.skajkut.chatapp.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.skajkut.chatapp.MainActivity;
import com.example.skajkut.chatapp.R;
import com.example.skajkut.chatapp.data.remote.FirebaseUserService;
import com.example.skajkut.chatapp.data.remote.RemoteDataSource;
import com.example.skajkut.chatapp.util.mvp.BaseView;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by n.sofronovic on 6/21/2017.
 */

public class LoginFragment extends BaseView implements LoginContract.View,
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks{

    private static final int RC_SIGN_IN = 9001;

    @BindView(R.id.btn_facebook) LoginButton mFacebookButton;
    @BindView(R.id.email_login) Button mLoginButton;
    private CallbackManager mCallbackManager;

    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mFirebaseAuth;

    private LoginContract.Presenter presenter;
    private FirebaseUserService firebaseUserService;

    @BindView(R.id.l_registration) LinearLayout registrationLayout;
    @BindView(R.id.l_login) LinearLayout loginLayout;
    @BindView(R.id.l_facebook_google_login) RelativeLayout facebookLayout;
    @BindView(R.id.et_firstname) TextInputEditText firstnameEditText;
    @BindView(R.id.et_lastname) TextInputEditText lastnameEditText;
    @BindView(R.id.et_username) TextInputEditText usernameEditText;
    @BindView(R.id.et_password) TextInputEditText passwordEditText;
    @BindView(R.id.et_email) TextInputEditText emailEditText;
    @BindView(R.id.btn_login) Button loginButton;
    @BindView(R.id.email) EditText emailLogin;
    @BindView(R.id.password) EditText passwordLogin;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RemoteDataSource remoteDataSource = RemoteDataSource.getInstance();
        FirebaseUserService firebaseUserService = FirebaseUserService.getInstance();
        presenter = new LoginPresenter(remoteDataSource, this, firebaseUserService); //TODO FirebaseUserService

        mCallbackManager = CallbackManager.Factory.create();
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        try {
            ButterKnife.bind(this, view);
        }catch (Exception e){
            e.printStackTrace();
        }

        initFacebook(view);
        initGoogle();

        return view;
    }


    private void initGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    @OnClick(R.id.btn_google_custom )
    public void onGoogleButtonClick(){
        signInWithGoogle();
    }

    private void signInWithGoogle(){
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(intent, RC_SIGN_IN);
    }

    private void initFacebook(View view) {
        mFacebookButton.setReadPermissions("email", "public_profile");
        mFacebookButton.setFragment(this);
        mFacebookButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }

            @Override
            public void onCancel() {
                Toast.makeText(getActivity(), "Cancel!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if (result.isSuccess()) {
                    GoogleSignInAccount gAccount = result.getSignInAccount();
                    authWithGoogle(gAccount);
                } else {
                    Toast.makeText(getActivity(), "Sign in failed", Toast.LENGTH_SHORT).show();
                    result.getStatus();
                    System.out.println("Google status :" + result.getStatus());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void authWithGoogle(GoogleSignInAccount account){

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();

                            Toast.makeText(getActivity(), user.getDisplayName(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), MainActivity.class));
                        }else{
                            Toast.makeText(getActivity(), "Login failed", Toast.LENGTH_SHORT).show();
                            task.getException();
                        }
                    }
                });
    }

    //Login with email
    @OnClick(R.id.btn_login)
    public void login() {
        final String email = emailLogin.getText().toString();
        final String password = passwordLogin.getText().toString();

        if(email.length() == 0) {
            emailLogin.setError("Email is required!");
        } else if(password.length() == 0) {
            passwordLogin.setError("Password is required");
        }

        presenter.loginViaEmail(email, password);
    }

    //Registration with email
    @OnClick(R.id.btn_submitRegistration)
    public void registration(){

        final String firstname = firstnameEditText.getText().toString();
        final String lastname = lastnameEditText.getText().toString();
        final String username = usernameEditText.getText().toString();
        final String password = passwordEditText.getText().toString();
        final String email = emailEditText.getText().toString();

        if (firstname.length() == 0){
            firstnameEditText.setError("First name is required!");
        }
        else if(lastname.length() == 0){
            lastnameEditText.setError("Last name is required!");
        }
        else if(username.length() == 0){
            usernameEditText.setError("Username is required!");
        }
        else if(password.length() <= 5){
            passwordEditText.setError("Password must be longer than 5 characters!");
        }else if(email.length() == 0){
            emailEditText.setError("Email is required!");
        }

        presenter.registration(firstname, lastname, username, password, email);

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getActivity(), "Google Play Services error.", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnected(Bundle bundle){
        Toast.makeText(getActivity(), "User connected!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getActivity(), "Connection suspended!", Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.email_login)
    public void onBtnEmailLogin()  {
        presenter.onLoginWithEmailAndPasswordClicked(true);

    }

    @OnClick(R.id.btn_register)
    public void onBtnRegister(){
        presenter.onRegistrationClicked(true);
    }

    @OnClick(R.id.btn_closeRegistration)
    public void onBtnCloseRegistration(){
        presenter.onFacebokLoginClicked(true);
    }

    @Override
    public void showRegistrationLayout(boolean show) {
        if(show){
            registrationLayout.setVisibility(View.VISIBLE);
            loginLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoginWithEmailAndPassword(boolean show) {
        if (show){
            loginLayout.setVisibility(View.VISIBLE);
            facebookLayout.setVisibility(View.GONE);
        }
    }


    @Override
    public void showFacebookLoginLayout(boolean show) {
        if (show){
            facebookLayout.setVisibility(View.VISIBLE);
            registrationLayout.setVisibility(View.GONE);

        }
    }

    @Override
    public void showLoginFailed() {
        Toast.makeText(getActivity(), "Login failed!", Toast.LENGTH_SHORT).show();
    }


    @Override
    public Context getPermission() {
        return getActivity();
    }

    @Override
    public void startNewActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}
