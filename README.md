## 🎵 Audio Application — Java OOP Project

```
A Java console application that simulates an audio streaming platform where users can search for music and podcasts, manage favorites, and persist history using a clean layered architecture.
📌 About the Project
This project was built independently as part of my Java learning journey.
Instead of only following tutorials, I designed the project architecture first and then implemented the system based on that structure.
The goal is to demonstrate strong Object-Oriented Programming fundamentals while progressively improving architecture, code quality, testability, and features.
This project continues to evolve as I learn new concepts and refactor the codebase with more robust engineering practices.
🚀 Features
Core Features
•
Music search via iTunes API
•
Podcast search via iTunes API
•
Search history persistence using JSON
•
Favorites filtering (rating >= 3)
•
Console menu interaction
•
Duplicate prevention system
•
User rating system
•
Error handling for API failures
•
Input validation
•
Layered project structure
Current Improvements Already Implemented
•
Audio base class to reduce duplication
•
Identity-based duplicate prevention for different audio types
•
Centralized audio update and persistence flow
•
Safer local persistence with temporary file replacement
•
Shutdown hook save
•
Centralized iTunes integration base repository
•
Config constants organized in a dedicated config layer
•
JUnit test suite for core rules and repository integration behavior
•
Maven Wrapper support
🏗️ Architecture
The project follows a layered architecture design:
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
📦 Project Structure
src/
└── main/
    └── java/
        └── com/vn/
            ├── config/
            │   └── AppConfig.java
            ├── model/
            │   ├── Audio.java
            │   ├── Music.java
            │   ├── Podcast.java
            │   └── Playable.java
            ├── repository/
            │   ├── HistoryRepository.java
            │   ├── ITunesSearchException.java
            │   ├── ITunesSearchRepository.java
            │   ├── MusicRepository.java
            │   └── PodcastRepository.java
            ├── service/
            │   ├── AudioManagerService.java
            │   ├── AudioUpdateResult.java
            │   ├── AudioWorkflowService.java
            │   ├── FavoritesService.java
            │   ├── MenuService.java
            │   ├── MusicService.java
            │   └── PodcastService.java
            └── Main.java
⭐ Rating System
Favorites are determined using a simple rating rule:
rating >= 3
Example:
Title
Rating
Favorite
Wonderwall
4 ⭐
Yes
Boulevard of Broken Dreams
2 ⭐
No
The Alibi
5 ⭐
Yes
💡 OOP Concepts Applied
Encapsulation
All attributes are protected through constructors, getters, and controlled update methods.
Example:
audio.getTitle();
audio.getRating();
music.setRating(5);
Instead of exposing raw mutable state directly everywhere.
Polymorphism
Different audio types are handled through a common contract:
List<Playable>
Allowing the same logic to work with Music and Podcast.
Interfaces
The Playable interface defines shared behavior and a common identity contract.
Inheritance
The project now includes an Audio base class to reduce duplication between Music and Podcast.
▶️ How to Run
Clone the repository:
git clone https://github.com/Vinizeira/audio-application
Navigate to the project folder:
cd audio-application
Option 1 — PowerShell script
powershell -ExecutionPolicy Bypass -File .\run.ps1
Option 2 — Maven Wrapper
Run tests:
.\mvnw.cmd test
🛠️ Technologies
•
Java 25
•
IntelliJ IDEA
•
org.json
•
iTunes Search API
•
JUnit 5
•
Maven
•
Git
•
GitHub
🗺️ Development Roadmap
Level 1 — MVP
•
[x] Core audio models
•
[x] API integration
•
[x] Favorites system
•
[x] JSON persistence
•
[x] Console interface
•
[x] Basic error handling
Level 2 — Code Quality
•
[x] Main orchestration cleanup
•
[x] Remove duplicated logic
•
[x] Add shared audio base class
•
[x] Centralize update and persistence flow
•
[x] Improve naming and responsibilities
•
[x] Improve input validation
Level 3 — Architecture Improvements
•
[x] Configuration separation
•
[x] Better service orchestration
•
[x] Better repository abstraction
•
[x] Exception hierarchy for iTunes integration
•
[x] Dependency-friendly repository design
Level 4 — Robustness and Maintainability
•
[x] Unit testing with JUnit
•
[x] Repository behavior tests without network
•
[x] Invalid JSON and connection failure handling
•
[x] Maven Wrapper support
•
[x] Documentation improvement
Future Expansion
•
[ ] Search filters
•
[ ] Statistics system
•
[ ] Export functionality
•
[ ] End-to-end console flow tests
•
[ ] Logging system
•
[ ] CI pipeline
📈 Learning Goals
This project is used to practice:
•
Object-Oriented Programming
•
Clean Code principles
•
Layered architecture
•
API integration
•
Error handling
•
Data persistence
•
Automated testing
•
Software evolution practices
•
Git workflow
🔮 Future Improvements
Planned improvements:
•
End-to-end application tests
•
Statistics dashboard
•
Export features (JSON / CSV)
•
Logging framework
•
CI automation
•
Dependency injection concepts
🧠 Development Approach
This project follows a learning-driven development approach:
Learn concept → Apply in project → Refactor → Improve architecture
This allows the project to evolve alongside my skills.
👨‍💻 Author
Vinicius Pereira
Java Developer
