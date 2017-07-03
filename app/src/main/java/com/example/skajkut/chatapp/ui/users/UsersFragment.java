package com.example.skajkut.chatapp.ui.users;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.skajkut.chatapp.R;
import com.example.skajkut.chatapp.data.remote.RemoteDataSource;
import com.example.skajkut.chatapp.util.mvp.BaseView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

/**
 * Created by Stefan Kajkut on 7/3/17.
 * For more information contact me on stefan.kajkutsf@gmail.com .
 */

public class UsersFragment extends BaseView implements UsersContract.View {

    @BindView(R.id.et_search_users)
    EditText mSearchUsers;

    private UsersContract.Presenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RemoteDataSource remoteDataSource = RemoteDataSource.getInstance();
        mPresenter = new UsersPresenter(remoteDataSource, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_tab_users, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnTextChanged(R.id.et_search_users)
    public void onTextChanged() {
        String[] params = mSearchUsers
                .getText().toString().split(" ");
        onFindUsers(params);
    }

    @Override
    public void onFindUsers(String... params) {
        mPresenter.getUsersByParams(params);
    }
}
