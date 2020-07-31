package me.SuperRonanCraft.BetterEconomy;

import me.SuperRonanCraft.BetterEconomy.events.Commands;
import me.SuperRonanCraft.BetterEconomy.events.Events;
import me.SuperRonanCraft.BetterEconomy.resources.Permissions;
import me.SuperRonanCraft.BetterEconomy.resources.Systems;
import me.SuperRonanCraft.BetterEconomy.resources.data.Database;
import me.SuperRonanCraft.BetterEconomy.resources.economy.EconomyImplementer;
import me.SuperRonanCraft.BetterEconomy.resources.files.FileBasics;
import me.SuperRonanCraft.BetterEconomy.resources.files.Files;
import me.SuperRonanCraft.BetterEconomy.resources.files.lang.Messages;
import me.SuperRonanCraft.BetterEconomy.resources.economy.VaultHook;
import me.SuperRonanCraft.BetterEconomy.resources.softdepends.DependsPlaceholders;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.List;

public class BetterEconomy extends JavaPlugin {

    private static final EconomyImplementer economyImplementer = new EconomyImplementer();
    private static BetterEconomy instance;
    private final VaultHook vaultHook = new VaultHook();
    private final Events events = new Events();
    private final Commands commands = new Commands();
    private final Files files = new Files();
    private final Messages messages = new Messages();
    private final Permissions perms = new Permissions();
    private final Database database = new Database();
    private final Systems systems = new Systems();
    private DependsPlaceholders ph = null;
    //Settings
    private boolean debug = false;

    @Override
    public void onEnable() {
        instance = this;
        load(false);
    }

    public void load(boolean reload) {
        files.loadAll(); //Load Files
        debug = getFiles().getType(FileBasics.FILETYPE.CONFIG).getBoolean("Debug");
        if (reload)
            vaultHook.unhook();
        vaultHook.hook(); //Economy Start
        perms.register(); //Vault hook
        economyImplementer.load(); //Reset cache if any
        database.load(); //Reset cache and connect to database
        events.load(reload); //Event Listener
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            if (ph == null)
                ph = new DependsPlaceholders();
            ph.register();
        }
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

    public Database getDatabase() {
        return database;
    }

    public Events getEvents() {
        return events;
    }

    public Systems getSystems() {
        return systems;
    }

    public void debug(String msg) {
        if (debug)
            getLogger().info(msg);
    }

    public EconomyImplementer getEconomy() {
        return economyImplementer;
    }
}
