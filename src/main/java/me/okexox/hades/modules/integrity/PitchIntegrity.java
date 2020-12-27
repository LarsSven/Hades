package me.okexox.hades.modules.integrity;

import me.okexox.hades.data.PlayerData;
import me.okexox.hades.modules.base.interfaces.CheckMove;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.DetectionType;
import org.bukkit.event.player.PlayerMoveEvent;

public class PitchIntegrity extends Detection implements CheckMove {
    public PitchIntegrity() {
        super("PitchIntegrity", DetectionType.Ban);
    }

    @Override
    public void check(PlayerMoveEvent e, PlayerData data) {
        double pitch = e.getTo().getPitch();
        if(pitch < -90 || pitch > 90) {
            flag(e.getPlayer(), "Pitch: " + Math.round(pitch * 100.)/100.);
        }
    }
}
