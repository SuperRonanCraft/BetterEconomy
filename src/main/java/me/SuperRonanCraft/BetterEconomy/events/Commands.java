package me.SuperRonanCraft.BetterEconomy.events;

import me.SuperRonanCraft.BetterEconomy.events.commands.EconomyCommandTabPlayers;
import me.SuperRonanCraft.BetterEconomy.events.commands.EconomyCommandTypes;
import me.SuperRonanCraft.BetterEconomy.BetterEconomy;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Commands {

    public void onCommand(CommandSender sendi, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            if (!(sendi instanceof Player)) {
                getPl().getMessages().errorConsole(label);
            } else {
                EconomyCommandTypes.BAL.get().execute(sendi, label, args);
            }
        } else {
            for (EconomyCommandTypes type : EconomyCommandTypes.values()) {
                if (type.name().equalsIgnoreCase(args[0])) //Argument
                    if (type.get().hasPerm(sendi)) //Permission to run
                        if (sendi instanceof Player || type.get().allowConsole()) { //Is a player, or console is allowed
                            type.get().execute(sendi, label, args);
                            return;
                        }
            }
            EconomyCommandTypes.HELP.get().execute(sendi, label, args);
        }
    }

    public List<String> onTab(CommandSender sendi, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            for (EconomyCommandTypes type : EconomyCommandTypes.values()) {
                if (type.name().startsWith(args[0].toUpperCase())) //Argument
                    if (type.get().hasPerm(sendi)) //Permission to run
                        list.add(type.name().toLowerCase());
            }
        } else if (args.length == 2) {
            for (EconomyCommandTypes type : EconomyCommandTypes.values()) {
                if (type.get() instanceof EconomyCommandTabPlayers)
                    if (type.name().equalsIgnoreCase(args[0])) //Argument
                        if (type.get().hasPerm(sendi)) { //Permission to run
                            List<String> tabcompletes = ((EconomyCommandTabPlayers) type.get()).tabPlayers(sendi, args[1]);
                            if (!tabcompletes.isEmpty())
                                list.addAll(tabcompletes);
                        }
            }
        }
        return list;
    }

    private BetterEconomy getPl() {
        return BetterEconomy.getInstance();
    }
}
