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

        double speedMax;
        if(player.isOnGround() && !data.isFlying()) {
            speedMax = Settings.MAX_ONGROUND_HSPEED;
        } else {
            speedMax = Settings.MAX_OFFGROUND_HSPEED;
        }
        Location from = e.getFrom();
        Location to = e.getTo();
        double hDistance = Math.sqrt(Math.pow((to.getX()-from.getX()),2) + Math.pow((to.getZ()-from.getZ()),2));
        int speedEffect = DataList.getPlayer(player.getName()).getSpeed();
        speedMax += 0.1 * speedEffect;
        if(hDistance > speedMax) {
            flag(e.getPlayer(), "Speed=" + round(hDistance) + " Max=" + speedMax);
        }
    }
}