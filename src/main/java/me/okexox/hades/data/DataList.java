package me.okexox.hades.data;

import java.util.HashMap;

public class DataList {
    private static final HashMap<String,PlayerData> data = new HashMap();

    public static void addPlayer(String name) {
        data.put(name, new PlayerData());
    }

    public static void removePlayer(String name) {
        data.remove(name);
    }

    public static PlayerData getPlayer(String name) {
        return data.get(name);
    }
}
