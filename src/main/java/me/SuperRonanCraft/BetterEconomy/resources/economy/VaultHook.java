package me.SuperRonanCraft.BetterEconomy.resources.economy;

import me.SuperRonanCraft.BetterEconomy.BetterEconomy;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.ServicePriority;

public class VaultHook {

    private Economy provider;

    public void hook() {
        provider = getPl().getEconomy();
        Bukkit.getServicesManager().register(Economy.class, this.provider, getPl(), ServicePriority.Normal);
        getPl().debug("hooked into VaultAPI");
    }

    public void unhook() {
        Bukkit.getServicesManager().unregister(Economy.class, this.provider);
        getPl().debug("unhooked from VaultAPI");
    }

    private BetterEconomy getPl() {
        return BetterEconomy.getInstance();
    }
}
