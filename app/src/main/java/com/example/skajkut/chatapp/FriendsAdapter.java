package com.example.skajkut.chatapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.skajkut.chatapp.data.model.User;
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
        return new ViewHolder(view, mContext, users);
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
        Context mContext;
        List<User> users;

        private FirebaseDatabase mFirebaseDatabase;
        private DatabaseReference mDatabaseReference;
        private FirebaseUser mUser;
        private FirebaseAuth mFirebaseAuth;

        private final String FAV_USER = "favoruser.sp";

        public ViewHolder(View itemView, Context mContext, List<User> users) {
            super(itemView);
            this.mContext = mContext;
            this.users = users;

            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mFirebaseAuth = FirebaseAuth.getInstance();

            userImageView = (ImageView) itemView.findViewById(R.id.iv_cw_userImage);
            emailTextView = (TextView) itemView.findViewById(R.id.tv_cw_email);
            fullnameTextView = (TextView) itemView.findViewById(R.id.tv_cw_fullname);
            favoriteIcon = (ImageView) itemView.findViewById(R.id.iv_favoriteIcon);

            favoriteIcon.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (view.getId() == favoriteIcon.getId()){
                int position = getAdapterPosition();
                User user = this.users.get(position);
                //Map<String, String> userMap = new HashMap<>();
                //userMap.put("-KnIiEnhsL4W8944e5xq", user.getUsername());
                String id = user.getId();
                String username = user.getUsername();
                addToFavorites(id, username);
            }
        }

        private void addToFavorites(String id, String username){
            mDatabaseReference = mFirebaseDatabase.getReference("favoritefriends");
            mUser = FirebaseAuth.getInstance().getCurrentUser();
            if(mUser != null){
                id = "-KnIiEneW8DHOqdWGoUC";
                mDatabaseReference.child(mUser.getUid()).child(id).setValue(username);
                changeFavoriteIcon();
            }
        }

        private void changeFavoriteIcon(){
            Picasso.with(mContext).load(R.drawable.filledstar).into(favoriteIcon);
        }

    }
}
