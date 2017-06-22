package com.example.skajkut.chatapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;

/**
 * Created by Stefan Kajkut on 6/22/2017.
 * Contact me on stefan.kajkutsf@gmail.com.
 */

public class FriendsFragment extends Fragment {

    private RecyclerView.LayoutManager mLayoutManager;
    private FriendsAdapter mFriendsAdapter;

    @BindView(R.id.rw_friendList) RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view  = inflater.inflate(R.layout.fragment_friend_list, container, false);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mFriendsAdapter = new FriendsAdapter();

        return view;
    }
}
