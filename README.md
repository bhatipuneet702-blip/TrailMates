# TripSync — Group Travel Manager

> Plan together. Split fairly. Pack privately. Keep trip memories in one place.

**Project status:** 🚧 Planning / early prototype. Features listed below are goals and are not necessarily implemented yet.

## Overview

TripSync is a proposed Android application for groups traveling together. It combines shared expense splitting, personal packing checklists, and a trip media gallery in a single place. The long-term vision includes itineraries, document sharing, and opt-in live location sharing.

The app is being developed as a **Java + XML Android project**. The first milestone is a **single-device, offline prototype**; real-time sharing between different users will require a backend and authentication in a later milestone.

## Planned MVP features

### 1. Shared expense splitter
- Create expenses with a description, amount, payer, and participating travelers.
- Split expenses equally among selected participants.
- Show per-person shares and, later, net balances and settlements.
- **MVP limitation:** Group members and expenses may initially be stored on one device, not synchronized across phones.

### 2. Private packing checklist
- Add packing items and mark them packed or unpacked.
- Remove items and track packing progress.
- Keep each traveler's list private once accounts and synchronization are introduced.
- **MVP limitation:** A local list is private to the app installation; user-level privacy requires authentication and access controls.

### 3. Trip media gallery
- Select photos and videos using Android's system photo picker.
- Preview selected media in the app.
- **MVP limitation:** Selected media is local to the device. Group uploads and sharing will be added with cloud storage.

## Future roadmap

- [ ] **Milestone 1 — Android prototype:** Navigation, expense calculation, packing checklist, local media picker
- [ ] **Milestone 2 — Local persistence:** Save trips, expenses, packing items, and media references with SQLite
- [ ] **Milestone 3 — Better expense splitting:** Unequal shares, settlements, transaction history, reports
- [ ] **Milestone 4 — Real group collaboration:** Authentication, trip creation, invitations, synchronized shared data
- [ ] **Milestone 5 — Cloud gallery and documents:** Secure media uploads, tickets, and booking confirmations
- [ ] **Milestone 6 — Trip itinerary:** Daily activities, reminders, and schedule updates
- [ ] **Milestone 7 — Optional location sharing:** Consent-based, time-limited location sharing with easy opt-out

## Tech stack

| Layer | Technology |
| --- | --- |
| Android app | Java |
| UI | XML layouts / Android Views |
| IDE | Android Studio |
| Build | Gradle (Android Studio project) |
| Local storage (planned) | Android SQLite (`SQLiteOpenHelper`), or a Java-compatible Room 2.x setup |
| Media selection | Android Photo Picker |
| Cloud sync (future) | Firebase or a custom backend — to be decided |
| Maps / location (future) | Android location APIs and a maps SDK — to be decided |

**No separate MySQL or PostgreSQL installation is required for the MVP.**

## Getting started

### Prerequisites

- Android Studio with Android SDK and Build Tools
- A compatible Android phone with USB debugging enabled, or an Android emulator
- Git (recommended for contributing and version control)

### Run the app

1. Clone this repository:

   ```bash
   git clone https://github.com/YOUR_USERNAME/TripSync.git
   ```

2. Open **Android Studio** and select **Open**.
3. Choose the cloned project folder.
4. Let Gradle synchronize and install any requested SDK components.
5. Connect an Android phone with USB debugging enabled, or start an emulator.
6. Select the device and click **Run**.

> Replace `YOUR_USERNAME` with the actual GitHub account name. These steps will work after the Android Studio project files have been pushed to this repository.

## Proposed application sections

```text
TripSync
├── Common Space (group-visible after cloud sync)
│   ├── Expenses
│   ├── Media Gallery
│   ├── Trip Documents [future]
│   ├── Itinerary [future]
│   └── Live Locations [future, opt-in]
└── Private Space (account-private in future release)
    └── Packing Checklist
```

## Privacy and security goals

- Packing lists must not be readable by other group members after user accounts are introduced.
- Shared trip content must be restricted to authorized trip members.
- Location sharing must be **off by default**, explicitly enabled, and easy to stop.
- Uploaded tickets and documents must be protected with appropriate access controls.
- Avoid committing passwords, API keys, service-account files, or real personal travel documents to GitHub.

## Development notes

This project is in early development. Screenshots, implementation details, supported Android versions, and release instructions will be added as the prototype is built.

## Contributing

Suggestions, bug reports, and improvements are welcome through GitHub Issues. Contribution guidelines may be added as the project grows.

## License

No license has been selected yet. Add a `LICENSE` file if you want to specify how others may use or contribute to the code.
