package com.tripsync.app.ui.packing;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.tripsync.app.R;
import com.tripsync.app.databinding.ItemPackingBinding;
import com.tripsync.app.db.entities.PackingItem;

public class PackingAdapter extends ListAdapter<PackingItem, PackingAdapter.PackingViewHolder> {

    public interface OnPackingItemActionListener {
        void onItemCheckChanged(PackingItem item, boolean isChecked);
        void onItemDeleted(PackingItem item);
    }

    private static final DiffUtil.ItemCallback<PackingItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<PackingItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull PackingItem oldItem, @NonNull PackingItem newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull PackingItem oldItem, @NonNull PackingItem newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.isPacked() == newItem.isPacked() &&
                    oldItem.getCategoryId().equals(newItem.getCategoryId());
        }
    };

    private final OnPackingItemActionListener listener;

    public PackingAdapter(OnPackingItemActionListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    @NonNull
    @Override
    public PackingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPackingBinding binding = ItemPackingBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new PackingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PackingViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    static class PackingViewHolder extends RecyclerView.ViewHolder {
        private final ItemPackingBinding binding;

        public PackingViewHolder(@NonNull ItemPackingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(PackingItem item, OnPackingItemActionListener listener) {
            binding.tvItemName.setText(item.getName());

            // Remove listener temporarily to avoid triggering on bind
            binding.checkboxItem.setOnCheckedChangeListener(null);
            binding.checkboxItem.setChecked(item.isPacked());

            // Update strikethrough and text color
            updateStrikeThrough(item.isPacked());

            // Set click listener on row to toggle
            binding.getRoot().setOnClickListener(v -> {
                binding.checkboxItem.toggle();
            });

            // Set checkbox listener
            binding.checkboxItem.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (listener != null) {
                    listener.onItemCheckChanged(item, isChecked);
                }
                updateStrikeThrough(isChecked);
            });

            // Set menu button listener
            binding.menuButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemDeleted(item);
                }
            });
        }

        private void updateStrikeThrough(boolean isPacked) {
            if (isPacked) {
                binding.tvItemName.setPaintFlags(binding.tvItemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                binding.tvItemName.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.text_secondary));
            } else {
                binding.tvItemName.setPaintFlags(binding.tvItemName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                binding.tvItemName.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.on_surface));
            }
        }
    }
}
