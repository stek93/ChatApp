package com.example.skajkut.chatapp.ui.friend;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.skajkut.chatapp.R;
import com.example.skajkut.chatapp.data.model.User;
import com.example.skajkut.chatapp.ui.conversation.ChatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by n.sofronovic on 6/22/2017.
 */

public class
FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {

    private Context mContext;
    private List<User> users = new ArrayList<>();
    private List<User> favoriteFriends;
    private User currentUser;

    public FriendsAdapter(Context mContext, List<User> users, User currentUser) {
        this.mContext = mContext;
        this.users = users;
        this.currentUser = currentUser;
    }

    @Override
    public FriendsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_friend, parent, false);
        return new ViewHolder(view, mContext, users);
    }

    @Override
    public void onBindViewHolder(FriendsAdapter.ViewHolder holder, int position) {

        User user = users.get(position);
        holder.emailTextView.setText(user.getUsername());
        holder.fullnameTextView.setText(user.getFirstname() + " " + user.getLastname());
        if (user.getPhoto()!=null) {
            Picasso.with(mContext).load(user.getPhoto()).into(holder.userImageView);
        }

        if(currentUser.getFavoriteList().get(user.getId()) != null) {
            Picasso.with(mContext).load(R.drawable.favfullstar).into(holder.favoriteIcon);
        } else {
            Picasso.with(mContext).load(R.drawable.emptystar).into(holder.favoriteIcon);
        }

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
        Context mContext;
        List<User> users;

        private FirebaseDatabase mFirebaseDatabase;
        private DatabaseReference mDatabaseReference;
        private FirebaseUser mUser;
        private FirebaseAuth mFirebaseAuth;

        public ViewHolder(View itemView, Context mContext, List<User> users) {
            super(itemView);
            this.mContext = mContext;
            this.users = users;

            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mFirebaseAuth = FirebaseAuth.getInstance();

            emailTextView = (TextView) itemView.findViewById(R.id.tv_cw_email);
            fullnameTextView = (TextView) itemView.findViewById(R.id.tv_cw_fullname);
            favoriteIcon = (ImageView) itemView.findViewById(R.id.iv_favoriteIcon);
            userImageView = (ImageView) itemView.findViewById(R.id.iv_cw_friendImage);

            favoriteIcon.setOnClickListener(this);
            userImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            User user = this.users.get(position);

            if (view.getId() == favoriteIcon.getId()){

                addToFavorites(user);
            }else if(view.getId() == userImageView.getId()){

                Intent i = new Intent(mContext, ChatActivity.class);
                i.putExtra("reciever_id", user.getId() + " " + user.getFirstname());
                mContext.startActivity(i);
            }
        }


        private void addToFavorites(User user){
            favoriteFriends = new ArrayList<User>(currentUser.getFavoriteList().values());

            if (currentUser.getFavoriteList().get(user.getId()) != null){
                mDatabaseReference = mFirebaseDatabase.getReference("favoritefriends");
                mUser = FirebaseAuth.getInstance().getCurrentUser();
                mDatabaseReference.child(mUser.getUid()).child(user.getId()).removeValue();
                currentUser.getFavoriteList().remove(user.getId());

                changeFavoriteIcon(false);

            }else {
                mDatabaseReference = mFirebaseDatabase.getReference("favoritefriends");
                mUser = FirebaseAuth.getInstance().getCurrentUser();
                mDatabaseReference.child(mUser.getUid()).child(user.getId()).setValue(user.getUsername());
                currentUser.getFavoriteList().put(user.getId(), user);

                changeFavoriteIcon(true);
            }

        }

        private void changeFavoriteIcon(boolean setImage){
            if (setImage) {
                Picasso.with(mContext).load(R.drawable.favfullstar).into(favoriteIcon);
            }else{
                Picasso.with(mContext).load(R.drawable.emptystar).into(favoriteIcon);
            }
        }



    }




}
