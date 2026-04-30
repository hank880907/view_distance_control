---
description: LuckPerms event behavior reference for this plugin. Use when working on LuckPerms event listeners, debouncing logic, or permission change handling.
user-invocable: false
---

`UserDataRecalculateEvent` fires **multiple times per permission change** (typically 4). This is expected LuckPerms
behavior: a single node mutation triggers several internal cache invalidations — the immediate in-memory update, a
post-storage-save reload, inheritance/context recalculation, and the Bukkit permission attachment rebuild. Each
invalidation dispatches the event independently. To avoid applying the view distance redundantly on each firing, the
`LuckPermsListener` handler should **debounce** per player: cancel any pending scheduled task for that player and
reschedule, so only the final event in the burst takes effect.
