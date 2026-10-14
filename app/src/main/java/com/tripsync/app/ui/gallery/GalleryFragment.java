package com.tripsync.app.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import com.tripsync.app.databinding.FragmentGalleryBinding;
import com.tripsync.app.db.entities.MediaItem;
import com.tripsync.app.viewmodel.AppViewModel;
import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private AppViewModel viewModel;
    private final List<MediaItem> mediaList = new ArrayList<>();
    private GalleryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        adapter = new GalleryAdapter(mediaList);
        binding.rvGallery.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        binding.rvGallery.setAdapter(adapter);

        binding.fabAddMedia.setOnClickListener(v -> {
            // TODO: Photo picker integration in Step 4
        });

        // Observe media from ViewModel
        viewModel.currentTripMedia.observe(getViewLifecycleOwner(), media -> {
            mediaList.clear();
            mediaList.addAll(media);
            adapter.notifyDataSetChanged();
            updateEmptyState();
        });

        updateEmptyState();
    }

    private void updateEmptyState() {
        if (mediaList.isEmpty()) {
            binding.tvEmptyGallery.setVisibility(View.VISIBLE);
            binding.rvGallery.setVisibility(View.GONE);
            binding.fabAddMedia.setVisibility(View.GONE);
        } else {
            binding.tvEmptyGallery.setVisibility(View.GONE);
            binding.rvGallery.setVisibility(View.VISIBLE);
            binding.fabAddMedia.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
