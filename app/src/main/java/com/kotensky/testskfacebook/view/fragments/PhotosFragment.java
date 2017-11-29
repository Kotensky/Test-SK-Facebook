package com.kotensky.testskfacebook.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kotensky.testskfacebook.R;
import com.kotensky.testskfacebook.listeners.OnRecyclerItemClickListener;
import com.kotensky.testskfacebook.model.data.AlbumEntity;
import com.kotensky.testskfacebook.model.data.ImageEntity;
import com.kotensky.testskfacebook.model.data.ResponseEntity;
import com.kotensky.testskfacebook.presenter.PhotosPresenter;
import com.kotensky.testskfacebook.view.adapter.PhotosGridAdapter;
import com.kotensky.testskfacebook.view.fragments.view.PhotosView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotosFragment extends Fragment implements PhotosView, SwipeRefreshLayout.OnRefreshListener, OnRecyclerItemClickListener {

    private static final int SPAN_COUNT = 4;

    public static Fragment newInstance(AlbumEntity albumEntity) {
        PhotosFragment photosFragment = new PhotosFragment();

        Bundle arguments = new Bundle();
        arguments.putString(ALBUM_ENTITY_KEY, new Gson().toJson(albumEntity));
        photosFragment.setArguments(arguments);

        return photosFragment;
    }

    @BindView(R.id.swipe_refresh_photos)
    SwipeRefreshLayout swipeRefreshLayoutPhotos;
    @BindView(R.id.recycler_view_photos)
    RecyclerView recyclerViewGridPhotos;

    private static final String ALBUM_ENTITY_KEY = "album_entity_key";
    private AlbumEntity albumEntity;
    private PhotosPresenter photosPresenter;
    private PhotosGridAdapter photosGridAdapter;
    private ResponseEntity<List<ImageEntity>> responseEntity;
    private List<List<ImageEntity>> photoEntities = new ArrayList<>();
    private boolean isLoadingNextPage = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount;

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
        setupRecyclerView();
        photosPresenter = new PhotosPresenter(this);
        photosPresenter.getPhotosFirstPage(albumEntity.getId());
        swipeRefreshLayoutPhotos.setOnRefreshListener(this);
    }

    private void setupRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), SPAN_COUNT);
        photosGridAdapter = new PhotosGridAdapter(getContext(), photoEntities, this);
        recyclerViewGridPhotos.setLayoutManager(layoutManager);
        recyclerViewGridPhotos.setAdapter(photosGridAdapter);
        recyclerViewGridPhotos.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount && !isLoadingNextPage) {
                        String nextUrl = responseEntity.getPaging().getNext();
                        isLoadingNextPage = true;
                        if (nextUrl != null && !nextUrl.isEmpty()) {
                            photosPresenter.getPhotosNextPage(nextUrl);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void showLoading() {
        swipeRefreshLayoutPhotos.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayoutPhotos.setRefreshing(false);
    }

    @Override
    public void showErrorMessage(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponseObtained(ResponseEntity<List<ImageEntity>> body, boolean isFirstPage) {
        this.responseEntity = body;
        if (isFirstPage) photoEntities.clear();
        photoEntities.addAll(responseEntity.getData());
        photosGridAdapter.notifyDataSetChanged();
        isLoadingNextPage = false;
    }

    @Override
    public void onRefresh() {
        photosPresenter.getPhotosFirstPage(albumEntity.getId());
    }

    @Override
    public void onClick(int position) {

    }

    @Override
    public void onLongClick(int position) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (photosPresenter != null) {
            photosPresenter.cancel();
            photosPresenter.destroy();
        }
    }
}
