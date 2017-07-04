package com.example.skajkut.chatapp.ui.conversation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.skajkut.chatapp.R;
import com.example.skajkut.chatapp.data.model.Message;
import com.example.skajkut.chatapp.data.remote.RemoteDataSource;
import com.example.skajkut.chatapp.util.mvp.BaseView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by internship007 on 7/4/17.
 */

public class ChatFragment extends BaseView implements ChatContract.View {

    private RecyclerView.LayoutManager mUserImagesLayoutManager;
    private RecyclerView.LayoutManager mMessagesLayoutManager;

    private MessagesAdapter mMessagesAdapter;
    private UserImagesAdapter mUserImagesAdapter;

    private ChatContract.Presenter presenter;

    String recieverID;

    @BindView(R.id.rw_conversation_messages)
    RecyclerView messagesRecylerView;
    @BindView(R.id.rw_conversation_userImages)
    RecyclerView userImagesRecylcerView;
    @BindView(R.id.et_message)
    EditText messageEditText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RemoteDataSource remoteDataSource = RemoteDataSource.getInstance();

        presenter = new ChatPresenter(remoteDataSource, this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, view);

        mUserImagesLayoutManager = new LinearLayoutManager(getPermission(),
                LinearLayoutManager.HORIZONTAL, false);
        userImagesRecylcerView.setLayoutManager(mUserImagesLayoutManager);

        mMessagesLayoutManager = new LinearLayoutManager(getPermission());
        messagesRecylerView.setLayoutManager(mMessagesLayoutManager);

        recieverID = getActivity().getIntent().getStringExtra("reciever_id");

        presenter.getMessages();

        return view;
    }

    @OnClick(R.id.iv_send_message)
    public void sendMessage(){
        String message = messageEditText.getText().toString();
        presenter.sendNewMessage(message, recieverID);
    }

    @Override
    public void showMessages(List<Message> messages) {
        mMessagesAdapter = new MessagesAdapter(getPermission(), messages);
        messagesRecylerView.setAdapter(mMessagesAdapter);
    }

    @Override
    public Context getPermission() {
        return super.getPermission();
    }
}
