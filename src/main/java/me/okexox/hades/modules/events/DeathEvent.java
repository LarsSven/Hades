package me.okexox.hades.modules.events;

import me.okexox.hades.data.DataList;
import me.okexox.hades.data.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathEvent implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        PlayerData data = DataList.getPlayer(e.getEntity().getName());
        data.setSlowedDown(false);
    }
}
