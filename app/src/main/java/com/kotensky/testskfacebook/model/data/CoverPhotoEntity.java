package com.kotensky.testskfacebook.model.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CoverPhotoEntity {

    @SerializedName("id")
    private String id;
    @SerializedName("images")
    private List<ImageEntity> images;

    public String getId() {
        return id;
    }

    public List<ImageEntity> getImages() {
        return images;
    }
}
