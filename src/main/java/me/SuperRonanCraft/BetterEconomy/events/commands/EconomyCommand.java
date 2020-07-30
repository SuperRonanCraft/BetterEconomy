package me.SuperRonanCraft.BetterEconomy.events.commands;

import me.SuperRonanCraft.BetterEconomy.BetterEconomy;
import org.bukkit.command.CommandSender;

import java.util.List;

public interface EconomyCommand {

    void execute(CommandSender sendi, String label, String[] args);

    boolean hasPerm(CommandSender sendi);

    default boolean allowConsole() {
        return true;
    };

    default boolean enabled() {
        return true;
    };

    default BetterEconomy getPl() {
        return BetterEconomy.getInstance();
    }
}
