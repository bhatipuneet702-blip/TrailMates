package com.tripsync.app.db.dao;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.tripsync.app.db.entities.Member;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
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
public final class MemberDao_Impl implements MemberDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Member> __insertionAdapterOfMember;

  private final EntityDeletionOrUpdateAdapter<Member> __deletionAdapterOfMember;

  private final EntityDeletionOrUpdateAdapter<Member> __updateAdapterOfMember;

  public MemberDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMember = new EntityInsertionAdapter<Member>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `members` (`id`,`tripId`,`name`,`isCurrentUser`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Member value) {
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
        if (value.name == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.name);
        }
        final int _tmp = value.isCurrentUser ? 1 : 0;
        stmt.bindLong(4, _tmp);
      }
    };
    this.__deletionAdapterOfMember = new EntityDeletionOrUpdateAdapter<Member>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `members` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Member value) {
        if (value.id == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.id);
        }
      }
    };
    this.__updateAdapterOfMember = new EntityDeletionOrUpdateAdapter<Member>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `members` SET `id` = ?,`tripId` = ?,`name` = ?,`isCurrentUser` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Member value) {
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
        if (value.name == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.name);
        }
        final int _tmp = value.isCurrentUser ? 1 : 0;
        stmt.bindLong(4, _tmp);
        if (value.id == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.id);
        }
      }
    };
  }

  @Override
  public void insert(final Member member) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfMember.insert(member);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Member member) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfMember.handle(member);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Member member) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfMember.handle(member);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<Member>> getMembersForTrip(final String tripId) {
    final String _sql = "SELECT * FROM members WHERE tripId = ? ORDER BY isCurrentUser DESC, name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (tripId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tripId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"members"}, false, new Callable<List<Member>>() {
      @Override
      public List<Member> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTripId = CursorUtil.getColumnIndexOrThrow(_cursor, "tripId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIsCurrentUser = CursorUtil.getColumnIndexOrThrow(_cursor, "isCurrentUser");
          final List<Member> _result = new ArrayList<Member>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Member _item;
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
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final boolean _tmpIsCurrentUser;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCurrentUser);
            _tmpIsCurrentUser = _tmp != 0;
            _item = new Member(_tmpId,_tmpTripId,_tmpName,_tmpIsCurrentUser);
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
  public List<Member> getMembersForTripSync(final String tripId) {
    final String _sql = "SELECT * FROM members WHERE tripId = ? ORDER BY isCurrentUser DESC, name ASC";
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
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfIsCurrentUser = CursorUtil.getColumnIndexOrThrow(_cursor, "isCurrentUser");
      final List<Member> _result = new ArrayList<Member>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Member _item;
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
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final boolean _tmpIsCurrentUser;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsCurrentUser);
        _tmpIsCurrentUser = _tmp != 0;
        _item = new Member(_tmpId,_tmpTripId,_tmpName,_tmpIsCurrentUser);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<Member> getMemberById(final String memberId) {
    final String _sql = "SELECT * FROM members WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (memberId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, memberId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"members"}, false, new Callable<Member>() {
      @Override
      public Member call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTripId = CursorUtil.getColumnIndexOrThrow(_cursor, "tripId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIsCurrentUser = CursorUtil.getColumnIndexOrThrow(_cursor, "isCurrentUser");
          final Member _result;
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
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final boolean _tmpIsCurrentUser;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCurrentUser);
            _tmpIsCurrentUser = _tmp != 0;
            _result = new Member(_tmpId,_tmpTripId,_tmpName,_tmpIsCurrentUser);
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
  public Member getMemberByIdSync(final String memberId) {
    final String _sql = "SELECT * FROM members WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (memberId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, memberId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTripId = CursorUtil.getColumnIndexOrThrow(_cursor, "tripId");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfIsCurrentUser = CursorUtil.getColumnIndexOrThrow(_cursor, "isCurrentUser");
      final Member _result;
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
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final boolean _tmpIsCurrentUser;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsCurrentUser);
        _tmpIsCurrentUser = _tmp != 0;
        _result = new Member(_tmpId,_tmpTripId,_tmpName,_tmpIsCurrentUser);
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
  public LiveData<Integer> getMemberCountForTrip(final String tripId) {
    final String _sql = "SELECT COUNT(*) FROM members WHERE tripId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (tripId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tripId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"members"}, false, new Callable<Integer>() {
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

  @Override
  public Member getCurrentUserForTrip(final String tripId) {
    final String _sql = "SELECT * FROM members WHERE tripId = ? AND isCurrentUser = 1 LIMIT 1";
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
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfIsCurrentUser = CursorUtil.getColumnIndexOrThrow(_cursor, "isCurrentUser");
      final Member _result;
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
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final boolean _tmpIsCurrentUser;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsCurrentUser);
        _tmpIsCurrentUser = _tmp != 0;
        _result = new Member(_tmpId,_tmpTripId,_tmpName,_tmpIsCurrentUser);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
