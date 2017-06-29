package com.example.skajkut.chatapp.ui.conversation;

import android.content.Context;

import com.example.skajkut.chatapp.data.model.Conversation;
import com.example.skajkut.chatapp.util.mvp.BaseView;

import java.util.List;


/**
 * Created by Stefan Kajkut on 6/26/2017.
 * Contact me on stefan.kajkutsf@gmail.com.
 */

public class ConversationFragment extends BaseView implements ConversationContract.View {

    private ConversationContract.Presenter mPresenter;

    @Override
    public void showPhoto(List<String> photos) {

    }

    @Override
    public void showConversationDetails(Conversation conversation) {
        //setujemo converzaciju
    }

    @Override
    public Context getPermission() {
        return null;
    }
}
