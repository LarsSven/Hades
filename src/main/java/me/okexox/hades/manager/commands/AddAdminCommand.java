package me.okexox.hades.manager.commands;

import me.okexox.hades.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.okexox.hades.data.PersistentData.HADES;

public class AddAdminCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 0) {
            commandSender.sendMessage(HADES + ChatColor.BLUE + "You have not provided a name to be added to the admin list");
            return true;
        }
        String playerName = strings[0];
        Player player = Bukkit.getPlayer(playerName);
        if(player == null) {
            commandSender.sendMessage(HADES + ChatColor.BLUE + "No player with the name \"" + strings[0] + "\" is known.");
            return true;
        }
        if(Main.data.getAdmins().contains(playerName)) {
            commandSender.sendMessage(HADES + ChatColor.BLUE + "\"" + playerName + "\" already is an admin.");
        } else {
            Main.data.addAdmin(playerName);
            commandSender.sendMessage(HADES + ChatColor.BLUE + "Added \"" + playerName + "\" to the admins.");
        }
        return true;
    }
}
