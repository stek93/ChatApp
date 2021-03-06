package com.example.skajkut.chatapp.ui.friend;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.skajkut.chatapp.R;
import com.example.skajkut.chatapp.data.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFriendsAdapter extends RecyclerView.Adapter<FavoriteFriendsAdapter.ViewHolder> {

    private Context mContext;
    private List<User> favoriteFriends = new ArrayList<>();

    public List<User> getFavoriteFriends() {
        return favoriteFriends;
    }

    public FavoriteFriendsAdapter(Context mContext, List<User> favoriteFriends) {
        this.mContext = mContext;
        this.favoriteFriends = favoriteFriends;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_fav_friend, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = favoriteFriends.get(position);
        //TODO Dodati korisniku sliku
        if(user.getUsername()!=null){
            holder.username.setText(user.getUsername());
        }else{
            holder.username.setText(user.getFirstname());
        }
        if (user.getPhoto()!=null) {
            Picasso.with(mContext).load(user.getPhoto()).into(holder.userImage);
        }

    }

    @Override
    public int getItemCount() {
        return favoriteFriends.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView userImage;
        private TextView username;

        public ViewHolder(View itemView) {
            super(itemView);

            userImage = (ImageView) itemView.findViewById(R.id.iv_cw_favFriendImage);
            username = (TextView) itemView.findViewById(R.id.tv_cw_username);
        }
    }
}
