package me.okexox.hades.modules.events;

import me.okexox.hades.data.DataList;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class SneakEvent implements Listener {
    @EventHandler
    public void sneakEvent(PlayerToggleSneakEvent e) {
        DataList.getPlayer(e.getPlayer().getName()).setSlowedDown(!e.getPlayer().isSneaking());
    }
}
