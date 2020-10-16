package me.SuperRonanCraft.BetterEconomy.resources.data;

import me.SuperRonanCraft.BetterEconomy.resources.files.FileDatabase;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class DatabaseFile implements BetterEcoDatabase {
    private final Database db;
    private final FileDatabase database = new FileDatabase();
    private final String bal = "Balance";

    DatabaseFile(Database db) {
        this.db = db;
    }

    @Override
    public void load() {
        database.load();
    }

    @Override
    public void unload() {
    }

    //Check if a player exists
    @Override
    public boolean playerExists(UUID id) {
        return database.getDatabase().isConfigurationSection(id.toString());
    }

    //Create a player
    @Override
    public boolean playerCreate(final UUID id, Player p, double def) {
        FileConfiguration data = database.getDatabase();
        String section = id.toString();
        if (!data.isConfigurationSection(section)) {
            data.set(section + "." + db.playerName, p.getName());
            data.set(section + "." + bal, def);
            database.saveDatabase(data);
            return true;
        }
        return false;
    }

    //Get a players balance
    @Override
    public double playerBalance(final UUID id) {
        double balance = 0.0;
        FileConfiguration data = database.getDatabase();
        String section = id.toString();
        if (data.isConfigurationSection(section))
            balance = data.getDouble(section + "." + bal);
        return balance;
    }

    //Set Balance
    @Override
    public void playerSetBalance(final UUID id, double amt) {
        FileConfiguration data = database.getDatabase();
        String section = id.toString();
        data.set(section + "." + bal, amt);
        database.saveDatabase(data);
    }

    //Add to Balance
    @Override
    public boolean playerAddBalance(final UUID id, double amt) {
        FileConfiguration data = database.getDatabase();
        String section = id.toString();
        double getCurrent = playerBalance(id);
        data.set(section + "." + bal, getCurrent + amt);
        return true;
    }

    //Get top players
    @Override
    public List<DatabasePlayer> getTop(int amt) {
        FileConfiguration data = database.getDatabase();
        List<DatabasePlayer> players = new ArrayList<>();
        for (Object section : data.getKeys(false).toArray()) {
            String uuid = section.toString();
            ConfigurationSection result = data.getConfigurationSection(uuid);
            String tempName = result.getString(db.playerName);
            DatabasePlayer pInfo = new DatabasePlayer(tempName, UUID.fromString(uuid), result.getDouble(bal));
            players.add(pInfo);
        }
        List<DatabasePlayer> topPlayers = new ArrayList<>();
        for (int i = 0; i < amt; i++) {
            topPlayers.add(null);
            for (DatabasePlayer p : players) {
                if (!topPlayers.contains(p))
                    if (topPlayers.get(i) == null)
                        topPlayers.set(i, p);
                    else if (p.balance > topPlayers.get(i).balance)
                        topPlayers.set(i, p);
            }
        }
        return topPlayers;
    }

    //Get a list of players similar to input name
    @Override
    public List<DatabasePlayer> getSimilarPlayers(final String name) {
        FileConfiguration data = database.getDatabase();
        List<DatabasePlayer> players = new ArrayList<>();
        for (Object section : data.getKeys(false).toArray()) {
            String uuid = section.toString();
            ConfigurationSection result = data.getConfigurationSection(uuid);
            String tempName = result.getString(db.playerName);
            if (name.equalsIgnoreCase(tempName)) { //Name equals found name
                players.clear();
                DatabasePlayer pInfo = new DatabasePlayer(tempName, UUID.fromString(uuid), result.getDouble(bal));
                players.add(pInfo);
                break;
            } else if (tempName.toLowerCase().startsWith(name.toLowerCase())) { //Found name starts with name
                DatabasePlayer pInfo = new DatabasePlayer(tempName, UUID.fromString(uuid), result.getDouble(bal));
                players.add(pInfo);
            }
        }
        return players;
    }
}
