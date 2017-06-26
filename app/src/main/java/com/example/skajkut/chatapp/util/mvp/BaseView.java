package com.example.skajkut.chatapp.util.mvp;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.skajkut.chatapp.R;

import butterknife.BindView;

/**
 * Created by Stefan Kajkut on 6/26/2017.
 * Contact me on stefan.kajkutsf@gmail.com.
 */

public abstract class BaseView extends Fragment implements IBaseView {

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setProgressBar(boolean show) {
        if(show) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showNetworkFailureMessage(boolean show) {
        // TODO
    }
}
