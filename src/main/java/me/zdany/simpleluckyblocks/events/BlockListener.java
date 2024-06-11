package me.zdany.simpleluckyblocks.events;

import me.zdany.simpleluckyblocks.NMS;
import me.zdany.simpleluckyblocks.SimpleLuckyBlocks;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

public class BlockListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        List<ItemStack> items = new ArrayList<>();
        if(NMS.getNMSVersion() == 8) {
            try {
                items.add((ItemStack) player.getInventory().getClass().getMethod("getItemInHand").invoke(player.getInventory()));
            }catch (Exception e) {
                Bukkit.getLogger().log(Level.SEVERE, "" + e);
            }
        }else {
            items.add(player.getInventory().getItemInMainHand());
            items.add(player.getInventory().getItemInOffHand());
        }
        items.forEach(item -> {
            if(item.getItemMeta() == null) return;
            if(item.getItemMeta().getLore() == null) return;
            if(!item.getItemMeta().getLore().contains("Lucky Block")) return;
            block.setMetadata("lucky-block", new FixedMetadataValue(SimpleLuckyBlocks.getInstance(), 0));
        });
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if(!SimpleLuckyBlocks.getInstance().getConfig().getBoolean("enabled")) return;
        if(!player.hasPermission("simpleluckyblocks.action")) return;
        if(!SimpleLuckyBlocks.getInstance().isLuckyBlock(block)) return;
        List<ConfigurationSection> actions = new ArrayList<>();
        for(String action : SimpleLuckyBlocks.getInstance().getConfig().getConfigurationSection("actions").getKeys(false)) actions.add(SimpleLuckyBlocks.getInstance().getConfig().getConfigurationSection("actions." + action));
        int selectedAction = new Random().nextInt(actions.size());
        actions.get(selectedAction).getStringList("commands").forEach(command -> {
            SimpleLuckyBlocks.getInstance().executeLuckyCommand(actions.get(selectedAction).getInt("sender"), player, command);
        });
        if(NMS.getNMSVersion() >= 12) {
            event.setDropItems(false);
        }else {
            event.setCancelled(true);
            block.setType(Material.AIR);
        }
    }
}
