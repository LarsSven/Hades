package me.okexox.hades.modules.movement;

import me.okexox.hades.data.PlayerData;
import me.okexox.hades.modules.base.interfaces.CheckMove;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.FlagType;
import me.okexox.hades.modules.base.interfaces.DetectionType;
import me.okexox.hades.utility.BasicFunctions;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import static me.okexox.hades.utility.BasicFunctions.round;
import static me.okexox.hades.utility.Settings.GCD;

public class NoGravity extends Detection implements CheckMove {
    public NoGravity() {
        super("NoGravity", FlagType.Ban, DetectionType.Movement);
    }

    public void check(PlayerMoveEvent e, PlayerData data) {
        Player player = e.getPlayer();
        if(data.flewRecently()
            || player.isInsideVehicle()
            || (e.getTo().getY() - 0.24918707874468282) % GCD == 0 //Max jump height
            || !BasicFunctions.checkAllBlockAround(e.getPlayer().getLocation(), 0)
            || data.inCombat()) {
            return;
        }
        Location loc = e.getPlayer().getLocation().clone();
        loc.setY(loc.getY()-1);
        double difference = (e.getTo().getY() - e.getFrom().getY());
        if(BasicFunctions.checkAllBlockAround(loc, 0) && difference == 0) {
            flag(e.getPlayer(), "yDif=" + difference + " y=" + round(e.getTo().getY()));
        }
    }
}
