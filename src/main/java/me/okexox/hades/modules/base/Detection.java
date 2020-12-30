package me.okexox.hades.modules.base;

import me.okexox.hades.Main;
import me.okexox.hades.data.DataList;
import me.okexox.hades.data.PersistentData;
import me.okexox.hades.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

import java.io.Serializable;

import static me.okexox.hades.data.PersistentData.HADES;

public abstract class Detection implements Serializable {
    private final String detectionName;
    private final FlagType flagType;
    private final DetectionType detectionType;

    public Detection(String name, FlagType flagType, DetectionType detectionType) {
        this.detectionName = name;
        this.flagType = flagType;
        this.detectionType = detectionType;
    }

    protected void flag(Cancellable e, Player player, String message) {
        String violations = punishPlayer(e, player);
        for(String name : PersistentData.getInstance().getAdmins()) {
            Player admin = Bukkit.getPlayer(name);
            if(admin != null) {
                admin.sendMessage(HADES + ChatColor.DARK_GREEN + "[" + flagType.toString() + "] " + ChatColor.BLUE + player.getName() + " flagged " + ChatColor.DARK_RED + "[" + detectionName + "] " + ChatColor.BLUE + "[" + message + "]" + violations + ".");
            }
        }
    }

    protected void flag(Cancellable e, Player player) {
        String violations = punishPlayer(e, player);
        for(String name : PersistentData.getInstance().getAdmins()) {
            Player admin = Bukkit.getPlayer(name);
            if(admin != null) {
                admin.sendMessage(HADES + ChatColor.DARK_GREEN + "[" + flagType.toString() + "] " + ChatColor.BLUE + player.getName() + " flagged " + ChatColor.DARK_RED + "[" + detectionName + "]" + ChatColor.BLUE + violations + ".");
            }
        }
    }

    public static void notify(String message) {
        for(String name : PersistentData.getInstance().getAdmins()) {
            Player admin = Bukkit.getPlayer(name);
            if(admin != null) {
                admin.sendMessage(HADES + ChatColor.BLUE + message);
            }
        }
    }

    public DetectionType getDetectionType() {
        return detectionType;
    }

    private String punishPlayer(Cancellable e, Player player) {
        if(PersistentData.getInstance().punishPlayers()) {
            if(flagType.equals(FlagType.Block)) {
                e.setCancelled(true);
            } else if(flagType.equals(FlagType.Ban)) {
                int violations = DataList.getViolations(player.getName()).addViolation(detectionName);
                if(violations > 5) {
                    player.kickPlayer(HADES + ChatColor.DARK_RED + " You have been removed for cheating.");
                    DataList.resetViolations(player.getName());
                }
                return " (" + violations + ")";
            }
        }
        return "";
    }
}
