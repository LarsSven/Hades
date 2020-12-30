package me.okexox.hades.modules.movement;

import me.okexox.hades.data.PlayerData;
import me.okexox.hades.modules.base.interfaces.CheckMove;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.FlagType;
import me.okexox.hades.modules.base.DetectionType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.player.PlayerMoveEvent;

import static me.okexox.hades.utility.BasicFunctions.round;

public class Jesus extends Detection implements CheckMove {
    public Jesus() {
        super("Jesus", FlagType.Ban, DetectionType.Movement);
    }

    public void check(PlayerMoveEvent e, PlayerData data) {
        //Checks the onground on the next tick due to a delay of the onground value
        if(data.isJesusFlagged()) {
            if(e.getPlayer().isOnGround()) {
                flag(e, e.getPlayer(), "y=" + round(e.getPlayer().getLocation().getY()));
            }
            data.setJesusFlagged(false);
        }

        if(!e.getPlayer().getLocation().getBlock().getType().equals(Material.AIR)
            || !isSafeDistance(e.getFrom().clone())
        ) {
            return;
        }
        Location loc = e.getTo().clone();
        World world = loc.getWorld();
        loc.setY(loc.getY()-1);
        if(world.getBlockAt(loc).isLiquid()) {
            data.setJesusFlagged(true);
        }
    }

    /**
     * Ensures that the player isn't on a Z X coordinate that could allow them to sneak above the water on the next block
     * @return
     */
    private static boolean isSafeDistance(Location loc) {
        double XDecimal = loc.getX() % 1.;
        double ZDecimal = loc.getZ() % 1;
        loc.setX(loc.getX()-1);
        if(XDecimal < 0.3 && !loc.getBlock().isLiquid()) {
            return false;
        }
        loc.setX(loc.getX()+2);
        if(XDecimal > 0.7 && !loc.getBlock().isLiquid()) {
            return false;
        }
        loc.setX(loc.getX()-1);
        loc.setZ(loc.getZ()-1);
        if(ZDecimal < 0.3 && !loc.getBlock().isLiquid()) {
            return false;
        }
        loc.setZ(loc.getZ()+2);
        return !(ZDecimal > 0.7) || loc.getBlock().isLiquid();
    }
}
