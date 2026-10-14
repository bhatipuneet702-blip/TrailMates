package com.tripsync.app.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "packing_categories",
        foreignKeys = @ForeignKey(entity = Trip.class,
                parentColumns = "id",
                childColumns = "tripId",
                onDelete = ForeignKey.CASCADE))
public class PackingCategory {
    @PrimaryKey
    @NonNull
    public String id;
    public String tripId;
    public String name;
    public int sortOrder;

    public PackingCategory(String id, String tripId, String name, int sortOrder) {
        this.id = id;
        this.tripId = tripId;
        this.name = name;
        this.sortOrder = sortOrder;
    }

    public String getId() {
        return id;
    }

    public String getTripId() {
        return tripId;
    }

    public String getName() {
        return name;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
