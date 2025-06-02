# DummyJSONApp

**DummyJSONApp** is a modern Android app built with a Clean Architecture and MVVM approach using Jetpack Compose, Kotlin Coroutines, Flow, Hilt, and Room. It consumes the DummyJSON API, allowing users to browse, filter, and view detailed product data with full offline support. Key features include pagination, FTS-based search, a form screen with validation, and a reactive UI built on modern best practices.


---

## 🚀 Features

- **Product Listing with Pagination**  
  Displays a paginated list of products using Jetpack **Paging 3** with infinite scroll.

- **Offline Caching**  
  Utilizes **Room** database to cache products locally, supporting offline access.

- **Search & Filtering (FTS)**  
  Advanced search using **Full-Text Search (FTS)** in Room for fast, local filtering. While other options like in-memory filtering or remote query filtering were considered, FTS was chosen due to assignment requirements (advanced/partial filtering) and the design choice to preload and store all product data locally. In-memory filtering was rejected due to poor scalability.

- **Smart Refreshing**  
  Uses **DataStore** to persist a flag to decide whether remote data needs fetching.

- **Detailed Product View**  
  Each product includes a detail screen with title, description, price, and thumbnail.

- **Form Screen with Validation**  
  A form screen was implemented (per the assignment) and includes input validation logic. The screen is accessible through the bottom navigation bar as a sub-feature of the main app.

- **Loading & Error States**  
  UI responds appropriately to loading and error scenarios with retry mechanisms.

---

## 🧱 Architecture

This app follows **Clean Architecture** and **MVVM**, separating concerns into three clear layers — Presentation (ViewModel + UI), Domain (Use Cases, Business Logic), and Data (Room DB, Retrofit, Repositories). This promotes testability, scalability, and maintainability.


### Presentation Layer
- Contains **ViewModels**, UI logic, and **UI States**.
- Uses **StateFlow** for reactive UI updates.
- Implements **search debouncing** via `debounce()` and `distinctUntilChanged()`.

### Domain Layer
- Includes **Use Cases** like `GetFilteredProductsUseCase` encapsulating business rules.

### Data Layer
- Manages data from both remote and local sources.
- **Repository Pattern** abstracts data access.
- Uses **Retrofit** for network, **Room** + **FTS** for local caching and filtering.
- **DataStore** holds flags to manage syncing logic.

---

## 📂 Package Structure

```
com.example.dummyjsonapp
├── data
│   ├── local          # Room DB, DAO, entities, FTS setup
│   ├── remote         # Retrofit service, DTOs
│   └── repository     # Repository implementation
├── domain
│   ├── model          # Domain models
│   ├── repository     # Repository interface
│   └── useCases       # Business logic use cases
├── presentation
│   ├── viewmodels     # ViewModel classes, UI State
│   └── ui             # Activities, fragments or composables
├── util               # Resource, Error handling, mappers
└── di                 # Hilt modules and DI config
```

---

## 🛠️ Tech Stack

| Layer                | Technology                                  |
|----------------------|---------------------------------------------|
| UI                   | Jetpack Compose, Navigation, Material3      |
| Domain               | Kotlin, Use Cases                           |
| Data                 | Room, Retrofit, DataStore                   |
| Dependency Injection | Hilt (Dagger)                               |
| Paging               | Jetpack Paging 3                            |
| Search               | Room FTS                                    |
| Async / Reactive     | Coroutines, Flow                            |
| Testing              | JUnit4, MockK, Turbine, Coroutine Test      |

---

## ✅ Testing Strategy (To Improve)

Basic unit tests are included for key logic such as:

- ViewModel behavior and state transitions  
- Utility classes  
- Database functions including FTS filtering and DAO queries

If more time were available, further testing would focus on broader coverage and UI-level interactions.

---

## 🗼️ Screenshots

| Product List | Product Detail | Search | Form Screen |
|--------------|----------------|--------|-------------|


![image](https://github.com/user-attachments/assets/4a69f52c-43de-4158-baec-d585c669d282)

![image](https://github.com/user-attachments/assets/88910a86-5d79-4355-b438-dd2527cf5f85)

![image](https://github.com/user-attachments/assets/3fece114-bc70-46ba-811d-adb78a54df11)


---

## 📦 How to Run

1. Clone the repo:
   ```bash
   git clone https://github.com/ist170542/DummyJSONApp
   ```
2. Open in Android Studio.
3. Run on a device or emulator.

---

> DummyJSONApp was built as an academic assignment and showcases practical application of modern Android principles, including Clean Architecture, reactive programming with Flow, offline-first design, and robust pagination + filtering logic.
