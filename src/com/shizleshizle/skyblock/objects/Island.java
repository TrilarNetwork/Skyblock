package com.shizleshizle.skyblock.objects;

import com.shizleshizle.skyblock.SkyBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Island {
    private Location location;
    private String owner;

    public Island(Location loc, String owner) {
        this.location = loc;
        this.owner = owner;
    }

    public Island(String owner) {
        this.owner = owner;
    }

    public Location getLocation() {
        return location;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public boolean hasIslandSaved() {
        try {
            Statement query = SkyBlock.sql.getConnection().createStatement();
            ResultSet rs = query.executeQuery("SELECT * FROM Islands WHERE owner='" + owner + "';");
            if (rs.next()) {
                return true;
            }
            rs.close();
            query.close();
        } catch (SQLException e) {
            Bukkit.getLogger().info("Skyblock >> MySQL Error: " + e);
        }
        return false;
    }

    public void save() {
        try {
            Statement data = SkyBlock.sql.getConnection().createStatement();
            if (hasIslandSaved()) {
                data.execute("UPDATE Islands SET x='" + location.getX() + "', y='" + location.getY() + "', z='" + location.getZ() + "', yaw='" + location.getYaw() + "', " +
                        "pitch='" + location.getPitch() + "', world='" + location.getWorld() + "' WHERE owner='" + owner + "';");
            } else {
                data.execute("INSERT INTO Islands (owner, x, y, z, yaw, pitch, world) VALUES ('" + owner + "', '" + location.getX() + "', '" + location.getY() + "', '" +
                        location.getZ() + "', '" + location.getYaw() + "', '" + location.getPitch() + "', '" + location.getWorld() + "');");
            }
            data.close();
        } catch (SQLException e) {
            Bukkit.getLogger().info("Skyblock >> MySQL Error: " + e);
        }
    }

    public void load() {
        try {
            Statement query = SkyBlock.sql.getConnection().createStatement();
            ResultSet rs = query.executeQuery("SELECT * FROM Islands WHERE owner='" + owner + "';");
            if (rs.next()) {
                location = new Location(Bukkit.getWorld(rs.getString("world")), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"),
                        rs.getFloat("yaw"), rs.getFloat("pitch"));
            }
            rs.close();
            query.close();
        } catch (SQLException e) {
            Bukkit.getLogger().info("Skyblock >> MySQL Error: " + e);
        }
    }

    public void delete() {
        try {
            SkyBlock.sql.getConnection().createStatement().execute("DELETE FROM Islands WHERE owner='" + owner + "';");
        } catch (SQLException e) {
            Bukkit.getLogger().info("Skyblock >> MySQL Error: " + e);
        }
    }
}
