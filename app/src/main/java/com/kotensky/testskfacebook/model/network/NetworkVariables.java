package com.kotensky.testskfacebook.model.network;

public class NetworkVariables {
    static final String MAIN_API_URL = "https://graph.facebook.com";
    private static final String VERSION = "v2.11/";

    static final String ALBUMS = VERSION +"/me/albums/";
    static final String ALBUM_PHOTOS = VERSION +"/{album-id}/photos/";

    public static final String IMAGES_FIELD = "images";
    public static final String ALBUM_FIELDS = "cover_photo{images},created_time,name";

}
