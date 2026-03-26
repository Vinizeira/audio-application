# 🎧 Audio Application

> A Java console application that simulates an audio streaming platform where users can search for music and podcasts, rate content, manage favorites, and persist listening history using a layered architecture.

<p align="left">
  <img src="https://img.shields.io/badge/Java-25-red?style=for-the-badge" alt="Java 25" />
  <img src="https://img.shields.io/badge/Maven-Wrapper-blue?style=for-the-badge" alt="Maven Wrapper" />
  <img src="https://img.shields.io/badge/Tests-JUnit%205-success?style=for-the-badge" alt="JUnit 5" />
  <img src="https://img.shields.io/badge/API-iTunes-lightgrey?style=for-the-badge" alt="iTunes API" />
</p>

---

## 📌 About the Project

This project was built independently as part of my Java learning journey.

Instead of only following tutorials, I first designed the structure of the application and then implemented it step by step, improving architecture, code quality, and testability over time.

The main goal is to practice Object-Oriented Programming in a real evolving project, not just in isolated examples.

---

## 🚀 Features

### Core Features

- 🎵 Search music via iTunes API
- 🎙️ Search podcasts via iTunes API
- ⭐ Rate audio results from `0` to `5`
- ❤️ Filter favorites with `rating >= 3`
- 🛡️ Prevent duplicate entries by identity
- 💾 Persist history in `data/History.json`
- 🔒 Save history safely using temporary file replacement
- 🧯 Handle invalid input and API failures
- 🧠 Keep responsibilities separated with a layered design

### Current Engineering Improvements

- Shared `Audio` base class
- `Playable` contract with identity comparison
- `AudioWorkflowService` to keep `Main` lean
- Centralized update and persistence flow in `AudioManagerService`
- `org.json` for persistence and API parsing
- Config constants isolated in `AppConfig`
- Maven Wrapper included
- Unit and repository tests with fake HTTP client

---

## 🏗️ Architecture

The project follows a layered architecture:

```text
Main (Application Entry Point)
│
├── Service Layer
│   ├── AudioWorkflowService
│   ├── AudioManagerService
│   ├── FavoritesService
│   ├── MenuService
│   ├── MusicService
│   └── PodcastService
│
├── Repository Layer
│   ├── ITunesSearchRepository
│   ├── MusicRepository
│   ├── PodcastRepository
│   └── HistoryRepository
│
├── Model Layer
│   ├── Audio
│   ├── Music
│   ├── Podcast
│   └── Playable
│
└── Config Layer
    └── AppConfig
```

---

## 📦 Project Structure

```text
src/
├── main/
│   └── java/
│       └── com/vn/
│           ├── config/
│           │   └── AppConfig.java
│           ├── model/
│           │   ├── Audio.java
│           │   ├── Music.java
│           │   ├── Podcast.java
│           │   └── Playable.java
│           ├── repository/
│           │   ├── HistoryRepository.java
│           │   ├── ITunesSearchException.java
│           │   ├── ITunesSearchRepository.java
│           │   ├── MusicRepository.java
│           │   └── PodcastRepository.java
│           ├── service/
│           │   ├── AudioManagerService.java
│           │   ├── AudioUpdateResult.java
│           │   ├── AudioWorkflowService.java
│           │   ├── FavoritesService.java
│           │   ├── MenuService.java
│           │   ├── MusicService.java
│           │   └── PodcastService.java
│           └── Main.java
└── test/
    └── java/
        └── com/vn/
```

---

## ⭐ Rating System

Favorites are determined using a simple rule:

```text
rating >= 3
```

Example:

| Title | Rating | Favorite |
|------|--------|----------|
| Wonderwall | 4 ⭐ | Yes |
| Boulevard of Broken Dreams | 2 ⭐ | No |
| The Alibi | 5 ⭐ | Yes |

---

## 💡 OOP Concepts Applied

### Encapsulation

State is controlled through constructors, getters, and specific update methods.

```java
audio.getTitle();
audio.getRating();
music.setRating(5);
```

### Polymorphism

Different audio types are handled using a common contract:

```java
List<Playable>
```

This allows shared logic to work with both `Music` and `Podcast`.

### Interfaces

`Playable` defines the shared behavior contract for audio entities.

### Inheritance

`Audio` centralizes common state and behavior shared by `Music` and `Podcast`.

---

## 🧪 Tests

The project currently includes coverage for:

- audio identity rules
- duplicate prevention
- rating updates
- favorites filtering
- history persistence round-trip
- music repository mapping
- podcast repository mapping
- invalid API status handling
- invalid JSON handling
- network failure handling

---

## ▶️ How to Run

Clone the repository:

```bash
git clone https://github.com/Vinizeira/audio-application
cd audio-application
```

### Option 1: PowerShell script

```powershell
powershell -ExecutionPolicy Bypass -File .\run.ps1
```

### Option 2: Maven Wrapper

Run tests:

```powershell
.\mvnw.cmd test
```

---

## 🛠️ Technologies

- Java 25
- Maven Wrapper
- org.json
- JUnit 5
- iTunes Search API
- Git
- GitHub
- IntelliJ IDEA

---

## 🗺️ Development Roadmap

### Level 1 — MVP

- [x] Core audio models
- [x] API integration
- [x] Favorites system
- [x] JSON persistence
- [x] Console interface
- [x] Basic error handling

### Level 2 — Code Quality

- [x] Reduce duplication
- [x] Clean `Main`
- [x] Create shared `Audio` base class
- [x] Improve service responsibilities
- [x] Improve input validation

### Level 3 — Architecture Improvements

- [x] Separate config constants
- [x] Introduce better workflow orchestration
- [x] Improve repository abstraction
- [x] Add custom integration exception
- [x] Make repositories test-friendly

### Level 4 — Robustness and Maintainability

- [x] Add JUnit test suite
- [x] Test repositories without real network
- [x] Handle invalid JSON and connection failures
- [x] Add Maven Wrapper
- [x] Improve documentation

### Future Expansion

- [ ] Search filters
- [ ] Statistics dashboard
- [ ] Export functionality
- [ ] End-to-end console flow tests
- [ ] Logging system
- [ ] CI pipeline

---

## 📈 Learning Goals

This project is used to practice:

- Object-Oriented Programming
- Clean Code
- Layered architecture
- API integration
- Error handling
- Data persistence
- Automated testing
- Software evolution practices
- Git workflow

---

## 🔮 Future Improvements

- End-to-end application tests
- Statistics features
- Export to JSON / CSV
- Logging framework
- CI automation
- Dependency injection concepts

---

## 🧠 Development Approach

```text
Learn concept → Apply in project → Refactor → Improve architecture
```

This project evolves together with my learning process.

---

## 👨‍💻 Author

**Vinicius Pereira**

Java Developer
