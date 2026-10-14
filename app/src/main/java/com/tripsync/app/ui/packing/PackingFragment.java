package com.tripsync.app.ui.packing;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.tripsync.app.R;
import com.tripsync.app.databinding.FragmentPackingBinding;
import com.tripsync.app.db.entities.PackingCategory;
import com.tripsync.app.db.entities.PackingItem;
import com.tripsync.app.viewmodel.AppViewModel;

import java.util.ArrayList;
import java.util.List;

public class PackingFragment extends Fragment implements PackingAdapter.OnPackingItemActionListener {

    private FragmentPackingBinding binding;
    private AppViewModel viewModel;
    private PackingAdapter adapter;
    private String selectedCategoryId = null;
    private List<PackingCategory> categories = new ArrayList<>();
    private List<PackingItem> allItems = new ArrayList<>();

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

        // Setup RecyclerView
        adapter = new PackingAdapter(this);
        binding.rvPackingItems.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvPackingItems.setAdapter(adapter);

        binding.btnAddItem.setOnClickListener(v -> showAddItemDialog());

        // Observe current trip
        viewModel.currentTrip.observe(getViewLifecycleOwner(), trip -> {
            if (trip != null) {
                binding.btnAddItem.setEnabled(true);
                seedCategoriesIfNeeded(trip.getId());
                observeCategories(trip.getId());
                observeItems(trip.getId());
            } else {
                binding.btnAddItem.setEnabled(false);
                showEmptyTrip();
            }
        });

        updateUI();
    }

    private void seedCategoriesIfNeeded(String tripId) {
        new Thread(() -> {
            try {
                // Check if categories exist
                List<PackingCategory> existing = viewModel.packingRepository.getCategoriesForTripSync(tripId);
                if (existing.isEmpty()) {
                    viewModel.seedDefaultPackingCategories();
                }
            } catch (Exception e) {
                Log.e("TripSync", "Error seeding categories: " + e.getMessage());
            }
        }).start();
    }

    private void observeCategories(String tripId) {
        viewModel.currentTripPackingCategories.observe(getViewLifecycleOwner(), cats -> {
            if (cats != null) {
                categories.clear();
                categories.addAll(cats);
                updateChips();
                updateUI();
            }
        });
    }

    private void observeItems(String tripId) {
        viewModel.currentTripPackingItems.observe(getViewLifecycleOwner(), items -> {
            if (items != null) {
                allItems.clear();
                allItems.addAll(items);
                updateItemsList();
                updateProgress();
            }
        });
    }

    private void updateChips() {
        binding.chipContainer.removeAllViews();

        // "All" chip
        Chip allChip = createChip("All", null, selectedCategoryId == null);
        binding.chipContainer.addView(allChip);

        // Category chips
        for (PackingCategory category : categories) {
            Chip chip = createChip(category.getName(), category.getId(), 
                    selectedCategoryId != null && selectedCategoryId.equals(category.getId()));
            binding.chipContainer.addView(chip);
        }

        // "+ Category" chip
        Chip addChip = new Chip(requireContext());
        addChip.setText(R.string.add_category);
        addChip.setChipBackgroundColorResource(R.color.surface);
        addChip.setTextColor(requireContext().getColor(R.color.on_surface));
        addChip.setOnClickListener(v -> showAddCategoryDialog());
        binding.chipContainer.addView(addChip);
    }

    private Chip createChip(String label, @Nullable String categoryId, boolean isSelected) {
        Chip chip = new Chip(requireContext());
        chip.setText(label);
        chip.setCheckable(false);
        chip.setChipBackgroundColorResource(isSelected ? R.color.primary : R.color.surface);
        chip.setTextColor(requireContext().getColor(isSelected ? R.color.on_primary : R.color.on_surface));
        chip.setTag(categoryId);

        chip.setOnClickListener(v -> {
            selectedCategoryId = categoryId;
            updateChips();
            updateItemsList();
            scrollChipIntoView(chip);
        });

        chip.setOnLongClickListener(v -> {
            if (categoryId != null) {
                showCategoryMenu(getCategoryByIdSync(categoryId));
                return true;
            }
            return false;
        });

        return chip;
    }

    private void scrollChipIntoView(Chip chip) {
        binding.chipScroll.post(() -> {
            binding.chipScroll.smoothScrollTo(chip.getLeft(), chip.getTop());
        });
    }

    private void updateItemsList() {
        List<PackingItem> filtered;
        if (selectedCategoryId == null) {
            filtered = new ArrayList<>(allItems);
        } else {
            filtered = new ArrayList<>();
            for (PackingItem item : allItems) {
                if (selectedCategoryId.equals(item.getCategoryId())) {
                    filtered.add(item);
                }
            }
        }
        adapter.submitList(filtered);
        updateEmptyState(filtered.isEmpty());
    }

    private void updateProgress() {
        String tripId = viewModel.getCurrentTripIdValue();
        if (tripId == null) return;

        try {
            int packed = viewModel.getPackedCountForTrip(tripId);
            int total = viewModel.getTotalCountForTrip(tripId);
            
            binding.tvProgressCount.setText(String.format("%d/%d", packed, total));
            binding.progressBar.setProgress(total > 0 ? (int)((packed * 100.0) / total) : 0);
        } catch (Exception e) {
            Log.e("TripSync", "Error updating progress: " + e.getMessage());
        }
    }

    private void updateEmptyState(boolean isEmpty) {
        if (isEmpty && allItems.isEmpty()) {
            binding.tvEmptyPacking.setVisibility(View.VISIBLE);
            binding.rvPackingItems.setVisibility(View.GONE);
        } else {
            binding.tvEmptyPacking.setVisibility(View.GONE);
            binding.rvPackingItems.setVisibility(View.VISIBLE);
        }
    }

    private void showEmptyTrip() {
        binding.chipContainer.removeAllViews();
        binding.rvPackingItems.setVisibility(View.GONE);
        binding.tvEmptyPacking.setText(R.string.create_trip_first_packing);
        binding.tvEmptyPacking.setVisibility(View.VISIBLE);
    }

    private void showAddCategoryDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        builder.setTitle(R.string.add_packing_category);

        TextInputLayout til = new TextInputLayout(requireContext());
        til.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);
        til.setPadding(50, 40, 50, 40);

        EditText et = new EditText(requireContext());
        et.setHint(R.string.category_name_hint);
        et.setInputType(InputType.TYPE_CLASS_TEXT);
        til.addView(et);

        builder.setView(til);
        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = et.getText().toString().trim();
            String tripId = viewModel.getCurrentTripIdValue();

            if (tripId == null) {
                Snackbar.make(binding.getRoot(), "No trip selected", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (!viewModel.packingRepository.validateCategoryName(tripId, name)) {
                til.setError(getString(R.string.invalid_category_name));
                return;
            }

            int maxSort = categories.stream().mapToInt(PackingCategory::getSortOrder).max().orElse(-1) + 1;
            viewModel.createPackingCategory(name, maxSort);
            dialog.dismiss();
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showCategoryMenu(PackingCategory category) {
        String[] options = {"Rename", "Delete"};
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(category.getName())
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        showRenameCategoryDialog(category);
                    } else {
                        showDeleteCategoryDialog(category);
                    }
                })
                .show();
    }

    private void showRenameCategoryDialog(PackingCategory category) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        builder.setTitle(R.string.rename_category);

        TextInputLayout til = new TextInputLayout(requireContext());
        til.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);
        til.setPadding(50, 40, 50, 40);

        EditText et = new EditText(requireContext());
        et.setText(category.getName());
        et.setInputType(InputType.TYPE_CLASS_TEXT);
        til.addView(et);

        builder.setView(til);
        builder.setPositiveButton("Save", (dialog, which) -> {
            String newName = et.getText().toString().trim();
            String tripId = viewModel.getCurrentTripIdValue();

            if (tripId == null) return;

            if (!viewModel.packingRepository.validateCategoryName(tripId, newName)) {
                til.setError(getString(R.string.invalid_category_name));
                return;
            }

            category.setName(newName);
            viewModel.updatePackingCategory(category);
            dialog.dismiss();
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showDeleteCategoryDialog(PackingCategory category) {
        // Check if this is the Others category
        if ("Others".equalsIgnoreCase(category.getName()) && categories.size() == 1) {
            Snackbar.make(binding.getRoot(), R.string.cannot_delete_others, Snackbar.LENGTH_SHORT).show();
            return;
        }

        String[] options = {getString(R.string.move_to_others), getString(R.string.delete_items_too)};
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.delete_category)
                .setItems(options, (dialog, which) -> {
                    String tripId = viewModel.getCurrentTripIdValue();
                    if (tripId == null) return;

                    if (which == 0) {
                        // Move to Others
                        String othersId = viewModel.packingRepository.getOthersCategoryId(tripId);
                        if (othersId != null) {
                            viewModel.deletePackingCategory(category.getId(), othersId);
                        }
                    } else {
                        // Delete items too
                        viewModel.deletePackingCategory(category.getId(), null);
                    }
                    dialog.dismiss();
                })
                .show();
    }

    private void showAddItemDialog() {
        String tripId = viewModel.getCurrentTripIdValue();
        if (tripId == null) {
            Snackbar.make(binding.getRoot(), R.string.create_trip_first_packing, Snackbar.LENGTH_SHORT).show();
            return;
        }

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        builder.setTitle(R.string.add_packing_item);

        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 40);

        TextInputLayout tilName = new TextInputLayout(requireContext());
        tilName.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);
        EditText etName = new EditText(requireContext());
        etName.setHint(R.string.item_name_hint);
        etName.setInputType(InputType.TYPE_CLASS_TEXT);
        tilName.addView(etName);
        layout.addView(tilName);

        builder.setView(layout);
        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = etName.getText().toString().trim();
            String categoryId = selectedCategoryId;

            if (categoryId == null && !categories.isEmpty()) {
                categoryId = categories.get(0).getId();
            }

            if (categoryId == null) {
                Snackbar.make(binding.getRoot(), "No category available", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (!viewModel.packingRepository.validateItemName(categoryId, name)) {
                tilName.setError(getString(R.string.invalid_item_name));
                return;
            }

            viewModel.createPackingItem(categoryId, name);
            dialog.dismiss();
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
        Snackbar.make(binding.getRoot(), R.string.item_deleted, Snackbar.LENGTH_LONG)
                .setAction(R.string.item_undo, v -> {
                    viewModel.createPackingItem(item.getCategoryId(), item.getName());
                })
                .show();
    }

    private void updateUI() {
        updateItemsList();
        updateProgress();
    }

    private PackingCategory getCategoryByIdSync(String categoryId) {
        for (PackingCategory cat : categories) {
            if (cat.getId().equals(categoryId)) {
                return cat;
            }
        }
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
