package com.tripsync.app;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.tripsync.app.databinding.ActivityMainBinding;
import com.tripsync.app.db.entities.Member;
import com.tripsync.app.db.entities.Trip;
import com.tripsync.app.ui.expenses.ExpensesFragment;
import com.tripsync.app.ui.gallery.GalleryFragment;
import com.tripsync.app.ui.packing.PackingFragment;
import com.tripsync.app.viewmodel.AppViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AppViewModel viewModel;
    private TripSelectorAdapter tripSelectorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(AppViewModel.class);

        // Set up trip selector card click listener
        binding.tripSelectorCard.setOnClickListener(v -> showTripSelector());

        // Observe current trip and update UI
        viewModel.currentTrip.observe(this, trip -> {
            if (trip != null) {
                binding.tripName.setText(trip.getName());
                binding.tripSelectorCard.setVisibility(View.VISIBLE);
                binding.bottomNavigation.setVisibility(View.VISIBLE);
                binding.fragmentContainer.setVisibility(View.VISIBLE);
                updateTripSelectorSubtitle();
            } else {
                // No current trip, show empty state
                binding.tripName.setText(R.string.create_trip_empty_state);
                binding.tripSelectorCard.setVisibility(View.GONE);
                binding.bottomNavigation.setVisibility(View.GONE);
                binding.fragmentContainer.setVisibility(View.GONE);
            }
        });

        // Observe current trip members for member count and date range
        viewModel.currentTripMembers.observe(this, members -> {
            updateTripSelectorSubtitle();
        });

        // Observe all trips to check if empty on first launch
        viewModel.getAllTrips().observe(this, trips -> {
            if (trips.isEmpty()) {
                showCreateTripDialog();
            }
        });

        // Load initial fragment
        if (savedInstanceState == null) {
            loadFragment(new ExpensesFragment());
        }

        // Bottom navigation
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_expenses) {
                loadFragment(new ExpensesFragment());
                return true;
            } else if (id == R.id.nav_packing) {
                loadFragment(new PackingFragment());
                return true;
            } else if (id == R.id.nav_gallery) {
                loadFragment(new GalleryFragment());
                return true;
            }
            return false;
        });

        // Handle menu click
        binding.iconMenu.setOnClickListener(v -> showTripMenu());
    }

    private void updateTripSelectorSubtitle() {
        String tripId = viewModel.getCurrentTripIdValue();
        if (tripId == null || viewModel.currentTrip.getValue() == null) {
            return;
        }

        int memberCount = viewModel.currentTripMembers.getValue() != null ? 
                viewModel.currentTripMembers.getValue().size() : 0;
        
        long startDate = viewModel.currentTrip.getValue().getStartDateMillis();
        long endDate = viewModel.currentTrip.getValue().getEndDateMillis();
        
        String dateRange = formatDateRange(startDate, endDate);
        String subtitle = getString(R.string.trip_meta, memberCount, dateRange);
        
        binding.tripSubtitle.setText(subtitle);
    }

    private String formatDateRange(long startMillis, long endMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM", Locale.getDefault());
        String start = sdf.format(new Date(startMillis));
        String end = sdf.format(new Date(endMillis));
        return start + " - " + end;
    }

    private void showTripSelector() {
        BottomSheetDialog bottomSheet = new BottomSheetDialog(this);
        
        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        
        RecyclerView rvTrips = new RecyclerView(this);
        rvTrips.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        rvTrips.setLayoutManager(new LinearLayoutManager(this));
        
        tripSelectorAdapter = new TripSelectorAdapter(new ArrayList<>(), 
                viewModel.getCurrentTripIdValue(),
                tripId -> {
                    viewModel.setCurrentTrip(tripId);
                    bottomSheet.dismiss();
                }
        );
        
        rvTrips.setAdapter(tripSelectorAdapter);
        
        // Observe all trips
        viewModel.getAllTrips().observe(this, trips -> {
            List<Trip> mutableTrips = new ArrayList<>(trips);
            tripSelectorAdapter.setTrips(mutableTrips);
            tripSelectorAdapter.setCurrentTripId(viewModel.getCurrentTripIdValue());
        });
        
        container.addView(rvTrips);
        
        // Add "+ New Trip" button at bottom
        Button btnNewTrip = new Button(this);
        btnNewTrip.setText(R.string.new_trip);
        btnNewTrip.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        btnNewTrip.setOnClickListener(v -> {
            bottomSheet.dismiss();
            showCreateTripDialog();
        });
        container.addView(btnNewTrip);
        
        bottomSheet.setContentView(container);
        bottomSheet.show();
    }

    private void showTripMenu() {
        Trip currentTrip = viewModel.currentTrip.getValue();
        
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle(R.string.trip_name);
        
        String[] options;
        if (currentTrip == null) {
            options = new String[]{getString(R.string.new_trip)};
        } else {
            options = new String[]{
                getString(R.string.new_trip),
                getString(R.string.edit_trip),
                getString(R.string.manage_members),
                getString(R.string.delete_trip)
            };
        }
        
        builder.setItems(options, (dialog, which) -> {
            if (currentTrip == null) {
                if (which == 0) showCreateTripDialog();
            } else {
                switch (which) {
                    case 0: // New trip
                        showCreateTripDialog();
                        break;
                    case 1: // Edit trip
                        showEditTripDialog();
                        break;
                    case 2: // Manage members
                        showManageMembersDialog();
                        break;
                    case 3: // Delete trip
                        showDeleteTripConfirmDialog();
                        break;
                }
            }
        });
        
        builder.show();
    }

    private void showCreateTripDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle(R.string.create_trip);
        
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 40);
        
        EditText etTripName = new EditText(this);
        etTripName.setHint(R.string.trip_name);
        layout.addView(etTripName);
        
        EditText etStartDate = new EditText(this);
        etStartDate.setHint(R.string.start_date);
        etStartDate.setInputType(InputType.TYPE_NULL);
        etStartDate.setFocusable(false);
        layout.addView(etStartDate);
        
        EditText etEndDate = new EditText(this);
        etEndDate.setHint(R.string.end_date);
        etEndDate.setInputType(InputType.TYPE_NULL);
        etEndDate.setFocusable(false);
        layout.addView(etEndDate);
        
        final long[] startDateMillis = {System.currentTimeMillis()};
        final long[] endDateMillis = {System.currentTimeMillis() + 86400000}; // +1 day
        
        etStartDate.setOnClickListener(v -> showDatePicker(true, etStartDate, startDateMillis, etEndDate, endDateMillis));
        etEndDate.setOnClickListener(v -> showDatePicker(false, etEndDate, endDateMillis, etStartDate, startDateMillis));
        
        EditText etMembers = new EditText(this);
        etMembers.setHint("Member names (comma-separated)");
        etMembers.setText("You");
        layout.addView(etMembers);
        
        builder.setView(layout);
        builder.setPositiveButton(R.string.save, (dialog, which) -> {
            String tripName = etTripName.getText().toString().trim();
            String membersStr = etMembers.getText().toString().trim();
            
            if (tripName.isEmpty() || membersStr.isEmpty()) {
                return;
            }
            
            String[] memberNames = membersStr.split(",");
            for (int i = 0; i < memberNames.length; i++) {
                memberNames[i] = memberNames[i].trim();
            }
            
            viewModel.createTrip(tripName, startDateMillis[0], endDateMillis[0], memberNames);
        });
        
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }

    private void showEditTripDialog() {
        Trip currentTrip = viewModel.currentTrip.getValue();
        if (currentTrip == null) return;
        
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle(R.string.edit_trip);
        
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 40);
        
        EditText etTripName = new EditText(this);
        etTripName.setText(currentTrip.getName());
        etTripName.setHint(R.string.trip_name);
        layout.addView(etTripName);
        
        EditText etStartDate = new EditText(this);
        etStartDate.setText(formatDateRange(currentTrip.getStartDateMillis(), currentTrip.getStartDateMillis()).split(" - ")[0]);
        etStartDate.setInputType(InputType.TYPE_NULL);
        etStartDate.setFocusable(false);
        etStartDate.setHint(R.string.start_date);
        layout.addView(etStartDate);
        
        EditText etEndDate = new EditText(this);
        etEndDate.setText(formatDateRange(currentTrip.getEndDateMillis(), currentTrip.getEndDateMillis()).split(" - ")[0]);
        etEndDate.setInputType(InputType.TYPE_NULL);
        etEndDate.setFocusable(false);
        etEndDate.setHint(R.string.end_date);
        layout.addView(etEndDate);
        
        final long[] startDateMillis = {currentTrip.getStartDateMillis()};
        final long[] endDateMillis = {currentTrip.getEndDateMillis()};
        
        etStartDate.setOnClickListener(v -> showDatePicker(true, etStartDate, startDateMillis, etEndDate, endDateMillis));
        etEndDate.setOnClickListener(v -> showDatePicker(false, etEndDate, endDateMillis, etStartDate, startDateMillis));
        
        builder.setView(layout);
        builder.setPositiveButton(R.string.save, (dialog, which) -> {
            String newName = etTripName.getText().toString().trim();
            
            // Validate
            if (newName.isEmpty()) {
                etTripName.setError("Trip name cannot be empty");
                return;
            }
            
            if (endDateMillis[0] < startDateMillis[0]) {
                etEndDate.setError("End date must be after start date");
                return;
            }
            
            // Update trip object with both name and dates
            Trip updatedTrip = currentTrip;
            updatedTrip.setName(newName);
            updatedTrip.setStartDateMillis(startDateMillis[0]);
            updatedTrip.setEndDateMillis(endDateMillis[0]);
            viewModel.updateTrip(updatedTrip);
        });
        
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }

    private void showManageMembersDialog() {
        Trip currentTrip = viewModel.currentTrip.getValue();
        if (currentTrip == null) return;
        
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle(R.string.manage_members);
        
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(20, 40, 20, 40);
        
        RecyclerView rvMembers = new RecyclerView(this);
        rvMembers.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        rvMembers.setLayoutManager(new LinearLayoutManager(this));
        
        final MemberAdapter[] adapter = {null};
        
        layout.addView(rvMembers);
        
        builder.setView(layout);
        builder.setPositiveButton("Add Member", (dialog, which) -> {
            showAddMemberDialog(currentTrip.getId());
        });
        
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        
        // Observe members and populate adapter
        viewModel.currentTripMembers.observe(this, members -> {
            if (adapter[0] == null) {
                adapter[0] = new MemberAdapter(new ArrayList<>(members), 
                    new MemberAdapter.OnMemberActionListener() {
                        @Override
                        public void onMemberRenamed(Member member, String newName) {
                            member.setName(newName);
                            viewModel.updateMemberName(member.getId(), newName);
                        }

                        @Override
                        public void onMemberRemoved(Member member) {
                            viewModel.removeMemberFromTrip(member.getId());
                        }
                    }, 
                    MainActivity.this
                );
                rvMembers.setAdapter(adapter[0]);
            } else {
                adapter[0].notifyDataSetChanged();
            }
        });
    }

    private void showAddMemberDialog(String tripId) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Add Member");
        
        EditText etMemberName = new EditText(this);
        etMemberName.setHint("Member name");
        etMemberName.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(etMemberName);
        
        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = etMemberName.getText().toString().trim();
            if (!name.isEmpty()) {
                viewModel.addMemberToTrip(tripId, name);
            }
        });
        
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showDeleteTripConfirmDialog() {
        Trip currentTrip = viewModel.currentTrip.getValue();
        if (currentTrip == null) return;
        
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle(R.string.delete_trip);
        builder.setMessage(R.string.confirm_delete_trip);
        builder.setPositiveButton(R.string.delete_trip, (dialog, which) -> {
            viewModel.deleteTrip(currentTrip.getId());
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }

    private void showDatePicker(boolean isStartDate, EditText etDisplay, long[] selectedDate, EditText etOtherDate, long[] otherDate) {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setSelection(selectedDate[0])
                .build();
        
        datePicker.addOnPositiveButtonClickListener(selection -> {
            selectedDate[0] = selection;
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            etDisplay.setText(sdf.format(new Date(selectedDate[0])));
        });
        
        datePicker.show(getSupportFragmentManager(), null);
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}

