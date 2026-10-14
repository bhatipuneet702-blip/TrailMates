# Step 1 (1a + 1b) - Testing Guide

## Build Instructions

1. Open Android Studio
2. Go to `File > Sync Now` to sync Gradle
3. Run `Build > Clean Project` followed by `Build > Build Project`
4. If successful, you should see "Build Successful" in the Build window

## Testing Checklist

### Part 1a: Trip Selector + Create/Edit/Delete + String Formatting

#### First Launch (Empty State)
- [ ] Run app for the first time (clear app data if you've installed before)
- [ ] App should automatically open "Create Trip" dialog
- [ ] Dialog has fields: Trip Name, Start Date, End Date, Member Names
- [ ] **VERIFY**: No "%d" or "%s" placeholder text visible anywhere

**Expected**: Dialog shows with empty fields ready for input

#### Create First Trip
- [ ] Enter Trip Name: "Goa Vacation"
- [ ] Tap "Start Date" field
  - [ ] MaterialDatePicker appears
  - [ ] Select Jan 15, 2025
  - [ ] Tap OK
- [ ] Tap "End Date" field
  - [ ] Select Jan 20, 2025
  - [ ] Tap OK
- [ ] Enter Member Names: "You, Alice, Bob"
- [ ] Tap "Save" button
- [ ] Dialog closes and returns to main screen

**Expected**: Trip card shows:
- Title: "Goa Vacation"
- Subtitle: "3 members • 15 Jan - 20 Jan"
- Mountain icon visible
- **NO "%d" or "%s" text visible**

#### Trip Selector
- [ ] Tap trip card
- [ ] Bottom sheet opens showing all trips
- [ ] Current trip has a checkmark
- [ ] Tap different trip (if you create one)
- [ ] Bottom sheet closes
- [ ] Trip card updates to show new trip
- [ ] All tab content (Expenses, Packing, Gallery) updates automatically

**Expected**: Bottom sheet shows trip list, tapping switches trip smoothly

#### Create Multiple Trips
- [ ] Tap trip card > bottom sheet
- [ ] Swipe up to see "+ New Trip" or scroll
- [ ] Tap "+ New Trip" (if not visible, use 3-dot menu instead)
- [ ] Create "Manali Trip" (Jan 1-5, 2025) with members "You, Charlie, Diana, Eve"
- [ ] Dialog closes
- [ ] Bottom sheet shows both trips
- [ ] Switch between trips using bottom sheet
- [ ] Each trip card updates

**Expected**: Can create and switch between multiple trips

#### Trip Menu (3-Dot Icon)
- [ ] Tap 3-dot menu in header
- [ ] Popup shows: "Edit trip", "Manage members", "Delete trip"
- [ ] Tap "Edit trip"
  - [ ] Dialog shows current trip name and dates
  - [ ] Change name to "Goa Holiday"
  - [ ] Tap Save
  - [ ] Trip card updates immediately
- [ ] Tap 3-dot menu > "Delete trip"
  - [ ] Confirm dialog appears: "Delete this trip and all its data?"
  - [ ] Tap Delete
  - [ ] Trip deleted, switches to next trip if available
  - [ ] If last trip, Create Trip dialog opens

**Expected**: All menu operations work and update UI

#### Member Management
- [ ] Tap 3-dot menu > "Manage members"
- [ ] Dialog shows list of members:
  - [ ] "You" (marked as "You" in gray, no Remove button)
  - [ ] "Alice" with Edit and Remove buttons
  - [ ] "Bob" with Edit and Remove buttons
- [ ] Tap "Edit" next to "Alice"
  - [ ] Text field with "Alice" appears
  - [ ] Clear and type "Alicia"
  - [ ] Tap Save
  - [ ] List updates, member is now "Alicia"
- [ ] Tap "Remove" next to "Bob"
  - [ ] "Bob" disappears from list
  - [ ] Tap "+ Add Member" at bottom or in dialog
  - [ ] "Add Member" dialog opens
  - [ ] Enter "Ben"
  - [ ] Tap Add
  - [ ] "Ben" appears in list
- [ ] Close dialog
- [ ] Return to main screen

**Expected**: Members can be added, edited, renamed, and removed

#### Data Persistence
- [ ] With trips and members set up, go to Android Studio
- [ ] Click menu: `Run > Stop 'app'` to kill the app
- [ ] Click menu: `Run > Run 'app'` to restart
- [ ] **VERIFY**:
  - [ ] All trips are still there
  - [ ] Current trip selection is preserved
  - [ ] All members are still assigned to their trips
  - [ ] Trip card shows correct metadata (member count, date range)

**Expected**: Data survives app restart

---

### Part 1b: Icons & Touch Targets

#### Icon Appearance
- [ ] Header logo shows checklist icon (✓-like symbol)
- [ ] 3-dot menu icon (⋮) is visible and tappable (48dp touch area)
- [ ] Trip card shows mountain icon (circular background)
- [ ] Trip card chevron (expand arrow) points down
- [ ] All icons have proper color (white on teal header)

**Expected**: All icons render clearly, no placeholder "+" signs

#### Touch Targets
- [ ] Tap 3-dot menu icon - should be easy to tap (48dp)
- [ ] Tap trip card - should be easy to tap entire card
- [ ] Tap chevron - should respond to touch anywhere in 48dp area
- [ ] All buttons in dialogs are touchable

**Expected**: All touch areas are at least 48x48 dp and responsive

#### Bottom Sheet & Dialogs
- [ ] Trip selector bottom sheet shows properly
- [ ] Trip list items are readable and tappable
- [ ] Date picker shows correct date range (Material 3 style)
- [ ] All dialog buttons are properly sized

**Expected**: All UI components render correctly

---

### General Checks

#### Fragment Navigation
- [ ] Tap "Expenses" tab at bottom - Expenses screen appears
- [ ] Tap "Packing List" tab - Packing screen appears
- [ ] Tap "Gallery" tab - Gallery screen appears
- [ ] Tabs are highlighted when active
- [ ] Tab labels are readable

**Expected**: All tabs work, screens display

#### No Crashes
- [ ] Throughout all above tests, app should not crash
- [ ] No ANR (Application Not Responding) dialogs
- [ ] Logcat should have no ERROR messages (warnings are OK)

**Expected**: Smooth operation, no crashes

#### Accessibility
- [ ] All icons have contentDescription attributes
- [ ] Text is readable (contrast is sufficient)
- [ ] Touch targets meet minimum size

**Expected**: No accessibility warnings in Android Studio

---

## Test Results Summary

Fill in after testing:

- [ ] String formatting: No "%d"/"%s" visible
- [ ] Trip selector works
- [ ] Member management works
- [ ] Icons render correctly
- [ ] Data persists after restart
- [ ] No crashes observed
- [ ] All touch targets responsive

---

## Next Steps After Testing

If all tests pass:
- Proceed to Step 2: Expenses (add/edit/delete + calculated cards)

If issues found:
- Document in issues below and fix before proceeding

### Issues Found:
(List any issues encountered during testing)

1. 
2. 
3. 

