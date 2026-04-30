### Added
- New `afk-distance-delay` config option (default: `5`) delays AFK view distance reduction by a configurable number of seconds after a player goes AFK. Set to `0` for the previous immediate behaviour.
- New `console-log` config option (default: `false`) logs view distance changes to the server console.
- New `/vdc list` command shows every online player's current view distance at once.

### Changed
- `/vdc check <player>` renamed to `/vdc get <player>`; the associated permission node is renamed from `viewdistancecontrol.check` to `viewdistancecontrol.get`.
- `/vdc get` (and `/vdc list`) now show `(pending AFK)` while the AFK delay is counting down, so the real AFK state is visible immediately even before the view distance switches.
- View distance is now only set on the player when the value actually changes; notifications and console logs are suppressed for no-op updates.
n renamed from `viewdistancecontrol.check` to `viewdistancecontrol.get`.