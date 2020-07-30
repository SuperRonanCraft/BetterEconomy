package me.SuperRonanCraft.BetterEconomy.resources.data;

import me.SuperRonanCraft.BetterEconomy.BetterEconomy;
import me.SuperRonanCraft.BetterEconomy.resources.files.FileBasics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Database {
    private final MySQLConnection sql = new MySQLConnection();
    public String serverName = "NULL";
    //Static Prefix's
    public String uuid = "UUID", playerName = "Name";
    //Storage
    List<UUID> UUIDs = new ArrayList<>();
    HashMap<UUID, Double> Balance = new HashMap<>();

    //LOADING
    public void load() {
        resetCache();
        FileBasics.FILETYPE sql = FileBasics.FILETYPE.CONFIG;
        serverName = sql.getString("Database.Server");
        if (serverName.equals(uuid) || serverName.equals(playerName)) { //Cant name a server "Name"... dummy
            serverName = "ERROR";
            getPl().getLogger().severe("You can't name this server " + serverName + "! Please change it asap!");
        }
        this.sql.load(sql);
        createColumns();
    }

    //MYSQL
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
                }
                if (!cNames.contains(playerName)) {
                    PreparedStatement insert = sql.getConnection()
                            .prepareStatement("ALTER TABLE " + sql.table + " ADD " + playerName + " VARCHAR(16)");
                    insert.executeUpdate();
                    debug("Added missing column " + playerName);
                }
                debug("Mysql is setup!");
            } else { //Table doesn't exist, create one
                PreparedStatement createStatement = sql.getConnection()
                        .prepareStatement("CREATE TABLE " + sql.table + " (" + uuid + " VARCHAR(36) PRIMARY KEY, "
                                + serverName + " DOUBLE DEFAULT 0 NOT NULL ," + playerName + " VARCHAR(16))");
                createStatement.executeUpdate();
                debug("Tables created! Mysql is setup!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //PLAYER BALANCE'S
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
                        .prepareStatement("INSERT INTO " + sql.table + "(" + uuid + "," + playerName +"," + serverName + ")" +
                                "VALUE (?,?,?)");
                insert.setString(1, id.toString());
                insert.setString(2, p.getName());
                insert.setDouble(3, def);
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
            String updateWithName = "UPDATE " + sql.table +  " SET " + serverName + "=?, " + playerName + "=? WHERE " + uuid + "=?";
            String updateWithoutName = "UPDATE " + sql.table +  " SET " + serverName + "=? WHERE " + uuid + "=?";
            String name = Bukkit.getOfflinePlayer(id).getName();
            PreparedStatement insert = sql.getConnection().prepareStatement(name != null ? updateWithName : updateWithoutName);
            insert.setDouble(1, amt);
            if (name != null) {
                insert.setString(2, name);
                insert.setString(3, id.toString());
            } else
                insert.setString(2, id.toString());
            insert.executeUpdate();
            debug("Player updated in Database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<DatabasePlayer> getTop(int amt) {
        try {
            debug("Grabbing top players...");
            PreparedStatement statement = sql.getConnection()
                    .prepareStatement("SELECT * FROM " + sql.table + " ORDER BY " + serverName + " DESC LIMIT ?");
            statement.setInt(1, amt);
            ResultSet result = statement.executeQuery();
            int rowcount = 0;
            if (result.last()) {
                rowcount = result.getRow();
                result.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
            }
            debug("Grabbed " + rowcount + " top players!");
            List<DatabasePlayer> list = new ArrayList<>();
            while(result.next()) {
                String name = result.getString(playerName);
                UUID id = UUID.fromString(result.getString(uuid));
                double bal = result.getDouble(serverName);
                list.add(new DatabasePlayer(name, id, bal));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<DatabasePlayer> getSimilarPlayers(final String name) {
        List<DatabasePlayer> list = new ArrayList<>();
        try {
            debug("Grabbing similar players...");
            PreparedStatement statement = sql.getConnection()
                    .prepareStatement("SELECT * FROM " + sql.table + " WHERE " + playerName + " LIKE ?");
            statement.setString(1, "%" + name + "%");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                DatabasePlayer info = new DatabasePlayer(result.getString(playerName), UUID.fromString(result.getString(uuid)), result.getDouble(serverName));
                list.add(info);
            }
            for (DatabasePlayer p : list) {
                if (p.name.equalsIgnoreCase(name)) {
                    list.clear();
                    list.add(p);
                    debug("Name " + name + " is perfect");
                    break;
                }
            }
            debug("Grabbed " + list.size() + " similar players!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
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
