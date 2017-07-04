package com.example.skajkut.chatapp.ui.users;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.skajkut.chatapp.R;
import com.example.skajkut.chatapp.data.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Stefan Kajkut on 7/4/17.
 * For more information contact me on stefan.kajkutsf@gmail.com .
 */

public class UsersAdapter extends
        RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    private Context mContext;
    private List<User> mUsersList = new ArrayList<>();

    public UsersAdapter(Context context, List<User> users) {
        this.mContext = context;
        this.mUsersList = users;
    }

    @Override
    public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_new_friend, parent, false);

        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UsersViewHolder holder, int position) {
        User user = mUsersList.get(position);

        holder.bindUser(user);
    }

    @Override
    public int getItemCount() {
        return mUsersList.size();
    }


    class UsersViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_new_friend_name)
        TextView mFriendName;

        @BindView(R.id.tv_new_friend_email)
        TextView mEmail;

        @BindView(R.id.tv_new_friend_username)
        TextView mUsername;

        private User mUser;

        public UsersViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindUser(User user) {
            mUser = user;
            mFriendName.setText(user.getFirstname() + user.getLastname());
            mEmail.setText(user.getEmail());
            mUsername.setText(user.getUsername());
        }
    }
}
