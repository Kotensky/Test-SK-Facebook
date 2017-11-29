package com.kotensky.testskfacebook.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.kotensky.testskfacebook.R;
import com.kotensky.testskfacebook.model.data.AlbumEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotosFragment extends Fragment {

    public static Fragment newInstance(AlbumEntity albumEntity) {
        PhotosFragment photosFragment = new PhotosFragment();

        Bundle arguments = new Bundle();
        arguments.putString(ALBUM_ENTITY_KEY, new Gson().toJson(albumEntity));
        photosFragment.setArguments(arguments);

        return photosFragment;
    }

//    @BindView(R.id.swipe_refresh_photos)
//    SwipeRefreshLayout swipeRefreshLayoutPhotos;
//    @BindView(R.id.recycler_view_photos)
//    RecyclerView recyclerViewGridPhotos;

    private static final String ALBUM_ENTITY_KEY = "album_entity_key";
    private AlbumEntity albumEntity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            String albumEntityJson = getArguments().getString(ALBUM_ENTITY_KEY);
            if (albumEntityJson != null && !albumEntityJson.isEmpty()) {
                albumEntity = new Gson().fromJson(albumEntityJson, AlbumEntity.class);
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);
        ButterKnife.bind(this, view);
        setupDefaultValues();
        return view;
    }

    private void setupDefaultValues() {
    }
}
