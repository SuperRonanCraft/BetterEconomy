package me.SuperRonanCraft.BetterEconomy.resources.data;

import me.SuperRonanCraft.BetterEconomy.resources.files.FileBasics;
import me.SuperRonanCraft.BetterEconomy.BetterEconomy;
import org.bukkit.scheduler.BukkitScheduler;
import sun.security.tools.keytool.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    private final Database db;

    //MySQL Config
    private Connection connection;
    String host, database, username, password, table, server;
    private int port;
    private boolean debug;
    private BukkitScheduler mysqlTimer;

    MySQLConnection(Database db) {
        this.db = db;
    }

    private BetterEconomy getPl() {return BetterEconomy.getInstance();}

    public void load() {
        FileBasics.FILETYPE sql = FileBasics.FILETYPE.CONFIG;
        debug = getPl().getFiles().getType(sql).getBoolean("Debug");
        setup(sql);
    }

    private void setup(FileBasics.FILETYPE sql) {
        String pre = "Database.MySQL.";
        host = sql.getString(pre + "host");
        port = sql.getInt(pre + "port");
        database = sql.getString(pre + "database");
        username = sql.getString(pre + "username");
        password = sql.getString(pre + "password");
        table = sql.getString(pre + "tablePrefix") + "data";
        server = sql.getString("Database.Server");
        try {
            synchronized (this) {
                if (getConnection() != null && !getConnection().isClosed()) {
                    return;
                }
                Class.forName("com.mysql.jdbc.Driver");
                setConnection(DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/"
                        + this.database, this.username, this.password));
                BetterEconomy.getInstance().getLogger().info("MySQL Connected!");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    Connection getConnection() {
        return connection;
    }

    void setConnection(Connection connection) {
        this.connection = connection;
    }
}
