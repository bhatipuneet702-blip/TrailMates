package com.tripsync.app.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tripsync.app.db.entities.PackingCategory;
import com.tripsync.app.db.entities.PackingItem;

import java.util.List;

@Dao
public interface PackingDao {
    // Categories
    @Insert
    void insertCategory(PackingCategory category);

    @Update
    void updateCategory(PackingCategory category);

    @Delete
    void deleteCategory(PackingCategory category);

    @Query("SELECT * FROM packing_categories WHERE tripId = :tripId ORDER BY sortOrder ASC")
    LiveData<List<PackingCategory>> getCategoriesForTrip(String tripId);

    @Query("SELECT * FROM packing_categories WHERE tripId = :tripId ORDER BY sortOrder ASC")
    List<PackingCategory> getCategoriesForTripSync(String tripId);

    @Query("SELECT * FROM packing_categories WHERE id = :categoryId")
    PackingCategory getCategoryByIdSync(String categoryId);

    // Items
    @Insert
    void insertItem(PackingItem item);

    @Update
    void updateItem(PackingItem item);

    @Delete
    void deleteItem(PackingItem item);

    @Query("SELECT * FROM packing_items WHERE tripId = :tripId ORDER BY categoryId ASC, name ASC")
    LiveData<List<PackingItem>> getItemsForTrip(String tripId);

    @Query("SELECT * FROM packing_items WHERE tripId = :tripId ORDER BY categoryId ASC, name ASC")
    List<PackingItem> getItemsForTripSync(String tripId);

    @Query("SELECT * FROM packing_items WHERE categoryId = :categoryId ORDER BY name ASC")
    LiveData<List<PackingItem>> getItemsForCategory(String categoryId);

    @Query("SELECT * FROM packing_items WHERE id = :itemId")
    PackingItem getItemByIdSync(String itemId);

    @Query("SELECT COUNT(*) FROM packing_items WHERE categoryId = :categoryId AND isPacked = 1")
    Integer getPackedCountForCategory(String categoryId);

    @Query("SELECT COUNT(*) FROM packing_items WHERE categoryId = :categoryId")
    Integer getTotalCountForCategory(String categoryId);

    @Query("SELECT COUNT(*) FROM packing_items WHERE tripId = :tripId AND isPacked = 1")
    LiveData<Integer> getPackedCountForTrip(String tripId);

    @Query("SELECT COUNT(*) FROM packing_items WHERE tripId = :tripId")
    LiveData<Integer> getTotalCountForTrip(String tripId);
}
