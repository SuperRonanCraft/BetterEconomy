package me.SuperRonanCraft.BetterEconomy.resources.data;

import me.SuperRonanCraft.BetterEconomy.BetterEconomy;
import me.SuperRonanCraft.BetterEconomy.resources.files.FileBasics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Database {
    private final MySQLConnection sql = new MySQLConnection();
    String serverName = "NULL";
    //Static Prefix's
    String  uuid = "UUID";
    //Storage
    List<UUID> UUIDs = new ArrayList<>();
    HashMap<UUID, Double> Balance = new HashMap<>();

    public void load() {
        resetCache();
        FileBasics.FILETYPE sql = FileBasics.FILETYPE.CONFIG;
        serverName = sql.getString("Database.Server");
        if (serverName.equals(uuid)) {
            serverName = "ERROR";
            getPl().getLogger().severe("You can't name this server " + serverName + "! Please change it asap!");
        }
        this.sql.load(sql);
        createColumns();
    }

    private void createColumns() {
        try {
            PreparedStatement statement = sql.getConnection()
                    .prepareStatement("SHOW TABLES LIKE '" + sql.table + "'");
            ResultSet result = statement.executeQuery();
            if (result.next()) { //Check for missing columns
                PreparedStatement columnStatement = sql.getConnection()
                        .prepareStatement("SHOW COLUMNS FROM " + sql.table);
                ResultSet columns = columnStatement.executeQuery();
                List<String> cNames = new ArrayList<>();
                while (columns.next()) {
                    String name = columns.getString(1);
                    cNames.add(name);
                }
                if (!cNames.contains(serverName)) {
                    PreparedStatement insert = sql.getConnection()
                            .prepareStatement("ALTER TABLE " + sql.table + " ADD " + serverName + " DOUBLE DEFAULT 0 NOT NULL");
                    insert.executeUpdate();
                    debug("Added missing column " + serverName);
                } else {
                    debug("Nothing missing! Mysql is setup!");
                }
            } else { //Table doesn't exist, create one
                PreparedStatement createStatement = sql.getConnection()
                        .prepareStatement("CREATE TABLE " + sql.table + " (" + uuid + " VARCHAR(36) PRIMARY KEY, "
                                + serverName + " DOUBLE DEFAULT 0 NOT NULL)");
                createStatement.executeUpdate();
                debug("Tables created! Mysql is setup!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean playerExists(UUID id) {
        try {
            PreparedStatement statement = sql.getConnection()
                    .prepareStatement("SELECT * FROM " + sql.table + " WHERE " + uuid + "=?");
            statement.setString(1, id.toString());
            ResultSet results = statement.executeQuery();
            boolean exists = results.next();
            if (exists)
                debug("Player exists!");
            else
                debug("Player DOES NOT exists!");
            return exists;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean playerCreate(final UUID id, Player p, double def) {
        try {
            if (!playerExists(id)) {
                PreparedStatement insert = sql.getConnection()
                        .prepareStatement("INSERT INTO " + sql.table + "(" + uuid + "," + serverName +")" +
                                "VALUE (?,?)");
                insert.setString(1, id.toString());
                insert.setDouble(2, def);
                insert.executeUpdate();
                debug("Player inserted into Database!");
                return true;
            } else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public double playerBalance(final UUID id) {
        try {
            PreparedStatement statement = sql.getConnection()
                    .prepareStatement("SELECT * FROM " + sql.table + " WHERE " + uuid + "=?");
            statement.setString(1, id.toString());
            ResultSet results = statement.executeQuery();
            if (results.next()) {
                debug("Grabbed players balance!");
                return results.getDouble(serverName);
            } else {
                debug("Could not Grab players balance?");
                return -1.0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1.0;
    }

    public void playerSetBalance(final UUID id, double amt) {
        try {
            PreparedStatement insert = sql.getConnection()
                    .prepareStatement("UPDATE " + sql.table +  " SET " + serverName + "=? " + " WHERE " + uuid + "=?");
            insert.setDouble(1, amt);
            insert.setString(2, id.toString());
            insert.executeUpdate();
            debug("Player updated in Database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void playerClean(final UUID id) {
        UUIDs.remove(id);
        Balance.remove(id);
    }

    void resetCache() {
        UUIDs.clear();
        Balance.clear();
    }

    void debug(String msg) {
        BetterEconomy.getInstance().debug(msg);
    }

    private BetterEconomy getPl() {
        return BetterEconomy.getInstance();
    }
}
