package com.tripsync.app.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.tripsync.app.db.dao.ExpenseDao;
import com.tripsync.app.db.dao.ExpenseDao_Impl;
import com.tripsync.app.db.dao.MediaDao;
import com.tripsync.app.db.dao.MediaDao_Impl;
import com.tripsync.app.db.dao.MemberDao;
import com.tripsync.app.db.dao.MemberDao_Impl;
import com.tripsync.app.db.dao.PackingDao;
import com.tripsync.app.db.dao.PackingDao_Impl;
import com.tripsync.app.db.dao.TripDao;
import com.tripsync.app.db.dao.TripDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile TripDao _tripDao;

  private volatile MemberDao _memberDao;

  private volatile ExpenseDao _expenseDao;

  private volatile PackingDao _packingDao;

  private volatile MediaDao _mediaDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `trips` (`id` TEXT NOT NULL, `name` TEXT, `startDateMillis` INTEGER NOT NULL, `endDateMillis` INTEGER NOT NULL, `avatarPath` TEXT, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `members` (`id` TEXT NOT NULL, `tripId` TEXT, `name` TEXT, `isCurrentUser` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`tripId`) REFERENCES `trips`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `expenses` (`id` TEXT NOT NULL, `tripId` TEXT, `title` TEXT, `categoryType` TEXT, `amountInPaise` INTEGER NOT NULL, `payerMemberId` TEXT, `dateMillis` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`tripId`) REFERENCES `trips`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`payerMemberId`) REFERENCES `members`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `expense_participants` (`expenseId` TEXT NOT NULL, `memberId` TEXT NOT NULL, PRIMARY KEY(`expenseId`, `memberId`), FOREIGN KEY(`expenseId`) REFERENCES `expenses`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`memberId`) REFERENCES `members`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `packing_categories` (`id` TEXT NOT NULL, `tripId` TEXT, `name` TEXT, `sortOrder` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`tripId`) REFERENCES `trips`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `packing_items` (`id` TEXT NOT NULL, `tripId` TEXT, `categoryId` TEXT, `name` TEXT, `isPacked` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`tripId`) REFERENCES `trips`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`categoryId`) REFERENCES `packing_categories`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `media_items` (`id` TEXT NOT NULL, `tripId` TEXT, `localPath` TEXT, `isVideo` INTEGER NOT NULL, `durationMs` INTEGER NOT NULL, `dateMillis` INTEGER NOT NULL, `isFavorite` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`tripId`) REFERENCES `trips`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '5f6c2df1cd1ddb59c7d1571f858d9634')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `trips`");
        _db.execSQL("DROP TABLE IF EXISTS `members`");
        _db.execSQL("DROP TABLE IF EXISTS `expenses`");
        _db.execSQL("DROP TABLE IF EXISTS `expense_participants`");
        _db.execSQL("DROP TABLE IF EXISTS `packing_categories`");
        _db.execSQL("DROP TABLE IF EXISTS `packing_items`");
        _db.execSQL("DROP TABLE IF EXISTS `media_items`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      public void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        _db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      public RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsTrips = new HashMap<String, TableInfo.Column>(5);
        _columnsTrips.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("startDateMillis", new TableInfo.Column("startDateMillis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("endDateMillis", new TableInfo.Column("endDateMillis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrips.put("avatarPath", new TableInfo.Column("avatarPath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTrips = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTrips = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTrips = new TableInfo("trips", _columnsTrips, _foreignKeysTrips, _indicesTrips);
        final TableInfo _existingTrips = TableInfo.read(_db, "trips");
        if (! _infoTrips.equals(_existingTrips)) {
          return new RoomOpenHelper.ValidationResult(false, "trips(com.tripsync.app.db.entities.Trip).\n"
                  + " Expected:\n" + _infoTrips + "\n"
                  + " Found:\n" + _existingTrips);
        }
        final HashMap<String, TableInfo.Column> _columnsMembers = new HashMap<String, TableInfo.Column>(4);
        _columnsMembers.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMembers.put("tripId", new TableInfo.Column("tripId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMembers.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMembers.put("isCurrentUser", new TableInfo.Column("isCurrentUser", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMembers = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysMembers.add(new TableInfo.ForeignKey("trips", "CASCADE", "NO ACTION",Arrays.asList("tripId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesMembers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMembers = new TableInfo("members", _columnsMembers, _foreignKeysMembers, _indicesMembers);
        final TableInfo _existingMembers = TableInfo.read(_db, "members");
        if (! _infoMembers.equals(_existingMembers)) {
          return new RoomOpenHelper.ValidationResult(false, "members(com.tripsync.app.db.entities.Member).\n"
                  + " Expected:\n" + _infoMembers + "\n"
                  + " Found:\n" + _existingMembers);
        }
        final HashMap<String, TableInfo.Column> _columnsExpenses = new HashMap<String, TableInfo.Column>(7);
        _columnsExpenses.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("tripId", new TableInfo.Column("tripId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("categoryType", new TableInfo.Column("categoryType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("amountInPaise", new TableInfo.Column("amountInPaise", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("payerMemberId", new TableInfo.Column("payerMemberId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("dateMillis", new TableInfo.Column("dateMillis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysExpenses = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysExpenses.add(new TableInfo.ForeignKey("trips", "CASCADE", "NO ACTION",Arrays.asList("tripId"), Arrays.asList("id")));
        _foreignKeysExpenses.add(new TableInfo.ForeignKey("members", "SET NULL", "NO ACTION",Arrays.asList("payerMemberId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesExpenses = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoExpenses = new TableInfo("expenses", _columnsExpenses, _foreignKeysExpenses, _indicesExpenses);
        final TableInfo _existingExpenses = TableInfo.read(_db, "expenses");
        if (! _infoExpenses.equals(_existingExpenses)) {
          return new RoomOpenHelper.ValidationResult(false, "expenses(com.tripsync.app.db.entities.Expense).\n"
                  + " Expected:\n" + _infoExpenses + "\n"
                  + " Found:\n" + _existingExpenses);
        }
        final HashMap<String, TableInfo.Column> _columnsExpenseParticipants = new HashMap<String, TableInfo.Column>(2);
        _columnsExpenseParticipants.put("expenseId", new TableInfo.Column("expenseId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenseParticipants.put("memberId", new TableInfo.Column("memberId", "TEXT", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysExpenseParticipants = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysExpenseParticipants.add(new TableInfo.ForeignKey("expenses", "CASCADE", "NO ACTION",Arrays.asList("expenseId"), Arrays.asList("id")));
        _foreignKeysExpenseParticipants.add(new TableInfo.ForeignKey("members", "CASCADE", "NO ACTION",Arrays.asList("memberId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesExpenseParticipants = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoExpenseParticipants = new TableInfo("expense_participants", _columnsExpenseParticipants, _foreignKeysExpenseParticipants, _indicesExpenseParticipants);
        final TableInfo _existingExpenseParticipants = TableInfo.read(_db, "expense_participants");
        if (! _infoExpenseParticipants.equals(_existingExpenseParticipants)) {
          return new RoomOpenHelper.ValidationResult(false, "expense_participants(com.tripsync.app.db.entities.ExpenseParticipant).\n"
                  + " Expected:\n" + _infoExpenseParticipants + "\n"
                  + " Found:\n" + _existingExpenseParticipants);
        }
        final HashMap<String, TableInfo.Column> _columnsPackingCategories = new HashMap<String, TableInfo.Column>(4);
        _columnsPackingCategories.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPackingCategories.put("tripId", new TableInfo.Column("tripId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPackingCategories.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPackingCategories.put("sortOrder", new TableInfo.Column("sortOrder", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPackingCategories = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysPackingCategories.add(new TableInfo.ForeignKey("trips", "CASCADE", "NO ACTION",Arrays.asList("tripId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesPackingCategories = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPackingCategories = new TableInfo("packing_categories", _columnsPackingCategories, _foreignKeysPackingCategories, _indicesPackingCategories);
        final TableInfo _existingPackingCategories = TableInfo.read(_db, "packing_categories");
        if (! _infoPackingCategories.equals(_existingPackingCategories)) {
          return new RoomOpenHelper.ValidationResult(false, "packing_categories(com.tripsync.app.db.entities.PackingCategory).\n"
                  + " Expected:\n" + _infoPackingCategories + "\n"
                  + " Found:\n" + _existingPackingCategories);
        }
        final HashMap<String, TableInfo.Column> _columnsPackingItems = new HashMap<String, TableInfo.Column>(5);
        _columnsPackingItems.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPackingItems.put("tripId", new TableInfo.Column("tripId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPackingItems.put("categoryId", new TableInfo.Column("categoryId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPackingItems.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPackingItems.put("isPacked", new TableInfo.Column("isPacked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPackingItems = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysPackingItems.add(new TableInfo.ForeignKey("trips", "CASCADE", "NO ACTION",Arrays.asList("tripId"), Arrays.asList("id")));
        _foreignKeysPackingItems.add(new TableInfo.ForeignKey("packing_categories", "SET NULL", "NO ACTION",Arrays.asList("categoryId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesPackingItems = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPackingItems = new TableInfo("packing_items", _columnsPackingItems, _foreignKeysPackingItems, _indicesPackingItems);
        final TableInfo _existingPackingItems = TableInfo.read(_db, "packing_items");
        if (! _infoPackingItems.equals(_existingPackingItems)) {
          return new RoomOpenHelper.ValidationResult(false, "packing_items(com.tripsync.app.db.entities.PackingItem).\n"
                  + " Expected:\n" + _infoPackingItems + "\n"
                  + " Found:\n" + _existingPackingItems);
        }
        final HashMap<String, TableInfo.Column> _columnsMediaItems = new HashMap<String, TableInfo.Column>(7);
        _columnsMediaItems.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMediaItems.put("tripId", new TableInfo.Column("tripId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMediaItems.put("localPath", new TableInfo.Column("localPath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMediaItems.put("isVideo", new TableInfo.Column("isVideo", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMediaItems.put("durationMs", new TableInfo.Column("durationMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMediaItems.put("dateMillis", new TableInfo.Column("dateMillis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMediaItems.put("isFavorite", new TableInfo.Column("isFavorite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMediaItems = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysMediaItems.add(new TableInfo.ForeignKey("trips", "CASCADE", "NO ACTION",Arrays.asList("tripId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesMediaItems = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMediaItems = new TableInfo("media_items", _columnsMediaItems, _foreignKeysMediaItems, _indicesMediaItems);
        final TableInfo _existingMediaItems = TableInfo.read(_db, "media_items");
        if (! _infoMediaItems.equals(_existingMediaItems)) {
          return new RoomOpenHelper.ValidationResult(false, "media_items(com.tripsync.app.db.entities.MediaItem).\n"
                  + " Expected:\n" + _infoMediaItems + "\n"
                  + " Found:\n" + _existingMediaItems);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "5f6c2df1cd1ddb59c7d1571f858d9634", "be8d01027d1daa551e59d19774ce4c78");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "trips","members","expenses","expense_participants","packing_categories","packing_items","media_items");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `trips`");
      _db.execSQL("DELETE FROM `members`");
      _db.execSQL("DELETE FROM `expenses`");
      _db.execSQL("DELETE FROM `expense_participants`");
      _db.execSQL("DELETE FROM `packing_categories`");
      _db.execSQL("DELETE FROM `packing_items`");
      _db.execSQL("DELETE FROM `media_items`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(TripDao.class, TripDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MemberDao.class, MemberDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ExpenseDao.class, ExpenseDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PackingDao.class, PackingDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MediaDao.class, MediaDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public TripDao tripDao() {
    if (_tripDao != null) {
      return _tripDao;
    } else {
      synchronized(this) {
        if(_tripDao == null) {
          _tripDao = new TripDao_Impl(this);
        }
        return _tripDao;
      }
    }
  }

  @Override
  public MemberDao memberDao() {
    if (_memberDao != null) {
      return _memberDao;
    } else {
      synchronized(this) {
        if(_memberDao == null) {
          _memberDao = new MemberDao_Impl(this);
        }
        return _memberDao;
      }
    }
  }

  @Override
  public ExpenseDao expenseDao() {
    if (_expenseDao != null) {
      return _expenseDao;
    } else {
      synchronized(this) {
        if(_expenseDao == null) {
          _expenseDao = new ExpenseDao_Impl(this);
        }
        return _expenseDao;
      }
    }
  }

  @Override
  public PackingDao packingDao() {
    if (_packingDao != null) {
      return _packingDao;
    } else {
      synchronized(this) {
        if(_packingDao == null) {
          _packingDao = new PackingDao_Impl(this);
        }
        return _packingDao;
      }
    }
  }

  @Override
  public MediaDao mediaDao() {
    if (_mediaDao != null) {
      return _mediaDao;
    } else {
      synchronized(this) {
        if(_mediaDao == null) {
          _mediaDao = new MediaDao_Impl(this);
        }
        return _mediaDao;
      }
    }
  }
}
