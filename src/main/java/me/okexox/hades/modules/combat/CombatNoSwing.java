package me.okexox.hades.modules.combat;

import me.okexox.hades.data.PlayerData;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.FlagType;
import me.okexox.hades.modules.base.interfaces.CheckCombat;
import me.okexox.hades.modules.base.DetectionType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CombatNoSwing extends Detection implements CheckCombat {

    public CombatNoSwing() {
        super("CombatNoSwing", FlagType.Ban, DetectionType.Combat);
    }

    @Override
    public void check(EntityDamageByEntityEvent e, PlayerData data) {
        if(!data.isArmSwung()) {
            flag(e, (Player)e.getDamager());
        }
        data.setArmSwung(false);
    }
}
