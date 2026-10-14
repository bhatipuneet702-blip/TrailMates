package com.tripsync.app.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "media_items",
        foreignKeys = @ForeignKey(entity = Trip.class,
                parentColumns = "id",
                childColumns = "tripId",
                onDelete = ForeignKey.CASCADE))
public class MediaItem {
    @PrimaryKey
    @NonNull
    public String id;
    public String tripId;
    public String localPath; // path in app-private storage
    public boolean isVideo;
    public long durationMs;
    public long dateMillis;
    public boolean isFavorite;

    public MediaItem(String id, String tripId, String localPath, boolean isVideo,
                     long durationMs, long dateMillis, boolean isFavorite) {
        this.id = id;
        this.tripId = tripId;
        this.localPath = localPath;
        this.isVideo = isVideo;
        this.durationMs = durationMs;
        this.dateMillis = dateMillis;
        this.isFavorite = isFavorite;
    }

    public String getId() {
        return id;
    }

    public String getTripId() {
        return tripId;
    }

    public String getLocalPath() {
        return localPath;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public long getDurationMs() {
        return durationMs;
    }

    public long getDateMillis() {
        return dateMillis;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
