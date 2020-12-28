package me.okexox.hades.modules.events;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.okexox.hades.Main;
import me.okexox.hades.data.DataList;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;

public class BlockDigEvent {
    public static void blockDig(Main main) {
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
}
