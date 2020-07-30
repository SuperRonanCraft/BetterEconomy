package me.SuperRonanCraft.BetterEconomy.events.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public interface EconomyCommandTabPlayers {

    default List<String> tabPlayers(CommandSender sendi, String arg) {
        List<String> list = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().toUpperCase().startsWith(arg.toUpperCase()))
                list.add(p.getName());
        }
        return list;
    }
}
