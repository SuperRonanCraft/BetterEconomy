package me.SuperRonanCraft.BetterEconomy.resources.data;

import java.util.HashMap;
import java.util.UUID;

public class Database {
    private MySQLDatabase sql = new MySQLDatabase(this);
    //private FileDB fileDB = new FileDB(this);
    //Static Prefix's
    String  uuid = "UUID",
            balance = "Balance",
            server = "Server";
    //Storage
    HashMap<UUID, Double>
            Balance = new HashMap<>();
    HashMap<UUID, String>
            UUIDs = new HashMap<>();
    boolean sqlEnabled = true;

    public void load() {
        //FILETYPE sql = FILETYPE.CONFIG;
        sqlEnabled = true;//sql.getBoolean("Data.MySQL.enabled");
        loadPlayers();
    }

    private void loadPlayers() {
        if (sqlEnabled)
            sql.load();
        downloadSql();
    }

    void downloadSql() {
        resetCache();
        if (sqlEnabled)
            sql.downloadTickets();
        //else
        //    fileDB.downloadTickets();
    }

    void resetCache() {
        UUIDs.clear();
        Balance.clear();
    }


}
