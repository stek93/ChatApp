package com.example.skajkut.chatapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.skajkut.chatapp.data.model.User;
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

public class FriendsFragment extends Fragment {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private RecyclerView.LayoutManager mFriendsLayoutManager;
    private RecyclerView.LayoutManager mFavoritesLayoutManager;

    private FriendsAdapter mFriendsAdapter;
    private FavoriteFriendsAdapter mFavoritesAdapter;

    private List<User> users = new ArrayList<>();
    private List<User> favoriteFriends = new ArrayList<>();

    @BindView(R.id.rw_friendList) RecyclerView mRecyclerView;
    @BindView(R.id.rw_favoriteList) RecyclerView mFavRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_tab_friends, container, false);
        ButterKnife.bind(this, view);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        mFriendsLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mFriendsLayoutManager);

        mFavoritesLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        mFavRecyclerView.setLayoutManager(mFavoritesLayoutManager);


        getFriends();
        getFavoriteFriends();
        return view;
    }

    private void getFavoriteFriends(){
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabaseReference = mFirebaseDatabase
                .getReference("favoritefriends").child(mFirebaseUser.getUid());

        mDatabaseReference.keepSynced(true);

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot snap : dataSnapshot.getChildren()){


                    DatabaseReference userRef = mFirebaseDatabase.getReference().child("users");
                    Query q = userRef.orderByKey().equalTo(snap.getKey());

                    q.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                User u = snapshot.getValue(User.class);
                                favoriteFriends.add(u);
                            }
                            setFavoriteFriendsAdapter();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getFriends(){

        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabaseReference = mFirebaseDatabase
                .getReference("friendlist").child(mFirebaseUser.getUid());

        mDatabaseReference.keepSynced(true);

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot snap : dataSnapshot.getChildren()){


                    DatabaseReference userRef = mFirebaseDatabase.getReference().child("users");
                    Query q = userRef.orderByKey().equalTo(snap.getKey());

                    q.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                User u = snapshot.getValue(User.class);
                                users.add(u);
                            }
                            setFriendsAdapter();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setFriendsAdapter() {
        mFriendsAdapter = new FriendsAdapter(getActivity(), users);
        mRecyclerView.setAdapter(mFriendsAdapter);
    }

    private void setFavoriteFriendsAdapter(){
        mFavoritesAdapter = new FavoriteFriendsAdapter(getActivity(), favoriteFriends);
        mFavRecyclerView.setAdapter(mFavoritesAdapter);
    }


}

