package com.tripsync.app.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "expense_participants",
        primaryKeys = {"expenseId", "memberId"},
        foreignKeys = {
                @ForeignKey(entity = Expense.class,
                        parentColumns = "id",
                        childColumns = "expenseId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Member.class,
                        parentColumns = "id",
                        childColumns = "memberId",
                        onDelete = ForeignKey.CASCADE)
        })
public class ExpenseParticipant {
    @NonNull
    public String expenseId;
    @NonNull
    public String memberId;

    public ExpenseParticipant(String expenseId, String memberId) {
        this.expenseId = expenseId;
        this.memberId = memberId;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public String getMemberId() {
        return memberId;
    }
}
