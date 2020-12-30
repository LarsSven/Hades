package me.okexox.hades.modules.integrity;

import com.comphenix.protocol.events.PacketEvent;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.FlagType;
import me.okexox.hades.modules.base.interfaces.CheckAbilities;
import me.okexox.hades.modules.base.DetectionType;
import net.minecraft.server.v1_8_R3.PacketPlayInAbilities;
import org.bukkit.event.Event;

/**
 * While I'm sure little client devs will have their main fly flagged by this,
 * a lot of them forget to properly set this when they mess with it, so this trips
 * when they're just trying to fly in creative for example.
 */
public class CanFlyIntegrity extends Detection implements CheckAbilities {
    public CanFlyIntegrity() {
        super("CanFlyIntegrity", FlagType.Experimental, DetectionType.Integrity);
    }

    @Override
    public void check(PacketEvent e) {
        PacketPlayInAbilities packet = (PacketPlayInAbilities)e.getPacket().getHandle();
        if(!packet.c() && packet.isFlying()) {
            flag(e, e.getPlayer());
        }
    }
}
