package com.kotensky.testskfacebook.model.network;

public class NetworkVariables {
    static final String MAIN_API_URL = "https://graph.facebook.com/";
    private static final String VERSION = "v2.11/";

    static final String PROFILE = VERSION +"me/";
    static final String ALBUMS = VERSION +"me/albums/";

    public static final String ALBUM_FIELDS = "cover_photo{images},created_time,name";
}
