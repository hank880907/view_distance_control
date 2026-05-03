# View Distance Control

View Distance Control gives different chunk view distances to different groups of players on your server. Assign larger
distances as a perk for donors or VIPs, limit guests to save bandwidth, or let the plugin automatically pull back the
view distance for AFK players and restore it when they return. Getting started takes a single LuckPerms command per
group; players with no matching permission fall back to the defaults in config.

> **Note:** This plugin controls how much of the world is *streamed* to each player, not how much the server loads.
> Players see fewer chunks, but the server still keeps the same area loaded. If you want to reduce server load, lower
`simulation-distance` in `server.properties` instead.

## Dependencies

- LuckPerms
- EssentialsX
- PlaceholderAPI

## Configuration

`plugins/ViewDistanceControl/config.yml`:

```yaml
default-view-distance: 10       # Fallback view distance
default-afk-view-distance: 4    # Fallback AFK view distance
afk-view-distance-enable: true  # Set to false to disable AFK distance reduction
afk-distance-delay: 5           # Seconds before AFK distance applies (0 = immediate)
notify-player: false            # Notify player when their view distance changes
notify-message: "Your view distance has been changed to %viewdistancecontrol_distance%"
console-log: false              # Log view distance changes to the console
debug: false                    # Log debug messages for diagnosing listener triggers
```

The `notify-message` supports `&` color codes and other PlaceholderAPI placeholders.

## Permissions

| Node                              | Description                                                              |
|-----------------------------------|--------------------------------------------------------------------------|
| `viewdistancecontrol.default.<N>` | Sets normal view distance to N. Highest granted value wins.              |
| `viewdistancecontrol.afk.<N>`     | Sets AFK view distance to N. Highest granted value wins.                 |
| `viewdistancecontrol.max.<N>`     | Caps the resolved view distance to at most N. Lowest granted value wins. |
| `viewdistancecontrol.reload`      | Allows `/vdc reload`                                                     |
| `viewdistancecontrol.get`         | Allows `/vdc get <player>` and `/vdc list`                               |
| `viewdistancecontrol.afkbypass`   | Bypasses AFK view distance reduction                                     |

**Example** — give a VIP group a view distance of 16:

```
lp group vip permission set viewdistancecontrol.default.16 true
```

**Example** — cap a restricted group to at most 6 chunks, regardless of other groups:

```
lp group restricted permission set viewdistancecontrol.max.6 true
```

> **Note:** The view distance is capped by `view-distance` in `server.properties`. If a value greater than the
`view-distance` is set, the `view-distnace` would apply.

## PlaceholderAPI

| Placeholder                      | Description                    |
|----------------------------------|--------------------------------|
| `%viewdistancecontrol_distance%` | Player's current view distance |

## Commands

| Command             | Description                                                  |
|---------------------|--------------------------------------------------------------|
| `/vdc reload`       | Reloads config and reapplies distances to all online players |
| `/vdc get <player>` | Shows a player's current view distance and AFK state         |
| `/vdc list`         | Shows all online players' view distances                     |