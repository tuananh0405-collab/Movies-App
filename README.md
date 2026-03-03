# 🎬 Movies App

A feature-rich Android movie browsing application built with **Clean Architecture** and modern Android development practices. The app fetches movie data from [The Movie Database (TMDB) API](https://www.themoviedb.org/documentation/api), allowing users to browse, search, filter, and save their favorite movies — all with a polished, responsive UI.

---

## 📸 Screenshots

| Splash Screen | Movie List | Movie Detail |
|:-:|:-:|:-:|
| Animated splash with slide-in effect | Browse movies in list or grid view | Full details with cast & crew |

| Favorites | Profile | Settings |
|:-:|:-:|:-:|
| Manage your saved movies | Edit user profile with Firebase | Configure filters & categories |

---

## ✨ Features

- **Browse Movies** — View popular, top-rated, upcoming, and now-playing movies from TMDB.
- **List & Grid Views** — Toggle between list and grid layout for browsing movies.
- **Movie Details** — View detailed information including overview, rating, release date, adult flag, and full cast & crew.
- **Favorites** — Mark movies as favorites and persist them locally using Room Database.
- **User Profile** — Create and manage a user profile (name, email, birthday, gender, avatar) stored in Firebase Realtime Database.
- **Reminders** — Schedule movie reminders with date/time pickers, powered by WorkManager notifications.
- **Settings** — Configure movie category filters, sort options, rating filters, release year, and pages per loading.
- **Splash Screen** — Animated splash screen with slide-in transition.
- **Pagination** — Efficient infinite scrolling with Paging 3 library.
- **Reactive Programming** — Fully reactive data streams with RxJava 2.
- **Dependency Injection** — Modular, testable code with Dagger 2.
- **Swipe to Refresh** — Pull-to-refresh support for the movie list.
- **Custom Notifications** — Reminder notifications with custom layouts (collapsed & expanded).
- **Navigation** — Multi-tab navigation using ViewPager2 with nested Navigation Graphs.

---

## 🏗️ Architecture

The project follows **Clean Architecture** principles with clear separation of concerns across three layers:

```
┌─────────────────────────────────────────────────┐
│                 Presentation                     │
│  Activities · Fragments · Adapters · ViewModels  │
├─────────────────────────────────────────────────┤
│                   Domain                         │
│     Models · Use Cases · Repository Interfaces   │
├─────────────────────────────────────────────────┤
│                    Data                          │
│  Repository Impl · Remote (API) · Local (Room)   │
└─────────────────────────────────────────────────┘
```

### Package Structure

```
com.example.anhvt86_3
├── app
│   ├── di                          # Dependency Injection (Dagger)
│   │   ├── AppComponent.java       # Dagger component
│   │   ├── AppModule.java          # App-level providers
│   │   ├── NetworkModule.java      # Retrofit / network providers
│   │   └── MyApplication.java      # Application class
│   └── utils
│       ├── BindingAdapters.java    # Data binding adapters
│       ├── Constants.java          # API keys & base URL
│       ├── Functionutils.java      # Utility functions
│       └── NetworkState.java       # Network state helper
│
├── data
│   ├── datasource
│   │   ├── local
│   │   │   ├── AppDatabase.java    # Room database instance
│   │   │   ├── dao
│   │   │   │   ├── MovieDAO.java   # Movie DAO
│   │   │   │   └── ReminderDAO.java# Reminder DAO
│   │   │   └── entity
│   │   │       └── MovieEntity.java# Room entity
│   │   └── remote
│   │       ├── MoviePagingSource.java  # Paging data source
│   │       ├── MovieRetrofitAPI.java   # Retrofit API interface
│   │       └── response
│   │           ├── CreditsResponse.java
│   │           └── MovieResponse.java
│   ├── mapper
│   │   └── MovieMapper.java        # Entity ↔ Domain mapper
│   └── repository
│       ├── MovieRepositoryImpl.java
│       ├── ReminderRepositoryImpl.java
│       └── UserProfileRepositoryImpl.java
│
├── domain
│   ├── model
│   │   ├── CastMember.java
│   │   ├── CrewMember.java
│   │   ├── ListMovie.java
│   │   ├── Movie.java              # Core movie model (Parcelable)
│   │   ├── Reminder.java
│   │   ├── Settings.java
│   │   └── UserProfile.java
│   ├── repository
│   │   ├── IMovieRepository.java
│   │   ├── ReminderRepository.java
│   │   └── UserRepository.java
│   └── usecase
│       ├── GetMoviesUseCase.java
│       ├── ReminderUseCase.java
│       └── UserProfileUseCase.java
│
└── presentation
    ├── activities
    │   ├── MainActivity.java        # Main host activity
    │   └── SplashScreenActivity.java
    ├── adapters
    │   ├── CastAndCrewAdapter.java
    │   ├── FavoriteAdapter.java
    │   ├── MovieAdapter.java
    │   ├── ReminderAdapter.java
    │   └── ViewPagerAdapter.java
    ├── fragments
    │   ├── AboutFragment.java
    │   ├── DetailFragment.java
    │   ├── FavoriteFragment.java
    │   ├── ListFragment.java
    │   ├── ProfileFragment.java
    │   ├── ReminderFragment.java
    │   └── SettingsFragment.java
    ├── navigations
    │   ├── HostAboutFragment.java
    │   ├── HostFavoriteFragment.java
    │   ├── HostListFragment.java
    │   └── HostSettingsFragment.java
    ├── viewmodel
    │   ├── MovieViewModel.java
    │   ├── ProfileViewModel.java
    │   └── ReminderViewModel.java
    └── workmanager
        └── ReminderWorker.java      # Background reminder notifications
```

---

## 🛠️ Tech Stack

| Category | Technology |
|---|---|
| **Language** | Java |
| **Min SDK** | 26 (Android 8.0 Oreo) |
| **Target SDK** | 34 (Android 14) |
| **Architecture** | Clean Architecture + MVVM |
| **DI** | Dagger 2 |
| **Networking** | Retrofit 2 + OkHttp 4 + Gson |
| **Reactive** | RxJava 2 + RxAndroid |
| **Pagination** | Paging 3 (RxJava2 integration) |
| **Local Database** | Room (SQLite) |
| **Remote Database** | Firebase Realtime Database |
| **Image Loading** | Picasso |
| **Navigation** | Jetpack Navigation Component |
| **Background Tasks** | WorkManager |
| **UI Binding** | Data Binding + View Binding |
| **UI Components** | Material Design Components |
| **Build System** | Gradle (Version Catalog) |

---

## 📋 Prerequisites

- **Android Studio** Hedgehog (2023.1.1) or later
- **JDK 8** or higher
- **Android SDK** with API Level 34 installed
- A valid **TMDB API Key** (see [Configuration](#-configuration))
- A **Firebase project** with Realtime Database enabled (for user profiles)

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone -b dev https://github.com/tuananh0405-collab/Movies-App.git
cd Movies-App
```

### 2. Open in Android Studio

1. Launch **Android Studio**.
2. Select **File → Open** and navigate to the cloned `Movies-App` directory.
3. Wait for Gradle sync to complete.

### 3. Configure the TMDB API Key

The app uses [The Movie Database (TMDB)](https://www.themoviedb.org/) API. To use your own key:

1. Sign up at [themoviedb.org](https://www.themoviedb.org/signup) and generate an API key.
2. Open `app/src/main/java/com/example/anhvt86_3/app/utils/Constants.java`.
3. Replace the existing key with your own:

```java
public static final String API_KEY = "YOUR_TMDB_API_KEY";
```

4. Also update the hardcoded keys in `MovieRetrofitAPI.java` endpoints if needed.

### 4. Configure Firebase

1. Go to the [Firebase Console](https://console.firebase.google.com/) and create a new project (or use an existing one).
2. Add an Android app with package name `com.example.anhvt86_3`.
3. Download the `google-services.json` file and place it in the `app/` directory (replacing the existing one).
4. Enable **Realtime Database** in the Firebase Console.

### 5. Build & Run

```bash
./gradlew assembleDebug
```

Or simply press **Run ▶️** in Android Studio to deploy to an emulator or connected device.

---

## ⚙️ Configuration

### Settings (In-App)

The app provides a comprehensive **Settings** screen where users can configure:

| Setting | Options |
|---|---|
| **Movie Category** | Popular, Top Rated, Upcoming, Now Playing |
| **Sort By** | Release Date, Rating |
| **Movie Rating** | 0–10 (SeekBar filter) |
| **Release Year** | Free-text input |
| **Pages Per Loading** | Number of pages to load per scroll |

---

## 🗂️ Navigation Structure

The app uses a **ViewPager2** with **TabLayout** for top-level navigation across 4 main sections, each hosting its own nested **Navigation Graph**:

```
MainActivity
├── 🏠 List Tab        → HostListFragment
│   ├── ListFragment         (movie browsing)
│   └── DetailFragment       (movie details)
├── ❤️ Favorite Tab    → HostFavoriteFragment
│   ├── FavoriteFragment     (saved movies)
│   └── DetailFragment       (movie details)
├── ⚙️ Settings Tab   → HostSettingsFragment
│   └── SettingsFragment     (app configuration)
└── ℹ️ About Tab       → HostAboutFragment
    ├── AboutFragment        (app info)
    ├── ProfileFragment      (user profile)
    └── ReminderFragment     (scheduled reminders)
```

---

## 🔔 Notifications

The app supports **scheduled movie reminders** using **WorkManager**:

1. Navigate to a movie's detail screen.
2. Tap the reminder button and select a date & time.
3. A **OneTimeWorkRequest** is enqueued with the calculated delay.
4. When triggered, a custom notification is displayed with:
   - Movie title and information
   - Custom collapsed and expanded notification layouts

---

## 🧪 Testing

The project includes basic test scaffolding:

```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

---

## 📁 Key Files

| File | Description |
|---|---|
| `Constants.java` | TMDB API key and base URL |
| `NetworkModule.java` | Retrofit instance configuration |
| `AppModule.java` | Dagger module for app-level dependencies |
| `AppDatabase.java` | Room database with Movie & Reminder entities |
| `MovieRepositoryImpl.java` | Data layer — combines API + local DB |
| `MovieViewModel.java` | Presentation layer — manages movie UI state |
| `MainActivity.java` | Main host with ViewPager2, TabLayout, DrawerLayout |
| `DetailFragment.java` | Movie details with cast/crew and reminder scheduling |
| `SettingsFragment.java` | PreferenceFragment for app configuration |

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. **Fork** the repository.
2. Create a new **feature branch**: `git checkout -b feature/my-feature`.
3. **Commit** your changes: `git commit -m "Add my feature"`.
4. **Push** to the branch: `git push origin feature/my-feature`.
5. Open a **Pull Request** against the `dev` branch.

---

## 📄 License

This project is for educational purposes. Please refer to the repository owner for licensing information.

---

## 🙏 Acknowledgements

- [The Movie Database (TMDB)](https://www.themoviedb.org/) for the movie data API.
- [Picasso](https://square.github.io/picasso/) for image loading.
- [Retrofit](https://square.github.io/retrofit/) for network requests.
- [Dagger](https://dagger.dev/) for dependency injection.
- [RxJava](https://github.com/ReactiveX/RxJava) for reactive programming.
- [Firebase](https://firebase.google.com/) for Realtime Database.

---

<p align="center">
  Made with ❤️ by <a href="https://github.com/tuananh0405-collab">tuananh0405-collab</a>
</p>
