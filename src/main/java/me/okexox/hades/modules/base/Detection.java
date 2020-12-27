package me.okexox.hades.modules.base;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class Detection {
    private final String name;
    private final DetectionType type;
    private static final String HADES = ChatColor.RED + "[Hades] ";

    public Detection(String name, DetectionType type) {
        this.name = name;
        this.type = type;
    }

    protected void flag(Player player,String message) {
        Bukkit.getOnlinePlayers().forEach(
                p -> p.sendMessage(HADES + ChatColor.DARK_GREEN + "[" + type.toString() + "] " + ChatColor.BLUE + player.getName() + " flagged " + ChatColor.DARK_RED + "[" + name + "] " + ChatColor.BLUE + "[" + message + "]" + ".")
        );
    }

    protected void flag(Player player) {
        Bukkit.getOnlinePlayers().forEach(
                p -> p.sendMessage(HADES + ChatColor.DARK_GREEN + "[" + type.toString() + "] " + ChatColor.BLUE + player.getName() + " flagged " + ChatColor.DARK_RED + "[" + name + "]" + ChatColor.BLUE + ".")
        );
    }

    public static void notify(String message) {
        Bukkit.getOnlinePlayers().forEach(
                p -> p.sendMessage(HADES + ChatColor.BLUE + message)
        );
    }
}
