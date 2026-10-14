package com.tripsync.app.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.tripsync.app.db.dao.ExpenseDao;
import com.tripsync.app.db.dao.MediaDao;
import com.tripsync.app.db.dao.MemberDao;
import com.tripsync.app.db.dao.PackingDao;
import com.tripsync.app.db.dao.TripDao;
import com.tripsync.app.db.entities.Expense;
import com.tripsync.app.db.entities.ExpenseParticipant;
import com.tripsync.app.db.entities.MediaItem;
import com.tripsync.app.db.entities.Member;
import com.tripsync.app.db.entities.PackingCategory;
import com.tripsync.app.db.entities.PackingItem;
import com.tripsync.app.db.entities.Trip;

@Database(entities = {
        Trip.class,
        Member.class,
        Expense.class,
        ExpenseParticipant.class,
        PackingCategory.class,
        PackingItem.class,
        MediaItem.class
}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase instance;

    public abstract TripDao tripDao();
    public abstract MemberDao memberDao();
    public abstract ExpenseDao expenseDao();
    public abstract PackingDao packingDao();
    public abstract MediaDao mediaDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "tripsync_db")
                            .build();
                }
            }
        }
        return instance;
    }
}
