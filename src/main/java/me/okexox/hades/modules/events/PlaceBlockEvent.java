package me.okexox.hades.modules.events;

import me.okexox.hades.Main;
import me.okexox.hades.data.DataList;
import me.okexox.hades.data.PersistentData;
import me.okexox.hades.data.PlayerData;
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
        for(Detection check : PersistentData.getInstance().getChecks()) {
            if(CheckBlockPlace.class.isAssignableFrom(check.getClass())) {
                checks.add((CheckBlockPlace) check);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        PlayerData data = DataList.getPlayer(e.getPlayer().getName());
        for(CheckBlockPlace check : checks) {
            check.check(e, data);
        }
    }
}
