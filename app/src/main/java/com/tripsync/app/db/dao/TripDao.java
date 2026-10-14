package com.tripsync.app.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tripsync.app.db.entities.Trip;

import java.util.List;

@Dao
public interface TripDao {
    @Insert
    void insert(Trip trip);

    @Update
    void update(Trip trip);

    @Delete
    void delete(Trip trip);

    @Query("SELECT * FROM trips ORDER BY startDateMillis DESC")
    LiveData<List<Trip>> getAllTrips();

    @Query("SELECT * FROM trips WHERE id = :tripId")
    LiveData<Trip> getTripById(String tripId);

    @Query("SELECT * FROM trips WHERE id = :tripId")
    Trip getTripByIdSync(String tripId);

    @Query("SELECT COUNT(*) FROM trips")
    LiveData<Integer> getTripCount();

    @Query("SELECT * FROM trips ORDER BY startDateMillis DESC")
    List<Trip> getAllTripsSync();
}
