package me.okexox.hades.modules.events;

import me.okexox.hades.modules.base.interfaces.CheckHeal;
import me.okexox.hades.modules.exploits.FastHeal;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import java.util.ArrayList;

public class HealEvent implements Listener {
    private final ArrayList<CheckHeal> checks;

    public HealEvent() {
        checks = new ArrayList<>();
        addChecks();
    }

    private void addChecks() {
        checks.add(new FastHeal());
    }

    @EventHandler
    public void onHeal(EntityRegainHealthEvent e) {
        for(CheckHeal check : checks) {
            check.check(e);
        }
    }
}
