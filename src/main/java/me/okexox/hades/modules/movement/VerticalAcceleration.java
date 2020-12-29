package me.okexox.hades.modules.movement;

import me.okexox.hades.data.PlayerData;
import me.okexox.hades.modules.base.interfaces.CheckMove;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.FlagType;
import me.okexox.hades.modules.base.DetectionType;
import me.okexox.hades.utility.BasicFunctions;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

public class VerticalAcceleration extends Detection implements CheckMove {
    public VerticalAcceleration() {
        super("VerticalAcceleration", FlagType.Ban, DetectionType.Movement);
    }

    @Override
    public void check(PlayerMoveEvent e, PlayerData data) {
        double vSpeed = e.getTo().getY()-e.getFrom().getY();
        Player player = e.getPlayer();
        if(player.isFlying()
           || data.wasSwimming()
           || data.inCombat()
           || !BasicFunctions.pistonSafety()
           || player.isInsideVehicle()
           || player.isOnGround()
           || data.isEdgeTeleport()
           || player.getLocation().getBlock().getType().equals(Material.LADDER)) {
            data.setLastVerticalSpeed(vSpeed);
            return;
        }

        if(data.getLastVerticalSpeed() < vSpeed && data.getLastVerticalSpeed() > 0) {
            flag(e.getPlayer(), "Last=" + BasicFunctions.round(data.getLastVerticalSpeed()) + " Now=" + BasicFunctions.round(vSpeed));
        }

        data.setLastVerticalSpeed(vSpeed);
    }
}
