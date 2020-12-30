package me.okexox.hades.modules.events;

import me.okexox.hades.Main;
import me.okexox.hades.data.PersistentData;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.interfaces.CheckCombat;
import me.okexox.hades.modules.base.interfaces.CheckHeal;
import me.okexox.hades.modules.base.interfaces.CheckMove;
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
        for(Detection check : PersistentData.getInstance().getChecks()) {
            if(CheckHeal.class.isAssignableFrom(check.getClass())) {
                checks.add((CheckHeal) check);
            }
        }
    }

    @EventHandler
    public void onHeal(EntityRegainHealthEvent e) {
        for(CheckHeal check : checks) {
            check.check(e);
        }
    }
}
