package me.okexox.hades.data;

public class ServerData {
    private static ServerData instance = new ServerData();

    public static ServerData getInstance() {
        return instance;
    }

    private long pistonExtendedTime;

    public long isPistonExtended() {
        return pistonExtendedTime;
    }

    public void setPistonExtended(long value) {
        pistonExtendedTime = value;
    }
}
