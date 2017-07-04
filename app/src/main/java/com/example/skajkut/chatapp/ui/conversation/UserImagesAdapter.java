package com.example.skajkut.chatapp.ui.conversation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.skajkut.chatapp.R;
import com.example.skajkut.chatapp.data.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by internship007 on 7/4/17.
 */

public class UserImagesAdapter extends RecyclerView.Adapter<UserImagesAdapter.ViewHolder> {

    private Context mContext;
    private List<User> users = new ArrayList<>();

    @Override
    public UserImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_conversation_user_images, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserImagesAdapter.ViewHolder holder, int position) {
        User user = users.get(position);
        if(user.getPhoto() != null) {
            Picasso.with(mContext).load(user.getPhoto()).into(holder.userIcon);
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_cw_userImage)
        ImageView userIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
