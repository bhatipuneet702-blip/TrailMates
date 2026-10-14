package com.tripsync.app.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.tripsync.app.db.AppDatabase;
import com.tripsync.app.db.dao.MediaDao;
import com.tripsync.app.db.entities.MediaItem;

import java.util.List;
import java.util.UUID;

public class MediaRepository {
    private final MediaDao mediaDao;

    public MediaRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        this.mediaDao = db.mediaDao();
    }

    public LiveData<List<MediaItem>> getMediaForTrip(String tripId) {
        return mediaDao.getMediaForTrip(tripId);
    }

    public LiveData<List<MediaItem>> getPhotosForTrip(String tripId) {
        return mediaDao.getPhotosForTrip(tripId);
    }

    public LiveData<List<MediaItem>> getVideosForTrip(String tripId) {
        return mediaDao.getVideosForTrip(tripId);
    }

    public LiveData<List<MediaItem>> getFavoritesForTrip(String tripId) {
        return mediaDao.getFavoritesForTrip(tripId);
    }

    public LiveData<Integer> getMediaCountForTrip(String tripId) {
        return mediaDao.getMediaCountForTrip(tripId);
    }

    public void createMediaItem(String tripId, String localPath, boolean isVideo, long durationMs, long dateMillis) {
        new Thread(() -> {
            String itemId = UUID.randomUUID().toString();
            MediaItem item = new MediaItem(itemId, tripId, localPath, isVideo, durationMs, dateMillis, false);
            mediaDao.insert(item);
        }).start();
    }

    public void toggleFavorite(String itemId) {
        new Thread(() -> {
            MediaItem item = mediaDao.getMediaByIdSync(itemId);
            if (item != null) {
                item.setFavorite(!item.isFavorite());
                mediaDao.update(item);
            }
        }).start();
    }

    public void deleteMedia(String itemId) {
        new Thread(() -> {
            MediaItem item = mediaDao.getMediaByIdSync(itemId);
            if (item != null) {
                mediaDao.delete(item);
            }
        }).start();
    }

    public void deleteMultipleMedia(List<String> itemIds) {
        new Thread(() -> mediaDao.deleteMultiple(itemIds)).start();
    }
}
