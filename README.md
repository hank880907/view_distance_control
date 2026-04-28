# ViewDistanceControl

A PaperMC plugin that sets per-player view distance via LuckPerms permission nodes. Supports a reduced view distance for
AFK players via EssentialsX.

## Requirements

- PaperMC 1.21.11+
- LuckPerms
- EssentialsX 2.21.2+
- PlaceholderAPI

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

The `notify-message` supports `&` color codes and other PlaceholderAPI placeholders.

## Permissions

| Node                              | Description                                                              |
|-----------------------------------|--------------------------------------------------------------------------|
| `viewdistancecontrol.default.<N>` | Sets normal view distance to N. Highest granted value wins.              |
| `viewdistancecontrol.afk.<N>`     | Sets AFK view distance to N. Highest granted value wins.                 |
| `viewdistancecontrol.max.<N>`     | Caps the resolved view distance to at most N. Lowest granted value wins. |
| `viewdistancecontrol.reload`      | Allows `/vdc reload`                                                     |
| `viewdistancecontrol.check`       | Allows `/vdc check <player>`                                             |
| `viewdistancecontrol.afkbypass`   | Bypasses AFK view distance reduction                                     |

**Example** — give a VIP group a view distance of 16:

```
lp group vip permission set viewdistancecontrol.default.16 true
```

**Example** — cap a restricted group to at most 6 chunks, regardless of other groups:

```
lp group restricted permission set viewdistancecontrol.max.6 true
```

## PlaceholderAPI

| Placeholder                      | Description                    |
|----------------------------------|--------------------------------|
| `%viewdistancecontrol_distance%` | Player's current view distance |

## Commands

| Command               | Description                                                  |
|-----------------------|--------------------------------------------------------------|
| `/vdc reload`         | Reloads config and reapplies distances to all online players |
| `/vdc check <player>` | Shows a player's current view distance and AFK state         |