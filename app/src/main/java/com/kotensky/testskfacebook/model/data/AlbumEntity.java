package com.kotensky.testskfacebook.model.data;

import com.google.gson.annotations.SerializedName;

public class AlbumEntity {

    @SerializedName("id")
    private String id;
    @SerializedName("cover_photo")
    private CoverPhotoEntity coverPhoto;
    @SerializedName("name")
    private String name;
    @SerializedName("created_time")
    private String createdTime;


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCreatedTime() {
        return createdTime;
    }
}
