package me.okexox.hades.modules.combat;

import me.okexox.hades.data.PlayerData;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.FlagType;
import me.okexox.hades.modules.base.interfaces.CheckCombat;
import me.okexox.hades.modules.base.DetectionType;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.BlockIterator;

import java.util.*;

/**
 * Can never be 100% accurate due to latency, so must be a block
 * Note that I deliberately did not choose for a full raytraced solution.
 * This is meant to be a more lightweight solution that does the job decently.
 */
public class WallHit extends Detection implements CheckCombat {
    public WallHit() {
        super("WallHit", FlagType.Experimental, DetectionType.Combat);
    }

    static private HashSet<Material> solid = getSolid();

    @Override
    public void check(EntityDamageByEntityEvent e, PlayerData data) {
        int i = 0;
        LivingEntity damager = (LivingEntity)e.getDamager();
        double distance = e.getDamager().getLocation().distance(e.getEntity().getLocation());
        distance -= 0.8; //Compensate for inaccurate distance calculation, as it should never overshoot
        int intDistance = (int)distance;
        if(intDistance <= 0) {
            return;
        }
        BlockIterator getBlocks = new BlockIterator(damager, intDistance);
        while(getBlocks.hasNext()) {
            Block block = getBlocks.next();
            if(solid.contains(block.getType())) {
                flag(e, (Player)e.getDamager(), block.getType().toString());
            }
        }
    }

    private static HashSet<Material> getSolid() {
        HashSet<Material> transparentBlocks = new HashSet<Material>();
        transparentBlocks.add(Material.STONE);
        transparentBlocks.add(Material.GRASS);
        transparentBlocks.add(Material.DIRT);
        transparentBlocks.add(Material.COBBLESTONE);
        transparentBlocks.add(Material.LOG);
        transparentBlocks.add(Material.LOG_2);
        transparentBlocks.add(Material.BEDROCK);
        transparentBlocks.add(Material.SAND);
        transparentBlocks.add(Material.GRAVEL);
        transparentBlocks.add(Material.GOLD_ORE);
        transparentBlocks.add(Material.IRON_ORE);
        transparentBlocks.add(Material.COAL_ORE);
        transparentBlocks.add(Material.WOOD);
        transparentBlocks.add(Material.LEAVES);
        transparentBlocks.add(Material.SPONGE);
        transparentBlocks.add(Material.LAPIS_ORE);
        transparentBlocks.add(Material.LAPIS_BLOCK);
        transparentBlocks.add(Material.DISPENSER);
        transparentBlocks.add(Material.SANDSTONE);
        transparentBlocks.add(Material.NOTE_BLOCK);
        transparentBlocks.add(Material.WOOL);
        transparentBlocks.add(Material.GOLD_BLOCK);
        transparentBlocks.add(Material.IRON_BLOCK);
        transparentBlocks.add(Material.BRICK);
        transparentBlocks.add(Material.TNT);
        transparentBlocks.add(Material.BOOKSHELF);
        transparentBlocks.add(Material.MOSSY_COBBLESTONE);
        transparentBlocks.add(Material.OBSIDIAN);
        transparentBlocks.add(Material.DIAMOND_ORE);
        transparentBlocks.add(Material.DIAMOND_BLOCK);
        transparentBlocks.add(Material.WORKBENCH);
        transparentBlocks.add(Material.FURNACE);
        transparentBlocks.add(Material.FURNACE);
        transparentBlocks.add(Material.REDSTONE_ORE);
        transparentBlocks.add(Material.REDSTONE_ORE);
        transparentBlocks.add(Material.SNOW);
        transparentBlocks.add(Material.CLAY);
        transparentBlocks.add(Material.JUKEBOX);
        transparentBlocks.add(Material.PUMPKIN);
        transparentBlocks.add(Material.NETHERRACK);
        transparentBlocks.add(Material.SOUL_SAND);
        transparentBlocks.add(Material.GLOWSTONE);
        transparentBlocks.add(Material.JACK_O_LANTERN);
        transparentBlocks.add(Material.MELON);
        transparentBlocks.add(Material.MYCEL);
        transparentBlocks.add(Material.NETHER_BRICK);
        transparentBlocks.add(Material.ENDER_STONE);
        transparentBlocks.add(Material.REDSTONE_LAMP_OFF);
        transparentBlocks.add(Material.REDSTONE_LAMP_ON);
        transparentBlocks.add(Material.EMERALD_ORE);
        transparentBlocks.add(Material.EMERALD_BLOCK);
        transparentBlocks.add(Material.COMMAND);
        transparentBlocks.add(Material.REDSTONE_BLOCK);
        transparentBlocks.add(Material.QUARTZ_ORE);
        transparentBlocks.add(Material.QUARTZ_BLOCK);
        transparentBlocks.add(Material.DROPPER);
        transparentBlocks.add(Material.STAINED_CLAY);
        transparentBlocks.add(Material.LEAVES);
        transparentBlocks.add(Material.WOOD);
        transparentBlocks.add(Material.PRISMARINE);
        transparentBlocks.add(Material.SEA_LANTERN);
        transparentBlocks.add(Material.HAY_BLOCK);
        transparentBlocks.add(Material.HARD_CLAY);
        transparentBlocks.add(Material.COAL_BLOCK);
        transparentBlocks.add(Material.PACKED_ICE);
        transparentBlocks.add(Material.RED_SANDSTONE);
        return transparentBlocks;
    }
}
