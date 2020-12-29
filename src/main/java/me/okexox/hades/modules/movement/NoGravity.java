package me.okexox.hades.modules.movement;

import me.okexox.hades.data.PlayerData;
import me.okexox.hades.modules.base.interfaces.CheckMove;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.FlagType;
import me.okexox.hades.modules.base.DetectionType;
import me.okexox.hades.utility.BasicFunctions;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import static me.okexox.hades.utility.BasicFunctions.round;

public class NoGravity extends Detection implements CheckMove {
    public NoGravity() {
        super("NoGravity", FlagType.Ban, DetectionType.Movement);
    }

    public void check(PlayerMoveEvent e, PlayerData data) {
        Player player = e.getPlayer();
        double difference = (e.getTo().getY() - e.getFrom().getY());
        if(data.flewRecently()
            || player.isInsideVehicle()
            || !BasicFunctions.checkAllBlockAround(e.getPlayer().getLocation(), 0)
        ) {
            data.setyDiff(difference);
            return;
        }
        Location loc = e.getPlayer().getLocation().clone();
        loc.setY(loc.getY()-1);
        if(BasicFunctions.checkAllBlockAround(loc, 0) && difference == 0 && data.getyDiff() == 0) {
            flag(e.getPlayer(), "yDif=" + difference + " y=" + round(e.getTo().getY()));
        }
        data.setyDiff(difference);
    }
}
