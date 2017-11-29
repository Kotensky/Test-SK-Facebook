package com.kotensky.testskfacebook.view.fragments.view;

import com.kotensky.testskfacebook.model.data.ImageEntity;
import com.kotensky.testskfacebook.model.data.ResponseEntity;
import com.kotensky.testskfacebook.view.BaseView;

import java.util.List;

public interface PhotosView extends BaseView {

    void onResponseObtained(ResponseEntity<List<ImageEntity>> body, boolean isFirst);
}
