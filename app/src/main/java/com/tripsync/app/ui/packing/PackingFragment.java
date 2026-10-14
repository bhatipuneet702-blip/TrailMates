package com.tripsync.app.ui.packing;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.tripsync.app.databinding.FragmentPackingBinding;
import com.tripsync.app.db.entities.PackingItem;
import com.tripsync.app.viewmodel.AppViewModel;
import java.util.ArrayList;
import java.util.List;

public class PackingFragment extends Fragment implements PackingAdapter.OnPackingItemActionListener {

    private FragmentPackingBinding binding;
    private AppViewModel viewModel;
    private final List<PackingItem> packingList = new ArrayList<>();
    private PackingAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPackingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        adapter = new PackingAdapter(packingList, this);
        binding.rvPackingItems.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvPackingItems.setAdapter(adapter);

        binding.btnAddItem.setOnClickListener(v -> showAddPackingDialog());

        // Observe packing items from ViewModel
        viewModel.currentTripPackingItems.observe(getViewLifecycleOwner(), items -> {
            packingList.clear();
            packingList.addAll(items);
            adapter.notifyDataSetChanged();
            updateUI();
        });

        updateUI();
    }

    private void showAddPackingDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        builder.setTitle("Add Packing Item");
        
        EditText etItemName = new EditText(requireContext());
        etItemName.setHint("Item name");
        etItemName.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(etItemName);
        
        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = etItemName.getText().toString().trim();
            if (name.isEmpty()) return;
            
            // TODO: Get first category ID or default
            String firstCategoryId = "default-category"; // Placeholder
            viewModel.createPackingItem(firstCategoryId, name);
        });
        
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    @Override
    public void onItemCheckChanged(PackingItem item, boolean isChecked) {
        item.setPacked(isChecked);
        viewModel.updatePackingItem(item);
        updateProgress();
    }

    @Override
    public void onItemDeleted(PackingItem item) {
        viewModel.deletePackingItem(item.getId());
    }

    private void updateUI() {
        updateEmptyState();
        updateProgress();
    }

    private void updateEmptyState() {
        if (packingList.isEmpty()) {
            binding.tvEmptyPacking.setVisibility(View.VISIBLE);
            binding.rvPackingItems.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.GONE);
        } else {
            binding.tvEmptyPacking.setVisibility(View.GONE);
            binding.rvPackingItems.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void updateProgress() {
        int packedCount = 0;
        for (PackingItem item : packingList) {
            if (item.isPacked()) {
                packedCount++;
            }
        }
        binding.progressBar.setProgress((int) (packedCount * 100.0 / Math.max(packingList.size(), 1)));
        binding.tvProgressCount.setText(String.format("%d/%d", packedCount, packingList.size()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
