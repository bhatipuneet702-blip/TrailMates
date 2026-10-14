package com.tripsync.app;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tripsync.app.databinding.ItemTripSelectorBinding;
import com.tripsync.app.db.entities.Trip;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TripSelectorAdapter extends RecyclerView.Adapter<TripSelectorAdapter.TripViewHolder> {
    private List<Trip> trips;
    private String currentTripId;
    private OnTripSelectedListener listener;

    public interface OnTripSelectedListener {
        void onTripSelected(String tripId);
    }

    public TripSelectorAdapter(List<Trip> trips, String currentTripId, OnTripSelectedListener listener) {
        this.trips = trips;
        this.currentTripId = currentTripId;
        this.listener = listener;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
        notifyDataSetChanged();
    }

    public void setCurrentTripId(String tripId) {
        this.currentTripId = tripId;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTripSelectorBinding binding = ItemTripSelectorBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new TripViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        holder.bind(trips.get(position));
    }

    @Override
    public int getItemCount() {
        return trips != null ? trips.size() : 0;
    }

    public class TripViewHolder extends RecyclerView.ViewHolder {
        private final ItemTripSelectorBinding binding;

        public TripViewHolder(ItemTripSelectorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Trip trip) {
            binding.tripName.setText(trip.getName());
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            String dateRange = sdf.format(new Date(trip.getStartDateMillis())) + " - " + 
                             sdf.format(new Date(trip.getEndDateMillis()));
            binding.tripDates.setText(dateRange);
            
            boolean isSelected = trip.getId().equals(currentTripId);
            binding.tripCheckbox.setChecked(isSelected);
            
            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) {
                    listener.onTripSelected(trip.getId());
                }
            });
        }
    }
}
