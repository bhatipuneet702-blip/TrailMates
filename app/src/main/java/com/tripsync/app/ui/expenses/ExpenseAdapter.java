package com.tripsync.app.ui.expenses;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.tripsync.app.R;
import com.tripsync.app.databinding.ItemExpenseBinding;
import com.tripsync.app.db.entities.Expense;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private final List<Expense> expenseList;
    private final OnExpenseClickListener listener;

    public interface OnExpenseClickListener {
        void onExpenseClick(Expense expense);
        void onExpenseLongClick(Expense expense);
    }

    public ExpenseAdapter(List<Expense> expenseList, OnExpenseClickListener listener) {
        this.expenseList = expenseList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemExpenseBinding binding = ItemExpenseBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ExpenseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        holder.bind(expenseList.get(position));
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    class ExpenseViewHolder extends RecyclerView.ViewHolder {
        private final ItemExpenseBinding binding;

        public ExpenseViewHolder(@NonNull ItemExpenseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Expense expense) {
            binding.tvTitle.setText(expense.getTitle());
            binding.tvPaidBy.setText(
                    binding.getRoot().getContext().getString(R.string.paid_by, "Member")); // TODO: lookup member name
            
            // Format amount in paise to rupees with Indian number format
            long rupees = expense.getAmountInPaise() / 100;
            long paise = expense.getAmountInPaise() % 100;
            String amount = String.format(Locale.getDefault(), "₹%d", rupees);
            binding.tvAmount.setText(amount);
            
            // Format date
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM", Locale.getDefault());
            binding.tvDate.setText(sdf.format(new Date(expense.getDateMillis())));
            
            // Set category icon and color
            int categoryIcon = getCategoryIcon(expense.getCategoryType());
            int categoryColor = getCategoryColor(expense.getCategoryType());
            binding.categoryIcon.setImageResource(categoryIcon);
            binding.iconBackground.setBackgroundTintList(
                    ContextCompat.getColorStateList(binding.getRoot().getContext(), categoryColor));
            
            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) listener.onExpenseClick(expense);
            });
            
            binding.getRoot().setOnLongClickListener(v -> {
                if (listener != null) listener.onExpenseLongClick(expense);
                return true;
            });
        }

        private int getCategoryIcon(String categoryType) {
            if (categoryType == null) return R.drawable.ic_category_food;
            switch (categoryType) {
                case "food": return R.drawable.ic_category_food;
                case "transport": return R.drawable.ic_category_transport;
                case "hotel": return R.drawable.ic_category_hotel;
                case "fuel": return R.drawable.ic_category_fuel;
                default: return R.drawable.ic_category_food;
            }
        }

        private int getCategoryColor(String categoryType) {
            if (categoryType == null) return R.color.accent_food_light;
            switch (categoryType) {
                case "food": return R.color.accent_food_light;
                case "transport": return R.color.accent_transport_light;
                case "hotel": return R.color.accent_hotel_light;
                case "fuel": return R.color.accent_fuel_light;
                default: return R.color.accent_food_light;
            }
        }
    }
}
