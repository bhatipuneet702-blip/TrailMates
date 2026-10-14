package com.tripsync.app.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tripsync.app.db.entities.MediaItem;

import java.util.List;

@Dao
public interface MediaDao {
    @Insert
    void insert(MediaItem mediaItem);

    @Update
    void update(MediaItem mediaItem);

    @Delete
    void delete(MediaItem mediaItem);

    @Query("DELETE FROM media_items WHERE id IN (:ids)")
    void deleteMultiple(List<String> ids);

    @Query("SELECT * FROM media_items WHERE tripId = :tripId ORDER BY dateMillis DESC")
    LiveData<List<MediaItem>> getMediaForTrip(String tripId);

    @Query("SELECT * FROM media_items WHERE tripId = :tripId ORDER BY dateMillis DESC")
    List<MediaItem> getMediaForTripSync(String tripId);

    @Query("SELECT * FROM media_items WHERE tripId = :tripId AND isVideo = 0 ORDER BY dateMillis DESC")
    LiveData<List<MediaItem>> getPhotosForTrip(String tripId);

    @Query("SELECT * FROM media_items WHERE tripId = :tripId AND isVideo = 1 ORDER BY dateMillis DESC")
    LiveData<List<MediaItem>> getVideosForTrip(String tripId);

    @Query("SELECT * FROM media_items WHERE tripId = :tripId AND isFavorite = 1 ORDER BY dateMillis DESC")
    LiveData<List<MediaItem>> getFavoritesForTrip(String tripId);

    @Query("SELECT * FROM media_items WHERE id = :itemId")
    MediaItem getMediaByIdSync(String itemId);

    @Query("SELECT COUNT(*) FROM media_items WHERE tripId = :tripId")
    LiveData<Integer> getMediaCountForTrip(String tripId);
}
