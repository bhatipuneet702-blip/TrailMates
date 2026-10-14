package com.tripsync.app.db.dao;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.tripsync.app.db.entities.Expense;
import com.tripsync.app.db.entities.ExpenseParticipant;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ExpenseDao_Impl implements ExpenseDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Expense> __insertionAdapterOfExpense;

  private final EntityInsertionAdapter<ExpenseParticipant> __insertionAdapterOfExpenseParticipant;

  private final EntityDeletionOrUpdateAdapter<Expense> __deletionAdapterOfExpense;

  private final EntityDeletionOrUpdateAdapter<Expense> __updateAdapterOfExpense;

  private final SharedSQLiteStatement __preparedStmtOfDeleteParticipantsForExpense;

  public ExpenseDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfExpense = new EntityInsertionAdapter<Expense>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `expenses` (`id`,`tripId`,`title`,`categoryType`,`amountInPaise`,`payerMemberId`,`dateMillis`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Expense value) {
        if (value.id == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.id);
        }
        if (value.tripId == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.tripId);
        }
        if (value.title == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.title);
        }
        if (value.categoryType == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.categoryType);
        }
        stmt.bindLong(5, value.amountInPaise);
        if (value.payerMemberId == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.payerMemberId);
        }
        stmt.bindLong(7, value.dateMillis);
      }
    };
    this.__insertionAdapterOfExpenseParticipant = new EntityInsertionAdapter<ExpenseParticipant>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `expense_participants` (`expenseId`,`memberId`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ExpenseParticipant value) {
        if (value.expenseId == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.expenseId);
        }
        if (value.memberId == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.memberId);
        }
      }
    };
    this.__deletionAdapterOfExpense = new EntityDeletionOrUpdateAdapter<Expense>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `expenses` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Expense value) {
        if (value.id == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.id);
        }
      }
    };
    this.__updateAdapterOfExpense = new EntityDeletionOrUpdateAdapter<Expense>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `expenses` SET `id` = ?,`tripId` = ?,`title` = ?,`categoryType` = ?,`amountInPaise` = ?,`payerMemberId` = ?,`dateMillis` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Expense value) {
        if (value.id == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.id);
        }
        if (value.tripId == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.tripId);
        }
        if (value.title == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.title);
        }
        if (value.categoryType == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.categoryType);
        }
        stmt.bindLong(5, value.amountInPaise);
        if (value.payerMemberId == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.payerMemberId);
        }
        stmt.bindLong(7, value.dateMillis);
        if (value.id == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.id);
        }
      }
    };
    this.__preparedStmtOfDeleteParticipantsForExpense = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM expense_participants WHERE expenseId = ?";
        return _query;
      }
    };
  }

  @Override
  public void insertExpense(final Expense expense) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfExpense.insert(expense);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertParticipant(final ExpenseParticipant participant) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfExpenseParticipant.insert(participant);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertParticipants(final List<ExpenseParticipant> participants) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfExpenseParticipant.insert(participants);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteExpense(final Expense expense) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfExpense.handle(expense);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateExpense(final Expense expense) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfExpense.handle(expense);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteParticipantsForExpense(final String expenseId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteParticipantsForExpense.acquire();
    int _argIndex = 1;
    if (expenseId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, expenseId);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteParticipantsForExpense.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Expense>> getExpensesForTrip(final String tripId) {
    final String _sql = "SELECT * FROM expenses WHERE tripId = ? ORDER BY dateMillis DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (tripId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tripId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"expenses"}, false, new Callable<List<Expense>>() {
      @Override
      public List<Expense> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTripId = CursorUtil.getColumnIndexOrThrow(_cursor, "tripId");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfCategoryType = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryType");
          final int _cursorIndexOfAmountInPaise = CursorUtil.getColumnIndexOrThrow(_cursor, "amountInPaise");
          final int _cursorIndexOfPayerMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "payerMemberId");
          final int _cursorIndexOfDateMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "dateMillis");
          final List<Expense> _result = new ArrayList<Expense>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Expense _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpTripId;
            if (_cursor.isNull(_cursorIndexOfTripId)) {
              _tmpTripId = null;
            } else {
              _tmpTripId = _cursor.getString(_cursorIndexOfTripId);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpCategoryType;
            if (_cursor.isNull(_cursorIndexOfCategoryType)) {
              _tmpCategoryType = null;
            } else {
              _tmpCategoryType = _cursor.getString(_cursorIndexOfCategoryType);
            }
            final long _tmpAmountInPaise;
            _tmpAmountInPaise = _cursor.getLong(_cursorIndexOfAmountInPaise);
            final String _tmpPayerMemberId;
            if (_cursor.isNull(_cursorIndexOfPayerMemberId)) {
              _tmpPayerMemberId = null;
            } else {
              _tmpPayerMemberId = _cursor.getString(_cursorIndexOfPayerMemberId);
            }
            final long _tmpDateMillis;
            _tmpDateMillis = _cursor.getLong(_cursorIndexOfDateMillis);
            _item = new Expense(_tmpId,_tmpTripId,_tmpTitle,_tmpCategoryType,_tmpAmountInPaise,_tmpPayerMemberId,_tmpDateMillis);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public List<Expense> getExpensesForTripSync(final String tripId) {
    final String _sql = "SELECT * FROM expenses WHERE tripId = ? ORDER BY dateMillis DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (tripId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tripId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTripId = CursorUtil.getColumnIndexOrThrow(_cursor, "tripId");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfCategoryType = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryType");
      final int _cursorIndexOfAmountInPaise = CursorUtil.getColumnIndexOrThrow(_cursor, "amountInPaise");
      final int _cursorIndexOfPayerMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "payerMemberId");
      final int _cursorIndexOfDateMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "dateMillis");
      final List<Expense> _result = new ArrayList<Expense>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Expense _item;
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        final String _tmpTripId;
        if (_cursor.isNull(_cursorIndexOfTripId)) {
          _tmpTripId = null;
        } else {
          _tmpTripId = _cursor.getString(_cursorIndexOfTripId);
        }
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        final String _tmpCategoryType;
        if (_cursor.isNull(_cursorIndexOfCategoryType)) {
          _tmpCategoryType = null;
        } else {
          _tmpCategoryType = _cursor.getString(_cursorIndexOfCategoryType);
        }
        final long _tmpAmountInPaise;
        _tmpAmountInPaise = _cursor.getLong(_cursorIndexOfAmountInPaise);
        final String _tmpPayerMemberId;
        if (_cursor.isNull(_cursorIndexOfPayerMemberId)) {
          _tmpPayerMemberId = null;
        } else {
          _tmpPayerMemberId = _cursor.getString(_cursorIndexOfPayerMemberId);
        }
        final long _tmpDateMillis;
        _tmpDateMillis = _cursor.getLong(_cursorIndexOfDateMillis);
        _item = new Expense(_tmpId,_tmpTripId,_tmpTitle,_tmpCategoryType,_tmpAmountInPaise,_tmpPayerMemberId,_tmpDateMillis);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Expense getExpenseByIdSync(final String expenseId) {
    final String _sql = "SELECT * FROM expenses WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (expenseId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, expenseId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTripId = CursorUtil.getColumnIndexOrThrow(_cursor, "tripId");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfCategoryType = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryType");
      final int _cursorIndexOfAmountInPaise = CursorUtil.getColumnIndexOrThrow(_cursor, "amountInPaise");
      final int _cursorIndexOfPayerMemberId = CursorUtil.getColumnIndexOrThrow(_cursor, "payerMemberId");
      final int _cursorIndexOfDateMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "dateMillis");
      final Expense _result;
      if(_cursor.moveToFirst()) {
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        final String _tmpTripId;
        if (_cursor.isNull(_cursorIndexOfTripId)) {
          _tmpTripId = null;
        } else {
          _tmpTripId = _cursor.getString(_cursorIndexOfTripId);
        }
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        final String _tmpCategoryType;
        if (_cursor.isNull(_cursorIndexOfCategoryType)) {
          _tmpCategoryType = null;
        } else {
          _tmpCategoryType = _cursor.getString(_cursorIndexOfCategoryType);
        }
        final long _tmpAmountInPaise;
        _tmpAmountInPaise = _cursor.getLong(_cursorIndexOfAmountInPaise);
        final String _tmpPayerMemberId;
        if (_cursor.isNull(_cursorIndexOfPayerMemberId)) {
          _tmpPayerMemberId = null;
        } else {
          _tmpPayerMemberId = _cursor.getString(_cursorIndexOfPayerMemberId);
        }
        final long _tmpDateMillis;
        _tmpDateMillis = _cursor.getLong(_cursorIndexOfDateMillis);
        _result = new Expense(_tmpId,_tmpTripId,_tmpTitle,_tmpCategoryType,_tmpAmountInPaise,_tmpPayerMemberId,_tmpDateMillis);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<String> getParticipantIdsForExpense(final String expenseId) {
    final String _sql = "SELECT memberId FROM expense_participants WHERE expenseId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (expenseId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, expenseId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final List<String> _result = new ArrayList<String>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final String _item;
        if (_cursor.isNull(0)) {
          _item = null;
        } else {
          _item = _cursor.getString(0);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<Long> getTotalExpensesForTrip(final String tripId) {
    final String _sql = "SELECT SUM(amountInPaise) FROM expenses WHERE tripId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (tripId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tripId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"expenses"}, false, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Long _result;
          if(_cursor.moveToFirst()) {
            final Long _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Long getTotalPaidByMemberSync(final String tripId, final String memberId) {
    final String _sql = "SELECT SUM(amountInPaise) FROM expenses WHERE tripId = ? AND payerMemberId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (tripId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tripId);
    }
    _argIndex = 2;
    if (memberId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, memberId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final Long _result;
      if(_cursor.moveToFirst()) {
        final Long _tmp;
        if (_cursor.isNull(0)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(0);
        }
        _result = _tmp;
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<Integer> getExpenseCountForMember(final String tripId, final String memberId) {
    final String _sql = "SELECT COUNT(*) FROM expense_participants WHERE memberId = ? AND expenseId IN (SELECT id FROM expenses WHERE tripId = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (memberId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, memberId);
    }
    _argIndex = 2;
    if (tripId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tripId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"expense_participants","expenses"}, false, new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if(_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
