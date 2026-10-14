package com.tripsync.app.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "packing_items",
        foreignKeys = {
                @ForeignKey(entity = Trip.class,
                        parentColumns = "id",
                        childColumns = "tripId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = PackingCategory.class,
                        parentColumns = "id",
                        childColumns = "categoryId",
                        onDelete = ForeignKey.SET_NULL)
        })
public class PackingItem {
    @PrimaryKey
    @NonNull
    public String id;
    public String tripId;
    public String categoryId;
    public String name;
    public boolean isPacked;

    public PackingItem(String id, String tripId, String categoryId, String name, boolean isPacked) {
        this.id = id;
        this.tripId = tripId;
        this.categoryId = categoryId;
        this.name = name;
        this.isPacked = isPacked;
    }

    public String getId() {
        return id;
    }

    public String getTripId() {
        return tripId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public boolean isPacked() {
        return isPacked;
    }

    public void setPacked(boolean packed) {
        isPacked = packed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
