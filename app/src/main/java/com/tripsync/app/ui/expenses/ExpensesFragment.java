package com.tripsync.app.ui.expenses;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import com.tripsync.app.R;
import com.tripsync.app.databinding.FragmentExpensesBinding;
import com.tripsync.app.db.AppDatabase;
import com.tripsync.app.db.entities.Expense;
import com.tripsync.app.db.entities.Member;
import com.tripsync.app.util.BalanceCalculator;
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
    private Expense deletedExpense;
    private List<String> deletedParticipants;

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

        // Create adapter with member lookup capability
        adapter = new ExpenseAdapter(
            new ExpenseAdapter.OnExpenseClickListener() {
                @Override
                public void onExpenseClick(Expense expense) {
                    showEditExpenseDialog(expense);
                }

                @Override
                public void onExpenseLongClick(Expense expense) {
                    showDeleteConfirmDialog(expense);
                }
            },
            AppDatabase.getInstance(requireContext()).memberDao(),
            AppDatabase.getInstance(requireContext())
        );

        binding.rvExpenses.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvExpenses.setAdapter(adapter);

        binding.btnAddExpense.setOnClickListener(v -> showAddExpenseDialog());
        
        // Handle "See All" link
        binding.tvSeeAll.setOnClickListener(v -> showAllExpensesBottomSheet());

        // Observe expenses and update adapter with DiffUtil animation
        viewModel.currentTripExpenses.observe(getViewLifecycleOwner(), expenses -> {
            if (expenses != null) {
                // Show only recent 5 expenses
                List<Expense> recentExpenses = new ArrayList<>();
                int limit = Math.min(5, expenses.size());
                for (int i = 0; i < limit; i++) {
                    recentExpenses.add(expenses.get(i));
                }
                adapter.submitList(new ArrayList<>(recentExpenses)); // Create new list to trigger DiffUtil
                updateEmptyState();
            }
        });

        // Observe total and balance
        viewModel.currentTripTotalExpenses.observe(getViewLifecycleOwner(), total -> {
            String totalStr = BalanceCalculator.formatIndianCurrency(total != null ? total : 0);
            binding.tvTotalAmount.setText(totalStr);
        });

        viewModel.currentTripYourBalance.observe(getViewLifecycleOwner(), balance -> {
            if (balance != null) {
                updateBalanceCard(balance);
            }
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

    private void updateBalanceCard(long balancePaise) {
        String amountStr;
        String captionStr;
        int color;

        if (balancePaise > 0) {
            amountStr = "+ " + BalanceCalculator.formatIndianCurrency(balancePaise);
            captionStr = getString(R.string.you_should_receive);
            color = R.color.success_green;
        } else if (balancePaise < 0) {
            amountStr = "- " + BalanceCalculator.formatIndianCurrency(Math.abs(balancePaise));
            captionStr = getString(R.string.you_owe);
            color = R.color.error_red;
        } else {
            amountStr = BalanceCalculator.formatIndianCurrency(0);
            captionStr = getString(R.string.all_settled);
            color = R.color.text_secondary;
        }

        binding.tvBalanceAmount.setText(amountStr);
        binding.tvBalanceAmount.setTextColor(requireContext().getColor(color));
        binding.tvBalanceCaption.setText(captionStr);
    }

    private void showAddExpenseDialog() {
        String tripId = viewModel.getCurrentTripIdValue();
        if (tripId == null) {
            Snackbar.make(binding.getRoot(), "Create a trip first", Snackbar.LENGTH_SHORT).show();
            return;
        }

        showExpenseDialog(null, tripId);
    }

    private void showEditExpenseDialog(Expense expense) {
        showExpenseDialog(expense, expense.getTripId());
    }

    private void showExpenseDialog(@Nullable Expense editExpense, String tripId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(editExpense == null ? "Add Expense" : "Edit Expense");

        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 40);

        // Title
        EditText etTitle = new EditText(requireContext());
        etTitle.setHint("Expense title");
        etTitle.setInputType(InputType.TYPE_CLASS_TEXT);
        if (editExpense != null) etTitle.setText(editExpense.getTitle());
        layout.addView(etTitle);

        // Amount
        EditText etAmount = new EditText(requireContext());
        etAmount.setHint("Amount (₹)");
        etAmount.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        if (editExpense != null) {
            double rupees = BalanceCalculator.paiseToRupees(editExpense.getAmountInPaise());
            etAmount.setText(String.format(Locale.getDefault(), "%.2f", rupees));
        }
        layout.addView(etAmount);

        // Category
        Spinner spinnerCategory = new Spinner(requireContext());
        String[] categories = {"Food", "Transport", "Hotel", "Fuel", "Shopping", "Other"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
            requireContext(), android.R.layout.simple_spinner_item, categories
        );
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);
        if (editExpense != null) {
            int index = getCategoryIndex(editExpense.getCategoryType());
            spinnerCategory.setSelection(index);
        }
        layout.addView(spinnerCategory);

        // Date
        EditText etDate = new EditText(requireContext());
        etDate.setHint("Date");
        etDate.setInputType(InputType.TYPE_NULL);
        etDate.setFocusable(false);
        final long[] selectedDate = {editExpense != null ? editExpense.getDateMillis() : System.currentTimeMillis()};
        etDate.setText(formatDate(selectedDate[0]));
        etDate.setOnClickListener(v -> showDatePickerForExpense(etDate, selectedDate));
        layout.addView(etDate);

        // Paid By
        Spinner spinnerPaidBy = new Spinner(requireContext());
        layout.addView(spinnerPaidBy);

        // Participants
        ChipGroup chipGroup = new ChipGroup(requireContext());
        layout.addView(chipGroup);

        builder.setView(layout);

        // Observe members and populate dropdowns
        viewModel.currentTripMembers.observe(getViewLifecycleOwner(), members -> {
            if (members != null && !members.isEmpty()) {
                String[] memberNames = new String[members.size()];
                for (int i = 0; i < members.size(); i++) {
                    memberNames[i] = members.get(i).getName();
                }

                ArrayAdapter<String> memberAdapter = new ArrayAdapter<>(
                    requireContext(), android.R.layout.simple_spinner_item, memberNames
                );
                memberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerPaidBy.setAdapter(memberAdapter);

                if (editExpense != null) {
                    for (int i = 0; i < members.size(); i++) {
                        if (members.get(i).getId().equals(editExpense.getPayerMemberId())) {
                            spinnerPaidBy.setSelection(i);
                            break;
                        }
                    }
                } else {
                    // Default to current user
                    for (int i = 0; i < members.size(); i++) {
                        if (members.get(i).isCurrentUser()) {
                            spinnerPaidBy.setSelection(i);
                            break;
                        }
                    }
                }

                // Populate participants
                chipGroup.removeAllViews();
                if (editExpense != null) {
                    List<String> participantIds = AppDatabase.getInstance(requireContext())
                        .expenseDao().getParticipantIdsForExpenseSync(editExpense.getId());
                    for (int i = 0; i < members.size(); i++) {
                        Chip chip = new Chip(requireContext());
                        chip.setText(members.get(i).getName());
                        chip.setCheckable(true);
                        chip.setChecked(participantIds != null && participantIds.contains(members.get(i).getId()));
                        chipGroup.addView(chip);
                    }
                } else {
                    for (Member member : members) {
                        Chip chip = new Chip(requireContext());
                        chip.setText(member.getName());
                        chip.setCheckable(true);
                        chip.setChecked(true);
                        chipGroup.addView(chip);
                    }
                }
            }
        });

        builder.setPositiveButton("Save", (dialog, which) -> {
            String title = etTitle.getText().toString().trim();
            String amountStr = etAmount.getText().toString().trim();
            int categoryIndex = spinnerCategory.getSelectedItemPosition();
            int payerIndex = spinnerPaidBy.getSelectedItemPosition();

            if (title.isEmpty()) {
                etTitle.setError("Title cannot be empty");
                return;
            }
            if (amountStr.isEmpty()) {
                etAmount.setError("Amount cannot be empty");
                return;
            }

            try {
                double rupees = Double.parseDouble(amountStr);
                if (rupees <= 0) {
                    etAmount.setError("Amount must be greater than 0");
                    return;
                }

                long amountInPaise = BalanceCalculator.rupeesToPaise(rupees);
                String[] categoryTypes = {"food", "transport", "hotel", "fuel", "shopping", "other"};
                String categoryType = categoryTypes[categoryIndex];

                List<Member> members = viewModel.currentTripMembers.getValue();
                if (members == null || members.isEmpty()) return;

                String payerMemberId = members.get(payerIndex).getId();

                // Get participant IDs
                List<String> participantIds = new ArrayList<>();
                for (int i = 0; i < chipGroup.getChildCount(); i++) {
                    Chip chip = (Chip) chipGroup.getChildAt(i);
                    if (chip.isChecked()) {
                        participantIds.add(members.get(i).getId());
                    }
                }

                if (participantIds.isEmpty()) {
                    Snackbar.make(binding.getRoot(), "Select at least 1 participant", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (editExpense != null) {
                    viewModel.updateExpense(editExpense.getId(), title, categoryType, amountInPaise, payerMemberId, selectedDate[0], participantIds);
                } else {
                    viewModel.createExpense(title, categoryType, amountInPaise, payerMemberId, selectedDate[0], participantIds);
                }
            } catch (NumberFormatException e) {
                etAmount.setError("Invalid amount");
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showDeleteConfirmDialog(Expense expense) {
        new AlertDialog.Builder(requireContext())
            .setTitle("Delete Expense")
            .setMessage("Are you sure?")
            .setPositiveButton("Delete", (dialog, which) -> {
                // Back up expense data for undo
                deletedExpense = expense;
                deletedParticipants = AppDatabase.getInstance(requireContext())
                    .expenseDao().getParticipantIdsForExpenseSync(expense.getId());

                viewModel.deleteExpense(expense.getId());

                Snackbar.make(binding.getRoot(), "Expense deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo", v -> {
                        if (deletedExpense != null) {
                            viewModel.createExpense(
                                deletedExpense.getTitle(),
                                deletedExpense.getCategoryType(),
                                deletedExpense.getAmountInPaise(),
                                deletedExpense.getPayerMemberId(),
                                deletedExpense.getDateMillis(),
                                deletedParticipants != null ? deletedParticipants : new ArrayList<>()
                            );
                            deletedExpense = null;
                            deletedParticipants = null;
                        }
                    })
                    .show();
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    private void showDatePickerForExpense(EditText etDate, long[] selectedDate) {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
            .setSelection(selectedDate[0])
            .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            selectedDate[0] = selection;
            etDate.setText(formatDate(selectedDate[0]));
        });

        datePicker.show(getChildFragmentManager(), null);
    }

    private String formatDate(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        return sdf.format(new Date(millis));
    }

    private int getCategoryIndex(String categoryType) {
        String[] types = {"food", "transport", "hotel", "fuel", "shopping", "other"};
        for (int i = 0; i < types.length; i++) {
            if (types[i].equals(categoryType)) return i;
        }
        return 0;
    }

    private void updateEmptyState() {
        if (adapter.getItemCount() == 0) {
            binding.tvEmptyExpenses.setVisibility(View.VISIBLE);
            binding.rvExpenses.setVisibility(View.GONE);
        } else {
            binding.tvEmptyExpenses.setVisibility(View.GONE);
            binding.rvExpenses.setVisibility(View.VISIBLE);
        }
    }

    private void showAllExpensesBottomSheet() {
        String tripId = viewModel.getCurrentTripIdValue();
        if (tripId == null) return;

        // Create a new dialog/bottom sheet for all expenses
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("All Expenses");
        
        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        
        androidx.recyclerview.widget.RecyclerView rv = new androidx.recyclerview.widget.RecyclerView(requireContext());
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        ExpenseAdapter allExpensesAdapter = new ExpenseAdapter(
            new ExpenseAdapter.OnExpenseClickListener() {
                @Override
                public void onExpenseClick(Expense expense) {
                    showEditExpenseDialog(expense);
                }

                @Override
                public void onExpenseLongClick(Expense expense) {
                    showDeleteConfirmDialog(expense);
                }
            },
            AppDatabase.getInstance(requireContext()).memberDao(),
            AppDatabase.getInstance(requireContext())
        );
        rv.setAdapter(allExpensesAdapter);
        layout.addView(rv);
        
        builder.setView(layout);
        builder.setNegativeButton("Close", null);
        
        AlertDialog dialog = builder.show();
        
        // Load all expenses for the trip
        viewModel.currentTripExpenses.observe(getViewLifecycleOwner(), expenses -> {
            if (expenses != null) {
                allExpensesAdapter.submitList(new ArrayList<>(expenses));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
