package me.okexox.hades.modules.movement;

import me.okexox.hades.data.PlayerData;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.DetectionType;
import me.okexox.hades.modules.base.FlagType;
import me.okexox.hades.modules.base.interfaces.CheckBlockPlace;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockNoSwing extends Detection implements CheckBlockPlace {
    public BlockNoSwing() {
        super("BlockNoSwing", FlagType.Ban, DetectionType.Movement);
    }

    @Override
    public void check(BlockPlaceEvent e, PlayerData data) {
        if(!data.maintainsBlockSwing()) {
            flag(e.getPlayer());
        }
        data.setBlockSwing(false);
    }
}
