package com.kotensky.testskfacebook.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kotensky.testskfacebook.R;
import com.kotensky.testskfacebook.listeners.OnRecyclerItemClickListener;
import com.kotensky.testskfacebook.model.data.AlbumEntity;
import com.kotensky.testskfacebook.model.data.ImageEntity;
import com.kotensky.testskfacebook.view.fragments.PhotosFragment;

import java.util.List;

import butterknife.BindView;


public class PhotosGridAdapter extends RecyclerView.Adapter<PhotosGridAdapter.PhotoGridViewHolder> {

    private Context context;
    private List<List<ImageEntity>> photoEntities;
    private OnRecyclerItemClickListener itemClickListener;

    public PhotosGridAdapter(Context context, List<List<ImageEntity>> photoEntities, OnRecyclerItemClickListener itemClickListener) {
        this.context = context;
        this.photoEntities = photoEntities;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public PhotoGridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.photo_grid_item, parent, false);
        return new PhotoGridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoGridViewHolder holder, int position) {
        List<ImageEntity> imageEntities = photoEntities.get(position);
        Glide.with(context)
                .load(imageEntities.get(imageEntities.size() - 1).getSource())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return photoEntities.size();
    }

    public class PhotoGridViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.grid_item_img)
        ImageView img;
        public PhotoGridViewHolder(View itemView) {
            super(itemView);
        }
    }
}
