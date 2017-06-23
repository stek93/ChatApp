package com.example.skajkut.chatapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.skajkut.chatapp.data.model.Conversation;
import com.example.skajkut.chatapp.util.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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
    private ConversationAdapter mAdapter;
    private List<Conversation> mConversationList;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mContext = getActivity();

        View view = inflater.inflate(R.layout.fragment_tab_chats, container, false);

        ButterKnife.bind(mContext, view);

        mConversationRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        updateUI();

        return view;
    }

    private void updateUI() {
        mConversationList = new ArrayList<>();
        setupFirebase();

        final DatabaseReference databaseReferenceOutter = mFirebaseDatabase
                .getReference("conversations").child("users");
        Query query = databaseReferenceOutter.orderByKey()
                .equalTo(mFirebaseAuth.getCurrentUser().getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    System.out.println(databaseReferenceOutter.getParent().getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mAdapter = new ConversationAdapter(query, null, null);
        mConversationRecyclerView.setAdapter(mAdapter);
    }

    private void setupFirebase() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    class ConversationListHolder extends RecyclerView.ViewHolder {

        private Conversation mConversation;

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

        public void bindConversation(Conversation conversation) {
            mConversation = conversation;
            //TODO
            mTitleMessage.setText("TITLE");
            Log.d("STEK", conversation.getUsers().get(0).getFirstname());
        }
    }

    private class ConversationAdapter
            extends FirebaseRecyclerAdapter<ConversationListHolder, Conversation> {

        public ConversationAdapter(Query query, @Nullable ArrayList<Conversation> conversations,
                                   @Nullable ArrayList<String> keys) {
            super(query, conversations, keys);
        }

        @Override
        public ConversationListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.card_view_conversation, parent, false);

            return new ConversationListHolder(view);
        }

        @Override
        public void onBindViewHolder(ConversationListHolder holder, int position) {
            Conversation conversation = getItem(position);
            holder.bindConversation(conversation);
        }

        // TODO implementirati ponasanja!

        @Override
        protected void itemAdded(Conversation conversation, String key, int position) {
            Log.d("MyAdapter", "Added a new item to the adapter.");
        }

        @Override
        protected void itemChanged(Conversation oldConversation,
                                             Conversation newConversation,
                                             String key, int position) {
            Log.d("MyAdapter", "Changed an item.");
        }

        @Override
        protected void itemRemoved(Conversation conversation, String key, int position) {
            Log.d("MyAdapter", "Removed an item from the adapter.");
        }

        @Override
        protected void itemMoved(Conversation conversation, String key,
                                 int oldPosition, int newPosition) {
            Log.d("MyAdapter", "Moved an item.");
        }
    }
}
