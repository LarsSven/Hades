package me.okexox.hades.modules.events;

import me.okexox.hades.data.DataList;
import me.okexox.hades.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageEvent implements Listener {
    @EventHandler
    public void updateCombat(EntityDamageEvent e) {
        if(!(e.getEntity() instanceof Player)) {
            return;
        }
        PlayerData data = DataList.getPlayer(e.getEntity().getName());
        data.setCombat(8);
    }
}
