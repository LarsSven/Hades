package me.okexox.hades.modules.events;

import me.okexox.hades.data.ServerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;

public class PistonExtendEvent implements Listener {
    @EventHandler
    public void piston(BlockPistonExtendEvent e) {
        ServerData.getInstance().setPistonExtended(System.currentTimeMillis());
    }
}
