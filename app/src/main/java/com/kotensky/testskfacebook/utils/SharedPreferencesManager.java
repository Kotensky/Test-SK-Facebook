package com.kotensky.testskfacebook.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.kotensky.testskfacebook.application.TestSKApplication;

public class SharedPreferencesManager {

    private static final String PREFERENCES_FILE_NAME = "com.kotensky.testskfacebook.shared_preferences";
    private static final String ACCESS_TOKEN = "access_token";

    private static SharedPreferences sharedPreferences =
            TestSKApplication
                    .getInstance()
                    .getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);


    public static void saveAccessToken(String accessToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACCESS_TOKEN, accessToken);
        editor.apply();
    }

    public static String getAccessToken() {
        return sharedPreferences.getString(ACCESS_TOKEN, null);
    }

    public static boolean isLoggedIn() {
        String token = sharedPreferences.getString(ACCESS_TOKEN, null);
        return token != null && !token.isEmpty();
    }
}
