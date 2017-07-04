package com.example.skajkut.chatapp.ui.conversation;

import com.example.skajkut.chatapp.data.model.Message;
import com.example.skajkut.chatapp.util.mvp.IBasePresenter;
import com.example.skajkut.chatapp.util.mvp.IBaseView;

import java.util.List;

/**
 * Created by internship007 on 7/4/17.
 */

public interface ChatContract {

    interface View extends IBaseView {

        void showMessages(List<Message> messages);

    }

    interface Presenter extends IBasePresenter<View>{

        void getMessages();

        void sendNewMessage(String message, String recieverID);
    }
}
