package com.example.skajkut.chatapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import java.util.concurrent.TimeUnit;

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

    private RecyclerView.LayoutManager mLayoutManager;
    private FriendsAdapter mFriendsAdapter;

    private List<String> usersKeys = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<User> friendList = new ArrayList<>();

    @BindView(R.id.rw_friendList)
    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_tab_friends, container, false);
        ButterKnife.bind(this, view);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        getUsersKeys();



        return view;
    }


    private void getUsersKeys(){

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
                            setAdapter();
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


    private void setAdapter() {
        mFriendsAdapter = new FriendsAdapter(getActivity(), users);
        mRecyclerView.setAdapter(mFriendsAdapter);
    }




}

