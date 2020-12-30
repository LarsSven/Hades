package me.okexox.hades.manager.commands;

import me.okexox.hades.data.PersistentData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static me.okexox.hades.data.PersistentData.HADES;

public class EnablePunishCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        PersistentData data = PersistentData.getInstance();
        if(data.punishPlayers()) {
            data.setPunishPlayers(false);
            commandSender.sendMessage(HADES + ChatColor.BLUE + "Disabled player punishments.");
        } else {
            data.setPunishPlayers(true);
            commandSender.sendMessage(HADES + ChatColor.BLUE + "Enabled player punishments.");
        }
        return true;
    }
}
