package com.example.skajkut.chatapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginActivity extends AppCompatActivity   {


    private GoogleApiClient mGoogleApiClient;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;

    SignInButton googleButton;

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FacebookSdk.sdkInitialize(getApplicationContext());
        //mCallbackManager = CallbackManager.Factory.create();

        //GenerateData.getInstance().generateRandomData();
        mFirebaseAuth = FirebaseAuth.getInstance();

            /**/


        /*mFacebook= (LoginButton) findViewById(R.id.login_button);
        mFacebook.setReadPermissions("email", "public_profile");
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("STEK", "success!!!");
                Log.d("STEK", loginResult.getAccessToken() + "");
                Profile profile = Profile.getCurrentProfile();
                Log.d("STEK", profile.getFirstName() + "   " + profile.getId());
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("STEK", "cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("STEK", error.toString());
                error.printStackTrace();
            }
        });*/

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_login);

        if(fragment == null) {
            fragment = new LoginFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_login, fragment)
                    .commit();
        }
    }

    public void handleFragment(LoginFragment lf) {
        FragmentManager fm = getSupportFragmentManager();
        /*Fragment fragment = fm.findFragmentById(R.id.fragment_login);

        if(fragment == null) {
            fragment = new LoginFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_login, fragment)
                    .commit();
        }*/
        FragmentTransaction transaction = fm.beginTransaction();
        EmailLoginFragment fragment = new EmailLoginFragment();
        //
        transaction.addToBackStack(null)
        .hide(lf)
        .add(R.id.fragment_login, fragment)
        .commit();
/*        fm.beginTransaction()
                .replace(R.id.fragment_login, new EmailLoginFragment())
                .addToBackStack(null)
                .commit();*/
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        Log.d("STEK", "handleFacebookAccessToken:" + accessToken);
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser user = mFirebaseAuth.getCurrentUser();
                } else {
                    Log.d("STEK", "auth failed");
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
    }


    /*private void signInWithGoogle(){
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(intent, RC_SIGN_IN);
    }*/

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_login);
        fragment.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                GoogleSignInAccount gAccount = result.getSignInAccount();
                authWithGoogle(gAccount);
            }else {
                Toast.makeText(this, "Sign in failed", Toast.LENGTH_SHORT).show();
            }
        }
    }*/

    /*private void authWithGoogle(GoogleSignInAccount account){

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            // TODO Proveriti jel smo ga vec dodali u bazu
                                addUser(user);
                            Toast.makeText(LoginActivity.this, "Login successful: " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }*/






}
