package com.tripsync.app.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "members",
        foreignKeys = @ForeignKey(entity = Trip.class,
                parentColumns = "id",
                childColumns = "tripId",
                onDelete = ForeignKey.CASCADE))
public class Member {
    @PrimaryKey
    @NonNull
    public String id;
    public String tripId;
    public String name;
    public boolean isCurrentUser;

    public Member(String id, String tripId, String name, boolean isCurrentUser) {
        this.id = id;
        this.tripId = tripId;
        this.name = name;
        this.isCurrentUser = isCurrentUser;
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

    public boolean isCurrentUser() {
        return isCurrentUser;
    }

    public void setName(String name) {
        this.name = name;
    }
}
