# ViewDistanceControl

A PaperMC plugin that sets per-player view distance via LuckPerms permission nodes. Supports a reduced view distance for AFK players via EssentialsX.

## Requirements

- PaperMC 1.21.11+
- LuckPerms
- EssentialsX 2.21.2+
- PlaceholderAPI (optional)

## Installation

1. Build with `./gradlew jar` or download the release jar.
2. Drop `ViewDistanceControl-*.jar` into your server's `plugins/` folder.
3. Restart the server.

## Configuration

`plugins/ViewDistanceControl/config.yml`:

```yaml
default-view-distance: 10       # Fallback view distance
default-afk-view-distance: 4    # Fallback AFK view distance
afk-view-distance-enable: true  # Set to false to disable AFK distance reduction
notify-player: false            # Notify player when their view distance changes
notify-message: "Your view distance has been changed to %viewdistancecontrol_distance%"
```

The `notify-message` supports `&` color codes. If PlaceholderAPI is installed, other PAPI placeholders are also resolved.

## Permissions

| Node | Description |
|------|-------------|
| `viewdistancecontrol.default.<N>` | Sets normal view distance to N. Highest granted value wins. |
| `viewdistancecontrol.afk.<N>` | Sets AFK view distance to N. Highest granted value wins. |
| `viewdistancecontrol.reload` | Allows `/vdc reload` |
| `viewdistancecontrol.check` | Allows `/vdc check <player>` |

**Example** — give a group a view distance of 12:
```
lp group vip permission set viewdistancecontrol.default.12 true
```

## PlaceholderAPI

| Placeholder | Description |
|-------------|-------------|
| `%viewdistancecontrol_distance%` | Player's current view distance |

## Commands

| Command | Description |
|---------|-------------|
| `/vdc reload` | Reloads config and reapplies distances to all online players |
| `/vdc check <player>` | Shows a player's current view distance and AFK state |

# Disclaimer

This is an AI generated Project. I have reviewed it, but please use it with care.