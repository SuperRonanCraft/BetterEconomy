package me.SuperRonanCraft.BetterEconomy.resources.data;

import me.SuperRonanCraft.BetterEconomy.resources.files.FileBasics;
import me.SuperRonanCraft.BetterEconomy.BetterEconomy;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.sql.*;
import java.util.UUID;
import java.util.logging.Level;

public class MySQLOLD {
    private final Database db;
    //MySQL Config
    private String host, database, username, password, table, server;
    private int port;
    private boolean debug;
    private BukkitScheduler mysqlTimer;

    private boolean enabled = true;

    MySQLOLD(Database db) {
        this.db = db;
    }

    private BetterEconomy getPl() {return BetterEconomy.getInstance();}

    public void load() {
        FileBasics.FileType sql = FileBasics.FileType.CONFIG;
        debug = getPl().getFiles().getType(sql).getBoolean("Debug");
        setup(sql);
    }

    private void setup(FileBasics.FileType sql) {
        String pre = "Database.MySQL.";
        host = sql.getString(pre + "host");
        port = sql.getInt(pre + "port");
        database = sql.getString(pre + "database");
        username = sql.getString(pre + "username");
        password = sql.getString(pre + "password");
        table = sql.getString(pre + "tablePrefix") + "data";
        server = sql.getString("Database.Server");
    }

    void downloadTickets() {
        mysqlTimer = getPl().getServer().getScheduler();
        /*if (FILETYPE.CONFIG.getBoolean("Timer.enabled")) {
            long timer = FILETYPE.CONFIG.getInt("Timer.time") * 1200;
            if (timer == 0)
                timer = 6000;
            long time = timer;
            mysqlTimer.scheduleSyncRepeatingTask(getPl(), downloadSql(), time, time);
        } else*/
        mysqlTimer.runTaskAsynchronously(getPl(), downloadSql());
    }

    private void setupSql(Statement stmt) {
        // BukkitRunnable r = new BukkitRunnable() {
        //    @Override
        //     public void run() {
        try {
            debug("Attemting to setup MySQL database...");
            if (stmt != null) {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + table +
                        " (" + db.uuid + " VARCHAR(36), " +
                        db.serverName + " DOUBLE, " +
                        "PRIMARY" + " KEY (" + db.uuid + "))");
                /*// NEW COLUMN "Flagged"
                try {
                    stmt.executeQuery("SELECT " + db.flagged + " FROM " + table);
                } catch (SQLException ex) {
                    stmt.executeUpdate("ALTER TABLE " + table + " ADD " + db.flagged + " BOOLEAN NOT NULL DEFAULT " + "false");
                }*/
                //stmt.close();
                debug("MySQL database has been setup!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //}
        //};
        //r.runTaskAsynchronously(getPl());
    }

    private Runnable downloadSql() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    db.resetCache(); //Reset the cache before downloading tickets
                    Statement stmt = getStatement(true);
                    if (stmt != null) {
                        setupSql(stmt);
                        ResultSet result = stmt.executeQuery("SELECT * FROM " + table); //Get the result list and cache it
                        while (result.next()) {
                            UUID id = UUID.fromString(result.getString(db.uuid));
                            db.UUIDs.add(id);
                            db.Balance.put(id, result.getDouble(db.serverName));
                        }
                        stmt.close();
                        debug("MySQL database has been downloaded!");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };
    }


    void newPlayer(UUID id) {
        executeUpdate("INSERT INTO " + table + " (" + db.uuid + ", " + db.serverName + ") " +
                "VALUES" + " ('" + id.toString() + "', " + 0 + ");");

    }



    //RESOURCES
    private Connection openConnections() throws SQLException, ClassNotFoundException {
        synchronized (this) {
            debug("Connecting to MySQL database...");
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database +
                    "?autoReconnect=true&useSSL=false", this.username, this.password);
        }
    }

    private Statement getStatement(boolean debug) {
        try {
            return openConnections().createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            debug("Connection to MySQL database failed!");
            if (mysqlTimer != null) {
                mysqlTimer.cancelTasks(getPl());
                getPl().getMessages().sms(Bukkit.getConsoleSender(),
                        "%prefix%&cFailed to " + "bind " + "to " + "MySQLDatabase " + "database! Make sure " + "your "
                                + "information is correct! " + "&fDatabase" + " " + "switched to " + "yml file! " +
                                "&eCanceling MySQLDatabase " + "Timer!");
            } else
                getPl().getMessages().sms(Bukkit.getConsoleSender(),
                        "%prefix%&cFailed to " + "bind " + "to " + "MySQLDatabase " + "database! Make sure " + "your "
                                + "information is correct! " + "&fDatabase" + " " + "switched to " + "yml file!");
            enabled = false;
        }
        return null;
    }

    private void executeUpdate(String sql) {
        BukkitRunnable r = new BukkitRunnable() {
            @Override
            public void run() {
                Statement stmt = getStatement(debug);
                try {
                    if (stmt != null) {
                        stmt.executeUpdate(sql);
                        stmt.close();
                        debug("MySQL connected and updated successfully!");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };
        r.runTaskAsynchronously(getPl());
    }

    private void executeUpdate(String[] ary) {
        BukkitRunnable r = new BukkitRunnable() {
            @Override
            public void run() {
                Statement stmt = getStatement(debug);
                try {
                    if (stmt != null) {
                        for (String str : ary)
                            stmt.executeUpdate(str);
                        stmt.close();
                        debug("MySQL connected and updated successfully!");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };
        r.runTaskAsynchronously(getPl());
    }

    private void debug(String str) { //Debug messages
        if (debug)
            getPl().getLogger().log(Level.INFO, str);
    }
}
