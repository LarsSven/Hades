package me.okexox.hades.modules.integrity;

import me.okexox.hades.data.PlayerData;
import me.okexox.hades.modules.base.interfaces.CheckMove;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.FlagType;
import me.okexox.hades.modules.base.DetectionType;
import org.bukkit.event.player.PlayerMoveEvent;

public class VehicleIntegrity extends Detection implements CheckMove {
    public VehicleIntegrity() {
        super("VehicleIntegrity", FlagType.Ban, DetectionType.Integrity);
    }

    @Override
    public void check(PlayerMoveEvent e, PlayerData data) {
        if(e.getPlayer().isInsideVehicle() && e.getPlayer().getVehicle() == null) {
            flag(e, e.getPlayer());
        }
    }
}
