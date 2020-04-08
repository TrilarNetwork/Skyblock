package me.shizleshizle.skyblock.objects;

import me.shizleshizle.core.objects.User;
import me.shizleshizle.core.utils.Numbers;
import me.shizleshizle.skyblock.SkyBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class IslandHandler {
    public ArrayList<Island> islands;
    private final int DISTANCE_BETWEEN_ISLANDS = 250;

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

    public boolean hasIsland(User p) {
        try {
            Statement query = SkyBlock.sql.getConnection().createStatement();
            ResultSet rs = query.executeQuery("SELECT * FROM Islands WHERE owner='" + p.getName() + "';");
            if (rs.next()) {
                rs.close();
                query.close();
                return true;
            }
            rs.close();
            query.close();
        } catch (SQLException e) {
            Bukkit.getLogger().info("Skyblock >> hasIsland Error: " + e);
        }
        return false;
    }
    //x double, y double, z double, yaw double, pitch double, world varchar(100)

    private ArrayList<Location> getLocations() {
        ArrayList<Location> locations = new ArrayList<>();
        try {
            Statement query = SkyBlock.sql.getConnection().createStatement();
            ResultSet rs = query.executeQuery("SELECT * FROM Islands");
            while (rs.next()) {
                locations.add(new Location(Bukkit.getWorld(rs.getString("world")), rs.getFloat("x"), rs.getFloat("y"), rs.getFloat("z"),
                        rs.getFloat("yaw"), rs.getFloat("pitch")));
            }
            rs.close();
            query.close();
        } catch (SQLException e) {
            Bukkit.getLogger().info("Skyblock >> Locations >> Error: " + e);
        }
        return locations;
    }


    public Location getNewIslandLocation(User p) {
        Location newLoc = new Location(p.getWorld(), Numbers.getRandom(0, 6000000), 70, Numbers.getRandom(0, 6000000));
        ArrayList<Location> locations = getLocations();
        for (Location existingLoc : locations) {
            while (existingLoc.distance(newLoc) < DISTANCE_BETWEEN_ISLANDS) {
                newLoc.add(new Vector(1, 0, 1));
            }
        }
        return newLoc;
    }
}
