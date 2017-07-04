package com.example.skajkut.chatapp.ui.users;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.skajkut.chatapp.R;
import com.example.skajkut.chatapp.data.model.User;
import com.example.skajkut.chatapp.data.remote.DataSource;
import com.example.skajkut.chatapp.data.remote.FirebaseUserService;
import com.example.skajkut.chatapp.data.remote.RemoteDataSource;
import com.example.skajkut.chatapp.util.mvp.BaseView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Stefan Kajkut on 7/3/17.
 * For more information contact me on stefan.kajkutsf@gmail.com .
 */

public class UsersFragment extends BaseView implements UsersContract.View {

    @BindView(R.id.et_search_users)
    EditText mSearchUsers;

    @BindView(R.id.ib_search_users)
    ImageButton mSearchButton;

    @BindView(R.id.spinner)
    Spinner mSpinner;

    @BindView(R.id.rw_searched_friends)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_no_users_found)
    TextView mNoResultFound;

    private RecyclerView.LayoutManager mUsersLayoutManager;

    private UsersContract.Presenter mPresenter;

    private UsersAdapter mUsersAdapter;

    private List<User> tempUsersList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tempUsersList = new ArrayList<>();
        RemoteDataSource remoteDataSource = RemoteDataSource.getInstance();
        mPresenter = new UsersPresenter(remoteDataSource, this);

        String id = remoteDataSource.getCurrentUserID();
        remoteDataSource.getUserByID("XLIm1VcF6ocjdCAR30OLwtHOXm73", new DataSource.GetUserCallback() {
            @Override
            public void onSuccess(User user) {
                System.out.println();
            }

            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onNetworkFailure() {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_tab_users, container, false);
        ButterKnife.bind(this, view);

        mUsersLayoutManager = new LinearLayoutManager(getPermission());
        mRecyclerView.setLayoutManager(mUsersLayoutManager);

        return view;
    }

    @OnClick(R.id.ib_search_users)
    public void onSearchClicked() {
        String searchValue = mSearchUsers.getText().toString();
        String searchQuery = mSpinner.getSelectedItem().toString();
        onFindUsers(searchValue, searchQuery);
    }

    @Override
    public void onFindUsers(String searchValue, String searchQuery) {
        mPresenter.getUsersByParams(searchValue, searchQuery);
        clearList();
    }

    private void clearList() {
        if(mRecyclerView.getAdapter() != null) {
            int size = this.tempUsersList.size();
            this.tempUsersList.clear();
            mRecyclerView.getAdapter().notifyItemRangeRemoved(0, size);
        }
    }

    @Override
    public void onSearchResults(List<User> users) {
        tempUsersList = users;
        if(users != null && users.size() > 0) {
            mUsersAdapter = new UsersAdapter(getPermission(), users);
            mRecyclerView.setAdapter(mUsersAdapter);
        } else {
            mNoResultFound.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
    }
}
