package com.kotensky.testskfacebook.view.activities.view;


import com.kotensky.testskfacebook.model.data.AlbumEntity;
import com.kotensky.testskfacebook.model.data.ResponseEntity;
import com.kotensky.testskfacebook.view.BaseView;

public interface AlbumsView extends BaseView {

    void onResponseObtained(ResponseEntity<AlbumEntity> body);
}
