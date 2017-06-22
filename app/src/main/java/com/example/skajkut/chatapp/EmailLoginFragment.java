package com.example.skajkut.chatapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.skajkut.chatapp.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.channels.FileChannel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by n.sofronovic on 6/22/2017.
 */

public class EmailLoginFragment extends Fragment {

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private String userId;
    private static final String USER_PREF = "user.sp";

    @BindView(R.id.l_registration) LinearLayout registratLayout;
    @BindView(R.id.l_login) LinearLayout loginLayout;
    @BindView(R.id.et_firstname) TextInputEditText firstnameEditText;
    @BindView(R.id.et_lastname) TextInputEditText lastnameEditText;
    @BindView(R.id.et_username) TextInputEditText usernameEditText;
    @BindView(R.id.et_password) TextInputEditText passwordEditText;
    @BindView(R.id.et_email) TextInputEditText emailEditText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_email_login, container, false);
        ButterKnife.bind(this, view);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        return view;
    }

    @OnClick(R.id.btn_register)
    public void onRegisterClick(){
        loginLayout.setVisibility(View.GONE);
        registratLayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_closeRegistration)
    public void onRegisterClose(){
        loginLayout.setVisibility(View.VISIBLE);
        registratLayout.setVisibility(View.GONE);
    }

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

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(getActivity(), "User with this email already exists!", Toast.LENGTH_SHORT).show();
                        }else{
                            User user = new User(firstname, lastname, username, password, email);

                            userId = databaseReference.push().getKey();
                            databaseReference = firebaseDatabase.getReference("users");

                            databaseReference.child(userId).setValue(user);

                            addUserToSharedPref(userId, firstname, lastname, username, password, email);
                        }
                    }
                });

    }

    private void addUserToSharedPref(String id, String firstname, String lastname, String username, String password, String email) {
        SharedPreferences.Editor editor = this.getActivity().getSharedPreferences(USER_PREF, Context.MODE_PRIVATE).edit();
        editor.putString("id", id);
        editor.putString("firstname", firstname);
        editor.putString("lastname", lastname);
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putString("email", email);
        editor.apply();

    }
}
