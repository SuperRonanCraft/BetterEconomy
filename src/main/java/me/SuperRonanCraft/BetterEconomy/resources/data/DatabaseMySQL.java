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
import java.util.List;
import java.util.UUID;

public class DatabaseMySQL implements BetterEcoDatabase {
    private final MySQLConnection sql = new MySQLConnection();
    public String serverName = "NULL";
    private final Database db;

    DatabaseMySQL(Database db) {
        this.db = db;
    }

    @Override
    public void load() {
        serverName = FileBasics.FileType.CONFIG.getString("Database.Server");
        if (serverName.equals(db.uuid) || serverName.equals(db.playerName)) { //Cant name a server "Name"... dummy
            serverName = "ERROR";
            BetterEconomy.getInstance().getLogger().severe("You can't name this server " + serverName + "! Please change it asap!");
        }
        loadSql().runTaskAsynchronously(BetterEconomy.getInstance());
    }

    private BukkitRunnable loadSql() {
        return new BukkitRunnable() {
            @Override
            public void run() {
            sql.load();
            if (sql.isEnabled())
                createColumns();
            else //Force local database if failed
                db.forceLocal();
            }
        };
    }

    @Override
    public void unload() {
        if (sql.isEnabled())
            this.sql.close();
    }

    //MYSQL Setup
    private void createColumns() {
        try (Connection connection = sql.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement stmt = connection.prepareStatement("SHOW TABLES LIKE '" + sql.table + "'");
                 ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    // Alter existing tables
                    List<String> cNames;
                    try (PreparedStatement columnStatement = connection.prepareStatement(
                            "SHOW COLUMNS FROM " + sql.table);
                         ResultSet columns = columnStatement.executeQuery()) {
                        cNames = new ArrayList<>();
                        while (columns.next()) {
                            String name = columns.getString(1);
                            cNames.add(name);
                        }
                    }
                    if (!cNames.contains(serverName)) {
                        try (PreparedStatement insert = sql.getConnection()
                                .prepareStatement("ALTER TABLE " + sql.table + " ADD " + serverName + " DOUBLE DEFAULT 0 NOT NULL")) {
                            insert.executeUpdate();
                        }
                        debug("Added missing column " + serverName);
                    }
                    if (!cNames.contains(db.playerName)) {
                        try (PreparedStatement insert = sql.getConnection()
                                .prepareStatement("ALTER TABLE " + sql.table + " ADD " + db.playerName + " VARCHAR(16)")) {
                            insert.executeUpdate();
                        }
                        debug("Added missing column " + db.playerName);
                    }
                } else {
                    // Table does not exist, create them
                    try (PreparedStatement createStatement = connection.prepareStatement(
                            "CREATE TABLE " + sql.table + " (" + db.uuid + " VARCHAR(36) PRIMARY KEY, "
                                    + serverName + " DOUBLE DEFAULT 0 NOT NULL ," + db.playerName + " VARCHAR(16))")) {
                        createStatement.executeUpdate();
                    }
                    debug("Table created!");
                }
                connection.commit();
            } catch (SQLException ex) {
                ex.printStackTrace();
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
                debug("Mysql database is ready!");
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            BetterEconomy.getInstance().getLogger().severe("MYSQL database is not configured correctly! Could not fetch tables!");
        }
    }

    //Check if a player exists
    @Override
    public boolean playerExists(UUID id) {
        try (Connection connection = sql.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + sql.table + " WHERE " + db.uuid + " = ?");
            statement.setString(1, id.toString());
            try (ResultSet results = statement.executeQuery()) {
                if (results.next()) {
                    debug("Player exists!");
                    return true;
                } else {
                    debug("Player DOES NOT exists!");
                    return false;
                }
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Create a player
    @Override
    public boolean playerCreate(final UUID id, Player p, double def) {
        if (!playerExists(id)) {
            try (Connection connection = sql.getConnection()) {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO " + sql.table + "(" + db.uuid + "," + db.playerName + "," + serverName + ")" + "VALUE (?,?,?)");
                statement.setString(1, id.toString());
                statement.setString(2, p.getName());
                statement.setDouble(3, def);
                statement.executeUpdate();
                debug("Player inserted into Database!");
                return true;
            } catch (SQLException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //Get a players balance
    @Override
    public double playerBalance(final UUID id) {
        try (Connection connection = sql.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement("SELECT * FROM " + sql.table + " WHERE " + db.uuid + "=?");
            statement.setString(1, id.toString());
            ResultSet results = statement.executeQuery();
            if (results.next()) {
                debug("Grabbed players balance!");
                return results.getDouble(serverName);
            } else
                debug("Could not Grab players balance?");
        } catch (SQLException  | NullPointerException e) {
            e.printStackTrace();
        }
        return -1.0;
    }

    //Set Balance
    @Override
    public void playerSetBalance(final UUID id, double amt) {
        try (Connection connection = sql.getConnection()) {
            String updateWithName = "UPDATE " + sql.table +  " SET " + serverName + "=?, " + db.playerName + "=? WHERE " + db.uuid + "=?";
            String updateWithoutName = "UPDATE " + sql.table +  " SET " + serverName + "=? WHERE " + db.uuid + "=?";
            String name = Bukkit.getOfflinePlayer(id).getName();
            PreparedStatement statement = connection.prepareStatement(name != null ? updateWithName : updateWithoutName);
            statement.setDouble(1, amt);
            if (name != null) {
                statement.setString(2, name);
                statement.setString(3, id.toString());
            } else
                statement.setString(2, id.toString());
            statement.executeUpdate();
            debug("Player updated in Database!");
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    //Add to Balance
    @Override
    public boolean playerAddBalance(final UUID id, double amt) {
        try (Connection connection = sql.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE " + sql.table + " SET " + serverName + "=" + serverName + "+? WHERE " + db.uuid + "=?");
            statement.setDouble(1, amt);
            statement.setString(2, id.toString());
            statement.executeUpdate();
            debug("Player updated in Database!");
            return true;
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Get top players
    @Override
    public List<DatabasePlayer> getTop(int amt) {
        debug("Grabbing top players....");
        try (Connection connection = sql.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement("SELECT * FROM " + sql.table + " ORDER BY " + serverName + " DESC LIMIT ?");
            statement.setInt(1, amt);
            ResultSet result = statement.executeQuery();
            List<DatabasePlayer> list = new ArrayList<>();
            while(result.next()) {
                String name = result.getString(db.playerName);
                UUID id = UUID.fromString(result.getString(db.uuid));
                double bal = result.getDouble(serverName);
                list.add(new DatabasePlayer(name, id, bal));
            }
            debug("Grabbed " + list.size() + " top players!");
            return list;
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Get a list of players similar to input name
    @Override
    public List<DatabasePlayer> getSimilarPlayers(final String name) {
        List<DatabasePlayer> list = new ArrayList<>();
        try (Connection connection = sql.getConnection()) {
            debug("Grabbing similar players...");
            PreparedStatement statement = connection
                    .prepareStatement("SELECT * FROM " + sql.table + " WHERE " + db.playerName + " LIKE ?");
            statement.setString(1, "%" + name + "%");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                DatabasePlayer info = new DatabasePlayer(result.getString(db.playerName), UUID.fromString(result.getString(db.uuid)), result.getDouble(serverName));
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
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return list;
    }
}
