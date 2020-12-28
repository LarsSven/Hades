package me.okexox.hades.modules.base;

import me.okexox.hades.Main;
import me.okexox.hades.modules.base.interfaces.DetectionType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.Serializable;

import static me.okexox.hades.data.PersistentData.HADES;

public abstract class Detection implements Serializable {
    private final String name;
    private final FlagType flagType;
    private final DetectionType detectionType;

    public Detection(String name, FlagType flagType, DetectionType detectionType) {
        this.name = name;
        this.flagType = flagType;
        this.detectionType = detectionType;
    }

    protected void flag(Player player,String message) {
        for(String name : Main.data.getAdmins()) {
            Player admin = Bukkit.getPlayer(name);
            if(admin.isOnline()) {
                admin.sendMessage(HADES + ChatColor.DARK_GREEN + "[" + flagType.toString() + "] " + ChatColor.BLUE + player.getName() + " flagged " + ChatColor.DARK_RED + "[" + name + "] " + ChatColor.BLUE + "[" + message + "]" + ".");
            }
        }
    }

    protected void flag(Player player) {
        for(String name : Main.data.getAdmins()) {
            Player admin = Bukkit.getPlayer(name);
            if(admin.isOnline()) {
                admin.sendMessage(HADES + ChatColor.DARK_GREEN + "[" + flagType.toString() + "] " + ChatColor.BLUE + player.getName() + " flagged " + ChatColor.DARK_RED + "[" + name + "]" + ChatColor.BLUE + ".");
            }
        }
    }

    public static void notify(String message) {
        for(String name : Main.data.getAdmins()) {
            Player admin = Bukkit.getPlayer(name);
            if(admin.isOnline()) {
                admin.sendMessage(HADES + ChatColor.BLUE + message);
            }
        }
    }

    public DetectionType getDetectionType() {
        return detectionType;
    }
}
