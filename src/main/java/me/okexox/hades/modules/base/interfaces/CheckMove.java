package me.okexox.hades.modules.base.interfaces;

import me.okexox.hades.data.PlayerData;
import org.bukkit.event.player.PlayerMoveEvent;

public interface CheckMove {
    void check(PlayerMoveEvent e, PlayerData data);
}
