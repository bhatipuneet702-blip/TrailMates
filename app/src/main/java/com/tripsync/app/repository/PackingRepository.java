package com.tripsync.app.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.tripsync.app.db.AppDatabase;
import com.tripsync.app.db.dao.PackingDao;
import com.tripsync.app.db.entities.PackingCategory;
import com.tripsync.app.db.entities.PackingItem;

import java.util.List;
import java.util.UUID;

public class PackingRepository {
    private final PackingDao packingDao;
    private final AppDatabase db;

    public PackingRepository(Application application) {
        db = AppDatabase.getInstance(application);
        this.packingDao = db.packingDao();
    }

    public LiveData<List<PackingCategory>> getCategoriesForTrip(String tripId) {
        return packingDao.getCategoriesForTrip(tripId);
    }

    public List<PackingCategory> getCategoriesForTripSync(String tripId) {
        return packingDao.getCategoriesForTripSync(tripId);
    }

    public LiveData<List<PackingItem>> getItemsForTrip(String tripId) {
        return packingDao.getItemsForTrip(tripId);
    }

    public LiveData<List<PackingItem>> getItemsForCategory(String categoryId) {
        return packingDao.getItemsForCategory(categoryId);
    }

    public LiveData<Integer> getPackedCountForTrip(String tripId) {
        return packingDao.getPackedCountForTrip(tripId);
    }

    public LiveData<Integer> getTotalCountForTrip(String tripId) {
        return packingDao.getTotalCountForTrip(tripId);
    }

    public void seedDefaultCategories(String tripId) {
        new Thread(() -> {
            List<PackingCategory> existing = packingDao.getCategoriesForTripSync(tripId);
            if (existing.isEmpty()) {
                String[] defaultNames = {"Clothes", "Toiletries", "Electronics", "Others"};
                for (int i = 0; i < defaultNames.length; i++) {
                    String categoryId = UUID.randomUUID().toString();
                    PackingCategory category = new PackingCategory(categoryId, tripId, defaultNames[i], i);
                    packingDao.insertCategory(category);
                }
            }
        }).start();
    }

    public void createCategory(String tripId, String name, int sortOrder) {
        new Thread(() -> {
            String categoryId = UUID.randomUUID().toString();
            PackingCategory category = new PackingCategory(categoryId, tripId, name, sortOrder);
            packingDao.insertCategory(category);
        }).start();
    }

    public void updateCategory(PackingCategory category) {
        new Thread(() -> packingDao.updateCategory(category)).start();
    }

    public void deleteCategory(String categoryId, String moveToCategoryId) {
        new Thread(() -> {
            try {
                db.runInTransaction(() -> {
                    // If moveToCategory provided, move items there; otherwise just delete them
                    if (moveToCategoryId != null) {
                        List<PackingItem> items = packingDao.getItemsForTripSync(packingDao.getCategoryByIdSync(categoryId).getTripId());
                        for (PackingItem item : items) {
                            if (item.getCategoryId().equals(categoryId)) {
                                item.setCategoryId(moveToCategoryId);
                                packingDao.updateItem(item);
                            }
                        }
                    }
                    PackingCategory category = packingDao.getCategoryByIdSync(categoryId);
                    if (category != null) {
                        packingDao.deleteCategory(category);
                    }
                });
            } catch (Exception e) {
                Log.e("TripSync", "Error deleting category: " + e.getMessage());
            }
        }).start();
    }

    public boolean validateCategoryName(String tripId, String name) {
        if (name == null || name.trim().isEmpty() || name.length() > 20) {
            return false;
        }
        // Check for duplicates (case-insensitive)
        PackingCategory existing = packingDao.getCategoryByNameSync(tripId, name.trim());
        return existing == null;
    }

    public boolean validateItemName(String categoryId, String name) {
        if (name == null || name.trim().isEmpty() || name.length() > 40) {
            return false;
        }
        // Check for duplicates in the same category
        PackingItem existing = packingDao.getItemByNameSync(categoryId, name.trim());
        return existing == null;
    }

    public String getOthersCategoryId(String tripId) {
        PackingCategory others = packingDao.getOthersCategorySync(tripId);
        return others != null ? others.getId() : null;
    }

    public int getPackedCountForTripSync(String tripId) {
        Integer count = packingDao.getPackedCountForTripSync(tripId);
        return count != null ? count : 0;
    }

    public int getTotalCountForTripSync(String tripId) {
        Integer count = packingDao.getTotalCountForTripSync(tripId);
        return count != null ? count : 0;
    }

    public void createItem(String tripId, String categoryId, String name) {
        new Thread(() -> {
            String itemId = UUID.randomUUID().toString();
            PackingItem item = new PackingItem(itemId, tripId, categoryId, name, false);
            packingDao.insertItem(item);
        }).start();
    }

    public void updateItem(PackingItem item) {
        new Thread(() -> packingDao.updateItem(item)).start();
    }

    public void deleteItem(String itemId) {
        new Thread(() -> {
            PackingItem item = packingDao.getItemByIdSync(itemId);
            if (item != null) {
                packingDao.deleteItem(item);
            }
        }).start();
    }

    public Integer getPackedCountForCategory(String categoryId) {
        return packingDao.getPackedCountForCategory(categoryId);
    }

    public Integer getTotalCountForCategory(String categoryId) {
        return packingDao.getTotalCountForCategory(categoryId);
    }
}
