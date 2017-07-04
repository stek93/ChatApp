package com.example.skajkut.chatapp.ui.conversation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.skajkut.chatapp.R;

/**
 * Created by internship007 on 7/4/17.
 */

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

                FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_chat);

        if(fragment == null) {
            fragment = new ChatFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_chat, fragment)
                    .commit();
        }
    }
}
