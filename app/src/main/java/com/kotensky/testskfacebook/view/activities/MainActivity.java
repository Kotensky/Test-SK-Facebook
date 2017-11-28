package com.kotensky.testskfacebook.view.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.kotensky.testskfacebook.R;
import com.kotensky.testskfacebook.model.data.AlbumEntity;
import com.kotensky.testskfacebook.model.data.ImageEntity;
import com.kotensky.testskfacebook.model.data.ResponseEntity;
import com.kotensky.testskfacebook.model.network.ApiFactory;
import com.kotensky.testskfacebook.presenter.AlbumsPresenter;
import com.kotensky.testskfacebook.utils.SharedPreferencesManager;
import com.kotensky.testskfacebook.view.activities.view.AlbumsView;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AlbumsView {

    private CallbackManager callbackManager;
    private AlbumsPresenter albumsPresenter;
    private ResponseEntity<AlbumEntity> responseEntity;
    private List<AlbumEntity> albumEntities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        SharedPreferencesManager.saveAccessToken(loginResult.getAccessToken().getToken());
                        Log.e("TAG", loginResult.getAccessToken().getToken());

                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        setupDefaultValues();
    }

    private void setupDefaultValues() {
        albumsPresenter = new AlbumsPresenter(this);
        albumsPresenter.getAlbumsFirstPage();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showErrorMessage(String error) {
    }

    @Override
    public void showLoginScreen() {

    }

    @Override
    public void onResponseObtained(ResponseEntity<AlbumEntity> responseEntity) {
        this.responseEntity = responseEntity;

        for(AlbumEntity albumEntity : responseEntity.getData()){
            Log.e("TAG", albumEntity.getName() + " " + albumEntity.getCreatedTime());
        }
    }
}
