package me.SuperRonanCraft.BetterEconomy;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class BetterEconomy extends JavaPlugin {

    public HashMap<UUID, Double> playerBank = new HashMap<>();
    public EconomyImplementer economyImplementater = new EconomyImplementer();
    public static BetterEconomy getInstance;
    //Privates
    private final VaultHook vaultHook = new VaultHook();
    private final EconomyCommands commands = new EconomyCommands();

    @Override
    public void onDisable() {
        vaultHook.unhook();
    }

    @Override
    public void onEnable() {
        getInstance = this;
        vaultHook.hook();
    }

    @Override
    public boolean onCommand(CommandSender sendi, Command command, String label, String[] args) {
        commands.onCommand(sendi, command, label, args);
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return super.onTabComplete(sender, command, alias, args);
    }
}
