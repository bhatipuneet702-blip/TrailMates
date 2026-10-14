package com.tripsync.app.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tripsync.app.db.entities.Expense;
import com.tripsync.app.db.entities.ExpenseParticipant;

import java.util.List;

@Dao
public interface ExpenseDao {
    @Insert
    void insertExpense(Expense expense);

    @Insert
    void insertParticipant(ExpenseParticipant participant);

    @Insert
    void insertParticipants(List<ExpenseParticipant> participants);

    @Update
    void updateExpense(Expense expense);

    @Delete
    void deleteExpense(Expense expense);

    @Query("DELETE FROM expense_participants WHERE expenseId = :expenseId")
    void deleteParticipantsForExpense(String expenseId);

    @Query("SELECT * FROM expenses WHERE tripId = :tripId ORDER BY dateMillis DESC")
    LiveData<List<Expense>> getExpensesForTrip(String tripId);

    @Query("SELECT * FROM expenses WHERE tripId = :tripId ORDER BY dateMillis DESC")
    List<Expense> getExpensesForTripSync(String tripId);

    @Query("SELECT * FROM expenses WHERE id = :expenseId")
    Expense getExpenseByIdSync(String expenseId);

    @Query("SELECT memberId FROM expense_participants WHERE expenseId = :expenseId")
    List<String> getParticipantIdsForExpense(String expenseId);

    @Query("SELECT memberId FROM expense_participants WHERE expenseId = :expenseId")
    List<String> getParticipantIdsForExpenseSync(String expenseId);

    @Query("SELECT SUM(amountInPaise) FROM expenses WHERE tripId = :tripId")
    LiveData<Long> getTotalExpensesForTrip(String tripId);

    @Query("SELECT SUM(amountInPaise) FROM expenses WHERE tripId = :tripId AND payerMemberId = :memberId")
    Long getTotalPaidByMemberSync(String tripId, String memberId);

    @Query("SELECT COUNT(*) FROM expense_participants WHERE memberId = :memberId AND expenseId IN " +
           "(SELECT id FROM expenses WHERE tripId = :tripId)")
    LiveData<Integer> getExpenseCountForMember(String tripId, String memberId);
}
