package me.SuperRonanCraft.BetterEconomy.resources.data;

import me.SuperRonanCraft.BetterEconomy.BetterEconomy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class Database implements Listener {
    private final MySQLConnection sql = new MySQLConnection(this);
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

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        playerCreate(p.getUniqueId(), p);
    }

    public void load() {
        sql.load();
        BetterEconomy.getInstance().getServer().getPluginManager().registerEvents(this, BetterEconomy.getInstance());
    }

    public boolean playerExists(UUID id) {
        try {
            PreparedStatement statement = sql.getConnection()
                    .prepareStatement("SELECT * FROM " + sql.table + " WHERE " + uuid + "=?");
            statement.setString(1, id.toString());
            ResultSet results = statement.executeQuery();
            boolean exists = results.next();
            if (exists)
                BetterEconomy.getInstance().getLogger().info("Player exists!");
            else
                BetterEconomy.getInstance().getLogger().info("Player DOES NOT exists!");
            return exists;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void playerCreate(final UUID id, Player p) {
        try {
            if (!playerExists(id)) {
                PreparedStatement insert = sql.getConnection()
                        .prepareStatement("INSERT INTO " + sql.table + "(" + uuid + "," + balance + "," + server +")" +
                                "VALUE (?,?,?)");
                insert.setString(1, id.toString());
                insert.setDouble(2, 0);
                insert.setString(3, server);
                insert.executeUpdate();
                BetterEconomy.getInstance().getLogger().info("Player Inserted into Database!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void resetCache() {
        UUIDs.clear();
        Balance.clear();
    }


}
