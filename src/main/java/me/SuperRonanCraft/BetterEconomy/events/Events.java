package me.SuperRonanCraft.BetterEconomy.events;

import me.SuperRonanCraft.BetterEconomy.BetterEconomy;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Events implements Listener {

    public void load() {
        Bukkit.getPluginManager().registerEvents(this, BetterEconomy.getInstance());
    }

    @EventHandler
    public void equals(PlayerJoinEvent e) {
        BetterEconomy.getInstance().economyImplementater.createPlayerAccount(e.getPlayer());
    }
}
