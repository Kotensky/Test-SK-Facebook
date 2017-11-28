package com.kotensky.testskfacebook.view;


public interface BaseView {

    void showLoading();

    void hideLoading();

    void showErrorMessage(String error);

    void showLoginScreen();

}
