package me.SuperRonanCraft.BetterEconomy.events.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public interface EconomyCommandDoubleGrab { //Grab a double based off of arguments in a Double.parse grad

    default Double getDouble(CommandSender sendi, String arg) throws NumberFormatException {
        String num = arg.replaceAll("[^0-9]", "");
        double amt = Double.parseDouble(num);
        if (arg.substring(arg.length() - 1).equalsIgnoreCase("k"))
            amt *= 1000;
        return amt;
    }
}
