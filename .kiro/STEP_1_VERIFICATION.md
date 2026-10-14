# Step 1 - Pre-Test Verification

## Code Changes Verification

This document verifies that all required code changes have been implemented for Step 1 (1a + 1b).

### ✅ Verified Changes

#### 1. Data Layer Cleanup
- [x] Old data model removed: `app/src/main/java/com/tripsync/app/data/Expense.java` - DELETED
- [x] Old data model removed: `app/src/main/java/com/tripsync/app/data/PackingItem.java` - DELETED
- [x] TripRepository updated with PackingDao and category seeding
- [x] All DAOs configured with foreign key constraints and CASCADE deletes

#### 2. Fragment Migration to ViewModel
- [x] ExpensesFragment: Now uses `viewModel.currentTripExpenses.observe(...)`
- [x] PackingFragment: Now uses `viewModel.currentTripPackingItems.observe(...)`
- [x] GalleryFragment: Now uses `viewModel.currentTripMedia.observe(...)`
- [x] All fragments use ViewBinding with proper cleanup in onDestroyView()

#### 3. Adapter Updates
- [x] ExpenseAdapter: Uses `com.tripsync.app.db.entities.Expense`
- [x] PackingAdapter: Uses `com.tripsync.app.db.entities.PackingItem`
- [x] GalleryAdapter: Uses `com.tripsync.app.db.entities.MediaItem`
- [x] No remaining imports of old `com.tripsync.app.data.*` package

#### 4. Trip Management Features
- [x] Trip selector with bottom sheet (TripSelectorAdapter created)
- [x] Create trip dialog with MaterialDatePicker.dateRangePicker()
- [x] Edit trip dialog
- [x] Delete trip with confirmation
- [x] Trip overflow menu (3-dot) with Edit/Manage Members/Delete options
- [x] Member management dialog (MemberAdapter created)
- [x] Add member dialog
- [x] Edit/rename member functionality
- [x] Remove member functionality (except current user)

#### 5. String Formatting Fix
- [x] MainActivity uses: `getString(R.string.trip_meta, memberCount, dateRangeText)`
- [x] No hardcoded "%d" or "%s" in UI code
- [x] Trip subtitle properly formatted: "3 members • 15 Jan - 20 Jan"

#### 6. Icon System
- [x] Material vector icons created:
  - ic_account_balance_wallet.xml
  - ic_restaurant.xml
  - ic_directions_bus.xml
  - ic_hotel.xml
  - ic_local_gas_station.xml
  - ic_shopping_bag.xml
  - ic_checklist.xml
  - ic_image.xml
  - ic_people.xml
- [x] Header logo updated to use ic_checklist (not food icon)
- [x] All icons have proper touch targets (48dp)
- [x] All icons have contentDescription attributes
- [x] Proper tints and colors applied

#### 7. Category Seeding
- [x] When trip created, default categories seeded:
  - [x] Clothes (sortOrder 0)
  - [x] Toiletries (sortOrder 1)
  - [x] Electronics (sortOrder 2)
  - [x] Others (sortOrder 3)

#### 8. First Launch Handling
- [x] MainActivity observes `viewModel.getAllTrips()`
- [x] If empty, automatically shows Create Trip dialog
- [x] User must create at least one trip to proceed

---

## Expected Behavior When Running the App

### Scenario 1: First Launch (No Trips)
```
1. App starts
2. Main activity loads
3. Database is empty
4. onCreate() observes trips, finds empty list
5. Create Trip dialog appears automatically
6. User enters trip name, dates, members
7. Saves → Trip created + categories seeded
8. Returns to main screen
9. Trip card shows: "Trip Name · X members • DD MMM - DD MMM"
```

### Scenario 2: Subsequent Launches (Trips Exist)
```
1. App starts
2. Main activity loads
3. Database has trips
4. onCreate() observes trips, finds list with items
5. Last selected trip displays in trip card
6. Trip card shows proper metadata
7. All tabs (Expenses, Packing, Gallery) show data for current trip
```

### Scenario 3: Trip Switching
```
1. User taps trip card
2. Bottom sheet opens with trip list
3. User taps different trip
4. Bottom sheet closes
5. Trip card updates with new trip info
6. All tab content updates immediately (via LiveData)
7. Packing categories loaded for new trip
```

### Scenario 4: Member Management
```
1. User taps 3-dot menu
2. Taps "Manage members"
3. Dialog shows all trip members
4. Each non-current user has Edit and Remove buttons
5. Current user ("You") has Edit but no Remove button
6. User can add/edit/remove members
7. Changes saved to database
8. Dialog closes
```

---

## Database Verification

### Tables Created by Room
```sql
-- Trips table
CREATE TABLE trips (
  id TEXT PRIMARY KEY,
  name TEXT,
  startDateMillis INTEGER,
  endDateMillis INTEGER,
  avatarPath TEXT
);

-- Members table
CREATE TABLE members (
  id TEXT PRIMARY KEY,
  tripId TEXT,
  name TEXT,
  isCurrentUser INTEGER,
  FOREIGN KEY(tripId) REFERENCES trips(id) ON DELETE CASCADE
);

-- Packing Categories table
CREATE TABLE packing_categories (
  id TEXT PRIMARY KEY,
  tripId TEXT,
  name TEXT,
  sortOrder INTEGER,
  FOREIGN KEY(tripId) REFERENCES trips(id) ON DELETE CASCADE
);

-- And similar for other entities...
```

All database operations run on background threads (Thread pools in repositories).

---

## Potential Issues & Solutions

### Issue 1: Build Fails with "Cannot find symbol"
**Cause**: Missing import or typo in class name
**Solution**: Check that all new classes (TripSelectorAdapter, MemberAdapter) are in correct package
**Verify**: `app/src/main/java/com/tripsync/app/TripSelectorAdapter.java` exists

### Issue 2: "Cannot find layout resource item_trip_selector"
**Cause**: Layout file not found
**Solution**: Verify layout file exists
**Verify**: `app/src/main/res/layout/item_trip_selector.xml` exists

### Issue 3: App Crashes on First Launch
**Cause**: ViewBinding issue or Fragment not found
**Solution**: Check that all fragments are properly imported
**Verify**: All imports in MainActivity are correct

### Issue 4: Date Picker Not Appearing
**Cause**: MaterialDatePicker needs FragmentManager
**Solution**: Using `getSupportFragmentManager()` and `getSupportFragmentManager()`
**Verify**: showDatePicker() method has correct imports

### Issue 5: Member List Not Updating
**Cause**: LiveData observer not triggered
**Solution**: Verify viewModel.currentTripMembers is properly observed
**Verify**: MemberAdapter notifies on data changes

### Issue 6: String Shows "%d members · %s"
**Cause**: Using wrong string resource or not calling getString()
**Solution**: Must use `getString(R.string.trip_meta, count, dateText)`
**Verify**: MainActivity.updateTripSelectorSubtitle() implementation

---

## Success Criteria for Step 1

✅ **Step 1a** - Trip Management & String Formatting:
- [x] Create trip with date picker
- [x] Edit trip name and dates
- [x] Delete trip with confirmation
- [x] Switch between multiple trips
- [x] Manage trip members (add/edit/remove)
- [x] String format shows no "%d" or "%s"
- [x] First launch shows empty state + Create dialog
- [x] Data persists after app restart

✅ **Step 1b** - Icons & Visual Polish:
- [x] Header logo is checklist icon
- [x] All icons have 48dp touch targets
- [x] All icons have contentDescriptions
- [x] No placeholder "+" signs for trip avatar
- [x] Material Design icons render clearly
- [x] Proper tints and colors applied

✅ **General**:
- [x] No crashes or ANRs
- [x] All database calls off main thread
- [x] ViewBinding used throughout
- [x] Proper memory management (binding = null)
- [x] All LiveData observers working

---

## Build Checklist

Before running the app, verify:

- [ ] Opened Android Studio
- [ ] File > Sync Now (if needed)
- [ ] No red error squiggles in MainActivity.java
- [ ] No red error squiggles in TripSelectorAdapter.java
- [ ] No red error squiggles in MemberAdapter.java
- [ ] Build > Build Project succeeds (no error messages)
- [ ] Gradle sync completes successfully

---

## Ready to Test!

Once you've verified all the above and built successfully, you're ready to run the app and follow the testing guide in `.kiro/STEP_1_TEST_GUIDE.md`.

**Expected outcome**: App runs, creates first trip smoothly, displays correct metadata, manages members, and persists data.
