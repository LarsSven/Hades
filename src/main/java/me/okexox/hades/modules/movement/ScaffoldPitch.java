package me.okexox.hades.modules.movement;

import me.okexox.hades.data.PersistentData;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.DetectionType;
import me.okexox.hades.modules.base.FlagType;
import me.okexox.hades.modules.base.interfaces.CheckBlockPlace;
import me.okexox.hades.utility.BasicFunctions;
import org.bukkit.Location;
import org.bukkit.event.block.BlockPlaceEvent;

import static me.okexox.hades.utility.BasicFunctions.round;

public class ScaffoldPitch extends Detection implements CheckBlockPlace {
    public ScaffoldPitch() {
        super("ScaffoldPitch", FlagType.Experimental, DetectionType.Movement);
    }

    @Override
    public void check(BlockPlaceEvent e) {
        if(!PersistentData.getScaffoldBlocks().contains(e.getBlockAgainst().getType())) {
            return; //Unsafe block to check pitch on, like slabs, snow, and other non-full blocks
        }
        Location blockLoc = e.getBlockAgainst().getLocation().clone();
        Location aimLoc = e.getPlayer().getEyeLocation();
        //Correct y
        if(e.getBlock().getY() > blockLoc.getY()) { //Placed on a block below
            blockLoc.setY(blockLoc.getY()+1);
        } else if(e.getBlock().getY() == blockLoc.getY()) { //Placed on a one of the 4 vertical sides
            blockLoc.setY(blockLoc.getY()+0.5);
        }
        //Correct Z
        if(e.getBlock().getZ() > blockLoc.getZ()) {
            blockLoc.setZ(blockLoc.getZ()+1);
        } else if(e.getBlock().getZ() == blockLoc.getZ()) {
            blockLoc.setZ(blockLoc.getZ()+0.5);
        }
        //Correct X
        if(e.getBlock().getX() > blockLoc.getX()) {
            blockLoc.setX(blockLoc.getX()+1);
        } else if(e.getBlock().getX() == blockLoc.getX()) {
            blockLoc.setX(blockLoc.getX()+0.5);
        }

        double yDiff = blockLoc.getY()-aimLoc.getY();
        double xDiff = BasicFunctions.getHorizontalDistance(blockLoc, aimLoc);
        double expectedPitch = Math.atan2(yDiff, xDiff) * (180/Math.PI);
        double actualPitch = -e.getPlayer().getLocation().getPitch(); //Pitch is the other way around in mc
        double pitchDiff = Math.abs(expectedPitch-actualPitch);
        if(pitchDiff > 15) {
            flag(e.getPlayer(), "ePitch=" + round(expectedPitch) + " aPitch=" + round(actualPitch) + " pitchDiff=" + round(pitchDiff));
        }
    }
}
