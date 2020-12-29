package me.okexox.hades.modules.events;

import me.okexox.hades.Main;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.interfaces.CheckBlockPlace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;

public class PlaceBlockEvent implements Listener {
    private final ArrayList<CheckBlockPlace> checks;

    public PlaceBlockEvent() {
        checks = new ArrayList<>();
        addChecks();
    }

    private void addChecks() {
        for(Detection check : Main.data.getChecks()) {
            if(CheckBlockPlace.class.isAssignableFrom(check.getClass())) {
                checks.add((CheckBlockPlace) check);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        for(CheckBlockPlace check : checks) {
            check.check(e);
        }
    }
}
