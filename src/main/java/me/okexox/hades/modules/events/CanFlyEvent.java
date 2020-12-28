package me.okexox.hades.modules.events;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.okexox.hades.Main;
import me.okexox.hades.modules.base.Detection;
import net.minecraft.server.v1_8_R3.PacketPlayInAbilities;

public class CanFlyEvent {
    public static void canFly(Main main) {
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        manager.addPacketListener(new PacketAdapter(main, ListenerPriority.NORMAL, PacketType.Play.Client.ABILITIES) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                if (e.getPacketType() == PacketType.Play.Client.ABILITIES) {
                    PacketPlayInAbilities packet = (PacketPlayInAbilities)e.getPacket().getHandle();
                    Detection.notify("CanFly=" + packet.c() + "isFlying=" + packet.isFlying());
                }
            }
        });
    }
}
