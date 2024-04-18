package me.zdany.simpleluckyblocks;

import org.bukkit.Bukkit;

public class NMS {

    public static String getNMSPackage() {
        String v = Bukkit.getServer().getClass().getPackage().getName();
        return v.substring(v.lastIndexOf('.') + 1);
    }

    public static int getNMSVersion() {
        return Integer.parseInt(getNMSPackage().split("_")[1]);
    }
}
