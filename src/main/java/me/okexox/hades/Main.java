package me.okexox.hades;

import com.comphenix.protocol.ProtocolLibrary;
import me.okexox.hades.data.DataList;
import me.okexox.hades.data.PersistentData;
import me.okexox.hades.manager.commands.AddAdminCommand;
import me.okexox.hades.manager.commands.DetectionsCommand;
import me.okexox.hades.manager.commands.GetAdminsCommand;
import me.okexox.hades.manager.commands.RemoveAdminCommand;
import me.okexox.hades.modules.events.CombatEvent;
import me.okexox.hades.modules.events.*;
import me.okexox.hades.modules.exploits.Nuker;
import me.okexox.hades.modules.movement.*;
import me.okexox.hades.players.JoinLeaveHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public class Main extends JavaPlugin {
    public static final PersistentData data = retrieveData();

    @Override
    public void onEnable() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            DataList.addPlayer(p.getName());
        }
        registerAnticheat();
        addCommands();
    }

    private static PersistentData retrieveData() {
        File dataLocation = new File("hades" + File.separator + "data.hades");
        if(!dataLocation.exists()) {
            return new PersistentData();
        } else {
            try(FileInputStream fileInputStream = new FileInputStream(dataLocation);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                return (PersistentData) objectInputStream.readObject();
            } catch(IOException | ClassNotFoundException e) {
                System.out.println("Could not load in persistent data!");
            }
        }
        return new PersistentData();
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

    private void addCommands() {
        this.getCommand("GetHadesAdmins").setExecutor(new GetAdminsCommand());
        this.getCommand("AddHadesAdmin").setExecutor(new AddAdminCommand());
        this.getCommand("RemoveHadesAdmin").setExecutor(new RemoveAdminCommand());
        this.getCommand("HadesDetections").setExecutor(new DetectionsCommand());
    }

    @Override
    public void onDisable() {
        ProtocolLibrary.getProtocolManager().removePacketListeners(this);
        saveData();
    }

    private void saveData() {
        File saveDirectory = new File("hades");
        saveDirectory.mkdir();
        File dataLocation = new File("hades" + File.separator + "data.hades");
        try(FileOutputStream fileOutputStream = new FileOutputStream(dataLocation);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(data);
        } catch(FileNotFoundException e) {
            System.out.println("File could not be found!");
        } catch(IOException e) {
            System.out.println("Unable to write to file!");
        }
    }
}
