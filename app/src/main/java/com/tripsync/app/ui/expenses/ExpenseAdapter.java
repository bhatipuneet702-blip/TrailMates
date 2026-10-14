package com.tripsync.app.ui.expenses;

import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.tripsync.app.R;
import com.tripsync.app.databinding.ItemExpenseBinding;
import com.tripsync.app.db.AppDatabase;
import com.tripsync.app.db.dao.MemberDao;
import com.tripsync.app.db.entities.Expense;
import com.tripsync.app.db.entities.Member;
import com.tripsync.app.util.BalanceCalculator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExpenseAdapter extends ListAdapter<Expense, ExpenseAdapter.ExpenseViewHolder> {

    private final OnExpenseClickListener listener;
    private final MemberDao memberDao;
    private final AppDatabase database;
    private final int[] avatarColors;

    public interface OnExpenseClickListener {
        void onExpenseClick(Expense expense);
        void onExpenseLongClick(Expense expense);
    }

    public ExpenseAdapter(OnExpenseClickListener listener, MemberDao memberDao, AppDatabase database) {
        super(new DiffUtil.ItemCallback<Expense>() {
            @Override
            public boolean areItemsTheSame(@NonNull Expense oldItem, @NonNull Expense newItem) {
                return oldItem.getId().equals(newItem.getId());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Expense oldItem, @NonNull Expense newItem) {
                return oldItem.getAmountInPaise() == newItem.getAmountInPaise() &&
                       oldItem.getTitle().equals(newItem.getTitle()) &&
                       oldItem.getPayerMemberId().equals(newItem.getPayerMemberId());
            }
        });
        this.listener = listener;
        this.memberDao = memberDao;
        this.database = database;
        // Avatar colors for participants (max 4)
        this.avatarColors = new int[]{
            0xFF3DAA6D, // green
            0xFF9B59B6, // purple
            0xFFE74C3C, // red
            0xFFF28C28  // orange
        };
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
        holder.bind(getItem(position));
    }

    class ExpenseViewHolder extends RecyclerView.ViewHolder {
        private final ItemExpenseBinding binding;

        public ExpenseViewHolder(@NonNull ItemExpenseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Expense expense) {
            binding.tvTitle.setText(expense.getTitle());
            
            // Get payer member name
            try {
                Member payerMember = memberDao.getMemberByIdSync(expense.getPayerMemberId());
                String payerName = payerMember != null ? payerMember.getName() : "Unknown";
                binding.tvPaidBy.setText(binding.getRoot().getContext()
                        .getString(R.string.paid_by, payerName));
            } catch (Exception e) {
                Log.e("TripSync", "Error getting payer: " + e.getMessage());
                binding.tvPaidBy.setText(binding.getRoot().getContext()
                        .getString(R.string.paid_by, "Unknown"));
            }
            
            // Format amount using BalanceCalculator
            String amount = BalanceCalculator.formatIndianCurrency(expense.getAmountInPaise());
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
            
            // Populate participant avatars (max 4 + "+N" if more)
            populateParticipantAvatars(expense);
            
            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) listener.onExpenseClick(expense);
            });
            
            binding.getRoot().setOnLongClickListener(v -> {
                if (listener != null) listener.onExpenseLongClick(expense);
                return true;
            });
        }

        private void populateParticipantAvatars(Expense expense) {
            binding.avatarsContainer.removeAllViews();
            
            try {
                List<String> participantIds = database.expenseDao().getParticipantIdsForExpenseSync(expense.getId());
                if (participantIds == null) {
                    participantIds = new ArrayList<>();
                }
                
                int maxAvatars = 4;
                int displayCount = Math.min(participantIds.size(), maxAvatars);
                
                // Add avatar TextViews for first 4 participants
                for (int i = 0; i < displayCount; i++) {
                    TextView avatar = createAvatarView(participantIds.get(i), i);
                    binding.avatarsContainer.addView(avatar);
                }
                
                // Add "+N" avatar if more than 4 participants
                if (participantIds.size() > maxAvatars) {
                    TextView plusAvatar = createPlusAvatar(participantIds.size() - maxAvatars);
                    binding.avatarsContainer.addView(plusAvatar);
                }
            } catch (Exception e) {
                Log.e("TripSync", "Error populating avatars: " + e.getMessage());
            }
        }

        private TextView createAvatarView(String memberId, int index) {
            TextView avatar = new TextView(binding.getRoot().getContext());
            
            float density = binding.getRoot().getContext().getResources().getDisplayMetrics().density;
            int size = (int) (24 * density);
            
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            params.setMarginStart((int) (-8 * density)); // Negative margin for overlap
            if (index == 0) params.setMarginStart(0);
            avatar.setLayoutParams(params);
            
            try {
                Member member = memberDao.getMemberByIdSync(memberId);
                String initial = member != null && !member.getName().isEmpty() 
                    ? member.getName().substring(0, 1).toUpperCase()
                    : "?";
                
                avatar.setText(initial);
                avatar.setTextSize(12);
                avatar.setTextColor(0xFFFFFFFF); // White text
                avatar.setGravity(android.view.Gravity.CENTER);
                
                // Create circular background
                GradientDrawable drawable = new GradientDrawable();
                drawable.setShape(GradientDrawable.OVAL);
                drawable.setColor(avatarColors[index % avatarColors.length]);
                avatar.setBackground(drawable);
            } catch (Exception e) {
                Log.e("TripSync", "Error creating avatar: " + e.getMessage());
                avatar.setText("?");
                GradientDrawable drawable = new GradientDrawable();
                drawable.setShape(GradientDrawable.OVAL);
                drawable.setColor(0xFFCCCCCC);
                avatar.setBackground(drawable);
            }
            
            return avatar;
        }

        private TextView createPlusAvatar(int count) {
            TextView avatar = new TextView(binding.getRoot().getContext());
            
            float density = binding.getRoot().getContext().getResources().getDisplayMetrics().density;
            int size = (int) (24 * density);
            
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            params.setMarginStart((int) (-8 * density));
            avatar.setLayoutParams(params);
            
            avatar.setText("+" + count);
            avatar.setTextSize(10);
            avatar.setTextColor(0xFF666666);
            avatar.setGravity(android.view.Gravity.CENTER);
            
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.OVAL);
            drawable.setColor(0xFFEEEEEE);
            avatar.setBackground(drawable);
            
            return avatar;
        }

        private int getCategoryIcon(String categoryType) {
            if (categoryType == null) return R.drawable.ic_category_food;
            switch (categoryType) {
                case "food": return R.drawable.ic_restaurant;
                case "transport": return R.drawable.ic_directions_bus;
                case "hotel": return R.drawable.ic_hotel;
                case "fuel": return R.drawable.ic_local_gas_station;
                case "shopping": return R.drawable.ic_shopping_bag;
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
