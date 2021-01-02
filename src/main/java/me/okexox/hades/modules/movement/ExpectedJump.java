package me.okexox.hades.modules.movement;

import me.okexox.hades.data.PlayerData;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.DetectionType;
import me.okexox.hades.modules.base.FlagType;
import me.okexox.hades.modules.base.interfaces.CheckMove;
import me.okexox.hades.utility.BasicFunctions;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import static me.okexox.hades.utility.BasicFunctions.round;

/**
 * Checks whether air movements follow the expected physics of a jump
 */
public class ExpectedJump extends Detection implements CheckMove {
    public ExpectedJump() {
        super("ExpectedJump", FlagType.Experimental, DetectionType.Movement);
    }

    @Override
    public void check(PlayerMoveEvent e, PlayerData data) {
        Player player = e.getPlayer();
        double yDifference = e.getTo().getY() - e.getFrom().getY();
        if(yDifference < 0.01) {
            yDifference = 0;
        }
        if(data.inCombat()
           || data.wasNearSlimeBlock()
           || data.isFlying()
           || player.getLocation().getBlock().getType().equals(Material.LADDER)
           || data.wasSwimming()
           || !BasicFunctions.pistonSafety()
           || player.isInsideVehicle()
        ) {
            data.setLastYMotion(yDifference);
            return;
        }

        Location loc = player.getLocation().clone();
        loc.setY(loc.getY() + 2);
        if(!BasicFunctions.checkAllBlockAround(loc, 0)) { //Block above a player's head
            data.setLastYMotion(yDifference);
            return;
        }

        double expectedMotion;
        double lastMotion = data.getLastYMotion();
        if(lastMotion != 0 && yDifference != 0) {
            expectedMotion = (data.getLastYMotion() - 0.08) * 0.98; //0.08 is the gravity, and 0.98 is the drag
            double diff = Math.abs(yDifference - expectedMotion);
            if(diff > 0.0000001) {
                flag(e, player, "expected=" + round(expectedMotion) + " actual=" + round(yDifference) + " diff=" + round(diff) + " last=" + lastMotion);
            }
        }
        data.setLastYMotion(yDifference);
    }
}
