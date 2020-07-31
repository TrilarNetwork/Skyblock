package me.shizleshizle.skyblock.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public abstract class ClaimSubCommands {

    public abstract String getName();

    public abstract String getDesc();

    public abstract String getSyntax();

    public abstract void perform(Player p, String args[]);

    public abstract ArrayList<String> getSubComArgs(Player p, String args[]);
}
