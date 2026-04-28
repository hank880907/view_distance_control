## What's new

### Bug fix: view distance applied multiple times per permission change

LuckPerms fires `UserDataRecalculateEvent` several times for a single permission mutation (due to internal cache invalidation steps). This caused the view distance notification message to appear multiple times simultaneously. The handler is now debounced so only the final event in each burst takes effect.

### View distance cap (`viewdistancecontrol.max.<N>`)

You can now cap a player's view distance from above without touching their existing group permissions.

Assign `viewdistancecontrol.max.<N>` to a group and the resolved distance (from `default.<N>` / `afk.<N>` nodes and config defaults) will be clamped to at most `N`. If a player has multiple `max` nodes the lowest value wins (most restrictive). The cap applies in both normal and AFK states.

**Example** — limit a restricted group to at most 6 chunks regardless of other groups:

```
lp group restricted permission set viewdistancecontrol.max.6 true
```
