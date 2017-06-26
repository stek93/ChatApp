package com.example.skajkut.chatapp.util.mvp;

/**
 * Created by Stefan Kajkut on 6/26/2017.
 * Contact me on stefan.kajkutsf@gmail.com.
 */

public interface IBasePresenter<ViewT> {

    void onViewActive(ViewT view);

    void onViewInactive();

}
