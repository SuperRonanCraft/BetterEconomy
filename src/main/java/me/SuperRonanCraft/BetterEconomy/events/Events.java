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
        Bukkit.getPluginManager().registerEvents(this, getPl());
        if (reload)
            for (Player p : Bukkit.getOnlinePlayers())
                loadPlayer(p);
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent e) {
        loadPlayer(e.getPlayer());
    }

    public void loadPlayer(Player p) {
        boolean newPlayer = getPl().getDatabase().playerCreate(p.getUniqueId(), p);
        getPl().getEconomy().createPlayerAccount(p);
        if (newPlayer)
            getPl().getEconomy().playerBank.put(p.getUniqueId(), 25.0);
        else {
            double bal = getPl().getDatabase().playerBalance(p.getUniqueId());
            getPl().getEconomy().playerBank.put(p.getUniqueId(), bal);
        }
    }

    @EventHandler
    private void onLeave(PlayerQuitEvent e) {
        unloadPlayer(e.getPlayer());
    }

    public void unloadPlayer(Player p) {
        Double amt = getPl().getEconomy().playerBank.getOrDefault(p.getUniqueId(), 0.0);
        getPl().getDatabase().playerSetBalance(p.getUniqueId(), amt);
        getPl().getDatabase().playerClean(p.getUniqueId());
        getPl().getEconomy().playerBank.remove(p.getUniqueId());
    }

    private BetterEconomy getPl() {
        return BetterEconomy.getInstance();
    }
}
