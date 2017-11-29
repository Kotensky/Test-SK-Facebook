package com.kotensky.testskfacebook.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kotensky.testskfacebook.R;
import com.kotensky.testskfacebook.listeners.OnRecyclerItemClickListener;
import com.kotensky.testskfacebook.model.data.AlbumEntity;
import com.kotensky.testskfacebook.model.data.ResponseEntity;
import com.kotensky.testskfacebook.presenter.AlbumsPresenter;
import com.kotensky.testskfacebook.view.activities.MainActivity;
import com.kotensky.testskfacebook.view.adapter.AlbumsAdapter;
import com.kotensky.testskfacebook.view.fragments.view.AlbumsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlbumFragment extends Fragment implements AlbumsView, SwipeRefreshLayout.OnRefreshListener, OnRecyclerItemClickListener {

    @BindView(R.id.swipe_refresh_albums)
    SwipeRefreshLayout swipeRefreshLayoutAlbums;
    @BindView(R.id.recycler_view_albums)
    RecyclerView recyclerViewAlbums;

    private AlbumsPresenter albumsPresenter;
    private ResponseEntity<AlbumEntity> responseEntity;
    private List<AlbumEntity> albumEntities = new ArrayList<>();
    private AlbumsAdapter albumsAdapter;

    private boolean isLoadingNextPage = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        ButterKnife.bind(this, view);
        setupDefaultValues();
        return view;
    }

    private void setupDefaultValues() {
        setupRecyclerView();
        albumsPresenter = new AlbumsPresenter(this);
        albumsPresenter.getAlbumsFirstPage();
        swipeRefreshLayoutAlbums.setOnRefreshListener(this);
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        albumsAdapter = new AlbumsAdapter(getContext(), albumEntities, this);
        recyclerViewAlbums.setLayoutManager(layoutManager);
        recyclerViewAlbums.setAdapter(albumsAdapter);
        recyclerViewAlbums.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            albumsPresenter.getAlbumsNextPage(nextUrl);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void showLoading() {
        swipeRefreshLayoutAlbums.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayoutAlbums.setRefreshing(false);
    }

    @Override
    public void showErrorMessage(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponseObtained(ResponseEntity<AlbumEntity> responseEntity, boolean isFirstPage) {
        this.responseEntity = responseEntity;
        if (isFirstPage) albumEntities.clear();
        albumEntities.addAll(responseEntity.getData());
        albumsAdapter.notifyDataSetChanged();
        isLoadingNextPage = false;
    }

    @Override
    public void onRefresh() {
        albumsPresenter.getAlbumsFirstPage();
    }

    @Override
    public void onClick(int position) {
        if (getActivity() != null && getActivity() instanceof MainActivity) {
            ((MainActivity)getActivity()).addFragment(PhotosFragment.newInstance(albumEntities.get(position)));
        }
    }

    @Override
    public void onLongClick(int position) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (albumsPresenter != null) {
            albumsPresenter.cancel();
            albumsPresenter.destroy();
        }
    }

}
