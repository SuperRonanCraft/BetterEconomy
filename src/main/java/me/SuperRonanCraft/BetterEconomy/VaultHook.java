package me.SuperRonanCraft.BetterEconomy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.ServicePriority;

public class VaultHook {

    private Economy provider;

    public void hook() {
        provider = BetterEconomy.getInstance.economyImplementater;
        Bukkit.getServicesManager().register(Economy.class, this.provider, BetterEconomy.getInstance, ServicePriority.Normal);
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "VaultAPI hooked into " + ChatColor.AQUA + BetterEconomy.getInstance.getName());
    }

    public void unhook() {
        Bukkit.getServicesManager().unregister(Economy.class, this.provider);
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "VaultAPI unhooked from " + ChatColor.AQUA + BetterEconomy.getInstance.getName());
    }
}
