package me.SuperRonanCraft.BetterEconomy.resources.softdepends;

import me.SuperRonanCraft.BetterEconomy.BetterEconomy;
import me.SuperRonanCraft.BetterEconomy.events.commands.CmdTop;
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
        return null;
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
        if (player != null && s.equalsIgnoreCase("balance"))
            return String.valueOf(getPl().getEconomy().getBalance(player));
        else if (s.toLowerCase().startsWith("top_player_")) {
            String value = s.substring("top_player_".length());
            try {
                int index = Integer.parseInt(value);
                DatabasePlayer pInfo = getTop(index);
                if (pInfo != null)
                    if (pInfo.name == null) return ""; //Space filler
                    else return pInfo.name;
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                return "invalid top player index: " + value;
            }
        } else if (s.toLowerCase().startsWith("top_bal_")) {
            String value = s.substring("top_bal_".length());
            try {
                int index = Integer.parseInt(value);
                DatabasePlayer pInfo = getTop(index);
                if (pInfo != null)
                    if (pInfo.name == null) return ""; //Space filler
                    else return String.valueOf(pInfo.balance);
            } catch (NumberFormatException e) {
                return "invalid top bal index: " + value;
            }
        }
        return null;
    }

    private DatabasePlayer getTop(int index) {
        CmdTop cmd = (CmdTop) EconomyCommandTypes.TOP.get();
        if (index <= cmd.maxTopPlayers) { //No higher than max loaded players
            try {
                List<DatabasePlayer> players = cmd.getTopPlayers();
                if (players.size() >= index)
                    return players.get(index - 1);
            } catch (ArrayIndexOutOfBoundsException e) {/*Empty DatabasePlayer*/}
            return new DatabasePlayer(null, null, 0);
        } else
            return null;
    }

    private BetterEconomy getPl() {
        return BetterEconomy.getInstance();
    }
}
