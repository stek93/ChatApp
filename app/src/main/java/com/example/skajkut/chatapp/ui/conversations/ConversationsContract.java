package com.example.skajkut.chatapp.ui.conversations;

import com.example.skajkut.chatapp.data.model.Conversation;
import com.example.skajkut.chatapp.util.mvp.IBasePresenter;
import com.example.skajkut.chatapp.util.mvp.IBaseView;

import java.util.List;

/**
 * Created by n.sofronovic on 6/20/2017.
 */

public interface ConversationsContract {

    interface View extends IBaseView {

        void showConversationsList(List<Conversation> conversations);

    }

    interface Presenter extends IBasePresenter<View> {

        void getConversations();

    }

}
