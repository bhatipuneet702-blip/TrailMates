package com.tripsync.app.repository;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;

import com.tripsync.app.db.AppDatabase;
import com.tripsync.app.db.dao.MemberDao;
import com.tripsync.app.db.dao.PackingDao;
import com.tripsync.app.db.dao.TripDao;
import com.tripsync.app.db.entities.Member;
import com.tripsync.app.db.entities.PackingCategory;
import com.tripsync.app.db.entities.Trip;

import java.util.List;
import java.util.UUID;

public class TripRepository {
    private static final String PREF_CURRENT_TRIP_ID = "current_trip_id";
    private static final String PREF_NAME = "tripsync_prefs";
    private final TripDao tripDao;
    private final MemberDao memberDao;
    private final PackingDao packingDao;
    private final SharedPreferences preferences;

    public TripRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        this.tripDao = db.tripDao();
        this.memberDao = db.memberDao();
        this.packingDao = db.packingDao();
        this.preferences = application.getSharedPreferences(PREF_NAME, Application.MODE_PRIVATE);
    }

    public LiveData<List<Trip>> getAllTrips() {
        return tripDao.getAllTrips();
    }

    public LiveData<Trip> getTripById(String tripId) {
        return tripDao.getTripById(tripId);
    }

    public String getCurrentTripId() {
        return preferences.getString(PREF_CURRENT_TRIP_ID, null);
    }

    public void setCurrentTripId(String tripId) {
        preferences.edit().putString(PREF_CURRENT_TRIP_ID, tripId).apply();
    }

    public LiveData<Integer> getTripCount() {
        return tripDao.getTripCount();
    }

    public void createTrip(String name, long startDateMillis, long endDateMillis, String[] memberNames) {
        new Thread(() -> {
            String tripId = UUID.randomUUID().toString();
            Trip trip = new Trip(tripId, name, startDateMillis, endDateMillis);
            tripDao.insert(trip);

            // Create members (first one is current user)
            for (int i = 0; i < memberNames.length; i++) {
                String memberId = UUID.randomUUID().toString();
                boolean isCurrentUser = (i == 0);
                Member member = new Member(memberId, tripId, memberNames[i], isCurrentUser);
                memberDao.insert(member);
            }

            // Seed default packing categories
            String[] defaultCategoryNames = {"Clothes", "Toiletries", "Electronics", "Others"};
            for (int i = 0; i < defaultCategoryNames.length; i++) {
                String categoryId = UUID.randomUUID().toString();
                PackingCategory category = new PackingCategory(categoryId, tripId, defaultCategoryNames[i], i);
                packingDao.insertCategory(category);
            }

            // Set as current trip
            setCurrentTripId(tripId);
        }).start();
    }

    public void updateTrip(Trip trip) {
        new Thread(() -> tripDao.update(trip)).start();
    }

    public void deleteTrip(String tripId) {
        new Thread(() -> {
            Trip trip = tripDao.getTripByIdSync(tripId);
            if (trip != null) {
                tripDao.delete(trip);
                // If deleting current trip, switch to another or clear
                if (tripId.equals(getCurrentTripId())) {
                    List<Trip> remainingTrips = tripDao.getAllTripsSync();
                    if (!remainingTrips.isEmpty()) {
                        // Switch to most recent trip (last in list)
                        setCurrentTripId(remainingTrips.get(remainingTrips.size() - 1).getId());
                    } else {
                        // No trips left, clear current trip
                        setCurrentTripId(null);
                    }
                }
            }
        }).start();
    }

    public void addMemberToTrip(String tripId, String memberName) {
        new Thread(() -> {
            String memberId = UUID.randomUUID().toString();
            Member member = new Member(memberId, tripId, memberName, false);
            memberDao.insert(member);
        }).start();
    }

    public void removeMemberFromTrip(String memberId) {
        new Thread(() -> {
            Member member = memberDao.getMemberByIdSync(memberId);
            if (member != null) {
                memberDao.delete(member);
            }
        }).start();
    }

    public void updateMemberName(String memberId, String newName) {
        new Thread(() -> {
            Member member = memberDao.getMemberByIdSync(memberId);
            if (member != null) {
                member.setName(newName);
                memberDao.update(member);
            }
        }).start();
    }

    public List<Member> getMembersForTripSync(String tripId) {
        return memberDao.getMembersForTripSync(tripId);
    }

    public Member getCurrentUserForTrip(String tripId) {
        return memberDao.getCurrentUserForTrip(tripId);
    }

    public LiveData<List<Member>> getMembersForTrip(String tripId) {
        return memberDao.getMembersForTrip(tripId);
    }
}
