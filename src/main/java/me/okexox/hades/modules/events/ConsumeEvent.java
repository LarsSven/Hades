package me.okexox.hades.modules.events;

import me.okexox.hades.data.DataList;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class ConsumeEvent implements Listener {
    @EventHandler
    public void finishSlowdown(PlayerItemConsumeEvent e) {
        DataList.getPlayer(e.getPlayer().getName()).setSlowedDown(false);
    }
}
