## 🎵 Audio Application — Java OOP Project

```
A Java console application that simulates an audio streaming platform where users can search for music and podcasts, manage favorites, and persist history using a clean layered architecture.

---

## 📌 About the Project

This project was built independently as part of my Java learning journey.  
Instead of only following tutorials, I designed the project architecture first and then implemented the system based on that structure.

The goal is to demonstrate strong Object-Oriented Programming fundamentals while progressively improving architecture, code quality, and features.

This project is continuously evolving as I learn new concepts.

---

## 🚀 Features

### Core Features (Level 1 – Completed)

- Music search via iTunes API
- Podcast search via iTunes API
- Search history persistence using JSON
- Favorites filtering (rating ≥ 3.0)
- Console menu interaction
- Duplicate prevention system
- Automatic rating system
- Error handling for API failures
- Input validation
- Layered project structure

---

## 🏗️ Architecture

The project follows a layered architecture design:

```

Main (UI Layer)
│
├── Service Layer
│   ├── MusicService
│   └── PodcastService
│
├── Repository Layer
│   ├── MusicRepository
│   ├── PodcastRepository
│   └── HistoryRepository
│
└── Model Layer
├── Music
├── Podcast
└── Playable

```

---

## 📦 Project Structure

```

src/
└── com.vn.challenge
├── model
│   ├── Music.java
│   ├── Podcast.java
│   └── Playable.java
│
├── repository
│   ├── MusicRepository.java
│   ├── PodcastRepository.java
│   └── HistoryRepository.java
│
├── service
│   ├── MusicService.java
│   └── PodcastService.java
│
└── Main.java

```

---

## ⭐ Rating System

Favorites are determined using a simple rating rule:

```

rating >= 3.0

````

Example:

| Title | Rating | Favorite |
|------|--------|----------|
| Wonderwall | 3.75 ⭐ | Yes |
| Boulevard of Broken Dreams | 2.0 ⭐ | No |
| The Alibi | 5.0 ⭐ | Yes |

---

## 💡 OOP Concepts Applied

### Encapsulation

All attributes are private and accessed only through public methods.

Example:

```java
audio.play();
audio.like();
````

Instead of:

```java
audio.totalPlays++;
```

---

### Polymorphism

Different audio types are handled through a common interface:

```java
List<Playable>
```

Allowing the same logic to work with different implementations.

---

### Interfaces

The `Playable` interface defines the contract for audio behavior.

---

### Inheritance (Planned Improvement)

Future refactoring will introduce a base `Audio` class to reduce duplication between `Music` and `Podcast`.

---

## ▶️ How to Run

Clone the repository:

```bash
git clone https://github.com/Vinizeira/audio-application
```

Navigate to project folder:

```bash
cd audio-application
```

Compile:

```bash
javac Main.java
```

Run:

```bash
java Main
```

---

## 🛠️ Technologies

* Java 25
* IntelliJ IDEA
* Gson
* iTunes Search API
* Git
* GitHub

---

## 🗺️ Development Roadmap

### Level 1 — MVP (Completed)

* [x] Core audio models
* [x] API integration
* [x] Favorites system
* [x] JSON persistence
* [x] Console interface
* [x] Basic error handling

### Level 2 — Code Quality (In Progress)

* [ ] Code refactoring
* [ ] Input validation improvements
* [ ] Exception hierarchy
* [ ] Constants organization
* [ ] Naming improvements
* [ ] Remove duplicated logic

### Level 3 — Architecture Improvements

* [ ] DTO layer
* [ ] Mapper classes
* [ ] Better service abstraction
* [ ] Configuration separation
* [ ] Responsibility refactoring

### Level 4 — Feature Expansion

* [ ] Search filters
* [ ] Statistics system
* [ ] Export functionality
* [ ] Favorites improvements

### Level 5 — Advanced Improvements

* [ ] Unit testing (JUnit)
* [ ] Integration tests
* [ ] Logging system
* [ ] Performance improvements
* [ ] Code optimization

---

## 📈 Learning Goals

This project is used to practice:

* Object-Oriented Programming
* Clean Code principles
* Layered architecture
* API integration
* Error handling
* Data persistence
* Software evolution practices
* Git workflow

---

## 🔮 Future Improvements

Planned improvements:

* Unit testing implementation
* Statistics dashboard
* Export features (JSON / CSV)
* Logging framework
* Better architecture separation
* Dependency injection concepts

---

## 🧠 Development Approach

This project follows a learning-driven development approach:

```
Learn concept → Apply in project → Refactor → Improve architecture
```

This allows the project to evolve alongside my skills.

---

## 👨‍💻 Author

**Vinicius Pereira**

Java Developer
````
