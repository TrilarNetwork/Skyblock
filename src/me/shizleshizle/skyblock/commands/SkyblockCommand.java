package me.shizleshizle.skyblock.commands;

import me.shizleshizle.core.objects.User;
import me.shizleshizle.core.utils.ErrorMessages;
import me.shizleshizle.skyblock.SkyBlock;
import me.shizleshizle.skyblock.objects.Island;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SkyblockCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("skyblock")) {
            if (sender instanceof Player) {
                User p = new User((Player) sender);
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("island")) {
                        // TODO: Create island
                        SkyBlock.islands.addIsland(new Island(p, ));
                    } else if (args[0].equalsIgnoreCase("createworld")) {

                    } else {
                        ErrorMessages.doErrorMessage(p, ErrorMessages.Messages.INVALID_USAGE, "/skyblock <island|createworld|teleport|trust> [name]");
                    }
                } else if (args.length == 2) {

                }
            } else {
                sender.sendMessage(ChatColor.RED + "You cannot perform this command!");
            }
        }
        return false;
    }

    /*
    /skyblock island
    /skyblock createworld
    /skyblock teleport <worldname or playername>
    /skyblock trust <name>

     */
}
