package me.okexox.hades.modules.integrity;

import me.okexox.hades.data.PlayerData;
import me.okexox.hades.modules.base.interfaces.CheckMove;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.DetectionType;
import me.okexox.hades.utility.BasicFunctions;
import org.bukkit.Location;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import static me.okexox.hades.utility.Settings.GCD;

public class OnGroundIntegrity extends Detection implements CheckMove {
    public OnGroundIntegrity() {
        super("OnGroundIntegrity", DetectionType.Experimental);
    }

    public void check(PlayerMoveEvent e, PlayerData data) {
        Player player = e.getPlayer();
        for(Entity entity : e.getPlayer().getWorld().getNearbyEntities(e.getFrom(), 1., 1., 1.)) {
            if(entity instanceof Boat) {
                data.updateBoat(true);
                return; //Boat is too close by to be a reliable detection
            }
        }
        data.updateBoat(false);
        if(data.brokeBlockUnder()) {
            data.setBrokeUnderBlock(false);
            return;
        }
        Location loc = e.getPlayer().getLocation(); //Is cloned at the getter already
        loc.setY(loc.getY()-1);
        if(!BasicFunctions.checkAllBlockAround(loc, 0)
           || data.getBoatNearby()) {
            return;
        }

        double heightFrom = e.getFrom().getY();
        double heightTo = e.getTo().getY();

        if(player.isOnGround() && heightFrom % GCD != 0 && heightTo % GCD != 0) {
            flag(e.getPlayer(), "from=" + e.getFrom().getY() + " to=" + e.getTo().getY());
        }
    }
}
