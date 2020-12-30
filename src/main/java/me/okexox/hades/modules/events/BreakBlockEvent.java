package me.okexox.hades.modules.events;

import me.okexox.hades.Main;
import me.okexox.hades.data.PersistentData;
import me.okexox.hades.data.PlayerData;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.interfaces.CheckBlockBreak;
import me.okexox.hades.modules.base.interfaces.CheckCombat;
import me.okexox.hades.modules.exploits.Nuker;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;

public class BreakBlockEvent implements Listener {
    private final ArrayList<CheckBlockBreak> checks;

    public BreakBlockEvent() {
        checks = new ArrayList<>();
        addChecks();
    }

    private void addChecks() {
        for(Detection check : PersistentData.getInstance().getChecks()) {
            if(CheckBlockBreak.class.isAssignableFrom(check.getClass())) {
                checks.add((CheckBlockBreak) check);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(org.bukkit.event.block.BlockBreakEvent e) {
        for(CheckBlockBreak check : checks) {
            check.check(e);
        }
    }

    private void updateData(org.bukkit.event.block.BlockBreakEvent e, PlayerData data) {
        if(isUnderPlayer(e.getBlock().getLocation(), e.getPlayer().getLocation())) {
            data.setBrokeUnderBlock(true);
        }
    }

    private boolean isUnderPlayer(Location blockLoc, Location playerLoc) {
        return blockLoc.getY() == playerLoc.getY() - 1
                && Math.abs(blockLoc.getZ() - playerLoc.getZ()) < 1.3
                && Math.abs(blockLoc.getX() - playerLoc.getX()) < 1.3;
    }
}

