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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        holder.emailTextView.setText(user.getUsername());
        holder.fullnameTextView.setText(user.getFirstname() + " " + user.getLastname());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView userImageView;
        private TextView emailTextView;
        private TextView fullnameTextView;
        private ImageView favoriteIcon;

        public ViewHolder(View itemView) {
            super(itemView);

            userImageView = (ImageView) itemView.findViewById(R.id.iv_cw_userImage);
            emailTextView = (TextView) itemView.findViewById(R.id.tv_cw_email);
            fullnameTextView = (TextView) itemView.findViewById(R.id.tv_cw_fullname);
            favoriteIcon = (ImageView) itemView.findViewById(R.id.iv_favoriteIcon);

            favoriteIcon.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (view.getId() == favoriteIcon.getId()){
                Map<String, String> map = new HashMap<>();
            }
        }
    }
}
