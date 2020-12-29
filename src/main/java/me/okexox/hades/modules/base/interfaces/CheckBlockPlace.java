package me.okexox.hades.modules.base.interfaces;

import me.okexox.hades.data.PlayerData;
import org.bukkit.event.block.BlockPlaceEvent;

public interface CheckBlockPlace {
    void check(BlockPlaceEvent e, PlayerData data);
}
