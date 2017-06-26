package com.example.skajkut.chatapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.skajkut.chatapp.data.model.User;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by n.sofronovic on 6/21/2017.
 */

public class LoginFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks{

    private static final int RC_SIGN_IN = 9001;


    @BindView(R.id.btn_facebook) LoginButton mFacebookButton;
    @BindView(R.id.email_login)
    Button mLoginButton;
    private CallbackManager mCallbackManager;

    private GoogleApiClient mGoogleApiClient;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mFirebaseAuth;

    private static final String USER_SP = "user.sp";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCallbackManager = CallbackManager.Factory.create();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);

        initFacebook(view);
        initGoogle();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

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

    @OnClick(R.id.btn_google)
    public void onGoogleButtonClick(){
        signInWithGoogle();
    }

    @OnClick(R.id.email_login)
    public void onButtonClicked() {
        LoginActivity activity = (LoginActivity) getActivity();
        activity.handleFragment(this);
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

                            addUser(user);
                            Toast.makeText(getActivity(), user.getDisplayName(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), MainActivity.class));
                        }else{
                            Toast.makeText(getActivity(), "Login failed", Toast.LENGTH_SHORT).show();
                            task.getException();
                        }
                    }
                });
    }

    private void addUser(FirebaseUser firebaseUser){

        String id = firebaseUser.getUid();
        String name = firebaseUser.getDisplayName();
        String email = firebaseUser.getEmail();

        String[] nameValues = name.split(" ");
        String firstName = nameValues[0];
        String lastName = nameValues[1];

        User user = new User(id, firstName, lastName, email);

        mDatabaseReference = mFirebaseDatabase.getReference("users");
        mDatabaseReference.child(id).setValue(user);

        addUserToSharedPref(id, firstName, lastName, email);

    }

    private void addUserToSharedPref(String id, String firstName, String lastName, String email){
        SharedPreferences.Editor editor = this.getActivity().getSharedPreferences(USER_SP, Context.MODE_PRIVATE).edit();
        editor.putString("id", id);
        editor.putString("firstname", firstName);
        editor.putString("lastname", lastName);
        editor.putString("email", email);
        editor.apply();
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

}
