package me.okexox.hades.modules.movement;

import me.okexox.hades.data.PlayerData;
import me.okexox.hades.modules.base.interfaces.CheckMove;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.FlagType;
import me.okexox.hades.modules.base.DetectionType;
import me.okexox.hades.utility.BasicFunctions;
import me.okexox.hades.utility.Settings;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

public class ExcessiveJump extends Detection implements CheckMove {

    public ExcessiveJump() {
        super("ExcessiveJump", FlagType.Ban, DetectionType.Movement);
    }

    public void check(PlayerMoveEvent e, PlayerData data) {
        Player player = e.getPlayer();
        data.updateSwimming(BasicFunctions.isSwimming(player.getLocation()));
        if(player.isFlying()
           || data.wasSwimming() //You can hit a slab when jumping out of water without it trigger onground, so give swimming a buffer
           || (e.getFrom().getY() >= e.getTo().getY())
           || data.inCombat()
           || player.isInsideVehicle()
           || data.isBadJump()
           || player.getLocation().getBlock().getType().equals(Material.LADDER)) {
            data.setJump(false);
            return;
        }
        data.setJump(true);
        int jumpTime = data.getJumpTime();
        int maxJumpTime = Settings.MAX_JUMP_TIME;
        if(data.getBoatNearby()) {
            maxJumpTime += 1;
        }
        if(data.getJumpEffect() > 0) {
            maxJumpTime += 2 + data.getJumpEffect();
        }
        if(jumpTime > maxJumpTime) {
            flag(e, player, jumpTime + " MoveTicks");
        }
    }
}
