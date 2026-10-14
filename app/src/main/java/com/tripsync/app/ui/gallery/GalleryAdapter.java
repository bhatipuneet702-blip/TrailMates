package com.tripsync.app.ui.gallery;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.tripsync.app.databinding.ItemGalleryBinding;
import com.tripsync.app.db.entities.MediaItem;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private final List<MediaItem> mediaList;

    public GalleryAdapter(List<MediaItem> mediaList) {
        this.mediaList = mediaList;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGalleryBinding binding = ItemGalleryBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new GalleryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        holder.bind(mediaList.get(position));
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    static class GalleryViewHolder extends RecyclerView.ViewHolder {
        private final ItemGalleryBinding binding;

        public GalleryViewHolder(@NonNull ItemGalleryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MediaItem media) {
            // Load image from local file path using Glide
            Glide.with(binding.galleryImage.getContext())
                    .load(media.getLocalPath())
                    .centerCrop()
                    .into(binding.galleryImage);
        }
    }
}
