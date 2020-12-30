package me.okexox.hades.manager.commands;

import me.okexox.hades.Main;
import me.okexox.hades.data.PersistentData;
import me.okexox.hades.modules.base.Detection;
import me.okexox.hades.modules.base.DetectionType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DetectionsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        StringBuilder message = new StringBuilder();
        message.append(ChatColor.DARK_RED + "[Detections]\n");
        int count = 1;
        DetectionType lastType = DetectionType.Combat;
        message.append(ChatColor.DARK_GREEN + "---").append(lastType).append("---\n");
        for(Detection check : PersistentData.getInstance().getChecks()) {
            if(check.getDetectionType() != lastType) { //Detections are inserted into the list in a way so that all types are grouped together
                message.append(ChatColor.DARK_GREEN + "---").append(check.getDetectionType()).append("---\n");
                lastType = check.getDetectionType();
            }
            message.append(ChatColor.GOLD).append(count++).append(". " + ChatColor.BLUE).append(check.getClass().getSimpleName()).append('\n');
        }
        commandSender.sendMessage(message.toString());
        return true;
    }
}
