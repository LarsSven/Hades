package me.okexox.hades;

import com.comphenix.protocol.ProtocolLibrary;
import me.okexox.hades.data.DataList;
import me.okexox.hades.modules.events.CombatEvent;
import me.okexox.hades.modules.events.*;
import me.okexox.hades.modules.exploits.Nuker;
import me.okexox.hades.modules.movement.*;
import me.okexox.hades.players.JoinLeaveHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            DataList.addPlayer(p.getName());
        }
        registerAnticheat();
    }

    private void registerAnticheat() {
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new MoveEvent(), this);
        manager.registerEvents(new CombatEvent(), this);
        manager.registerEvents(new HealEvent(), this);
        manager.registerEvents(new JoinLeaveHandler(), this);
        manager.registerEvents(new InteractEvent(), this);
        manager.registerEvents(new BreakBlockEvent(), this);
        manager.registerEvents(new PistonExtendEvent(), this);
        manager.registerEvents(new SneakEvent(), this);
        manager.registerEvents(new ConsumeEvent(), this);
        manager.registerEvents(new DamageEvent(), this);
        manager.registerEvents(new DeathEvent(), this);
        manager.registerEvents(new AnimationEvent(), this);

        NoSlowDown.registerCancelCheck(this);
        Nuker.setTimer(this);
    }

    @Override
    public void onDisable() {
        ProtocolLibrary.getProtocolManager().removePacketListeners(this);
    }
}
