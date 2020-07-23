package me.SuperRonanCraft.BetterEconomy.resources.data;

import me.SuperRonanCraft.BetterEconomy.BetterEconomy;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import me.SuperRonanCraft.BetterEconomy.resources.FileBasics.FILETYPE;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;

public class MySQLDatabase {
    private Database db;
    //MySQL Config
    private String host, database, username, password, table;
    private int port;
    private boolean debug;
    private BukkitScheduler mysqlTimer;

    MySQLDatabase(Database db) {
        this.db = db;
    }

    private BetterEconomy getPl() {return BetterEconomy.getInstance();}

    public void load() {
        FILETYPE sql = FILETYPE.CONFIG;
        debug = getPl().getFiles().getType(sql).getBoolean("Debug");
        setup(sql);
    }

    private void setup(FILETYPE sql) {
        String pre = "MySQL.";
        host = sql.getString(pre + "host");
        port = sql.getInt(pre + "port");
        database = sql.getString(pre + "database");
        username = sql.getString(pre + "username");
        password = sql.getString(pre + "password");
        table = sql.getString(pre + "tablePrefix") + "data";
    }

    void downloadTickets() {
        mysqlTimer = getPl().getServer().getScheduler();
        if (FILETYPE.CONFIG.getBoolean("Timer.enabled")) {
            long timer = FILETYPE.CONFIG.getInt("Timer.time") * 1200;
            if (timer == 0)
                timer = 6000;
            long time = timer;
            mysqlTimer.scheduleSyncRepeatingTask(getPl(), downloadSql(), time, time);
        } else
            mysqlTimer.runTaskAsynchronously(getPl(), downloadSql());
    }

    private void setupSql(Statement stmt) {
        // BukkitRunnable r = new BukkitRunnable() {
        //    @Override
        //     public void run() {
        try {
            debug("Attemting to setup MySQL database...");
            if (stmt != null) {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + table + "(" + db.ticketID + " INT, " + db.uuid +
                        " VARCHAR(36), " + db.open + " BOOLEAN DEFAULT true, " + db.broadcast + " BOOLEAN " +
                        "DEFAULT false, " + db.x + " INT, " + db.y + " INT, " + db.z + " INT, " + db.world + " TEXT, "
                        + db.msg + " TEXT, " + db.reply + " TEXT, " + db.replier + " TEXT, " + db.resolved + " TEXT, "
                        + "PRIMARY" + " KEY (" + db.ticketID + "))");
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + nextid + "(" + db.nextTicketID + " INT " +
                        "DEFAULT 0, PRIMARY KEY (" + db.nextTicketID + "))");
                // NEW COLUMN "Flagged"
                try {
                    stmt.executeQuery("SELECT " + db.flagged + " FROM " + table);
                } catch (SQLException ex) {
                    stmt.executeUpdate("ALTER TABLE " + table + " ADD " + db.flagged + " BOOLEAN NOT NULL DEFAULT " + "false");
                }
                // NEW COLUMN "Importance"
                try {
                    stmt.executeQuery("SELECT " + db.importance + " FROM " + table);
                } catch (SQLException ex) {
                    stmt.executeUpdate("ALTER TABLE " + table + " ADD " + db.importance + " INT NOT NULL DEFAULT 0");
                }
                // NEW COLUMN "Rating"
                try {
                    stmt.executeQuery("SELECT " + db.rating + " FROM " + table);
                } catch (SQLException ex) {
                    stmt.executeUpdate("ALTER TABLE " + table + " ADD " + db.rating + " INT NOT NULL DEFAULT 0");
                }
                // NEW COLUMN "Category"
                try {
                    stmt.executeQuery("SELECT " + db.category + " FROM " + table);
                } catch (SQLException ex) {
                    stmt.executeUpdate("ALTER TABLE " + table + " ADD " + db.category + " TEXT DEFAULT NULL");
                }
                // NEW COLUMN "Time"
                try {
                    stmt.executeQuery("SELECT " + db.time + " FROM " + table);
                    try {
                        stmt.executeUpdate("ALTER TABLE " + table + " MODIFY COLUMN " + db.time + " TIMESTAMP " +
                                "DEFAULT" + " CURRENT_TIMESTAMP");
                    } catch (SQLException ex) {
                        stmt.executeUpdate("ALTER TABLE " + table + " DROP COLUMN " + db.time);
                        stmt.executeUpdate("ALTER TABLE " + table + " ADD " + db.time + " TIMESTAMP DEFAULT " +
                                "CURRENT_TIMESTAMP");
                    }
                } catch (SQLException ex) {
                    stmt.executeUpdate("ALTER TABLE " + table + " ADD " + db.time + " TIMESTAMP DEFAULT " +
                            "CURRENT_TIMESTAMP");
                }
                // NEW COLUMN "ClaimedBy"
                try {
                    stmt.executeQuery("SELECT " + db.claimedBy + " FROM " + table);
                } catch (SQLException ex) {
                    stmt.executeUpdate("ALTER TABLE " + table + " ADD " + db.claimedBy + " TEXT DEFAULT NULL");
                }
                // NEW COLUMN "Server"
                try {
                    stmt.executeQuery("SELECT " + db.server + " FROM " + table);
                } catch (SQLException ex) {
                    stmt.executeUpdate("ALTER TABLE " + table + " ADD " + db.server + " TEXT DEFAULT NULL");
                }
                //Setup counter
                ResultSet result = stmt.executeQuery("SELECT " + db.nextTicketID + " FROM " + nextid);
                if (!result.next())
                    stmt.executeUpdate("INSERT INTO " + nextid + " (" + db.nextTicketID + ") VALUES (0) ON " +
                            "DUPLICATE KEY UPDATE " + db.nextTicketID + " = 0;");
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
                        ResultSet result = stmt.executeQuery("SELECT * FROM " + table); //Get the result list and
                        // cache it
                        while (result.next()) {
                            String TicketID = result.getString(db.ticketID);
                            db.TicketIDs.add(TicketID);
                            db.UUIDs.put(TicketID, result.getString(db.uuid));
                            db.Open.put(TicketID, result.getBoolean(db.open));
                            db.Broadcast.put(TicketID, result.getBoolean(db.broadcast));
                            db.World.put(TicketID, result.getString(db.world));
                            db.X.put(TicketID, result.getInt(db.x));
                            db.Y.put(TicketID, result.getInt(db.y));
                            db.Z.put(TicketID, result.getInt(db.z));
                            db.Msg.put(TicketID, result.getString(db.msg));
                            db.Flagged.put(TicketID, result.getBoolean(db.flagged));
                            db.Importance.put(TicketID, result.getInt(db.importance));
                            db.Rating.put(TicketID, result.getInt(db.rating));
                            try {
                                db.Server.put(TicketID, result.getString(db.server));
                            } catch (NullPointerException e) {
                                // Server.put(TicketID, null);
                            }
                            try {
                                db.Reply.put(TicketID, result.getString(db.reply).split(db.seperator));
                            } catch (NullPointerException e) {
                                // Reply.put(TicketID, null);
                            }
                            try {
                                db.Time.put(TicketID, result.getTimestamp(db.time).toString());
                            } catch (NullPointerException e) {
                                // Time.put(TicketID, null);
                            }
                            try {
                                db.Replier.put(TicketID, result.getString(db.replier).split(db.seperator));
                            } catch (NullPointerException e) {
                                // Replier.put(TicketID, null);
                            }
                            try {
                                db.Resolved.put(TicketID, result.getString(db.resolved));
                            } catch (NullPointerException e) {
                                // Resolved.put(TicketID, null);
                            }
                            try {
                                db.Category.put(TicketID, result.getString(db.category));
                            } catch (NullPointerException e) {
                                // Category.put(TicketID, null);
                            }
                            try {
                                db.ClaimedBy.put(TicketID, result.getString(db.claimedBy));
                            } catch (NullPointerException e) {
                                // ClaimedBy.put(TicketID, null);
                            }
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


    void create(String ticket, int posx, int posy, int posz, String worl, String playerid, String msgs, String cat,
                int impor, String time) {
        executeUpdate("INSERT INTO " + table + " (" + db.ticketID + ", " + db.uuid + ", " + db.x + ", " + db.y + ", " +
                db.z + ", " + db.world + ", " + db.msg + ", " + db.category + ", " + db.importance + ", " + db.server + ", " + db.time + " ) " +
                "VALUES" + " (" + ticket + ", '" + playerid + "', " + posx + ", " + posy + ", " + posz + ", '" + worl + "', '" +
                msgs + "', '" + cat + "', " + impor + ", NULLIF('" + getPl().getSystems().getSettings().getServerName() +
                "', '" + null + "'), '" + time + "' );");
    }



    //RESOURCES
    private Connection openConnections(boolean debug) throws SQLException, ClassNotFoundException {
        synchronized (this) {
            if (debug)
                debug("Connecting to MySQL database...");
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database +
                    "?autoReconnect=true&useSSL=false", this.username, this.password);
        }
    }

    private Statement getStatement(boolean debug) {
        try {
            return openConnections(debug).createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            debug("Connection to MySQL database failed!");
            if (mysqlTimer != null) {
                mysqlTimer.cancelTasks(getPl());
                getPl().getText().getLang().getCore().sms(Bukkit.getConsoleSender(),
                        "%prefix%&cFailed to " + "bind " + "to " + "MySQLDatabase " + "database! Make sure " + "your "
                                + "information is correct! " + "&fDatabase" + " " + "switched to " + "yml file! " +
                                "&eCanceling MySQLDatabase " + "Timer!");
            } else
                getPl().getText().getLang().getCore().sms(Bukkit.getConsoleSender(),
                        "%prefix%&cFailed to " + "bind " + "to " + "MySQLDatabase " + "database! Make sure " + "your "
                                + "information is correct! " + "&fDatabase" + " " + "switched to " + "yml file!");
            db.sqlEnabled = false;
            db.downloadSql();
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
                        if (debug)
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
                        if (debug)
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
