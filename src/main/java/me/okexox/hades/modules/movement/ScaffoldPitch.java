package me.okexox.hades.modules.movement;

import me.okexox.hades.data.PersistentData;
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

public class ScaffoldPitch extends Detection implements CheckBlockPlace, CheckMove {
    public ScaffoldPitch() {
        super("ScaffoldPitch", FlagType.Ban, DetectionType.Movement);
    }

    @Override
    public void check(BlockPlaceEvent e, PlayerData data) {
        if(!PersistentData.getScaffoldBlocks().contains(e.getBlockAgainst().getType())) {
            return; //Unsafe block to check pitch on, like slabs, snow, and other non-full blocks
        }
        Location blockLoc = e.getBlockAgainst().getLocation().clone();
        Location aimLoc = e.getPlayer().getEyeLocation();
        BasicFunctions.correctBlocks(e.getBlock().getLocation(), blockLoc);

        double vDiff = blockLoc.getY()-aimLoc.getY();
        double hDiff = BasicFunctions.getHorizontalDistance(blockLoc, aimLoc);
        double expectedPitch = Math.atan2(vDiff, hDiff) * (180/Math.PI);
        data.setScaffoldPitchFlag(expectedPitch);
    }

    @Override
    public void check(PlayerMoveEvent e, PlayerData data) {
        double expectedPitch = data.getScaffoldPitchFlag();
        if(expectedPitch == -180) {
            return;
        }
        double actualPitch = -e.getTo().getPitch();
        double pitchDiff = Math.abs(expectedPitch-actualPitch);
        if(pitchDiff > 15) {
            flag(e, e.getPlayer(), "ePitch=" + round(expectedPitch) + " aPitch=" + round(actualPitch) + " pitchDiff=" + round(pitchDiff));
        }
        data.setScaffoldPitchFlag(-180);
    }
}
