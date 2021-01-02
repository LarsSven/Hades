package me.okexox.hades.modules.events;

import me.okexox.hades.Main;
import me.okexox.hades.data.DataList;
import me.okexox.hades.data.PersistentData;
import me.okexox.hades.data.PlayerData;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.interfaces.CheckHeal;
import me.okexox.hades.modules.base.interfaces.CheckMove;
import me.okexox.hades.modules.integrity.OnGroundIntegrity;
import me.okexox.hades.modules.integrity.PitchIntegrity;
import me.okexox.hades.modules.integrity.VehicleIntegrity;
import me.okexox.hades.modules.movement.*;
import me.okexox.hades.utility.BasicFunctions;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Stairs;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

import static me.okexox.hades.utility.BasicFunctions.checkBlockAround;

/**
 * This class will handle the movement events.
 * This prevents that the same operation, like retrieving PlayerData, is excessively redone.
 */
public class MoveEvent implements Listener {
    private final ArrayList<CheckMove> checks;

    public MoveEvent() {
        checks = new ArrayList<>();
        addChecks();
    }

    private void addChecks() {
        for(Detection check : PersistentData.getInstance().getChecks()) {
            if(CheckMove.class.isAssignableFrom(check.getClass())) {
                checks.add((CheckMove) check);
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        final PlayerData data = DataList.getPlayer(e.getPlayer().getName());
        updateMovementBefore(e, data);

        //Note that wrong edits of data in PlayerData or the event can cause clashes between detections.
        //Never edit any location contained within the event directly.
        for(CheckMove check : checks) {
            check.check(e, data);
        }

        updateMovementAfter(e, data);
    }

    private void updateMovementAfter(PlayerMoveEvent e, PlayerData data) {
        double yDiff = e.getTo().getY() - e.getFrom().getY();
        data.setLastYMotion(yDiff);
    }

    private void updateMovementBefore(PlayerMoveEvent e, PlayerData data) {
        Player player = e.getPlayer();
        data.setEdgeTeleport(BasicFunctions.edgeTeleport(e.getTo()));
        data.setOnGround(player.isOnGround());
        Location checkLoc = e.getPlayer().getLocation();
        checkLoc.setY(checkLoc.getY()-1);
        data.setNoFloorTicks(!BasicFunctions.checkAllBlockAround(checkLoc, 0));
        data.setFlewRecently(player.isFlying());
        data.updateSwimming(BasicFunctions.isSwimming(player.getLocation()));
        if(player.isOnGround()) {
            data.setBadJump(false);
        }
        if(checkBlockAround(e.getPlayer().getLocation(), 165)) { //Slime block
            data.setBadJump(true);
            data.setNearSlimeBlock(true);
        }
        data.updateCombat();
        for(PotionEffect effect : player.getActivePotionEffects()) {
            if(effect.getType().equals(PotionEffectType.SPEED)) {
                data.updateSpeed(effect.getAmplifier()+1);
            }
            if(effect.getType().equals(PotionEffectType.JUMP)) {
                data.updateJumpEffect(effect.getAmplifier()+1);
            }
        }
        data.updateSpeed(0);
        data.updateFly(player.isFlying());
    }
}
