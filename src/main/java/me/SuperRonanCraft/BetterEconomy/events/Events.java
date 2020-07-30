package me.SuperRonanCraft.BetterEconomy.events;

import me.SuperRonanCraft.BetterEconomy.BetterEconomy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Events implements Listener {

    public void load(boolean reload) {
        if (!reload)
            Bukkit.getPluginManager().registerEvents(this, getPl());
        for (Player p : Bukkit.getOnlinePlayers())
            loadPlayer(p);
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent e) {
        loadPlayer(e.getPlayer());
    }

    public void loadPlayer(Player p) {
        boolean newPlayer = getPl().getDatabase().playerCreate(p.getUniqueId(), p, 25.0);
        getPl().getEconomy().createPlayerAccount(p);
        if (newPlayer)
            getPl().getEconomy().depositPlayer(p, 25.0);
        else {
            double bal = getPl().getDatabase().playerBalance(p.getUniqueId());
            getPl().getEconomy().depositPlayer(p, bal);
        }
    }

    @EventHandler
    private void onLeave(PlayerQuitEvent e) {
        unloadPlayer(e.getPlayer());
    }

    public void unloadPlayer(Player p) {
        double amt = getPl().getEconomy().getBalance(p);
        getPl().getDatabase().playerSetBalance(p.getUniqueId(), amt);
        getPl().getDatabase().playerClean(p.getUniqueId());
        getPl().getEconomy().playerRemove(p.getUniqueId());
    }

    private BetterEconomy getPl() {
        return BetterEconomy.getInstance();
    }
}
