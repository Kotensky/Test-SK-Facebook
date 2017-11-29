package com.kotensky.testskfacebook.view.fragments.view;

import com.kotensky.testskfacebook.model.data.ImageListEntity;
import com.kotensky.testskfacebook.model.data.ResponseEntity;
import com.kotensky.testskfacebook.view.BaseView;

public interface PhotosView extends BaseView {

    void onResponseObtained(ResponseEntity<ImageListEntity> body, boolean isFirst);
}
