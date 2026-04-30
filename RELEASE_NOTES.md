## overview

This release improves robustness around view distance clamping and fixes two bugs in the LuckPerms listener. A new `debug` config option makes it easier to diagnose when and why listeners fire without having to add temporary logging.

### Added
- New `debug` config option (default: `false`) logs when each listener fires, so misfiring or missing events can be diagnosed without touching the code.

### Changed
- Config defaults (`default-view-distance`, `default-afk-view-distance`) and permission-node-granted distances are now clamped to the server's `view-distance` setting, with a warning logged when clamping occurs.

### Fixed
- Race condition in `LuckPermsListener` debounce logic: concurrent async `UserDataRecalculateEvent` firings could both see an empty pending map and schedule duplicate tasks. The remove→cancel→schedule→put sequence is now synchronized.
- `LuckPermsListener` no longer applies view distance if the player disconnects between when the event fires and when the scheduled task runs.