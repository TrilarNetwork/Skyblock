package me.shizleshizle.skyblock.events;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import me.shizleshizle.skyblock.SkyBlock;
import me.shizleshizle.skyblock.utils.BlockMetaData;
import me.shizleshizle.skyblock.utils.Claims;
import net.md_5.bungee.api.ChatColor;

public class MiningEvent implements Listener {

    private SkyBlock plugin;

    public MiningEvent(SkyBlock plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) throws EventException {
        Player p = event.getPlayer();
        Block b = event.getBlock();
        Location loc = b.getLocation();
        ItemStack item = p.getInventory().getItemInMainHand();

        for (Claims claim : plugin.claims) {
            if (claim.between(b.getX(), b.getZ())) {
                if (item.getType().name().endsWith("PICKAXE")) {
                    event.setCancelled(true);
                    switch (b.getType()) {
                        case GOLD_ORE:
                            afterBreak(p, b, Color.YELLOW, loc, 3);
                            break;
                        case IRON_ORE:
                            afterBreak(p, b, Color.WHITE, loc, 2);
                            break;
                        case DIAMOND_ORE:
                            afterBreak(p, b, Color.AQUA, loc, 5);
                            break;
                        case REDSTONE_ORE:
                            afterBreak(p, b, Color.RED, loc, 3);
                            break;
                        case STONE:
                            afterBreak(p, b, Color.GRAY, loc, 1);
                            break;
                        case COAL_ORE:
                            afterBreak(p, b, Color.GRAY, loc, 2);
                            break;
                        case BEDROCK:
                            break;
                        default:
                            p.sendMessage(ChatColor.DARK_RED + "You can't break that!");
                            break;
                    }
                } else if (p.getInventory().getItemInMainHand().getType().name().equals("GOLD_INGOT")) {
                    event.setCancelled(false);
                } else {
                    event.setCancelled(true);
                    p.sendMessage("You need a pickaxe!");
                }
            }
        }

    }

    private void afterBreak(Player p, Block b, Color color, Location loc, int exp) {
        try {
            ItemStack item = p.getInventory().getItemInMainHand();
            for(ItemStack drop : b.getDrops(item)) {
                p.getInventory().addItem(drop);
                p.giveExp(exp);
            }
            BlockMetaData blockMetaData;
            BlockMetaData blockMetaDataNew = null;
            boolean blockData = false;

            if (b.getType().equals(Material.STONE)) {
                for (BlockMetaData data : plugin.blockMetaData.keySet()) {
                    if (b.getX() == data.getX() && b.getY() == data.getY() && b.getZ() == data.getZ()) {
                        blockData = true;
                        blockMetaDataNew = new BlockMetaData(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(),
                                data.getMaterial());
                        plugin.blockMetaData.remove(data);
                        plugin.blockMetaData.put(blockMetaDataNew, System.currentTimeMillis());
                        b.setType(Material.BEDROCK);
                        throw new EventException();

                    }
                }
                if (!blockData) {
                    blockMetaData = new BlockMetaData(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), b.getType());
                    plugin.blockMetaData.put(blockMetaData, System.currentTimeMillis());
                    b.setType(Material.BEDROCK);
                    throw new EventException();
                }
            } else {
                blockMetaData = new BlockMetaData(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), b.getType());
                plugin.blockMetaData.put(blockMetaData, System.currentTimeMillis());
                b.setType(Material.STONE);
            }

            Particle.DustOptions dustOption = new Particle.DustOptions(color, 1);
            loc.add(0.5, 0.5, 0.5);
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 100, .5, .5, .5, 0, dustOption);
        } catch (EventException e) {
        }
    }

}
