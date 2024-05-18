package me.zdany.simpleluckyblocks.commands;

import me.zdany.simpleluckyblocks.Format;
import me.zdany.simpleluckyblocks.SimpleLuckyBlocks;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LuckyBlockCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch(args.length) {
            case 0:
                if(!(sender instanceof Player)) {
                    sender.sendMessage(Format.getColor(SimpleLuckyBlocks.getInstance().getConfig().getString("messages.only-player")));
                    return false;
                }
                Player player = (Player) sender;
                if(!player.hasPermission("simpleluckyblocks.cmd.luckyblock")) {
                    player.sendMessage(Format.getColor(SimpleLuckyBlocks.getInstance().getConfig().getString("simpleluckyblocks.cmd.luckyblock")));
                    return false;
                }
                SimpleLuckyBlocks.getInstance().giveLuckyBlocks(player, 1);
                break;
            case 1:
                int amount = Integer.parseInt(args[0]);
                if(!(sender instanceof Player)) {
                    sender.sendMessage(Format.getColor(SimpleLuckyBlocks.getInstance().getConfig().getString("messages.only-player")));
                    return false;
                }
                Player player1 = (Player) sender;
                if(!player1.hasPermission("simpleluckyblocks.cmd.luckyblock")) {
                    player1.sendMessage(Format.getColor(SimpleLuckyBlocks.getInstance().getConfig().getString("simpleluckyblocks.cmd.luckyblock")));
                    return false;
                }
                SimpleLuckyBlocks.getInstance().giveLuckyBlocks(player1, amount);
                break;
            case 2:
                int amount1 = Integer.parseInt(args[0]);
                Player target = Bukkit.getPlayerExact(args[1]);
                if(!sender.hasPermission("simpleluckyblocks.cmd.luckyblock")) {
                    sender.sendMessage(Format.getColor(SimpleLuckyBlocks.getInstance().getConfig().getString("simpleluckyblocks.cmd.luckyblock")));
                    return false;
                }
                if(target == null) {
                    sender.sendMessage(Format.getColor(Format.getPlaceholders(target, SimpleLuckyBlocks.getInstance().getConfig().getString("messages.not-online").replaceAll("\\{PLAYER}", args[1]))));
                    return false;
                }
                SimpleLuckyBlocks.getInstance().giveLuckyBlocks(target, amount1);
                break;
            default:
                sender.sendMessage(Format.getColor(SimpleLuckyBlocks.getInstance().getConfig().getString("messages.usage")));
                break;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> suggestions = new ArrayList<>();
        switch(args.length) {
            case 1:
                suggestions.add("<amount>");
                break;
            case 2:
                Bukkit.getOnlinePlayers().forEach(player -> suggestions.add(player.getName()));
                break;
        }
        return suggestions;
    }
}
