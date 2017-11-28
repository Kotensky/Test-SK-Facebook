package com.kotensky.testskfacebook.presenter;

import com.kotensky.testskfacebook.view.BaseView;

public abstract class BasePresenter<T extends BaseView> {

    private T view;

    public T getView() {
        return view;
    }

    public void setView(T view) {
        this.view = view;
    }

    public abstract void cancel();

    public abstract void destroy();

}
