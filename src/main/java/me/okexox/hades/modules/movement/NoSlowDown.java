package me.okexox.hades.modules.movement;

import me.okexox.hades.data.PlayerData;
import me.okexox.hades.modules.base.interfaces.CheckMove;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.FlagType;
import me.okexox.hades.modules.base.DetectionType;
import me.okexox.hades.utility.BasicFunctions;
import me.okexox.hades.utility.Settings;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import static me.okexox.hades.utility.BasicFunctions.round;

public class NoSlowDown extends Detection implements CheckMove {
    public NoSlowDown() {
        super("NoSlowDown", FlagType.Ban, DetectionType.Movement);
    }

    public void check(PlayerMoveEvent e, PlayerData data) {
        Player player = e.getPlayer();
        double maxSpeed = Settings.ONGROUND_SLOWDOWN_SPEED;
        if(!data.isSlowedDown()
            || !data.slowdownTransitioned()
            || player.isInsideVehicle()
            || data.wasNearSlimeBlock()
            || data.inCombat()) {
            return;
        }

        Location loc = player.getLocation();
        loc.setY(loc.getY()-1);
        if(data.getOnGroundMoves() < 4) {
            if(player.isSneaking()) {
                maxSpeed = Settings.OFFGROUND_SNEAKING_SLOWDOWN_SPEED;
            } else {
                maxSpeed = Settings.OFFGROUND_SLOWDOWN_SPEED;
            }
        } else if(loc.getWorld().getBlockTypeIdAt(loc) == 174) {
            maxSpeed = 0.35;
        }
        double speed = BasicFunctions.getHorizontalDistance(e.getTo(), e.getFrom());
        if(speed > maxSpeed) {
            flag(e, player, "Speed=" + round(speed));
        }
    }
}
