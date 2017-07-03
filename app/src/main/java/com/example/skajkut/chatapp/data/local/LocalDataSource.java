package com.example.skajkut.chatapp.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.skajkut.chatapp.data.model.User;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by internship007 on 7/3/17.
 */

public class LocalDataSource {

    public static final String USER_PREFS = "user.sp";
    public static LocalDataSource instance = null;

    public static LocalDataSource getInstance(){
        if (instance == null){
            instance = new LocalDataSource();
        }
        return instance;
    }

    public void readSharedPreferences(Context context, String username){
        SharedPreferences preferences = context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
        preferences.getString("username", "");

    }

    public void writeSharedPreferences(Context context, FirebaseUser user){
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE).edit();

        String fullname = user.getDisplayName();
        String[] split = new String[0];
        if (fullname != null) {
            split = fullname.split("\\s+");
        }
        String firstname = split[0];
        String lastname = split[1];

        editor.putString("firstname", firstname);
        editor.putString("lastname", lastname);
        editor.putString("email", user.getEmail());
        editor.apply();
    }
}
