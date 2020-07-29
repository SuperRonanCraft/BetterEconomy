package me.SuperRonanCraft.BetterEconomy.resources.data;

import me.SuperRonanCraft.BetterEconomy.resources.files.FileBasics.FILETYPE;
import me.SuperRonanCraft.BetterEconomy.BetterEconomy;
import org.bukkit.scheduler.BukkitScheduler;
import sun.security.tools.keytool.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    //MySQL Config
    private Connection connection;
    String host, database, username, table;
    private String password;
    private int port;
    private BukkitScheduler mysqlTimer;

    public void load(FILETYPE sql) {
        setup(sql);
    }

    private void setup(FILETYPE sql) {
        String pre = "Database.MySQL.";
        host = sql.getString(pre + "host");
        port = sql.getInt(pre + "port");
        database = sql.getString(pre + "database");
        username = sql.getString(pre + "username");
        password = sql.getString(pre + "password");
        table = sql.getString(pre + "tablePrefix") + "data";
        connect();
        fix();
    }

    private void connect() {
        try {
            synchronized (this) {
                if (getConnection() != null && !getConnection().isClosed()) {
                    getConnection().close();
                    debug("MySQL Disconnected!");
                    //return;
                }
                //Pure magic
                Class.forName("com.mysql.jdbc.Driver");
                setConnection(DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/"
                        + this.database, this.username, this.password));
                debug("MySQL Connected!");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void fix() {
        
    }

    Connection getConnection() {
        return connection;
    }

    void setConnection(Connection connection) {
        this.connection = connection;
    }

    private void debug(String msg) {
        BetterEconomy.getInstance().debug(msg);
    }
}
