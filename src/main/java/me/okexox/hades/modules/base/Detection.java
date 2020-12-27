package me.okexox.hades.modules.base;

import me.okexox.hades.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.Serializable;

import static me.okexox.hades.data.PersistentData.HADES;

public abstract class Detection implements Serializable {
    private final String name;
    private final DetectionType type;

    public Detection(String name, DetectionType type) {
        this.name = name;
        this.type = type;
    }

    protected void flag(Player player,String message) {
        for(Player admin : Main.data.getAdmins()) {
            if(admin.isOnline()) {
                admin.sendMessage(HADES + ChatColor.DARK_GREEN + "[" + type.toString() + "] " + ChatColor.BLUE + player.getName() + " flagged " + ChatColor.DARK_RED + "[" + name + "] " + ChatColor.BLUE + "[" + message + "]" + ".");
            }
        }
    }

    protected void flag(Player player) {
        for(Player admin : Main.data.getAdmins()) {
            if(admin.isOnline()) {
                admin.sendMessage(HADES + ChatColor.DARK_GREEN + "[" + type.toString() + "] " + ChatColor.BLUE + player.getName() + " flagged " + ChatColor.DARK_RED + "[" + name + "]" + ChatColor.BLUE + ".");
            }
        }
    }

    public static void notify(String message) {
        for(Player admin : Main.data.getAdmins()) {
            if(admin.isOnline()) {
                admin.sendMessage(HADES + ChatColor.BLUE + message);
            }
        }
    }
}
