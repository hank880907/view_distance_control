---
description: PaperMC view distance API reference for this plugin. Use when working on view distance code, Player API calls, chunk transmission, or any code that sets per-player view distances.
user-invocable: false
---

The plugin uses `Player.setSendViewDistance(int)` exclusively — never `Player.setViewDistance(int)`.

- `setViewDistance` controls how many chunks the **server loads** for a player. Reducing it causes chunks to unload.
- `setSendViewDistance` controls how many loaded chunks are **transmitted to the client**. Chunks beyond the send
  distance remain loaded on the server.

This means the effective per-player view distance is capped by the server's global `view-distance` in
`server.properties`. Values configured above that cap silently clamp to the server's loaded radius. This is intentional:
the plugin controls what players *see*, not what the server loads.
