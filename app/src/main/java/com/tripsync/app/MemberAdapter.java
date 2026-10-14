package com.tripsync.app;

import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.tripsync.app.databinding.ItemMemberBinding;
import com.tripsync.app.db.entities.Member;
import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {
    private final List<Member> members;
    private final OnMemberActionListener listener;
    private final Context context;

    public interface OnMemberActionListener {
        void onMemberRenamed(Member member, String newName);
        void onMemberRemoved(Member member);
    }

    public MemberAdapter(List<Member> members, OnMemberActionListener listener, Context context) {
        this.members = members;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMemberBinding binding = ItemMemberBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new MemberViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        holder.bind(members.get(position));
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder {
        private final ItemMemberBinding binding;

        public MemberViewHolder(ItemMemberBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Member member) {
            binding.tvMemberName.setText(member.getName());
            
            if (member.isCurrentUser()) {
                binding.tvMemberRole.setText("(You)");
            } else {
                binding.tvMemberRole.setText("");
            }
            
            binding.btnEdit.setOnClickListener(v -> showRenameMemberDialog(member));
            
            if (member.isCurrentUser()) {
                binding.btnRemove.setEnabled(false);
                binding.btnRemove.setAlpha(0.5f);
            } else {
                binding.btnRemove.setEnabled(true);
                binding.btnRemove.setAlpha(1f);
                binding.btnRemove.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onMemberRemoved(member);
                    }
                });
            }
        }

        private void showRenameMemberDialog(Member member) {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
            builder.setTitle("Rename Member");
            
            EditText etName = new EditText(context);
            etName.setText(member.getName());
            etName.setInputType(InputType.TYPE_CLASS_TEXT);
            etName.selectAll();
            builder.setView(etName);
            
            builder.setPositiveButton("Save", (dialog, which) -> {
                String newName = etName.getText().toString().trim();
                if (!newName.isEmpty() && listener != null) {
                    listener.onMemberRenamed(member, newName);
                }
            });
            
            builder.setNegativeButton("Cancel", null);
            builder.show();
        }
    }
}
