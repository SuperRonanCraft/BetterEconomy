package me.SuperRonanCraft.BetterEconomy.resources.softdepends;

import me.SuperRonanCraft.BetterEconomy.BetterEconomy;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class DependsPlaceholders extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return BetterEconomy.getInstance().getDescription().getName().toLowerCase();
    }

    @Override
    public String getPlugin() {
        return BetterEconomy.getInstance().getDescription().getName();
    }

    @Override
    public String getAuthor() {
        return BetterEconomy.getInstance().getDescription().getAuthors().get(0);
    }

    @Override
    public String getVersion() {
        return BetterEconomy.getInstance().getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String s) {
        return "It works!";
    }
}
