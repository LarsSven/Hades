package me.okexox.hades.modules.base.interfaces;

import me.okexox.hades.data.PlayerData;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public interface CheckCombat {
    void check(EntityDamageByEntityEvent e, PlayerData data);
}
