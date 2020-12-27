package me.okexox.hades.modules.integrity;

import me.okexox.hades.data.PlayerData;
import me.okexox.hades.modules.base.interfaces.CheckMove;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.DetectionType;
import org.bukkit.event.player.PlayerMoveEvent;

public class VehicleIntegrity extends Detection implements CheckMove {
    public VehicleIntegrity() {
        super("VehicleIntegrity", DetectionType.Ban);
    }

    @Override
    public void check(PlayerMoveEvent e, PlayerData data) {
        if(e.getPlayer().isInsideVehicle() && e.getPlayer().getVehicle() == null) {
            flag(e.getPlayer());
        }
    }
}
