package com.example.skajkut.chatapp.ui.conversation;

import android.content.Context;

import com.example.skajkut.chatapp.data.model.Conversation;
import com.example.skajkut.chatapp.util.mvp.IBasePresenter;
import com.example.skajkut.chatapp.util.mvp.IBaseView;

import java.util.List;

/**
 * Created by n.sofronovic on 6/20/2017.
 */

public interface ConversationContract {

    interface View extends IBaseView {

        void showPhoto(List<String> photos);

        void showConversationDetails(Conversation conversation);

    }

    interface Presenter extends IBasePresenter<View> {

        void getConversation(Context context, String conversationID);

    }

}
