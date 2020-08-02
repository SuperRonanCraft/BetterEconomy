package me.SuperRonanCraft.BetterEconomy.resources.data;

import me.SuperRonanCraft.BetterEconomy.resources.files.FileBasics.FileType;
import me.SuperRonanCraft.BetterEconomy.BetterEconomy;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class MySQLConnection {

	String table;
    private HikariDataSource dataSource;

    public void load(FileType sql) {
        setup(sql);
    }

    private void setup(FileType sql) {
        String pre = "Database.MySQL.";
        String host, database, username, password;
        host = sql.getString(pre + "host");
        int port = sql.getInt(pre + "port");
        database = sql.getString(pre + "database");
        username = sql.getString(pre + "username");
        password = sql.getString(pre + "password");
        int poolSize = sql.getInt("Database.PoolSize");
        table = sql.getString(pre + "tablePrefix") + "data";
        HikariConfig hConfig = new HikariConfig();
        hConfig.setJdbcUrl("jdbc:mysql://" + host + ':' + port + '/' + database);
        hConfig.setUsername(username);
        hConfig.setPassword(password);
        hConfig.setMinimumIdle(poolSize);
        hConfig.setMaximumPoolSize(poolSize);
        /*try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			debug("Mhh... Seems like the mysql isn't setup correctly, can you fix me in the config.yml for [BetterEconomy] <3");
		}*/
        dataSource = new HikariDataSource(hConfig);
    }

    Connection getConnection() throws SQLException {
        synchronized (this) {
            return dataSource.getConnection();
        }
    }

    private void debug(String msg) {
        BetterEconomy.getInstance().debug(msg);
    }
}
