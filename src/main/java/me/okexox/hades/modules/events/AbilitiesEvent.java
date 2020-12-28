package me.okexox.hades.modules.events;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.okexox.hades.Main;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.interfaces.CheckAbilities;
import me.okexox.hades.modules.base.interfaces.CheckHeal;
import net.minecraft.server.v1_8_R3.PacketPlayInAbilities;

import java.util.ArrayList;

public class AbilitiesEvent {
    private final ArrayList<CheckAbilities> checks;

    public AbilitiesEvent() {
        checks = new ArrayList<>();
        addChecks();
    }

    private void addChecks() {
        for(Detection check : Main.data.getChecks()) {
            if(CheckAbilities.class.isAssignableFrom(check.getClass())) {
                checks.add((CheckAbilities) check);
            }
        }
    }

    public void abilities(Main main) {
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        manager.addPacketListener(new PacketAdapter(main, ListenerPriority.NORMAL, PacketType.Play.Client.ABILITIES) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                if (e.getPacketType() == PacketType.Play.Client.ABILITIES) {
                    for(CheckAbilities check : checks) {
                        check.check(e);
                    }
                }
            }
        });
    }
}
