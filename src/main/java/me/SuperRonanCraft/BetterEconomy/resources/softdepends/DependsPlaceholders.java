package me.SuperRonanCraft.BetterEconomy.resources.softdepends;

import me.SuperRonanCraft.BetterEconomy.BetterEconomy;
import me.SuperRonanCraft.BetterEconomy.events.commands.CmdTop;
import me.SuperRonanCraft.BetterEconomy.events.commands.EconomyCommand;
import me.SuperRonanCraft.BetterEconomy.events.commands.EconomyCommandTypes;
import me.SuperRonanCraft.BetterEconomy.resources.data.DatabasePlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

import java.util.List;

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
        if (s.equalsIgnoreCase("balance"))
            return String.valueOf(getPl().getEconomy().getBalance(player));
        else if (s.toLowerCase().startsWith("top_player_")) {
            try {
                int topNum = Integer.parseInt(s.split("top_player_")[1]);
                List<DatabasePlayer> players = ((CmdTop) EconomyCommandTypes.TOP.get()).getTopPlayers();
                if (players.size() >= topNum)
                    return players.get(topNum - 1).name;
            } catch (ArrayIndexOutOfBoundsException e) {
                return null;
            }
        } else if (s.toLowerCase().startsWith("top_bal_")) {
            try {
                int topNum = Integer.parseInt(s.split("top_bal_")[1]);
                List<DatabasePlayer> players = ((CmdTop) EconomyCommandTypes.TOP.get()).getTopPlayers();
                if (players.size() >= topNum)
                    return String.valueOf(players.get(topNum - 1).balance);
            } catch (ArrayIndexOutOfBoundsException e) {
                return null;
            }
        }
        return null;
    }

    private BetterEconomy getPl() {
        return BetterEconomy.getInstance();
    }
}
