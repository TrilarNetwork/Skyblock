package com.shizleshizle.skyblock;

import com.shizleshizle.skyblock.objects.IslandHandler;
import me.shizleshizle.core.mysql.MySQLManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class SkyBlock extends JavaPlugin {
    public static MySQLManager sql;
    public static IslandHandler islands;

    public void onEnable() {
        final long time = System.currentTimeMillis();
        getLogger().info("Skyblock >> Enabling...");
        sql = MySQLManager.getInstance();
        if (!sql.hasConnection()) {
            sql.openConnection();
        }
        islands = new IslandHandler();
        final long result = System.currentTimeMillis() - time;
        getLogger().info("Skyblock >> Enabled! (" + result + " ms)");
    }

    public void onDisable() {
        final long time = System.currentTimeMillis();
        getLogger().info("Skyblock >> Disabling...");
        try {
            if (sql.hasConnection()) {
                sql.closeConnection();
            }
        } catch (SQLException e) {
            getLogger().info("Skyblock >> MySQL Error: " + e);
        }
        final long result = System.currentTimeMillis() - time;
        getLogger().info("Skyblock >> Disabled! (" + result + " ms)");
    }
}
