package com.kotensky.testskfacebook.model.data;

import com.google.gson.annotations.SerializedName;

public class ImageEntity {
    @SerializedName("width")
    private String width;
    @SerializedName("height")
    private String height;
    @SerializedName("source")
    private String source;

    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }

    public String getSource() {
        return source;
    }
}
