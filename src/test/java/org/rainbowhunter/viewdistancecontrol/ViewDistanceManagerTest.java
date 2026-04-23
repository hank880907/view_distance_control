package org.rainbowhunter.viewdistancecontrol;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.*;

class ViewDistanceManagerTest {

    private ConfigManager config;
    private ViewDistanceManager manager;
    private Player player;
    private UUID playerId;
    private MockedStatic<Bukkit> bukkit;

    @BeforeEach
    void setUp() {
        config = mock(ConfigManager.class);
        when(config.getDefaultViewDistance()).thenReturn(10);
        when(config.getDefaultAfkViewDistance()).thenReturn(4);
        when(config.isAfkEnabled()).thenReturn(true);
        when(config.isNotifyPlayer()).thenReturn(false);

        manager = new ViewDistanceManager(config);

        playerId = UUID.randomUUID();
        player = mock(Player.class);
        when(player.getUniqueId()).thenReturn(playerId);
        when(player.getEffectivePermissions()).thenReturn(Set.of());
        when(player.getViewDistance()).thenReturn(10);

        bukkit = mockStatic(Bukkit.class);
        bukkit.when(() -> Bukkit.getPlayer(playerId)).thenReturn(player);
    }

    @AfterEach
    void tearDown() {
        bukkit.close();
    }

    @Test
    void noPermission_usesConfigDefault() {
        manager.applyViewDistance(player);
        verify(player).setViewDistance(10);
    }

    @Test
    void singlePermissionNode_usedDirectly() {
        when(player.getEffectivePermissions()).thenReturn(Set.of(
                perm("viewdistancecontrol.default.12")
        ));
        manager.applyViewDistance(player);
        verify(player).setViewDistance(12);
    }

    @Test
    void multipleNodes_highestWins() {
        when(player.getEffectivePermissions()).thenReturn(Set.of(
                perm("viewdistancecontrol.default.8"),
                perm("viewdistancecontrol.default.16"),
                perm("viewdistancecontrol.default.12")
        ));
        manager.applyViewDistance(player);
        verify(player).setViewDistance(16);
    }

    @Test
    void negatedNode_ignored() {
        when(player.getEffectivePermissions()).thenReturn(Set.of(
                new PermissionAttachmentInfo(player, "viewdistancecontrol.default.12", null, false)
        ));
        manager.applyViewDistance(player);
        verify(player).setViewDistance(10);
    }

    @Test
    void afkPlayer_usesAfkConfigDefault() {
        manager.setAfk(playerId, true);
        verify(player).setViewDistance(4);
    }

    @Test
    void afkPlayer_usesAfkPermissionNode() {
        when(player.getEffectivePermissions()).thenReturn(Set.of(
                perm("viewdistancecontrol.afk.2")
        ));
        manager.setAfk(playerId, true);
        verify(player).setViewDistance(2);
    }

    @Test
    void afkPlayer_highestAfkNodeWins() {
        when(player.getEffectivePermissions()).thenReturn(Set.of(
                perm("viewdistancecontrol.afk.2"),
                perm("viewdistancecontrol.afk.6")
        ));
        manager.setAfk(playerId, true);
        verify(player).setViewDistance(6);
    }

    @Test
    void afkDisabled_normalDistanceUsedWhileAfk() {
        when(config.isAfkEnabled()).thenReturn(false);
        when(player.getEffectivePermissions()).thenReturn(Set.of(
                perm("viewdistancecontrol.default.12")
        ));
        manager.setAfk(playerId, true);
        verify(player).setViewDistance(12);
    }

    @Test
    void returnFromAfk_restoresNormalDistance() {
        when(player.getEffectivePermissions()).thenReturn(Set.of(
                perm("viewdistancecontrol.default.12"),
                perm("viewdistancecontrol.afk.2")
        ));
        manager.setAfk(playerId, true);
        verify(player).setViewDistance(2);

        manager.setAfk(playerId, false);
        verify(player).setViewDistance(12);
    }

    @Test
    void removePlayer_clearsAfkState() {
        manager.setAfk(playerId, true);
        manager.removePlayer(playerId);

        // After removal, applying distance should use normal (not AFK) distance
        manager.applyViewDistance(player);
        verify(player, atLeast(1)).setViewDistance(10);
    }

    private PermissionAttachmentInfo perm(String node) {
        return new PermissionAttachmentInfo(player, node, null, true);
    }
}
