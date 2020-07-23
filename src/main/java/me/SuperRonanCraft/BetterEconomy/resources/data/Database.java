package me.SuperRonanCraft.BetterEconomy.resources.data;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import me.SuperRonanCraft.BetterEconomy.resources.FileBasics.FILETYPE;

public class Database {
    private MySQLDatabase sql = new MySQLDatabase(this);
    //private FileDB fileDB = new FileDB(this);
    //Static Prefix's
    String  uuid = "UUID",
            balance = "Balance",
            server = "Server";
    //Storage
    HashMap<String, Integer>
            Balance = new HashMap<>();
    HashMap<String, String>
            UUIDs = new HashMap<>(),
            Server = new HashMap<>();
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
        Server.clear();
    }


}
