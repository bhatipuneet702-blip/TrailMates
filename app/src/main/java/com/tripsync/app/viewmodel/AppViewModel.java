package com.tripsync.app.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.tripsync.app.db.entities.Expense;
import com.tripsync.app.db.entities.Member;
import com.tripsync.app.db.entities.MediaItem;
import com.tripsync.app.db.entities.PackingCategory;
import com.tripsync.app.db.entities.PackingItem;
import com.tripsync.app.db.entities.Trip;
import com.tripsync.app.repository.ExpenseRepository;
import com.tripsync.app.repository.MediaRepository;
import com.tripsync.app.repository.PackingRepository;
import com.tripsync.app.repository.TripRepository;

import java.util.List;

public class AppViewModel extends AndroidViewModel {
    private final TripRepository tripRepository;
    private final ExpenseRepository expenseRepository;
    private final PackingRepository packingRepository;
    private final MediaRepository mediaRepository;

    private final MutableLiveData<String> currentTripId = new MutableLiveData<>();
    public LiveData<Trip> currentTrip;
    public LiveData<List<Member>> currentTripMembers;
    public LiveData<List<Expense>> currentTripExpenses;
    public LiveData<Long> currentTripTotalExpenses;
    public LiveData<Long> currentTripYourBalance;
    public LiveData<List<PackingCategory>> currentTripPackingCategories;
    public LiveData<List<PackingItem>> currentTripPackingItems;
    public LiveData<List<MediaItem>> currentTripMedia;

    public AppViewModel(Application application) {
        super(application);
        this.tripRepository = new TripRepository(application);
        this.expenseRepository = new ExpenseRepository(application);
        this.packingRepository = new PackingRepository(application);
        this.mediaRepository = new MediaRepository(application);

        // Set initial current trip ID
        String savedTripId = tripRepository.getCurrentTripId();
        currentTripId.setValue(savedTripId);

        // Observe current trip and derive members, expenses, etc.
        currentTrip = Transformations.switchMap(currentTripId, tripRepository::getTripById);

        currentTripMembers = Transformations.switchMap(currentTripId, tripId -> {
            if (tripId == null) {
                MutableLiveData<List<Member>> empty = new MutableLiveData<>();
                empty.setValue(List.of());
                return empty;
            }
            return tripRepository.getMembersForTrip(tripId);
        });

        currentTripExpenses = Transformations.switchMap(currentTripId, tripId -> {
            if (tripId == null) {
                return new MutableLiveData<>(List.of());
            }
            return expenseRepository.getExpensesForTrip(tripId);
        });

        currentTripTotalExpenses = Transformations.switchMap(currentTripId, tripId -> {
            if (tripId == null) {
                return new MutableLiveData<>(0L);
            }
            return expenseRepository.getTotalExpensesForTrip(tripId);
        });

        currentTripYourBalance = Transformations.switchMap(currentTripId, tripId -> {
            if (tripId == null) {
                return new MutableLiveData<>(0L);
            }
            Member currentUser = tripRepository.getCurrentUserForTrip(tripId);
            if (currentUser == null) {
                return new MutableLiveData<>(0L);
            }
            return expenseRepository.getYourBalanceForTrip(tripId, currentUser.getId());
        });

        currentTripPackingCategories = Transformations.switchMap(currentTripId, tripId -> {
            if (tripId == null) {
                return new MutableLiveData<>(List.of());
            }
            return packingRepository.getCategoriesForTrip(tripId);
        });

        currentTripPackingItems = Transformations.switchMap(currentTripId, tripId -> {
            if (tripId == null) {
                return new MutableLiveData<>(List.of());
            }
            return packingRepository.getItemsForTrip(tripId);
        });

        currentTripMedia = Transformations.switchMap(currentTripId, tripId -> {
            if (tripId == null) {
                return new MutableLiveData<>(List.of());
            }
            return mediaRepository.getMediaForTrip(tripId);
        });
    }

    // Trip operations
    public LiveData<List<Trip>> getAllTrips() {
        return tripRepository.getAllTrips();
    }

    public void setCurrentTrip(String tripId) {
        currentTripId.setValue(tripId);
        tripRepository.setCurrentTripId(tripId);
    }

    public String getCurrentTripIdValue() {
        return currentTripId.getValue();
    }

    public void createTrip(String name, long startDateMillis, long endDateMillis, String[] memberNames) {
        tripRepository.createTrip(name, startDateMillis, endDateMillis, memberNames);
    }

    public void updateTrip(Trip trip) {
        tripRepository.updateTrip(trip);
    }

    public void deleteTrip(String tripId) {
        tripRepository.deleteTrip(tripId);
    }

    public void addMemberToTrip(String tripId, String memberName) {
        tripRepository.addMemberToTrip(tripId, memberName);
    }

    public void removeMemberFromTrip(String memberId) {
        tripRepository.removeMemberFromTrip(memberId);
    }

    public void updateMemberName(String memberId, String newName) {
        tripRepository.updateMemberName(memberId, newName);
    }

    public Member getCurrentUserForCurrentTrip() {
        String tripId = currentTripId.getValue();
        if (tripId == null) return null;
        return tripRepository.getCurrentUserForTrip(tripId);
    }

    // Expense operations
    public void createExpense(String title, String categoryType, long amountInPaise,
                              String payerMemberId, long dateMillis, List<String> participantIds) {
        String tripId = currentTripId.getValue();
        if (tripId != null) {
            expenseRepository.createExpense(tripId, title, categoryType, amountInPaise, payerMemberId, dateMillis, participantIds);
        }
    }

    public void updateExpense(String expenseId, String title, String categoryType, long amountInPaise,
                              String payerMemberId, long dateMillis, List<String> participantIds) {
        expenseRepository.updateExpense(expenseId, title, categoryType, amountInPaise, payerMemberId, dateMillis, participantIds);
    }

    public void deleteExpense(String expenseId) {
        expenseRepository.deleteExpense(expenseId);
    }

    public long calculateYourBalance() {
        String tripId = currentTripId.getValue();
        Member currentUser = getCurrentUserForCurrentTrip();
        if (tripId == null || currentUser == null) return 0;
        return expenseRepository.calculateYourBalance(tripId, currentUser.getId());
    }

    // Packing operations
    public void seedDefaultPackingCategories() {
        String tripId = currentTripId.getValue();
        if (tripId != null) {
            packingRepository.seedDefaultCategories(tripId);
        }
    }

    public void createPackingCategory(String name, int sortOrder) {
        String tripId = currentTripId.getValue();
        if (tripId != null) {
            packingRepository.createCategory(tripId, name, sortOrder);
        }
    }

    public void updatePackingCategory(PackingCategory category) {
        packingRepository.updateCategory(category);
    }

    public void deletePackingCategory(String categoryId, String moveToCategoryId) {
        packingRepository.deleteCategory(categoryId, moveToCategoryId);
    }

    public void createPackingItem(String categoryId, String name) {
        String tripId = currentTripId.getValue();
        if (tripId != null) {
            packingRepository.createItem(tripId, categoryId, name);
        }
    }

    public void updatePackingItem(PackingItem item) {
        packingRepository.updateItem(item);
    }

    public void deletePackingItem(String itemId) {
        packingRepository.deleteItem(itemId);
    }

    // Media operations
    public void createMediaItem(String localPath, boolean isVideo, long durationMs, long dateMillis) {
        String tripId = currentTripId.getValue();
        if (tripId != null) {
            mediaRepository.createMediaItem(tripId, localPath, isVideo, durationMs, dateMillis);
        }
    }

    public void toggleMediaFavorite(String itemId) {
        mediaRepository.toggleFavorite(itemId);
    }

    public void deleteMediaItem(String itemId) {
        mediaRepository.deleteMedia(itemId);
    }

    public void deleteMultipleMediaItems(List<String> itemIds) {
        mediaRepository.deleteMultipleMedia(itemIds);
    }
}
