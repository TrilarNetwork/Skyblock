package me.shizleshizle.skyblock.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import me.shizleshizle.skyblock.SkyBlock;
import me.shizleshizle.skyblock.commands.subcommands.AddClaim;
import me.shizleshizle.skyblock.commands.subcommands.RemoveClaim;
import me.shizleshizle.skyblock.utils.Claims;

public class ClaimCommand implements TabExecutor {

    private SkyBlock plugin;
    private ArrayList<ClaimSubCommands> subcommands = new ArrayList<ClaimSubCommands>();
    public ArrayList<ClaimSubCommands> getSubCommands(){return subcommands;}

    public ClaimCommand(SkyBlock pl) {
        this.plugin = pl;
        subcommands.add(new AddClaim(plugin));
        subcommands.add(new RemoveClaim(plugin));
    }

    @SuppressWarnings("static-access")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.DARK_RED + "Invalid usage!");
            return true;
        }
        Player p = (Player) sender;
        if(args.length > 0) {
            for(int i = 0 ; i < getSubCommands().size(); i++) {
                if(args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                    getSubCommands().get(i).perform(p, args);
                }
            }
            if(args[0].equalsIgnoreCase("zones")) {
                if(plugin.claims.isEmpty()) {
                    p.sendMessage(ChatColor.RED + "There are no mining zones!");
                }
                p.sendMessage(ChatColor.BOLD.DARK_GREEN + "Zones: ");
                for(Claims claim : plugin.claims) {
                    p.sendMessage(ChatColor.DARK_RED + "\n----------\n");
                    p.sendMessage(ChatColor.GOLD + claim.getName());
                }
            }
        }
        else {
            p.sendMessage(ChatColor.DARK_RED + "Use an argument!");
        }
        return false;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        ArrayList<String> subCommandArgs = new ArrayList<>();

        if(args.length == 1) {
            for(int i = 0; i < getSubCommands().size(); i++) {
                subCommandArgs.add(getSubCommands().get(i).getName());
            }
            subCommandArgs.add("zones");
            return subCommandArgs;
        }
        else if(args.length == 2) {
            for(int i = 0 ; i < getSubCommands().size(); i++) {
                if(args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                    return getSubCommands().get(i).getSubComArgs((Player)sender, args);
                }
            }
        }
        return null;
    }
}
