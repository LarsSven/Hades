package me.okexox.hades.modules.movement;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
import me.okexox.hades.Main;
import me.okexox.hades.data.DataList;
import me.okexox.hades.data.PlayerData;
import me.okexox.hades.modules.base.interfaces.CheckMove;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.DetectionType;
import me.okexox.hades.utility.BasicFunctions;
import me.okexox.hades.utility.Settings;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

public class NoSlowDown extends Detection implements CheckMove {
    public NoSlowDown() {
        super("NoSlowDown", DetectionType.Ban);
    }

    public static void registerCancelCheck(Main main) {
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        manager.addPacketListener(new PacketAdapter(main, ListenerPriority.NORMAL, PacketType.Play.Client.BLOCK_DIG) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                if (e.getPacketType() == PacketType.Play.Client.BLOCK_DIG) {
                    PacketPlayInBlockDig packet = (PacketPlayInBlockDig)e.getPacket().getHandle();
                    if(packet.c() == PacketPlayInBlockDig.EnumPlayerDigType.RELEASE_USE_ITEM) {
                        DataList.getPlayer(e.getPlayer().getName()).setSlowedDown(false);
                    }
                }
            }
        });
    }

    public void check(PlayerMoveEvent e, PlayerData data) {
        Player player = e.getPlayer();
        double maxSpeed = Settings.ONGROUND_SLOWDOWN_SPEED;
        if(!data.isSlowedDown()
            || !data.slowdownTransitioned()
            || player.isInsideVehicle()
            || data.wasNearSlimeBlock()) {
            return;
        }

        Location loc = player.getLocation();
        loc.setY(loc.getY()-1);
        if(data.getOnGroundMoves() < 4) {
            if(player.isSneaking()) {
                maxSpeed = Settings.OFFGROUND_SNEAKING_SLOWDOWN_SPEED;
            } else {
                maxSpeed = Settings.OFFGROUND_SLOWDOWN_SPEED;
            }
        } else if(loc.getWorld().getBlockTypeIdAt(loc) == 174) {
            maxSpeed = 0.35;
        }
        double speed = BasicFunctions.getHorizontalDistance(e.getTo(), e.getFrom());
        if(speed > maxSpeed) {
            flag(player, "Speed=" + speed);
        }
    }
}
