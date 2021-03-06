package com.example.skajkut.chatapp.ui.friend;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.skajkut.chatapp.R;
import com.example.skajkut.chatapp.data.model.User;
import com.example.skajkut.chatapp.data.remote.RemoteDataSource;
import com.example.skajkut.chatapp.util.mvp.BaseView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Stefan Kajkut on 6/22/2017.
 * Contact me on stefan.kajkutsf@gmail.com.
 */

public class FriendsFragment extends BaseView implements FriendContract.View{

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



        mFavoritesAdapter = new FavoriteFriendsAdapter(getActivity(), favoriteFriends);
        mFavRecyclerView.setAdapter(mFavoritesAdapter);

        getFriendList();
        getFavoriteFriends();

        presenter.favoriteFriendsListener();
        return view;
    }



    private void getFavoriteFriends(){
        presenter.getFavoriteFreinds();

    }

    private void getFriendList(){
        presenter.getFriends();
    }

    @Override
    public void showFriendList(List<User> users, User currentUser) {
        mFriendsAdapter = new FriendsAdapter(getActivity(), users, currentUser);
        mRecyclerView.setAdapter(mFriendsAdapter);
        mFriendsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showFavoriteFriends(List<User> users) {
        if(users!=null && users.size() > 0)  {
//            mFavoritesAdapter = new FavoriteFriendsAdapter(getActivity(), users);
//            mFavRecyclerView.setAdapter(mFavoritesAdapter);
            Set<User> userSet = new HashSet<>(users);

            mFavoritesAdapter.getFavoriteFriends().clear();
            mFavoritesAdapter.getFavoriteFriends().addAll(users);
            mFavoritesAdapter.notifyDataSetChanged();

        }else {
            noFavoritesTextView.setVisibility(View.VISIBLE);
            mFavRecyclerView.setVisibility(View.GONE);
        }
        mFavoritesAdapter.notifyDataSetChanged();


    }


    @Override
    public Context getPermission() {
        return getActivity();
    }


    @Override
    public void showNoFriendMessage(List<User> users) {

    }

}

