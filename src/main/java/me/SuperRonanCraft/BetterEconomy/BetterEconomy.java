package me.SuperRonanCraft.BetterEconomy;

import me.SuperRonanCraft.BetterEconomy.events.Commands;
import me.SuperRonanCraft.BetterEconomy.events.Events;
import me.SuperRonanCraft.BetterEconomy.resources.Permissions;
import me.SuperRonanCraft.BetterEconomy.resources.data.Database;
import me.SuperRonanCraft.BetterEconomy.resources.economy.EconomyImplementer;
import me.SuperRonanCraft.BetterEconomy.resources.files.Files;
import me.SuperRonanCraft.BetterEconomy.resources.files.lang.Messages;
import me.SuperRonanCraft.BetterEconomy.resources.economy.VaultHook;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class BetterEconomy extends JavaPlugin {

    public HashMap<UUID, Double> playerBank = new HashMap<>();
    public EconomyImplementer economyImplementer = new EconomyImplementer();
    //Privates
    private static BetterEconomy instance;
    private final VaultHook vaultHook = new VaultHook();
    private final Events events = new Events();
    private final Commands commands = new Commands();
    private final Files files = new Files();
    private final Messages messages = new Messages();
    private final Permissions perms = new Permissions();
    private final Database database = new Database();

    @Override
    public void onEnable() {
        instance = this;
        load(false);
    }

    public void load(boolean reload) {
        if (reload) {
            vaultHook.unhook();
        }
        vaultHook.hook(); //Economy Start
        events.load(); //Event listener
        files.loadAll(); //Load Files
        perms.register();
        database.load();
    }

    @Override
    public void onDisable() {
        vaultHook.unhook();
    }

    @Override
    public boolean onCommand(CommandSender sendi, Command cmd, String label, String[] args) {
        commands.onCommand(sendi, cmd, label, args);
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sendi, Command cmd, String alias, String[] args) {
        return commands.onTab(sendi, args);
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

    public Permissions getPerms() {
        return perms;
    }
}
