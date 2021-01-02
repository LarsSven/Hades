package me.okexox.hades.modules.movement;

import me.okexox.hades.data.DataList;
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

import static me.okexox.hades.utility.BasicFunctions.*;

public class HorizontalSpeed extends Detection implements CheckMove {
    public HorizontalSpeed() {
        super("HorizontalSpeed", FlagType.Ban, DetectionType.Movement);
    }

    public void check(PlayerMoveEvent e, PlayerData data) {
        Player player = e.getPlayer();

        //Handle a speed from the last tick, needs to be delayed as onGround is delayed a tick.
        if(data.gethDistance() != -1) {
            double hDistance = data.gethDistance();
            double speedMax;
            if(data.getOnGroundMoves() > 3) {
                speedMax = Settings.MAX_ONGROUND_HSPEED;
            } else {
                speedMax = Settings.MAX_OFFGROUND_HSPEED;
            }
            int speedEffect = data.getSpeed();
            speedMax += 0.1 * speedEffect;
            speedMax = (player.isSprinting() ? speedMax * 1.3 : speedMax);

            if(hDistance > speedMax) {
                flag(e, e.getPlayer(), "Speed=" + round(hDistance) + " Max=" + round(speedMax));
            }
            data.sethDistance(-1);
        }

        //Handle current tick
        if(data.inCombat()
            || player.isInsideVehicle()
            || data.wasNearSlimeBlock()) {
            return;
        }

        Location locUnder = e.getFrom().clone();
        Location locAbove = e.getFrom().clone();
        locUnder.setY(locUnder.getY()-1);
        locAbove.setY(locUnder.getY()+2);
        if(!BasicFunctions.checkAllBlockAround(locAbove, 0) &&
                (BasicFunctions.checkAnyBlockAround(locUnder, 79)
                || BasicFunctions.checkAnyBlockAround(locUnder, 174))) {
            return; //Possible ice speed walks
        }

        double hDistance = BasicFunctions.getHorizontalDistance(e.getTo(), e.getFrom());
        data.sethDistance(hDistance);
    }
}