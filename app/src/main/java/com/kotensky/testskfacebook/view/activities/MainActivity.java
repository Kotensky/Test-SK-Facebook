package com.kotensky.testskfacebook.view.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.login.LoginManager;
import com.kotensky.testskfacebook.R;
import com.kotensky.testskfacebook.utils.SharedPreferencesManager;
import com.kotensky.testskfacebook.view.fragments.AlbumFragment;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!SharedPreferencesManager.isLoggedIn())
            showLoginScreen();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupDefaultValues();

    }

    private void setupDefaultValues() {
        fragmentManager = getSupportFragmentManager();
        AlbumFragment albumFragment = new AlbumFragment();
        addFragment(albumFragment);
    }


    private void showLogoutDialog() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
        builder.title(R.string.logout_dialog_title)
                .content(R.string.logout_dialog_content)
                .positiveText(R.string.yes)
                .negativeText(R.string.no)
                .onNegative((dialog, which) -> dialog.dismiss())
                .onPositive((dialog, which) -> {
                    LoginManager.getInstance().logOut();
                    SharedPreferencesManager.saveAccessToken(null);
                    showLoginScreen();
                });

        builder.build().show();
    }

    public void addFragment(Fragment fragment){
        String fragmentTag = fragment.getClass().getSimpleName();
        fragmentManager.beginTransaction()
                .add(R.id.main_container, fragment, fragmentTag)
                .addToBackStack(fragmentTag)
                .commit();
    }

    public void showLoginScreen() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                showLogoutDialog();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
