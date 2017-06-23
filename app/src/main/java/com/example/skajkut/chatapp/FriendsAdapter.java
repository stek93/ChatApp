package com.example.skajkut.chatapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.skajkut.chatapp.data.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by n.sofronovic on 6/22/2017.
 */

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {

    private Context mContext;
    private List<User> users = new ArrayList<>();

    public FriendsAdapter(Context mContext, List<User> users) {
        this.mContext = mContext;
        this.users = users;
    }

    @Override
    public FriendsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_friend, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FriendsAdapter.ViewHolder holder, int position) {
        User user = users.get(position);
        holder.usernameTextView.setText(user.getUsername());
        holder.fullnameTextView.setText(user.getFirstname() + " " + user.getLastname());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView userImageView;
        private TextView usernameTextView;
        private TextView fullnameTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            userImageView = (ImageView) itemView.findViewById(R.id.iv_cw_userImage);
            usernameTextView = (TextView) itemView.findViewById(R.id.tv_cw_username);
            fullnameTextView = (TextView) itemView.findViewById(R.id.tv_cw_fullname);
        }
    }
}
