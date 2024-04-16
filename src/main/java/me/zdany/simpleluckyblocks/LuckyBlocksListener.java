package me.zdany.simpleluckyblocks;

import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LuckyBlocksListener implements Listener {

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
    }
}
