package com.kotensky.testskfacebook.model.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseEntity<T> {

    @SerializedName("data")
    private List<T> data;
    @SerializedName("paging")
    private Paging paging;

    public List<T> getData() {
        return data;
    }

    public Paging getPaging() {
        return paging;
    }
}
