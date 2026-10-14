package com.tripsync.app.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "trips")
public class Trip {
    @PrimaryKey
    @NonNull
    public String id;
    public String name;
    public long startDateMillis;
    public long endDateMillis;
    public String avatarPath; // local file path or null

    public Trip(String id, String name, long startDateMillis, long endDateMillis) {
        this.id = id;
        this.name = name;
        this.startDateMillis = startDateMillis;
        this.endDateMillis = endDateMillis;
        this.avatarPath = null;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getStartDateMillis() {
        return startDateMillis;
    }

    public long getEndDateMillis() {
        return endDateMillis;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDateMillis(long startDateMillis) {
        this.startDateMillis = startDateMillis;
    }

    public void setEndDateMillis(long endDateMillis) {
        this.endDateMillis = endDateMillis;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }
}
