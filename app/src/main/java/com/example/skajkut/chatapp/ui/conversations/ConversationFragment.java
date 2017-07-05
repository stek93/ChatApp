package com.example.skajkut.chatapp.ui.conversations;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.skajkut.chatapp.R;
import com.example.skajkut.chatapp.data.model.Conversation;
import com.example.skajkut.chatapp.data.remote.RemoteDataSource;
import com.example.skajkut.chatapp.util.mvp.BaseView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Stefan Kajkut on 6/26/2017.
 * Contact me on stefan.kajkutsf@gmail.com.
 */

public class ConversationFragment extends BaseView implements ConversationsContract.View {

    private ConversationsContract.Presenter mPresenter;
    private RecyclerView.LayoutManager mConversationsLayoutManager;

    private ConversationsAdapter mConversationsAdapter;

    @BindView(R.id.rw_coneversation_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_no_conversations)
    TextView mNoConversationsTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RemoteDataSource remoteDataSource = RemoteDataSource.getInstance();
        mPresenter = new ConversationPresenter(remoteDataSource, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_tab_chats, container, false);

        ButterKnife.bind(this, view);

        mConversationsLayoutManager = new LinearLayoutManager(getPermission());
        mRecyclerView.setLayoutManager(mConversationsLayoutManager);


        mPresenter.getConversations();


        return view;
    }

    @Override
    public Context getPermission() {
        return getActivity();
    }

    @Override
    public void showConversationsList(List<Conversation> conversations) {
        /*if(conversations != null && conversations.size() > 0) {
            mConversationsAdapter = new ConversationsAdapter(getPermission(), conversations);
            mRecyclerView.setAdapter(mConversationsAdapter);
        } else {
            mNoConversationsTextView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }*/
    }
}
