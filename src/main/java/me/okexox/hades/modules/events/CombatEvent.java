package me.okexox.hades.modules.events;

import me.okexox.hades.Main;
import me.okexox.hades.data.DataList;
import me.okexox.hades.data.PlayerData;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.interfaces.CheckBlockBreak;
import me.okexox.hades.modules.base.interfaces.CheckCombat;
import me.okexox.hades.modules.base.interfaces.CheckHeal;
import me.okexox.hades.modules.combat.NoSwing;
import me.okexox.hades.modules.combat.WallHit;
import me.okexox.hades.modules.exploits.SelfDamage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;

public class CombatEvent implements Listener {
    private final ArrayList<CheckCombat> checks;

    public CombatEvent() {
        checks = new ArrayList<>();
        addChecks();
    }

    private void addChecks() {
        for(Detection check : Main.data.getChecks()) {
            if(CheckCombat.class.isAssignableFrom(check.getClass())) {
                checks.add((CheckCombat) check);
            }
        }
    }

    @EventHandler
    public void updateCombat(EntityDamageByEntityEvent e) {
        if(!(e.getDamager() instanceof Player)) {
            return;
        }
        PlayerData data = DataList.getPlayer(e.getDamager().getName());

        for(CheckCombat check : checks) {
            check.check(e, data);
        }
    }
}
