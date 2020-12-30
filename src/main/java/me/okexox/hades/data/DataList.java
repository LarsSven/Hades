package me.okexox.hades.data;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class DataList {
    private static final HashMap<String,PlayerData> data = new HashMap();

    private static final HashMap<String,PlayerViolations> violations = new HashMap<>();

    public static void addPlayer(String name) {
        data.put(name, new PlayerData());
        violations.put(name, new PlayerViolations());
    }

    public static void removePlayer(String name) {
        data.remove(name);
    }

    public static PlayerData getPlayer(String name) {
        return data.get(name);
    }

    public static PlayerViolations getViolations(String name) {
        return violations.get(name);
    }

    public static void resetViolations(String name) {
        violations.replace(name, new PlayerViolations());
    }
}
