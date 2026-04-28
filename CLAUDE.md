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

## View Distance API

The plugin uses `Player.setSendViewDistance(int)` exclusively — never `Player.setViewDistance(int)`.

- `setViewDistance` controls how many chunks the **server loads** for a player. Reducing it causes chunks to unload.
- `setSendViewDistance` controls how many loaded chunks are **transmitted to the client**. Chunks beyond the send distance remain loaded on the server.

This means the effective per-player view distance is capped by the server's global `view-distance` in `server.properties`. Values configured above that cap silently clamp to the server's loaded radius. This is intentional: the plugin controls what players *see*, not what the server loads.

## Permission Nodes

| Node                              | Purpose                                                        |
|-----------------------------------|----------------------------------------------------------------|
| `viewdistancecontrol.default.<N>` | Normal view distance; highest N wins                           |
| `viewdistancecontrol.afk.<N>`     | AFK view distance; highest N wins                              |
| `viewdistancecontrol.max.<N>`     | Caps resolved view distance to at most N; lowest N wins        |
| `viewdistancecontrol.reload`      | Allows `/vdc reload`                                           |
| `viewdistancecontrol.check`       | Allows `/vdc check <player>`                                   |
| `viewdistancecontrol.afkbypass`   | Allows player to bypass afk view distance                      |

`max.<N>` is applied after `default.<N>` / `afk.<N>` resolution. It lets a restriction group force a lower
ceiling without touching other groups. If multiple `max` nodes are present, the lowest (most restrictive) wins.
The cap also applies to AFK distances.

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
- Fix all IDE warnings when it is possible and appropriate.
- Always run the unit test and build to verify the project works whenever the code is changed.