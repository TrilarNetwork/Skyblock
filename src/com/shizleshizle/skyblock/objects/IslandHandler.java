package com.shizleshizle.skyblock.objects;

import com.shizleshizle.skyblock.SkyBlock;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class IslandHandler {
    public ArrayList<Island> islands;

    public IslandHandler() {
        islands = new ArrayList<>();
        setup();
    }

    public void setup() {
        try {
            SkyBlock.sql.getReady();
            SkyBlock.sql.getConnection().createStatement().execute("CREATE TABLE IF NOT EXISTS Islands (owner varchar(32), x double, y double, z double, yaw double, pitch double, " +
                    "world varchar(100), PRIMARY KEY(owner), FOREIGN KEY(owner) REFERENCES Player(player));");
            SkyBlock.sql.getConnection().createStatement().execute("CREATE TABLE IF NOT EXISTS Trusted (owner varchar(32). trusted varchar(32), PRIMARY KEY(owner, trusted), " +
                    "FOREIGN KEY(owner) REFERENCES Player(player), FOREIGN KEY(trusted) REFERENCES Player(player));");
        } catch (SQLException e) {
            Bukkit.getLogger().info("Skyblock >> Island Handler SQL Error: " + e);
        }
    }

    public ArrayList<Island> getIslands() {
        return islands;
    }

    public void addIsland(Island island) {
        islands.add(island);
    }

    public boolean exists(Island island) {
        return islands.contains(island);
    }

    public void removeIsland(Island island) {
        island.delete();
        islands.remove(island);
    }
}
