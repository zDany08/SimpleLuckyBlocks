package me.zdany.simpleluckyblocks;

import me.zdany.simpleluckyblocks.commands.LuckyBlockCommand;
import me.zdany.simpleluckyblocks.events.LuckyBlocksListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class SimpleLuckyBlocks extends JavaPlugin {

    private static SimpleLuckyBlocks instance;

    @Override
    public void onEnable() {
        instance = this;
        registerCommands();
        registerEvents(new LuckyBlocksListener());
        saveDefaultConfig();
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Create your own lucky blocks with " + getName() + "!");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "Thanks for using " + getName() + "!");
    }

    private void registerEvents(Listener... listeners) {
        for(Listener listener : listeners) getServer().getPluginManager().registerEvents(listener, this);
    }

    private void registerCommands() {
        registerCommand("luckyblock", new LuckyBlockCommand(), new LuckyBlockCommand());
    }

    private void registerCommand(String command, CommandExecutor executor) {
        getCommand(command).setExecutor(executor);
    }

    private void registerCommand(String command, CommandExecutor executor, TabCompleter tabCompleter) {
        registerCommand(command, executor);
        getCommand(command).setTabCompleter(tabCompleter);
    }

    public static SimpleLuckyBlocks getInstance() {
        return instance;
    }

    public boolean isLuckyBlock(Block block) {
        return block.hasMetadata("lucky-block");
    }

    public void executeLuckyCommand(int senderId, Player player, String command) {
        CommandSender sender = (senderId == 0) ? Bukkit.getConsoleSender() : player;
        Bukkit.dispatchCommand(sender, Format.getColor(Format.getPlaceholders(player, command.replaceAll("\\{PLAYER}", player.getName()))));
    }

    public void giveLuckyBlocks(Player player, int amount) {
        ItemStack luckyBlock = new ItemStack(Material.getMaterial(getConfig().getString("lucky-block.material")), amount);
        ItemMeta luckyBlockMeta = luckyBlock.getItemMeta();
        if(getConfig().getBoolean("lucky-block.enchanted")) luckyBlockMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, false);
        luckyBlockMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        luckyBlockMeta.setDisplayName(Format.getColor(Format.getPlaceholders(player, getConfig().getString("lucky-block.display-name"))));
        List<String> lore = new ArrayList<>();
        for(String line : getConfig().getStringList("lucky-block.lore")) lore.add(Format.getColor(Format.getPlaceholders(player, line)));
        lore.add("[Lucky Block]");
        luckyBlockMeta.setLore(lore);
        luckyBlock.setItemMeta(luckyBlockMeta);
        player.getInventory().addItem(luckyBlock);
    }
}
