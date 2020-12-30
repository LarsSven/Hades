package me.okexox.hades.modules.movement;

import me.okexox.hades.data.PlayerData;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.DetectionType;
import me.okexox.hades.modules.base.FlagType;
import me.okexox.hades.modules.base.interfaces.CheckBlockPlace;
import me.okexox.hades.modules.base.interfaces.CheckMove;
import me.okexox.hades.utility.BasicFunctions;
import org.bukkit.Location;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import static me.okexox.hades.utility.BasicFunctions.round;

public class ScaffoldYaw extends Detection implements CheckBlockPlace, CheckMove {
    public ScaffoldYaw() {
        super("ScaffoldYaw", FlagType.Ban, DetectionType.Movement);
    }

    @Override
    public void check(BlockPlaceEvent e, PlayerData data) {
        Location blockLoc = e.getBlockAgainst().getLocation().clone();
        Location aimLoc = e.getPlayer().getEyeLocation();

        BasicFunctions.correctBlocks(e.getBlock().getLocation(), blockLoc);

        double distance = BasicFunctions.getHorizontalDistance(blockLoc, aimLoc);
        if(distance < 1) {
            /*
            When the player looks right below them, slight differences in expected and actual angle
            mean a completely different angle as any angle possible can be described by a position on the block under them.
             */
            return;
        }

        double xDiff = blockLoc.getX()-aimLoc.getX();
        double zDiff = blockLoc.getZ()-aimLoc.getZ();
        double expectedYaw = -Math.atan2(xDiff,zDiff)  * (180/Math.PI);
        double normalizedEYaw = (expectedYaw < 0) ? expectedYaw + 360 : expectedYaw;
        data.setScaffoldYawFlag(normalizedEYaw);
    }

    @Override
    public void check(PlayerMoveEvent e, PlayerData data) {
        if(data.getScaffoldYawFlag() == -1) {
            return;
        }
        double actualYaw = e.getTo().getYaw();
        double correctedYaw = actualYaw % 360;
        double normalizedAYaw = (correctedYaw < 0) ? correctedYaw + 360 : correctedYaw;
        double normalizedEYaw = data.getScaffoldYawFlag();
        double yaw1 = Math.abs(normalizedAYaw - normalizedEYaw);
        double yaw2 = Math.abs(yaw1-360);
        double yawDiff = Math.min(yaw1, yaw2);
        if(yawDiff > 30) {
            flag(e, e.getPlayer(), "eYaw=" + round(normalizedEYaw) + " aYaw=" + round(normalizedAYaw) + " yawDiff=" + round(yawDiff));
        }
        data.setScaffoldYawFlag(-1);
    }
}
