@README.md

# Technical Requirements

- Package: `org.rainbowhunter.viewdistancecontrol`
- Target: PaperMC 1.21.11, Java 21, Gradle (Kotlin DSL)
- Hard dependencies: LuckPerms, EssentialsX, PlaceholderAPI

# Testing

Unit tests cover `ViewDistanceManager` logic (permission resolution, AFK switching, config fallback).
Run with: `./gradlew test` — report at `build/reports/tests/test/index.html`.

Unit tests use JUnit 5 + Mockito (with static mocking for `Bukkit`). They do **not** cover LuckPerms
event delivery, EssentialsX AFK events, or chunk loading — verify those on a real server.

# Coding Rules
- PaperMC API only. No NMS.
- No emojis.
- Keep it simple and concise; Prefer self-explanatory code over comment. Keep comment short and concise.
- Fix all IDE warnings when it is possible and appropriate.
- Always run the unit test and build to verify the project works whenever the code is changed.
- When adding new LuckPerms permission node, you also need to update the resources/plugin.yml file.
- Always keep the README.md file up-to-date.