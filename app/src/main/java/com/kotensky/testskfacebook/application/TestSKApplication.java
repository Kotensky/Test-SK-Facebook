package com.kotensky.testskfacebook.application;


import android.app.Application;


public class TestSKApplication extends Application {

    private static TestSKApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static TestSKApplication getInstance() {
        return instance;
    }
}
