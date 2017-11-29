package com.kotensky.testskfacebook.view.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kotensky.testskfacebook.R;
import com.kotensky.testskfacebook.listeners.OnRecyclerItemClickListener;
import com.kotensky.testskfacebook.model.data.AlbumEntity;
import com.kotensky.testskfacebook.utils.AppHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder> {

    private Context context;
    private List<AlbumEntity> albumEntities;
    private OnRecyclerItemClickListener itemClickListener;


    public AlbumsAdapter(Context context, List<AlbumEntity> albumEntities,  OnRecyclerItemClickListener listener) {
        this.context = context;
        this.albumEntities = albumEntities;
        this.itemClickListener = listener;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.album_item, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        AlbumEntity albumEntity = albumEntities.get(position);
        holder.name.setText(albumEntity.getName());
        holder.createdTime.setText(AppHelper.convertTime(albumEntity.getCreatedTime()));
        Glide.with(context)
                .load(albumEntity.getCoverPhoto().getImages().get(
                        albumEntity.getCoverPhoto().getImages().size() - 1).getSource())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.img);
        holder.itemView.setOnClickListener(view -> itemClickListener.onClick(position));
    }

    @Override
    public int getItemCount() {
        return albumEntities.size();
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.album_name_txt)
        TextView name;
        @BindView(R.id.album_created_time_txt)
        TextView createdTime;
        @BindView(R.id.album_cover_photo_img)
        ImageView img;

        public AlbumViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
