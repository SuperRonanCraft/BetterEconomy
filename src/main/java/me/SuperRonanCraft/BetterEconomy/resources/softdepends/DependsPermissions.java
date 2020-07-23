package me.SuperRonanCraft.BetterEconomy.resources.softdepends;

import me.SuperRonanCraft.BetterEconomy.BetterEconomy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;

public class DependsPermissions {
    public Permission p = null;
    private static DependsPermissions instance;

    public DependsPermissions() {
        instance = this;
    }

    public static DependsPermissions getInstance() {
        return instance;
    }

    public boolean hasPerm(String perm, CommandSender sendi) {
        if (p != null)
            return p.has(sendi, perm);
        return sendi.hasPermission(perm);
    }

    public void register() {
        try {
            if (BetterEconomy.getInstance().getServer().getPluginManager().isPluginEnabled("Vault")) {
                RegisteredServiceProvider<Permission> permissionProvider = BetterEconomy.getInstance().getServer()
                        .getServicesManager().getRegistration(Permission.class);
                p = permissionProvider.getProvider();
            } else
                p = null;
        } catch (NullPointerException e) {
            //Vault but no Perms
        }
    }
}
