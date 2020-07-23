package me.SuperRonanCraft.BetterEconomy;

import me.SuperRonanCraft.BetterEconomy.events.Commands;
import me.SuperRonanCraft.BetterEconomy.events.Events;
import me.SuperRonanCraft.BetterEconomy.resources.Files;
import me.SuperRonanCraft.BetterEconomy.resources.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class BetterEconomy extends JavaPlugin {

    public HashMap<UUID, Double> playerBank = new HashMap<>();
    public EconomyImplementer economyImplementater = new EconomyImplementer();
    //Privates
    private static BetterEconomy instance;
    private final VaultHook vaultHook = new VaultHook();
    private Events events = new Events();
    private final Commands commands = new Commands();
    private final Files files = new Files();
    private final Messages messages = new Messages();

    @Override
    public void onEnable() {
        instance = this;
        vaultHook.hook(); //Economy Start
        events.load(); //Event listener
        files.loadAll(); //Load Files
    }

    @Override
    public void onDisable() {
        vaultHook.unhook();
    }

    @Override
    public boolean onCommand(CommandSender sendi, Command command, String label, String[] args) {
        commands.onCommand(sendi, command, label, args);
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }

    public static BetterEconomy getInstance() {
        return instance;
    }

    public Files getFiles() {
        return files;
    }

    public Messages getMessages() {
        return messages;
    }
}
