package me.okexox.hades.manager.commands;

import me.okexox.hades.Main;
import me.okexox.hades.data.PersistentData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.okexox.hades.data.PersistentData.HADES;

public class RemoveAdminCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 0) {
            commandSender.sendMessage(HADES + ChatColor.BLUE + "You have not provided a name to be removed from the admin list");
            return true;
        }
        String playerName = strings[0];
        Player player = Bukkit.getPlayer(playerName);
        if(player == null) {
            commandSender.sendMessage(HADES + ChatColor.BLUE + "No player with the name \"" + strings[0] + "\" is known.");
            return true;
        }
        if(!PersistentData.getInstance().getAdmins().contains(playerName)) {
            commandSender.sendMessage(HADES + ChatColor.BLUE + "\"" + playerName + "\" is not an admin.");
        } else {
            PersistentData.getInstance().removeAdmin(playerName);
            commandSender.sendMessage(HADES + ChatColor.BLUE + "Removed \"" + playerName + "\" from the admins.");
        }
        return true;
    }
}
