# View Distance Control

## Overview

PaperMC plugin for per-player view distance control via LuckPerms permission nodes.

- Package: `org.rainbowhunter.viewdistancecontrol`
- Target: PaperMC 1.21.11, Java 21, Gradle (Kotlin DSL)
- Hard dependencies: LuckPerms, EssentialsX, PlaceholderAPI

## Features

- Per-player view distance set via LuckPerms permission nodes; falls back to config default.
- AFK view distance via EssentialsX `AfkStatusChangeEvent`, toggleable in config.
- Reacts dynamically to LuckPerms permission changes and `/vdc reload`.
- Does NOT modify simulation distance.

## Permission Nodes

| Node | Purpose |
|------|---------|
| `viewdistancecontrol.default.<N>` | Normal view distance; highest N wins |
| `viewdistancecontrol.afk.<N>` | AFK view distance; highest N wins |
| `viewdistancecontrol.reload` | Allows `/vdc reload` |
| `viewdistancecontrol.check` | Allows `/vdc check <player>` |
| `viewdistancecontrol.afkbypass` | Allows player to bypass afk view distance |

## Config (`config.yml`)

```yaml
default-view-distance: 10       # default view distance if the permission node is not set.
default-afk-view-distance: 4    # default afk view distance if the permission node is not set.
afk-view-distance-enable: true  # enable afk view distance.
notify-player: false            # notify player when their view distance changes.

# The message to sent to player when their view distance changes.
notify-message: "Your view distance have been changed to %viewdistancecontrol_distance%"
```

## Commands

- `/vdc reload` — reloads config and reapplies distances to all online players
- `/vdc check <player>` — shows a player's current view distance

## Testing

Unit tests cover `ViewDistanceManager` logic (permission resolution, AFK switching, config fallback).
Run with: `./gradlew test` — report at `build/reports/tests/test/index.html`.

Unit tests use JUnit 5 + Mockito (with static mocking for `Bukkit`). They do **not** cover LuckPerms
event delivery, EssentialsX AFK events, or chunk loading — verify those on a real server.

## Code Rules

- PaperMC API only. No NMS.
- No emojis. Short comments only.
- Keep it simple and concise.
