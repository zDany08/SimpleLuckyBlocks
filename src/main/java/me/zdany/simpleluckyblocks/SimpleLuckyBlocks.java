package me.zdany.simpleluckyblocks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleLuckyBlocks extends JavaPlugin {

    private static SimpleLuckyBlocks instance;

    @Override
    public void onEnable() {
        instance = this;
        registerEvents(new LuckyBlocksListener());
        saveDefaultConfig();
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Create your own lucky blocks with " + getName() + "!");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "Thanks for using " + getName() + "!");
    }

    public void registerEvents(Listener... listeners) {
        for(Listener listener : listeners) getServer().getPluginManager().registerEvents(listener, this);
    }

    public static SimpleLuckyBlocks getInstance() {
        return instance;
    }

    public boolean isLuckyBlock(Block block) {
        return block.getType().equals(Material.getMaterial(getConfig().getString("lucky-block")));
    }

    public void executeLuckyCommand(int senderId, Player player, String command) {
        CommandSender sender = (senderId == 0) ? Bukkit.getConsoleSender() : player;
        Bukkit.dispatchCommand(sender, Format.getColor(Format.getPlaceholders(player, command.replaceAll("\\{PLAYER}", player.getName()))));
    }
}
