package com.tripsync.app.ui.expenses;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;
import com.tripsync.app.databinding.FragmentExpensesBinding;
import com.tripsync.app.db.entities.Expense;
import com.tripsync.app.db.entities.Member;
import com.tripsync.app.viewmodel.AppViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExpensesFragment extends Fragment {

    private FragmentExpensesBinding binding;
    private AppViewModel viewModel;
    private ExpenseAdapter adapter;
    private final List<Expense> expenseList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentExpensesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        adapter = new ExpenseAdapter(expenseList, new ExpenseAdapter.OnExpenseClickListener() {
            @Override
            public void onExpenseClick(Expense expense) {
                // TODO: Edit expense
            }

            @Override
            public void onExpenseLongClick(Expense expense) {
                // TODO: Delete with confirmation
            }
        });

        binding.rvExpenses.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvExpenses.setAdapter(adapter);

        binding.btnAddExpense.setOnClickListener(v -> showAddExpenseDialog());

        // Observe expenses from ViewModel
        viewModel.currentTripExpenses.observe(getViewLifecycleOwner(), expenses -> {
            expenseList.clear();
            expenseList.addAll(expenses);
            adapter.notifyDataSetChanged();
            updateEmptyState();
        });

        // Disable/enable Add Expense button based on current trip
        viewModel.currentTrip.observe(getViewLifecycleOwner(), trip -> {
            binding.btnAddExpense.setEnabled(trip != null);
            if (trip == null) {
                binding.btnAddExpense.setAlpha(0.5f);
            } else {
                binding.btnAddExpense.setAlpha(1.0f);
            }
        });

        updateEmptyState();
    }

    private void showAddExpenseDialog() {
        String tripId = viewModel.getCurrentTripIdValue();
        if (tripId == null) {
            // Show Snackbar to create a trip first
            com.google.android.material.snackbar.Snackbar.make(
                    binding.getRoot(),
                    "Create a trip first",
                    com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
            ).show();
            return;
        }

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(requireContext());
        builder.setTitle("Add Expense");

        android.widget.LinearLayout layout = new android.widget.LinearLayout(requireContext());
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 40);

        // Title
        android.widget.EditText etTitle = new android.widget.EditText(requireContext());
        etTitle.setHint("Expense title");
        etTitle.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
        layout.addView(etTitle);

        // Amount (in rupees)
        android.widget.EditText etAmount = new android.widget.EditText(requireContext());
        etAmount.setHint("Amount (₹)");
        etAmount.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        layout.addView(etAmount);

        // Category dropdown
        android.widget.Spinner spinnerCategory = new android.widget.Spinner(requireContext());
        String[] categories = {"Food", "Transport", "Hotel", "Fuel", "Other"};
        android.widget.ArrayAdapter<String> categoryAdapter = new android.widget.ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categories
        );
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);
        layout.addView(spinnerCategory);

        // Date picker
        android.widget.EditText etDate = new android.widget.EditText(requireContext());
        etDate.setHint("Date");
        etDate.setInputType(android.text.InputType.TYPE_NULL);
        etDate.setFocusable(false);
        final long[] selectedDate = {System.currentTimeMillis()};
        etDate.setOnClickListener(v -> showDatePickerForExpense(etDate, selectedDate));
        layout.addView(etDate);

        // Paid by dropdown
        android.widget.Spinner spinnerPaidBy = new android.widget.Spinner(requireContext());
        layout.addView(spinnerPaidBy);

        // Participants (chips)
        android.widget.HorizontalScrollView scrollParticipants = new android.widget.HorizontalScrollView(requireContext());
        com.google.android.material.chip.ChipGroup chipGroup = new com.google.android.material.chip.ChipGroup(requireContext());
        chipGroup.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        scrollParticipants.addView(chipGroup);
        layout.addView(scrollParticipants);

        // Split toggle
        android.widget.CheckBox cbEqualSplit = new android.widget.CheckBox(requireContext());
        cbEqualSplit.setText("Equal split");
        cbEqualSplit.setChecked(true);
        layout.addView(cbEqualSplit);

        builder.setView(layout);

        // Observe members and populate dropdowns
        viewModel.currentTripMembers.observe(getViewLifecycleOwner(), members -> {
            if (members != null && !members.isEmpty()) {
                String[] memberNames = new String[members.size()];
                for (int i = 0; i < members.size(); i++) {
                    memberNames[i] = members.get(i).getName();
                }
                
                android.widget.ArrayAdapter<String> memberAdapter = new android.widget.ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        memberNames
                );
                memberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerPaidBy.setAdapter(memberAdapter);

                // Populate participant chips
                chipGroup.removeAllViews();
                for (int i = 0; i < members.size(); i++) {
                    com.google.android.material.chip.Chip chip = new com.google.android.material.chip.Chip(requireContext());
                    chip.setText(members.get(i).getName());
                    chip.setCheckable(true);
                    chip.setChecked(true);
                    chipGroup.addView(chip);
                }
            }
        });

        builder.setPositiveButton("Save", (dialog, which) -> {
            String title = etTitle.getText().toString().trim();
            String amountStr = etAmount.getText().toString().trim();
            int categoryIndex = spinnerCategory.getSelectedItemPosition();
            int payerIndex = spinnerPaidBy.getSelectedItemPosition();

            // Validation
            if (title.isEmpty()) {
                etTitle.setError("Title cannot be empty");
                return;
            }
            if (amountStr.isEmpty()) {
                etAmount.setError("Amount cannot be empty");
                return;
            }

            try {
                double amount = Double.parseDouble(amountStr);
                if (amount <= 0) {
                    etAmount.setError("Amount must be greater than 0");
                    return;
                }
                long amountInPaise = (long) (amount * 100);

                // Get category
                String[] categoryTypes = {"food", "transport", "hotel", "fuel", "other"};
                String categoryType = categoryTypes[categoryIndex];

                // Get payer member ID
                List<Member> members = viewModel.currentTripMembers.getValue();
                if (members == null || members.isEmpty()) {
                    return;
                }
                String payerMemberId = members.get(payerIndex).getId();

                // Get participant IDs
                java.util.List<String> participantIds = new java.util.ArrayList<>();
                for (int i = 0; i < chipGroup.getChildCount(); i++) {
                    com.google.android.material.chip.Chip chip = (com.google.android.material.chip.Chip) chipGroup.getChildAt(i);
                    if (chip.isChecked()) {
                        participantIds.add(members.get(i).getId());
                    }
                }

                // Create expense
                viewModel.createExpense(title, categoryType, amountInPaise, payerMemberId, selectedDate[0], participantIds);

            } catch (NumberFormatException e) {
                etAmount.setError("Invalid amount");
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showDatePickerForExpense(android.widget.EditText etDate, long[] selectedDate) {
        com.google.android.material.datepicker.MaterialDatePicker<Long> datePicker = 
                com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker()
                .setSelection(selectedDate[0])
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            selectedDate[0] = selection;
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd MMM yyyy", java.util.Locale.getDefault());
            etDate.setText(sdf.format(new java.util.Date(selectedDate[0])));
        });

        datePicker.show(getChildFragmentManager(), null);
    }

    private void updateEmptyState() {
        if (expenseList.isEmpty()) {
            binding.tvEmptyExpenses.setVisibility(View.VISIBLE);
            binding.rvExpenses.setVisibility(View.GONE);
        } else {
            binding.tvEmptyExpenses.setVisibility(View.GONE);
            binding.rvExpenses.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
