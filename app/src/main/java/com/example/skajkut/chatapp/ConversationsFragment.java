package com.example.skajkut.chatapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Stefan Kajkut on 6/22/2017.
 * Contact me on stefan.kajkutsf@gmail.com.
 */

public class ConversationsFragment extends Fragment {

    @BindView(R.id.rw_coneversation_list)
    RecyclerView mConversationRecyclerView;

    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mContext = getActivity();

        View view = inflater.inflate(R.layout.fragment_tab_chats, container, false);

       ButterKnife.bind(mContext, view);

       //mConversationRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        return view;
    }

    public class ConversationListHolder extends RecyclerView.ViewHolder {

        // TODO ako gledamo sliku kao putanju do resursa
        public String mUserImage;
        // TODO referenca na sliku koja predstavlja resurs unutar aplikacije i odnosi se na status poruka
        public int mMessageStatus;

        @BindView(R.id.tv_message_title)
        TextView mTitleMessage;

        @BindView(R.id.tv_last_message)
        TextView mLastMessage;

        @BindView(R.id.tv_message_time)
        TextView mTimeMessage;

        public ConversationListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(mContext, itemView);
        }

    }

    private class ConversationAdapter extends RecyclerView.Adapter<ConversationListHolder> {

        @Override
        public ConversationListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(ConversationListHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
