package com.tripsync.app.db.dao;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.tripsync.app.db.entities.MediaItem;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MediaDao_Impl implements MediaDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MediaItem> __insertionAdapterOfMediaItem;

  private final EntityDeletionOrUpdateAdapter<MediaItem> __deletionAdapterOfMediaItem;

  private final EntityDeletionOrUpdateAdapter<MediaItem> __updateAdapterOfMediaItem;

  public MediaDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMediaItem = new EntityInsertionAdapter<MediaItem>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `media_items` (`id`,`tripId`,`localPath`,`isVideo`,`durationMs`,`dateMillis`,`isFavorite`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MediaItem value) {
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
        if (value.localPath == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.localPath);
        }
        final int _tmp = value.isVideo ? 1 : 0;
        stmt.bindLong(4, _tmp);
        stmt.bindLong(5, value.durationMs);
        stmt.bindLong(6, value.dateMillis);
        final int _tmp_1 = value.isFavorite ? 1 : 0;
        stmt.bindLong(7, _tmp_1);
      }
    };
    this.__deletionAdapterOfMediaItem = new EntityDeletionOrUpdateAdapter<MediaItem>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `media_items` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MediaItem value) {
        if (value.id == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.id);
        }
      }
    };
    this.__updateAdapterOfMediaItem = new EntityDeletionOrUpdateAdapter<MediaItem>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `media_items` SET `id` = ?,`tripId` = ?,`localPath` = ?,`isVideo` = ?,`durationMs` = ?,`dateMillis` = ?,`isFavorite` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MediaItem value) {
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
        if (value.localPath == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.localPath);
        }
        final int _tmp = value.isVideo ? 1 : 0;
        stmt.bindLong(4, _tmp);
        stmt.bindLong(5, value.durationMs);
        stmt.bindLong(6, value.dateMillis);
        final int _tmp_1 = value.isFavorite ? 1 : 0;
        stmt.bindLong(7, _tmp_1);
        if (value.id == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.id);
        }
      }
    };
  }

  @Override
  public void insert(final MediaItem mediaItem) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfMediaItem.insert(mediaItem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final MediaItem mediaItem) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfMediaItem.handle(mediaItem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final MediaItem mediaItem) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfMediaItem.handle(mediaItem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<MediaItem>> getMediaForTrip(final String tripId) {
    final String _sql = "SELECT * FROM media_items WHERE tripId = ? ORDER BY dateMillis DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (tripId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tripId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"media_items"}, false, new Callable<List<MediaItem>>() {
      @Override
      public List<MediaItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTripId = CursorUtil.getColumnIndexOrThrow(_cursor, "tripId");
          final int _cursorIndexOfLocalPath = CursorUtil.getColumnIndexOrThrow(_cursor, "localPath");
          final int _cursorIndexOfIsVideo = CursorUtil.getColumnIndexOrThrow(_cursor, "isVideo");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfDateMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "dateMillis");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final List<MediaItem> _result = new ArrayList<MediaItem>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final MediaItem _item;
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
            final String _tmpLocalPath;
            if (_cursor.isNull(_cursorIndexOfLocalPath)) {
              _tmpLocalPath = null;
            } else {
              _tmpLocalPath = _cursor.getString(_cursorIndexOfLocalPath);
            }
            final boolean _tmpIsVideo;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsVideo);
            _tmpIsVideo = _tmp != 0;
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            final long _tmpDateMillis;
            _tmpDateMillis = _cursor.getLong(_cursorIndexOfDateMillis);
            final boolean _tmpIsFavorite;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_1 != 0;
            _item = new MediaItem(_tmpId,_tmpTripId,_tmpLocalPath,_tmpIsVideo,_tmpDurationMs,_tmpDateMillis,_tmpIsFavorite);
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
  public List<MediaItem> getMediaForTripSync(final String tripId) {
    final String _sql = "SELECT * FROM media_items WHERE tripId = ? ORDER BY dateMillis DESC";
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
      final int _cursorIndexOfLocalPath = CursorUtil.getColumnIndexOrThrow(_cursor, "localPath");
      final int _cursorIndexOfIsVideo = CursorUtil.getColumnIndexOrThrow(_cursor, "isVideo");
      final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
      final int _cursorIndexOfDateMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "dateMillis");
      final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
      final List<MediaItem> _result = new ArrayList<MediaItem>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final MediaItem _item;
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
        final String _tmpLocalPath;
        if (_cursor.isNull(_cursorIndexOfLocalPath)) {
          _tmpLocalPath = null;
        } else {
          _tmpLocalPath = _cursor.getString(_cursorIndexOfLocalPath);
        }
        final boolean _tmpIsVideo;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsVideo);
        _tmpIsVideo = _tmp != 0;
        final long _tmpDurationMs;
        _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
        final long _tmpDateMillis;
        _tmpDateMillis = _cursor.getLong(_cursorIndexOfDateMillis);
        final boolean _tmpIsFavorite;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsFavorite);
        _tmpIsFavorite = _tmp_1 != 0;
        _item = new MediaItem(_tmpId,_tmpTripId,_tmpLocalPath,_tmpIsVideo,_tmpDurationMs,_tmpDateMillis,_tmpIsFavorite);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<MediaItem>> getPhotosForTrip(final String tripId) {
    final String _sql = "SELECT * FROM media_items WHERE tripId = ? AND isVideo = 0 ORDER BY dateMillis DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (tripId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tripId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"media_items"}, false, new Callable<List<MediaItem>>() {
      @Override
      public List<MediaItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTripId = CursorUtil.getColumnIndexOrThrow(_cursor, "tripId");
          final int _cursorIndexOfLocalPath = CursorUtil.getColumnIndexOrThrow(_cursor, "localPath");
          final int _cursorIndexOfIsVideo = CursorUtil.getColumnIndexOrThrow(_cursor, "isVideo");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfDateMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "dateMillis");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final List<MediaItem> _result = new ArrayList<MediaItem>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final MediaItem _item;
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
            final String _tmpLocalPath;
            if (_cursor.isNull(_cursorIndexOfLocalPath)) {
              _tmpLocalPath = null;
            } else {
              _tmpLocalPath = _cursor.getString(_cursorIndexOfLocalPath);
            }
            final boolean _tmpIsVideo;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsVideo);
            _tmpIsVideo = _tmp != 0;
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            final long _tmpDateMillis;
            _tmpDateMillis = _cursor.getLong(_cursorIndexOfDateMillis);
            final boolean _tmpIsFavorite;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_1 != 0;
            _item = new MediaItem(_tmpId,_tmpTripId,_tmpLocalPath,_tmpIsVideo,_tmpDurationMs,_tmpDateMillis,_tmpIsFavorite);
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
  public LiveData<List<MediaItem>> getVideosForTrip(final String tripId) {
    final String _sql = "SELECT * FROM media_items WHERE tripId = ? AND isVideo = 1 ORDER BY dateMillis DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (tripId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tripId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"media_items"}, false, new Callable<List<MediaItem>>() {
      @Override
      public List<MediaItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTripId = CursorUtil.getColumnIndexOrThrow(_cursor, "tripId");
          final int _cursorIndexOfLocalPath = CursorUtil.getColumnIndexOrThrow(_cursor, "localPath");
          final int _cursorIndexOfIsVideo = CursorUtil.getColumnIndexOrThrow(_cursor, "isVideo");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfDateMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "dateMillis");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final List<MediaItem> _result = new ArrayList<MediaItem>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final MediaItem _item;
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
            final String _tmpLocalPath;
            if (_cursor.isNull(_cursorIndexOfLocalPath)) {
              _tmpLocalPath = null;
            } else {
              _tmpLocalPath = _cursor.getString(_cursorIndexOfLocalPath);
            }
            final boolean _tmpIsVideo;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsVideo);
            _tmpIsVideo = _tmp != 0;
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            final long _tmpDateMillis;
            _tmpDateMillis = _cursor.getLong(_cursorIndexOfDateMillis);
            final boolean _tmpIsFavorite;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_1 != 0;
            _item = new MediaItem(_tmpId,_tmpTripId,_tmpLocalPath,_tmpIsVideo,_tmpDurationMs,_tmpDateMillis,_tmpIsFavorite);
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
  public LiveData<List<MediaItem>> getFavoritesForTrip(final String tripId) {
    final String _sql = "SELECT * FROM media_items WHERE tripId = ? AND isFavorite = 1 ORDER BY dateMillis DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (tripId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tripId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"media_items"}, false, new Callable<List<MediaItem>>() {
      @Override
      public List<MediaItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTripId = CursorUtil.getColumnIndexOrThrow(_cursor, "tripId");
          final int _cursorIndexOfLocalPath = CursorUtil.getColumnIndexOrThrow(_cursor, "localPath");
          final int _cursorIndexOfIsVideo = CursorUtil.getColumnIndexOrThrow(_cursor, "isVideo");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
          final int _cursorIndexOfDateMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "dateMillis");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final List<MediaItem> _result = new ArrayList<MediaItem>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final MediaItem _item;
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
            final String _tmpLocalPath;
            if (_cursor.isNull(_cursorIndexOfLocalPath)) {
              _tmpLocalPath = null;
            } else {
              _tmpLocalPath = _cursor.getString(_cursorIndexOfLocalPath);
            }
            final boolean _tmpIsVideo;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsVideo);
            _tmpIsVideo = _tmp != 0;
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            final long _tmpDateMillis;
            _tmpDateMillis = _cursor.getLong(_cursorIndexOfDateMillis);
            final boolean _tmpIsFavorite;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_1 != 0;
            _item = new MediaItem(_tmpId,_tmpTripId,_tmpLocalPath,_tmpIsVideo,_tmpDurationMs,_tmpDateMillis,_tmpIsFavorite);
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
  public MediaItem getMediaByIdSync(final String itemId) {
    final String _sql = "SELECT * FROM media_items WHERE id = ?";
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
      final int _cursorIndexOfLocalPath = CursorUtil.getColumnIndexOrThrow(_cursor, "localPath");
      final int _cursorIndexOfIsVideo = CursorUtil.getColumnIndexOrThrow(_cursor, "isVideo");
      final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMs");
      final int _cursorIndexOfDateMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "dateMillis");
      final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
      final MediaItem _result;
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
        final String _tmpLocalPath;
        if (_cursor.isNull(_cursorIndexOfLocalPath)) {
          _tmpLocalPath = null;
        } else {
          _tmpLocalPath = _cursor.getString(_cursorIndexOfLocalPath);
        }
        final boolean _tmpIsVideo;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsVideo);
        _tmpIsVideo = _tmp != 0;
        final long _tmpDurationMs;
        _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
        final long _tmpDateMillis;
        _tmpDateMillis = _cursor.getLong(_cursorIndexOfDateMillis);
        final boolean _tmpIsFavorite;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsFavorite);
        _tmpIsFavorite = _tmp_1 != 0;
        _result = new MediaItem(_tmpId,_tmpTripId,_tmpLocalPath,_tmpIsVideo,_tmpDurationMs,_tmpDateMillis,_tmpIsFavorite);
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
  public LiveData<Integer> getMediaCountForTrip(final String tripId) {
    final String _sql = "SELECT COUNT(*) FROM media_items WHERE tripId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (tripId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, tripId);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"media_items"}, false, new Callable<Integer>() {
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
  public void deleteMultiple(final List<String> ids) {
    __db.assertNotSuspendingTransaction();
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("DELETE FROM media_items WHERE id IN (");
    final int _inputSize = ids.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final SupportSQLiteStatement _stmt = __db.compileStatement(_sql);
    int _argIndex = 1;
    for (String _item : ids) {
      if (_item == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, _item);
      }
      _argIndex ++;
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
