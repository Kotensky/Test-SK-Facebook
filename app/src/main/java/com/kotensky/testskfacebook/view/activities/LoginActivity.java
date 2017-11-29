package com.kotensky.testskfacebook.view.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.kotensky.testskfacebook.R;
import com.kotensky.testskfacebook.utils.SharedPreferencesManager;

import java.util.Collections;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements FacebookCallback<LoginResult> {

    private static final String USER_PHOTOS_PERMISSION = "user_photos";
    private CallbackManager callbackManager;

    @OnClick(R.id.login_btn)
    public void login(){
        LoginManager.getInstance().logInWithReadPermissions(this, Collections.singletonList(USER_PHOTOS_PERMISSION));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, this);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        SharedPreferencesManager.saveAccessToken(loginResult.getAccessToken().getToken());
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onCancel() {
        Toast.makeText(getApplicationContext(), getString(R.string.canceled), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(FacebookException exception) {
        if (exception.getMessage()!= null && !exception.getMessage().isEmpty()) {
            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
