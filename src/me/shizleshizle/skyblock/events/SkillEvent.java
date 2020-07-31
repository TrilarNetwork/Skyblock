package me.shizleshizle.skyblock.events;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.event.Listener;

import me.shizleshizle.skyblock.SkyBlock;
import me.shizleshizle.skyblock.utils.BlockMetaData;

public class SkillEvent implements Listener{

    private SkyBlock plugin;

    public SkillEvent(SkyBlock plugin) {this.plugin = plugin;}

    public void runBlockChanger() {
        Bukkit.getScheduler().runTaskTimer(plugin, this::restoreBlocks, 0, 20);
    }

    public void restoreBlocks(){
        try{
            for(BlockMetaData metadata : plugin.blockMetaData.keySet()) {
                long old = plugin.blockMetaData.get(metadata);
                long current = System.currentTimeMillis();
                long seconds = TimeUnit.MILLISECONDS.toSeconds(current - old);
                if(seconds >= 5) {
                    Location blockloc = new Location(metadata.getWorld(), metadata.getX(), metadata.getY(), metadata.getZ());
                    metadata.getWorld().getBlockAt(blockloc).setType(metadata.getMaterial());
                    metadata.getWorld().playEffect(blockloc, Effect.EXTINGUISH, 1, 10);
                    plugin.blockMetaData.remove(metadata);
                }
            }
        }
        catch(Exception e) {

        }
    }

}
