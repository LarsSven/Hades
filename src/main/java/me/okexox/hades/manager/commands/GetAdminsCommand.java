package me.okexox.hades.manager.commands;

import me.okexox.hades.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetAdminsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        StringBuilder message = new StringBuilder();
        message.append(ChatColor.RED + "[HADES ADMINS]\n");
        int count = 1;
        for(String admin : Main.data.getAdmins()) {
            message.append(ChatColor.GREEN).append(count++).append(". " + ChatColor.BLUE).append(admin).append('\n');
        }
        commandSender.sendMessage(message.toString());
        return true;
    }
}
