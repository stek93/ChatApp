package com.example.skajkut.chatapp.ui.conversations;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.skajkut.chatapp.R;
import com.example.skajkut.chatapp.data.model.Conversation;
import com.example.skajkut.chatapp.util.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Stefan Kajkut on 6/30/17.
 * For more information contact me on stefan.kajkutsf@gmail.com .
 */

public class ConversationsAdapter extends RecyclerView.Adapter<ConversationsAdapter.ConversationViewHolder> {

    private Context mContext;
    private List<Conversation> mConversations = new ArrayList<>();

    public ConversationsAdapter(Context context, List<Conversation> conversations) {
        this.mContext = context;
        this.mConversations = conversations;
    }

    @Override
    public ConversationsAdapter.ConversationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_conversation, parent, false);

        return new ConversationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ConversationsAdapter.ConversationViewHolder holder, int position) {
        Conversation conversation = mConversations.get(position);

        holder.bindConversation(conversation);
    }

    @Override
    public int getItemCount() {
        return mConversations.size();
    }

    class ConversationViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Conversation mConversation;
        private SimpleDateFormat mDateFormat;

        @BindView(R.id.img_profile_image)
        ImageView mConversationImageView;

        @BindView(R.id.tv_message_title)
        TextView mMessageTitleTextView;

        @BindView(R.id.tv_last_message)
        TextView mLastMessageTextView;

        @BindView(R.id.tv_message_time)
        TextView mMessageTimeTextView;

        public ConversationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            // TODO otvoriti novu aktivnost gde vidimo poruke
        }

        public void bindConversation(Conversation conversation) {
            mConversation = conversation;
            //TODO load slike;
            // mCOnversationImageView

            if (mConversation.getTitle()!= null) {
                mMessageTitleTextView.setText(mConversation.getTitle());
            } else {
                mMessageTitleTextView.setText("New conversation");
            }
            if(mConversation.getLastMessage() != null) {
                mLastMessageTextView.setText(mConversation.getLastMessage().getText());
                mMessageTimeTextView
                        .setText(DateTimeFormatter.dateTimeFormatter(mConversation
                                .getLastMessage()
                                .getDateTime()));
            } else {
                mLastMessageTextView.setText("Start conversation");
                mMessageTimeTextView.setText(DateTimeFormatter.dateTimeFormatter(new Date()));
            }
        }

    }
}