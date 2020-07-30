package me.SuperRonanCraft.BetterEconomy.resources;

import me.SuperRonanCraft.BetterEconomy.BetterEconomy;
import me.SuperRonanCraft.BetterEconomy.resources.data.DatabasePlayer;
import org.bukkit.command.CommandSender;

import java.util.*;

public class Systems {

    public DatabasePlayer getDatabasePlayer(CommandSender sendi, String name) {
        List<DatabasePlayer> similarPlayers = BetterEconomy.getInstance().getDatabase().getSimilarPlayers(name);
        if (!similarPlayers.isEmpty()) {
            if (similarPlayers.size() == 1) {
                return similarPlayers.get(0);
            } else {
                List<String> names = new ArrayList<>();
                for (DatabasePlayer info : similarPlayers)
                    names.add(info.name);
                BetterEconomy.getInstance().getMessages().getFailTooMany(sendi, name, Arrays.toString(names.toArray()));
            }
        } else {
            BetterEconomy.getInstance().getMessages().getFailName(sendi, name);
        }
        return null;
    }
}
