package com.kotensky.testskfacebook.model.data;


import com.google.gson.annotations.SerializedName;

public class Paging {

    @SerializedName("cursors")
    private Cursors cursors;
    @SerializedName("previous")
    private String previous;
    @SerializedName("next")
    private String next;

    public Cursors getCursors() {
        return cursors;
    }

    public String getPrevious() {
        return previous;
    }

    public String getNext() {
        return next;
    }
}
