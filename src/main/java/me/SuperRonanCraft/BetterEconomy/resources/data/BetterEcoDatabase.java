package me.SuperRonanCraft.BetterEconomy.resources.data;

import me.SuperRonanCraft.BetterEconomy.BetterEconomy;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public interface BetterEcoDatabase {

    //Load/Unload events to database's
    void load();
    void unload();

    //Check if a player exists
    boolean playerExists(UUID id);

    //Create a player
    boolean playerCreate(final UUID id, Player p, double def);

    //Get a players balance
    double playerBalance(final UUID id);

    //Set Balance
    void playerSetBalance(final UUID id, double amt);

    //Add to Balance
    boolean playerAddBalance(final UUID id, double amt);

    //Get top players
    List<DatabasePlayer> getTop(int amt);

    //Get a list of players similar to input name
    List<DatabasePlayer> getSimilarPlayers(final String name);

    default void debug(String msg) {
        BetterEconomy.getInstance().debug(msg);
    }
}
