package com.example.skajkut.chatapp.data.remote;

import android.app.Application;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by internship007 on 6/29/17.
 */

public class FirebaseUserService {

    private static FirebaseUserService instance = null;

    private FirebaseAuth firebaseAuth;

    public FirebaseUserService() {
        this.firebaseAuth = FirebaseAuth.getInstance();
    }


    public static FirebaseUserService getInstance(){
        if (instance == null){
            instance = new FirebaseUserService();
        }
        return instance;
    }

    public Task<AuthResult> getUserWithEmail(String email, String password){
        return firebaseAuth.signInWithEmailAndPassword(email, password);
    }

    public Task<AuthResult> registerUserWithEmail(String email, String password){
        return firebaseAuth.createUserWithEmailAndPassword(email, password);
    }
}
