package com.tripsync.app.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tripsync.app.db.entities.Member;

import java.util.List;

@Dao
public interface MemberDao {
    @Insert
    void insert(Member member);

    @Update
    void update(Member member);

    @Delete
    void delete(Member member);

    @Query("SELECT * FROM members WHERE tripId = :tripId ORDER BY isCurrentUser DESC, name ASC")
    LiveData<List<Member>> getMembersForTrip(String tripId);

    @Query("SELECT * FROM members WHERE tripId = :tripId ORDER BY isCurrentUser DESC, name ASC")
    List<Member> getMembersForTripSync(String tripId);

    @Query("SELECT * FROM members WHERE id = :memberId")
    LiveData<Member> getMemberById(String memberId);

    @Query("SELECT * FROM members WHERE id = :memberId")
    Member getMemberByIdSync(String memberId);

    @Query("SELECT COUNT(*) FROM members WHERE tripId = :tripId")
    LiveData<Integer> getMemberCountForTrip(String tripId);

    @Query("SELECT * FROM members WHERE tripId = :tripId AND isCurrentUser = 1 LIMIT 1")
    Member getCurrentUserForTrip(String tripId);
}
