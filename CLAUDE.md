# View Distance Control

## Overview

PaperMC plugin for per-player view distance control via LuckPerms permission nodes.

- Package: `org.rainbowhunter.viewdistancecontrol`
- Target: PaperMC 1.21.11, Java 21, Gradle (Kotlin DSL)
- Hard dependencies: LuckPerms, EssentialsX

## Features

- Per-player view distance set via LuckPerms permission nodes; falls back to config default.
- AFK view distance via EssentialsX `AfkStatusChangeEvent`, toggleable in config.
- Reacts dynamically to LuckPerms permission changes and `/vdc reload`.
- Does NOT modify simulation distance.
- Placeholder API integration.

## Permission Nodes

| Node | Purpose |
|------|---------|
| `viewdistancecontrol.default.<N>` | Normal view distance; highest N wins |
| `viewdistancecontrol.afk.<N>` | AFK view distance; highest N wins |
| `viewdistancecontrol.reload` | Allows `/vdc reload` |
| `viewdistancecontrol.check` | Allows `/vdc check <player>` |

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

## Code Rules

- PaperMC API only. No NMS.
- No emojis. Short comments only.
- Keep it simple.
