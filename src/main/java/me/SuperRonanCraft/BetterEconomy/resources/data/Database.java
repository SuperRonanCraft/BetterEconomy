package me.SuperRonanCraft.BetterEconomy.resources.data;

import me.SuperRonanCraft.BetterEconomy.BetterEconomy;
import me.SuperRonanCraft.BetterEconomy.resources.files.FileBasics;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
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
        FileBasics.FileType sql = FileBasics.FileType.CONFIG;
        serverName = sql.getString("Database.Server");
        if (serverName.equals(uuid) || serverName.equals(playerName)) { //Cant name a server "Name"... dummy
            serverName = "ERROR";
            getPl().getLogger().severe("You can't name this server " + serverName + "! Please change it asap!");
        }
        this.sql.load(sql);
        createColumns();
    }

    //MYSQL Setup
    private void createColumns() {
    	try (Connection conn = sql.getConnection()) {
    		conn.setAutoCommit(false);
        	try (PreparedStatement stmt = conn.prepareStatement("SHOW TABLES LIKE '" + sql.table + "'");
        			ResultSet result = stmt.executeQuery()) {

        		if (result.next()) {
        			// Alter existing tables
        			List<String> cNames;
    				try (PreparedStatement columnStatement = conn.prepareStatement(
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
                    if (!cNames.contains(playerName)) {
                        try (PreparedStatement insert = sql.getConnection()
                                .prepareStatement("ALTER TABLE " + sql.table + " ADD " + playerName + " VARCHAR(16)")) {
                        	insert.executeUpdate();
                        }
                        debug("Added missing column " + playerName);
                    }
        		} else {
        			// Tables do not exist; create them
        			try (PreparedStatement createStatement = conn.prepareStatement(
        					"CREATE TABLE " + sql.table + " (" + uuid + " VARCHAR(36) PRIMARY KEY, "
                            + serverName + " DOUBLE DEFAULT 0 NOT NULL ," + playerName + " VARCHAR(16))")) {
        				createStatement.executeUpdate();
        			}
                    debug("Tables created! Mysql is setup!");
        		}
        		conn.commit();
        	} catch (SQLException ex) {
        		ex.printStackTrace();
        		conn.rollback();
        	} finally {
        		conn.setAutoCommit(true);
        	}
    	} catch (SQLException ex) {
			ex.printStackTrace();
		}
    }

    //Check if a player exists
    public boolean playerExists(UUID id) {
    	try (Connection conn = sql.getConnection(); PreparedStatement statement = conn.prepareStatement("SELECT * FROM " + sql.table + " WHERE " + uuid + " = ?")) {
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
    	} catch (SQLException ex) {
			ex.printStackTrace();
		}
        return false;
    }

    //Create a player
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

    //Get a players balance
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

    //Set Balance
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

    //Add to Balance
    public boolean playerAddBalance(final UUID id, double amt) {
        try {
            PreparedStatement insert = sql.getConnection().prepareStatement("UPDATE " + sql.table + " SET " + serverName + "=" + serverName + "+? WHERE " + uuid + "=?");
            insert.setDouble(1, amt);
            insert.setString(2, id.toString());
            insert.executeUpdate();
            debug("Player updated in Database!");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Get top players
    public List<DatabasePlayer> getTop(int amt) {
        try {
            debug("Grabbing top players...");
            PreparedStatement statement = sql.getConnection()
                    .prepareStatement("SELECT * FROM " + sql.table + " ORDER BY " + serverName + " DESC LIMIT ?");
            statement.setInt(1, amt);
            ResultSet result = statement.executeQuery();
            List<DatabasePlayer> list = new ArrayList<>();
            while(result.next()) {
                String name = result.getString(playerName);
                UUID id = UUID.fromString(result.getString(uuid));
                double bal = result.getDouble(serverName);
                list.add(new DatabasePlayer(name, id, bal));
            }
            debug("Grabbed " + list.size() + " top players!");
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Get a list of players similar to input name
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
