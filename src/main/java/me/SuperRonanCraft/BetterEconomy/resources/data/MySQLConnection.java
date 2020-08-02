package me.SuperRonanCraft.BetterEconomy.resources.data;

import com.zaxxer.hikari.pool.HikariPool;
import me.SuperRonanCraft.BetterEconomy.resources.files.FileBasics;
import me.SuperRonanCraft.BetterEconomy.resources.files.FileBasics.FileType;
import me.SuperRonanCraft.BetterEconomy.BetterEconomy;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.scheduler.BukkitRunnable;

import javax.naming.CommunicationException;

public class MySQLConnection {

	String table;
    private HikariDataSource dataSource;

    void load() {
        FileType config = FileBasics.FileType.CONFIG;
        String pre = "Database.MySQL.";
        String host, database, username, password;
        host = config.getString(pre + "host");
        int port = config.getInt(pre + "port");
        database = config.getString(pre + "database");
        username = config.getString(pre + "username");
        password = config.getString(pre + "password");
        int poolSize = config.getInt("Database.PoolSize");
        table = config.getString(pre + "tablePrefix") + "data";
        HikariConfig hConfig = new HikariConfig();
        hConfig.setJdbcUrl("jdbc:mysql://" + host + ':' + port + '/' + database);
        hConfig.setUsername(username);
        hConfig.setPassword(password);
        hConfig.setMinimumIdle(poolSize);
        hConfig.setMaximumPoolSize(poolSize);
        try {
            dataSource = new HikariDataSource(hConfig);
            debug("MySQL connection successful!");
        } catch (HikariPool.PoolInitializationException e) {
            BetterEconomy.getInstance().getLogger().severe("Mhh... Seems like the mysql isn't setup correctly, can you fix me in the config.yml for [BetterEconomy] <3");
        }
    }

    Connection getConnection() throws SQLException {
        if (dataSource != null)
            return dataSource.getConnection();
        else
            return null;
    }

    boolean isEnabled() {
        return dataSource != null;
    }

    void close() {
        if (dataSource != null) {
            dataSource.close();
            dataSource = null;
            debug("MySQL connection closed!");
        }
    }

    private void debug(String msg) {
        BetterEconomy.getInstance().debug(msg);
    }
}
