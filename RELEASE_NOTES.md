## What's new

### AFK distance delay

A new `afk-distance-delay` config option (default: `5`) delays the AFK view distance reduction by a configurable number
of seconds after a player goes AFK. Set to `0` for the previous immediate behaviour.
During the AFK delay window, `/vdc get` now reports the player's real AFK state right away. The output shows
`(pending AFK)` while the delay is counting down and `(AFK)` once the reduced distance kicks in.

### New commands: `/vdc get` and `/vdc list`

`/vdc check <player>` has been renamed to `/vdc get <player>`. A new `/vdc list` command prints the view distance of
every online player at once, equivalent to running `/vdc get` on each one.

The associated permission node has been renamed from `viewdistancecontrol.check` to `viewdistancecontrol.get`.