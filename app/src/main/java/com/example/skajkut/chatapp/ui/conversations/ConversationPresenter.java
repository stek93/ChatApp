package com.example.skajkut.chatapp.ui.conversations;

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
                    view.showConversationsList(getConversations(conversations));
                    view.setProgressBar(false);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                if(view != null) {
                    view.setProgressBar(false);
                    view.showToastMessage("Something went wrong!");
                }
            }

            @Override
            public void onNetworkFailure() {
                if(view != null) {
                    view.setProgressBar(false);
                    view.showNetworkFailureMessage(true);
                }
            }

            private List<Conversation> getConversations(List<String> conversations) {

                final List<Conversation> conversationList = new ArrayList<Conversation>();

                for(String conversationID : conversations) {
                    remoteDataSource.getConversation(conversationID, new DataSource.GetConversationCallback() {
                        @Override
                        public void onSuccess(Conversation conversation) {
                            conversationList.add(conversation);
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

                return conversationList;
            }
        });
    }
}
