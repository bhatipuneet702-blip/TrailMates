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
import com.tripsync.app.db.entities.PackingCategory;
import com.tripsync.app.db.entities.PackingItem;
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
public final class PackingDao_Impl implements PackingDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PackingCategory> __insertionAdapterOfPackingCategory;

  private final EntityInsertionAdapter<PackingItem> __insertionAdapterOfPackingItem;

  private final EntityDeletionOrUpdateAdapter<PackingCategory> __deletionAdapterOfPackingCategory;

  private final EntityDeletionOrUpdateAdapter<PackingItem> __deletionAdapterOfPackingItem;

  private final EntityDeletionOrUpdateAdapter<PackingCategory> __updateAdapterOfPackingCategory;

  private final EntityDeletionOrUpdateAdapter<PackingItem> __updateAdapterOfPackingItem;

  public PackingDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPackingCategory = new EntityInsertionAdapter<PackingCategory>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `packing_categories` (`id`,`tripId`,`name`,`sortOrder`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PackingCategory value) {
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
        stmt.bindLong(4, value.sortOrder);
      }
    };
    this.__insertionAdapterOfPackingItem = new EntityInsertionAdapter<PackingItem>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `packing_items` (`id`,`tripId`,`categoryId`,`name`,`isPacked`) VALUES (?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PackingItem value) {
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
        if (value.categoryId == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.categoryId);
        }
        if (value.name == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.name);
        }
        final int _tmp = value.isPacked ? 1 : 0;
        stmt.bindLong(5, _tmp);
      }
    };
    this.__deletionAdapterOfPackingCategory = new EntityDeletionOrUpdateAdapter<PackingCategory>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `packing_categories` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PackingCategory value) {
        if (value.id == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.id);
        }
      }
    };
    this.__deletionAdapterOfPackingItem = new EntityDeletionOrUpdateAdapter<PackingItem>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `packing_items` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PackingItem value) {
        if (value.id == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.id);
        }
      }
    };
    this.__updateAdapterOfPackingCategory = new EntityDeletionOrUpdateAdapter<PackingCategory>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `packing_categories` SET `id` = ?,`tripId` = ?,`name` = ?,`sortOrder` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PackingCategory value) {
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
        stmt.bindLong(4, value.sortOrder);
        if (value.id == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.id);
        }
      }
    };
    this.__updateAdapterOfPackingItem = new EntityDeletionOrUpdateAdapter<PackingItem>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `packing_items` SET `id` = ?,`tripId` = ?,`categoryId` = ?,`name` = ?,`isPacked` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PackingItem value) {
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
        if (value.categoryId == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.categoryId);
        }
        if (value.name == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.name);
        }
        final int _tmp = value.isPacked ? 1 : 0;
        stmt.bindLong(5, _tmp);
        if (value.id == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.id);
        }
      }
    };
  }

  @Override
  public void insertCategory(final PackingCategory category) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfPackingCategory.insert(category);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertItem(final PackingItem item) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfPackingItem.insert(item);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteCategory(final PackingCategory category) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfPackingCategory.handle(category);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteItem(final PackingItem item) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfPackingItem.handle(item);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateCategory(final PackingCategory category) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfPackingCategory.handle(category);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateItem(final PackingItem item) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfPackingItem.handle(item);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<PackingCategory>> getCategoriesForTrip(final String tripId) {
    final String _sql = "SELECT * FROM packing_categories WHERE tripId = ? ORDER BY sortOrder ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (tripId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tripId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"packing_categories"}, false, new Callable<List<PackingCategory>>() {
      @Override
      public List<PackingCategory> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTripId = CursorUtil.getColumnIndexOrThrow(_cursor, "tripId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfSortOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "sortOrder");
          final List<PackingCategory> _result = new ArrayList<PackingCategory>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final PackingCategory _item;
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
            final int _tmpSortOrder;
            _tmpSortOrder = _cursor.getInt(_cursorIndexOfSortOrder);
            _item = new PackingCategory(_tmpId,_tmpTripId,_tmpName,_tmpSortOrder);
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
  public List<PackingCategory> getCategoriesForTripSync(final String tripId) {
    final String _sql = "SELECT * FROM packing_categories WHERE tripId = ? ORDER BY sortOrder ASC";
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
      final int _cursorIndexOfSortOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "sortOrder");
      final List<PackingCategory> _result = new ArrayList<PackingCategory>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final PackingCategory _item;
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
        final int _tmpSortOrder;
        _tmpSortOrder = _cursor.getInt(_cursorIndexOfSortOrder);
        _item = new PackingCategory(_tmpId,_tmpTripId,_tmpName,_tmpSortOrder);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public PackingCategory getCategoryByIdSync(final String categoryId) {
    final String _sql = "SELECT * FROM packing_categories WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (categoryId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, categoryId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTripId = CursorUtil.getColumnIndexOrThrow(_cursor, "tripId");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfSortOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "sortOrder");
      final PackingCategory _result;
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
        final int _tmpSortOrder;
        _tmpSortOrder = _cursor.getInt(_cursorIndexOfSortOrder);
        _result = new PackingCategory(_tmpId,_tmpTripId,_tmpName,_tmpSortOrder);
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
  public LiveData<List<PackingItem>> getItemsForTrip(final String tripId) {
    final String _sql = "SELECT * FROM packing_items WHERE tripId = ? ORDER BY categoryId ASC, name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (tripId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tripId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"packing_items"}, false, new Callable<List<PackingItem>>() {
      @Override
      public List<PackingItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTripId = CursorUtil.getColumnIndexOrThrow(_cursor, "tripId");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIsPacked = CursorUtil.getColumnIndexOrThrow(_cursor, "isPacked");
          final List<PackingItem> _result = new ArrayList<PackingItem>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final PackingItem _item;
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
            final String _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getString(_cursorIndexOfCategoryId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final boolean _tmpIsPacked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPacked);
            _tmpIsPacked = _tmp != 0;
            _item = new PackingItem(_tmpId,_tmpTripId,_tmpCategoryId,_tmpName,_tmpIsPacked);
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
  public List<PackingItem> getItemsForTripSync(final String tripId) {
    final String _sql = "SELECT * FROM packing_items WHERE tripId = ? ORDER BY categoryId ASC, name ASC";
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
      final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfIsPacked = CursorUtil.getColumnIndexOrThrow(_cursor, "isPacked");
      final List<PackingItem> _result = new ArrayList<PackingItem>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final PackingItem _item;
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
        final String _tmpCategoryId;
        if (_cursor.isNull(_cursorIndexOfCategoryId)) {
          _tmpCategoryId = null;
        } else {
          _tmpCategoryId = _cursor.getString(_cursorIndexOfCategoryId);
        }
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final boolean _tmpIsPacked;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsPacked);
        _tmpIsPacked = _tmp != 0;
        _item = new PackingItem(_tmpId,_tmpTripId,_tmpCategoryId,_tmpName,_tmpIsPacked);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<PackingItem>> getItemsForCategory(final String categoryId) {
    final String _sql = "SELECT * FROM packing_items WHERE categoryId = ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (categoryId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, categoryId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"packing_items"}, false, new Callable<List<PackingItem>>() {
      @Override
      public List<PackingItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTripId = CursorUtil.getColumnIndexOrThrow(_cursor, "tripId");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIsPacked = CursorUtil.getColumnIndexOrThrow(_cursor, "isPacked");
          final List<PackingItem> _result = new ArrayList<PackingItem>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final PackingItem _item;
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
            final String _tmpCategoryId;
            if (_cursor.isNull(_cursorIndexOfCategoryId)) {
              _tmpCategoryId = null;
            } else {
              _tmpCategoryId = _cursor.getString(_cursorIndexOfCategoryId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final boolean _tmpIsPacked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPacked);
            _tmpIsPacked = _tmp != 0;
            _item = new PackingItem(_tmpId,_tmpTripId,_tmpCategoryId,_tmpName,_tmpIsPacked);
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
  public PackingItem getItemByIdSync(final String itemId) {
    final String _sql = "SELECT * FROM packing_items WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (itemId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, itemId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTripId = CursorUtil.getColumnIndexOrThrow(_cursor, "tripId");
      final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryId");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfIsPacked = CursorUtil.getColumnIndexOrThrow(_cursor, "isPacked");
      final PackingItem _result;
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
        final String _tmpCategoryId;
        if (_cursor.isNull(_cursorIndexOfCategoryId)) {
          _tmpCategoryId = null;
        } else {
          _tmpCategoryId = _cursor.getString(_cursorIndexOfCategoryId);
        }
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final boolean _tmpIsPacked;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsPacked);
        _tmpIsPacked = _tmp != 0;
        _result = new PackingItem(_tmpId,_tmpTripId,_tmpCategoryId,_tmpName,_tmpIsPacked);
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
  public Integer getPackedCountForCategory(final String categoryId) {
    final String _sql = "SELECT COUNT(*) FROM packing_items WHERE categoryId = ? AND isPacked = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (categoryId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, categoryId);
    }
    __db.assertNotSuspendingTransaction();
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
      _statement.release();
    }
  }

  @Override
  public Integer getTotalCountForCategory(final String categoryId) {
    final String _sql = "SELECT COUNT(*) FROM packing_items WHERE categoryId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (categoryId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, categoryId);
    }
    __db.assertNotSuspendingTransaction();
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
      _statement.release();
    }
  }

  @Override
  public LiveData<Integer> getPackedCountForTrip(final String tripId) {
    final String _sql = "SELECT COUNT(*) FROM packing_items WHERE tripId = ? AND isPacked = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (tripId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tripId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"packing_items"}, false, new Callable<Integer>() {
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
  public LiveData<Integer> getTotalCountForTrip(final String tripId) {
    final String _sql = "SELECT COUNT(*) FROM packing_items WHERE tripId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (tripId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tripId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"packing_items"}, false, new Callable<Integer>() {
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
