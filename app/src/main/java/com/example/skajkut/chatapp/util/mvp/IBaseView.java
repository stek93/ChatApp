package com.example.skajkut.chatapp.util.mvp;

import android.content.Context;

import com.example.skajkut.chatapp.MainActivity;

/**
 * Created by Stefan Kajkut on 6/26/2017.
 * Contact me on stefan.kajkutsf@gmail.com.
 */

public interface IBaseView {

    void showToastMessage(String message);

    void setProgressBar(boolean show);

    void showNetworkFailureMessage(boolean show);

    Context getPermission();

    void startNewActivity();

}
