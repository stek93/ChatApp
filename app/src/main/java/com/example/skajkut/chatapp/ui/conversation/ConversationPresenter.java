package com.example.skajkut.chatapp.ui.conversation;

import android.content.Context;

import com.example.skajkut.chatapp.R;
import com.example.skajkut.chatapp.data.model.Conversation;
import com.example.skajkut.chatapp.data.model.User;
import com.example.skajkut.chatapp.data.remote.DataSource;
import com.example.skajkut.chatapp.data.remote.RemoteDataSource;
import com.example.skajkut.chatapp.util.mvp.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by n.sofronovic on 6/20/2017.
 */

public class ConversationPresenter extends BasePresenter<ConversationContract.View>
        implements ConversationContract.Presenter{

    private RemoteDataSource mRemoteDataSource;

    public ConversationPresenter(RemoteDataSource remoteDataSource,
                                 ConversationContract.View view) {
        this.mRemoteDataSource = remoteDataSource;
        this.view = view;
    }

    @Override
    public void getConversation(final Context context, String conversationID) {

        if(view == null) {
            return;
        }

        view.setProgressBar(true);

        mRemoteDataSource.getConversation(conversationID, new DataSource.GetConversationCallback() {
            @Override
            public void onSuccess(Conversation conversation) {
                if(view != null) {
                    view.showPhoto(getPhotos(new ArrayList<>(conversation.getUsers().values())));
                    view.showConversationDetails(conversation);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                if(view != null) {
                    view.setProgressBar(false);
                    view.showToastMessage(context.getString(R.string.error_message));
                }
            }

            @Override
            public void onNetworkFailure() {
                if(view != null) {
                    view.setProgressBar(false);
                    view.showNetworkFailureMessage(true);
                }
            }
        });
    }

    private List<String> getPhotos(List<User> users) {
        List<String> photos = new ArrayList<>();
        for(User u : users) {
            photos.add(u.getPhoto());
        }

        return photos;
    }

}
