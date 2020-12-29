package me.okexox.hades.modules.events;

import me.okexox.hades.data.DataList;
import me.okexox.hades.data.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;

public class AnimationEvent implements Listener {
    @EventHandler
    public void onAnimation(PlayerAnimationEvent e) {
        if(e.getAnimationType().equals(PlayerAnimationType.ARM_SWING)) {
            PlayerData data = DataList.getPlayer(e.getPlayer().getName());
            data.setArmSwung(true);
            data.setBlockSwing(true);
        }
    }
}
