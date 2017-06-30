package com.example.skajkut.chatapp.ui.conversations;

import android.util.Log;

import com.example.skajkut.chatapp.data.model.Conversation;
import com.example.skajkut.chatapp.data.remote.DataSource;
import com.example.skajkut.chatapp.data.remote.RemoteDataSource;
import com.example.skajkut.chatapp.util.mvp.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by n.sofronovic on 6/20/2017.
 */

public class ConversationPresenter extends BasePresenter<ConversationsContract.View>
        implements ConversationsContract.Presenter{

    private RemoteDataSource remoteDataSource;

    public ConversationPresenter(RemoteDataSource remoteDataSource,
                                 ConversationsContract.View view) {
        super();
        this.remoteDataSource = remoteDataSource;
        this.view = view;
    }

    @Override
    public void getConversations() {

        if(view == null) {
            return;
        }

        view.setProgressBar(true);

        String userID = remoteDataSource.getCurrentUser();
        remoteDataSource.getConversationList(userID, new DataSource.GetConversationListCallback() {
            @Override
            public void onSuccess(List<String> conversations) {
                if(view != null) {
                    getConversations(conversations);
                    view.setProgressBar(false);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                if(view != null) {
                    view.setProgressBar(false);
                }
            }

            @Override
            public void onNetworkFailure() {
                if(view != null) {
                    view.setProgressBar(false);
                    view.showNetworkFailureMessage(true);
                }
            }

            private void getConversations(final List<String> conversations) {

                final List<Conversation> conversationList = new ArrayList<Conversation>();

                final String lastConversationId = conversations.get(conversations.size() - 1);
                for(final String conversationID : conversations) {
                    remoteDataSource.getConversation(conversationID, new DataSource.GetConversationCallback() {
                        @Override
                        public void onSuccess(Conversation conversation) {
                            conversationList.add(conversation);
                            if (lastConversationId.equals(conversationID))
                                view.showConversationsList(conversationList);
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            if(view != null) {
                                view.showToastMessage("Conversation loading failed!");
                            }
                        }

                        @Override
                        public void onNetworkFailure() {
                            if(view != null) {
                                view.showNetworkFailureMessage(true);
                            }
                        }
                    });
                }

            }
        });
    }
}
