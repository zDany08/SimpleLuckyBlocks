package me.zdany.simpleluckyblocks;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Format {

    public static String getColor(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static String getPlaceholders(Player player, String str) {
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) return PlaceholderAPI.setPlaceholders(player, str);
        return str;
    }
}
