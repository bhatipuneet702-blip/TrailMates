package com.tripsync.app.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.tripsync.app.db.AppDatabase;
import com.tripsync.app.db.dao.ExpenseDao;
import com.tripsync.app.db.dao.MemberDao;
import com.tripsync.app.db.entities.Expense;
import com.tripsync.app.db.entities.ExpenseParticipant;
import com.tripsync.app.db.entities.Member;
import com.tripsync.app.util.BalanceCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExpenseRepository {
    private final ExpenseDao expenseDao;
    private final MemberDao memberDao;
    private final AppDatabase db;

    public ExpenseRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        this.db = database;
        this.expenseDao = database.expenseDao();
        this.memberDao = database.memberDao();
    }

    public LiveData<List<Expense>> getExpensesForTrip(String tripId) {
        return expenseDao.getExpensesForTrip(tripId);
    }

    public LiveData<Long> getTotalExpensesForTrip(String tripId) {
        return expenseDao.getTotalExpensesForTrip(tripId);
    }

    public void createExpense(String tripId, String title, String categoryType, long amountInPaise,
                              String payerMemberId, long dateMillis, List<String> participantIds) {
        new Thread(() -> {
            try {
                String expenseId = UUID.randomUUID().toString();
                Expense expense = new Expense(expenseId, tripId, title, categoryType, amountInPaise, payerMemberId, dateMillis);
                
                // Use transaction for atomicity
                db.beginTransaction();
                try {
                    expenseDao.insertExpense(expense);
                    for (String participantId : participantIds) {
                        ExpenseParticipant participant = new ExpenseParticipant(expenseId, participantId);
                        expenseDao.insertParticipant(participant);
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
            } catch (Exception e) {
                Log.e("TripSync", "Error creating expense: " + e.getMessage());
            }
        }).start();
    }

    public void updateExpense(String expenseId, String title, String categoryType, long amountInPaise,
                              String payerMemberId, long dateMillis, List<String> participantIds) {
        new Thread(() -> {
            try {
                Expense expense = expenseDao.getExpenseByIdSync(expenseId);
                if (expense != null) {
                    expense.setTitle(title);
                    expense.setCategoryType(categoryType);
                    expense.setAmountInPaise(amountInPaise);
                    expense.setPayerMemberId(payerMemberId);
                    expense.setDateMillis(dateMillis);
                    
                    // Use transaction
                    db.beginTransaction();
                    try {
                        expenseDao.updateExpense(expense);
                        expenseDao.deleteParticipantsForExpense(expenseId);
                        for (String participantId : participantIds) {
                            ExpenseParticipant participant = new ExpenseParticipant(expenseId, participantId);
                            expenseDao.insertParticipant(participant);
                        }
                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                    }
                }
            } catch (Exception e) {
                Log.e("TripSync", "Error updating expense: " + e.getMessage());
            }
        }).start();
    }

    public void deleteExpense(String expenseId) {
        new Thread(() -> {
            Expense expense = expenseDao.getExpenseByIdSync(expenseId);
            if (expense != null) {
                expenseDao.deleteParticipantsForExpense(expenseId);
                expenseDao.deleteExpense(expense);
            }
        }).start();
    }

    public List<String> getParticipantIdsForExpense(String expenseId) {
        return expenseDao.getParticipantIdsForExpense(expenseId);
    }

    public long calculateYourBalance(String tripId, String currentUserId) {
        // Get all expenses
        List<Expense> expenses = expenseDao.getExpensesForTripSync(tripId);
        List<Member> members = memberDao.getMembersForTripSync(tripId);

        long totalPaid = 0;
        long totalShare = 0;

        for (Expense expense : expenses) {
            List<String> participantIds = getParticipantIdsForExpense(expense.getId());

            // If current user paid, add to totalPaid
            if (expense.getPayerMemberId().equals(currentUserId)) {
                totalPaid += expense.getAmountInPaise();
            }

            // If current user is a participant, calculate share
            if (participantIds.contains(currentUserId)) {
                long share = BalanceCalculator.calculateEqualShare(expense.getAmountInPaise(), 
                        participantIds.size(), 
                        participantIds.indexOf(currentUserId));
                totalShare += share;
            }
        }

        return totalPaid - totalShare;
    }

    /**
     * Get balance as LiveData that reacts to expense/participant/member changes.
     */
    public LiveData<Long> getYourBalanceForTrip(String tripId, String currentUserId) {
        LiveData<List<Expense>> expenses = getExpensesForTrip(tripId);
        return Transformations.map(expenses, list -> calculateYourBalance(tripId, currentUserId));
    }
}
