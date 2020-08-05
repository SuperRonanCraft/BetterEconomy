package me.SuperRonanCraft.BetterEconomy.resources.data;

import me.SuperRonanCraft.BetterEconomy.BetterEconomy;
import me.SuperRonanCraft.BetterEconomy.resources.files.FileBasics;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Database {
    private BetterEcoDatabase database;
    //Static Prefix's
    //public String serverName = "NULL";
    public String uuid = "UUID", playerName = "Name";
    //Storage
    List<UUID> UUIDs = new ArrayList<>();
    HashMap<UUID, Double> Balance = new HashMap<>();

    //LOADING
    public void load() {
        resetCache();
        FileBasics.FileType config = FileBasics.FileType.CONFIG;
        if (config.getBoolean("Database.MySQL.enabled")) //MySQL database is enabled
            database = new DatabaseMySQL(this);
        else
            database = new DatabaseFile(this);
        database.load();
        /*serverName = config.getString("Database.Server");
        if (serverName.equals(uuid) || serverName.equals(playerName)) { //Cant name a server "Name"... dummy
            serverName = "ERROR";
            getPl().getLogger().severe("You can't name this server " + serverName + "! Please change it asap!");
        }*/
        //run().runTaskAsynchronously(getPl());
    }

    public void forceLocal() {
        database = new DatabaseFile(this);
        database.load();
    }

    public void unload() {
        database.unload();
    }


    //Check if a player exists
    public boolean playerExists(UUID id) {
    	return database.playerExists(id);
    }

    //Create a player
    public boolean playerCreate(final UUID id, Player p, double def) {
        return database.playerCreate(id, p, def);
    }

    //Get a players balance
    public double playerBalance(final UUID id) {
        return database.playerBalance(id);
    }

    //Set Balance
    public void playerSetBalance(final UUID id, double amt) {
        database.playerSetBalance(id, amt);
    }

    //Add to Balance
    public boolean playerAddBalance(final UUID id, double amt) {
        return database.playerAddBalance(id, amt);
    }

    //Get top players
    public List<DatabasePlayer> getTop(int amt) {
        return database.getTop(amt);
    }

    //Get a list of players similar to input name
    public List<DatabasePlayer> getSimilarPlayers(final String name) {
        return database.getSimilarPlayers(name);
    }

    //CLEAN UP
    public void playerClean(final UUID id) {
        UUIDs.remove(id);
        Balance.remove(id);
    }

    void resetCache() {
        UUIDs.clear();
        Balance.clear();
    }

    //GRABS
    void debug(String msg) {
        BetterEconomy.getInstance().debug(msg);
    }

    private BetterEconomy getPl() {
        return BetterEconomy.getInstance();
    }
}
