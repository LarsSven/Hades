package me.okexox.hades.modules.movement;

import me.okexox.hades.data.PlayerData;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.DetectionType;
import me.okexox.hades.modules.base.FlagType;
import me.okexox.hades.modules.base.interfaces.CheckBlockPlace;
import me.okexox.hades.modules.base.interfaces.CheckMove;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class ScaffoldBlockFace extends Detection implements CheckBlockPlace {
    public ScaffoldBlockFace() {
        super("ScaffoldBlockFace", FlagType.Ban, DetectionType.Movement);
    }

    @Override
    public void check(BlockPlaceEvent e, PlayerData data) {
        Vector against = e.getBlockAgainst().getLocation().toVector();
        Vector difference = e.getBlock().getLocation().toVector().subtract(e.getBlockAgainst().getLocation().toVector());
        Vector playerEye = e.getPlayer().getEyeLocation().toVector();
        if(difference.getY() == 1 && playerEye.getY() < against.getY()+1) {
            flag(e, e.getPlayer());
        } else if(difference.getY() == -1 && playerEye.getY() > against.getY()) {
            flag(e, e.getPlayer());
        } else if(difference.getX() == 1 && playerEye.getX() < against.getX()+1) {
            flag(e, e.getPlayer());
        } else if(difference.getX() == -1 && playerEye.getX() > against.getX()) {
            flag(e, e.getPlayer());
        } else if(difference.getZ() == 1 && playerEye.getZ() < against.getZ()+1) {
            flag(e, e.getPlayer());
        } else if(difference.getZ() == -1 && playerEye.getZ() > against.getZ()) {
            flag(e, e.getPlayer());
        }
    }
}
