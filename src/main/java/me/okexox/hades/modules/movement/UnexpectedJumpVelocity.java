package me.okexox.hades.modules.movement;

import me.okexox.hades.data.PlayerData;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.DetectionType;
import me.okexox.hades.modules.base.FlagType;
import me.okexox.hades.modules.base.interfaces.CheckMove;
import org.bukkit.event.player.PlayerMoveEvent;

import static me.okexox.hades.utility.Settings.GCD;

public class UnexpectedJumpVelocity extends Detection implements CheckMove {
    public UnexpectedJumpVelocity() {
        super("UnexpectedJumpVelocity", FlagType.Experimental, DetectionType.Movement);
    }

    @Override
    public void check(PlayerMoveEvent e, PlayerData data) {
        if(data.getLastYMotion() != 0
            || data.wasSwimming()
            || data.isFlying()
            || e.getTo().getY() % GCD == 0
        ) {
            return;
        }
        double yDiff = e.getTo().getY() - e.getFrom().getY();
        if(yDiff <= 0) {
            return;
        }
        if(Math.abs(yDiff-0.42) > 0.00001) {
            flag(e, e.getPlayer(), "yDiff=" + yDiff);
        }
    }
}
