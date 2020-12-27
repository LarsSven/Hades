package me.okexox.hades.modules.events;

import me.okexox.hades.data.DataList;
import me.okexox.hades.data.PlayerData;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;

public class InteractEvent implements Listener {
    @EventHandler
    public void startSlowdown(PlayerInteractEvent e) {
        if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        Player player = e.getPlayer();
        PlayerData data = DataList.getPlayer(player.getName());
        ItemStack item = player.getItemInHand();
        if(item == null ||
                (!item.getType().isEdible()
                        && !isDrinkablePotion(item)
                        && !isSword(item.getType()))) {
            return;
        }

        if(item.getType().isEdible()
                && (player.getGameMode() == GameMode.CREATIVE
                || (player.getFoodLevel() == 20 && !item.getType().equals(Material.GOLDEN_APPLE)))
        ) {
            return;
        }

        data.setSlowedDown(true);
    }

    private boolean isDrinkablePotion(ItemStack item) {
        if(item.getType().equals(Material.POTION)) {
            Potion potion = Potion.fromItemStack(item);
            if(potion.isSplash()) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    private boolean isSword(Material item) {
        return item.equals(Material.DIAMOND_SWORD)
                || item.equals(Material.GOLD_SWORD)
                || item.equals(Material.IRON_SWORD)
                || item.equals(Material.STONE_SWORD)
                || item.equals(Material.WOOD_SWORD);
    }
}
