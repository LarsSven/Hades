package me.okexox.hades.modules.movement;

import me.okexox.hades.data.PlayerData;
import me.okexox.hades.modules.base.interfaces.CheckMove;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.FlagType;
import me.okexox.hades.modules.base.DetectionType;
import me.okexox.hades.utility.BasicFunctions;
import me.okexox.hades.utility.Settings;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import static me.okexox.hades.utility.BasicFunctions.round;

public class VerticalSpeed extends Detection implements CheckMove {
    public VerticalSpeed() {
        super("VerticalSpeed", FlagType.Ban, DetectionType.Movement);
    }

    public void check(PlayerMoveEvent e, PlayerData data) {
        Player player = e.getPlayer();
        if(player.isInsideVehicle()
            || data.inCombat()
            || data.isBadJump()
            || data.isEdgeTeleport()
            || !BasicFunctions.pistonSafety()
            || player.isOnGround()
        ) {
            return;
        }
        Location loc = e.getPlayer().getLocation().clone();
        loc.setY(loc.getY()-1);

        double vDistance = e.getTo().getY() - e.getFrom().getY();
        if(vDistance > 0) {
            double maxDistance = Settings.MAX_VERTICAL_BASE;
            if(data.wasSwimming() && e.getPlayer().isFlying()) {
                maxDistance = Settings.MAX_VERTICAL_WATERFLY;
            } else {
                maxDistance += 0.15 * data.getJumpEffect();
            }
            if(vDistance > maxDistance) {
                flag(e.getPlayer(), "speed=" + round(vDistance) + " newLoc=" + round(e.getTo().getY()));
            }
        }
    }

}
