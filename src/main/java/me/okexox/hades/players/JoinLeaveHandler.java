package me.okexox.hades.players;

import me.okexox.hades.data.DataList;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveHandler implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        DataList.addPlayer(e.getPlayer().getName());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        DataList.removePlayer(e.getPlayer().getName());
    }
}
