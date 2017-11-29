package com.kotensky.testskfacebook.model.data;

import com.google.gson.annotations.SerializedName;

public class Cursors {

    @SerializedName("after")
    private String after;
    @SerializedName("before")
    private String before;

    public String getAfter() {
        return after;
    }

    public String getBefore() {
        return before;
    }
}