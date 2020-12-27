package me.okexox.hades.modules.combat;

import me.okexox.hades.data.PlayerData;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.DetectionType;
import me.okexox.hades.modules.base.interfaces.CheckCombat;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class NoSwing extends Detection implements CheckCombat {

    public NoSwing() {
        super("NoSwing", DetectionType.Ban);
    }

    @Override
    public void check(EntityDamageByEntityEvent e, PlayerData data) {
        if(!data.isArmSwung()) {
            flag((Player)e.getDamager());
        } else {
            data.setArmSwung(false);
        }
    }
}
