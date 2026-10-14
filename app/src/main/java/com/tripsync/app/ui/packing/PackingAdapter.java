package com.tripsync.app.ui.packing;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tripsync.app.databinding.ItemPackingBinding;
import com.tripsync.app.db.entities.PackingItem;
import java.util.List;

public class PackingAdapter extends RecyclerView.Adapter<PackingAdapter.PackingViewHolder> {

    public interface OnPackingItemActionListener {
        void onItemCheckChanged(PackingItem item, boolean isChecked);
        void onItemDeleted(PackingItem item);
    }

    private final List<PackingItem> items;
    private final OnPackingItemActionListener listener;

    public PackingAdapter(List<PackingItem> items, OnPackingItemActionListener listener) {
        this.items = items;
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
        holder.bind(items.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class PackingViewHolder extends RecyclerView.ViewHolder {
        private final ItemPackingBinding binding;

        public PackingViewHolder(@NonNull ItemPackingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(PackingItem item, OnPackingItemActionListener listener) {
            binding.tvItemName.setText(item.getName());

            binding.checkboxItem.setOnCheckedChangeListener(null);
            binding.checkboxItem.setChecked(item.isPacked());

            updateStrikeThrough(item.isPacked());

            binding.checkboxItem.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (listener != null) {
                    listener.onItemCheckChanged(item, isChecked);
                }
                updateStrikeThrough(isChecked);
            });

            binding.menuButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemDeleted(item);
                }
            });
        }

        private void updateStrikeThrough(boolean isPacked) {
            if (isPacked) {
                binding.tvItemName.setPaintFlags(binding.tvItemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                binding.checkboxItem.setChecked(true);
            } else {
                binding.tvItemName.setPaintFlags(binding.tvItemName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                binding.checkboxItem.setChecked(false);
            }
        }
    }
}
