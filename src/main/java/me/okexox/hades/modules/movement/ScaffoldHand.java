package me.okexox.hades.modules.movement;

import me.okexox.hades.data.PersistentData;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.DetectionType;
import me.okexox.hades.modules.base.FlagType;
import me.okexox.hades.modules.base.interfaces.CheckBlockPlace;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class ScaffoldHand extends Detection implements CheckBlockPlace {
    public ScaffoldHand() {
        super("ScaffoldHand", FlagType.Experimental, DetectionType.Movement);
    }

    @Override
    public void check(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        Block placedBlock = e.getBlock();
        ItemStack itemInHand = player.getItemInHand();
        if(placedBlock.getType().equals(itemInHand.getType())) {
            return;
        }
        if(PersistentData.getScaffoldBlocks().contains(placedBlock.getType())) {
            flag(player, "Item=" + itemInHand + " BLock=" + placedBlock);
        }
    }
}
