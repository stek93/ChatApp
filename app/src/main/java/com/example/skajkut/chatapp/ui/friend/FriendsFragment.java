package com.example.skajkut.chatapp.ui.friend;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skajkut.chatapp.R;
import com.example.skajkut.chatapp.data.model.User;
import com.example.skajkut.chatapp.data.remote.FirebaseUserService;
import com.example.skajkut.chatapp.data.remote.RemoteDataSource;
import com.example.skajkut.chatapp.ui.login.LoginContract;
import com.example.skajkut.chatapp.util.mvp.BaseView;
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

public class FriendsFragment extends BaseView implements FriendContract.View {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private RecyclerView.LayoutManager mFriendsLayoutManager;
    private RecyclerView.LayoutManager mFavoritesLayoutManager;

    private FriendsAdapter mFriendsAdapter;
    private FavoriteFriendsAdapter mFavoritesAdapter;

    private List<User> favoriteFriends = new ArrayList<>();

    private FriendContract.Presenter presenter;

    @BindView(R.id.rw_friendList) RecyclerView mRecyclerView;
    @BindView(R.id.rw_favoriteList) RecyclerView mFavRecyclerView;
    @BindView(R.id.tv_no_favorites) TextView noFavoritesTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RemoteDataSource remoteDataSource = RemoteDataSource.getInstance();

        presenter = new FriendPresenter(remoteDataSource,this);

        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_tab_friends, container, false);
        ButterKnife.bind(this, view);

        mFriendsLayoutManager = new LinearLayoutManager(getPermission());
        mRecyclerView.setLayoutManager(mFriendsLayoutManager);

        mFavoritesLayoutManager = new LinearLayoutManager(getPermission(),
                LinearLayoutManager.HORIZONTAL, false);
        mFavRecyclerView.setLayoutManager(mFavoritesLayoutManager);

        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        getFriendList();
        getFavoriteFriends();

        return view;
    }

    private void getFavoriteFriends(){
        presenter.getFavoriteFreinds();

    }

    private void getFriendList(){
        presenter.getFriends();
    }

    @Override
    public void showFriendList(List<User> users) {
        mFriendsAdapter = new FriendsAdapter(getActivity(), users);
        mRecyclerView.setAdapter(mFriendsAdapter);
    }

    @Override
    public void showFavoriteFriends(List<User> users) {
        if(users!=null && users.size() > 0)  {
            mFavoritesAdapter = new FavoriteFriendsAdapter(getActivity(), users);
            mFavRecyclerView.setAdapter(mFavoritesAdapter);
        }else {
            noFavoritesTextView.setVisibility(View.VISIBLE);
            mFavRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showFavoriteIcon() {

    }

    @Override
    public Context getPermission() {
        return getActivity();
    }

}

