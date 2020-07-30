package me.SuperRonanCraft.BetterEconomy.events.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CmdBalance implements EconomyCommand, EconomyCommandHelpable, EconomyCommandTabPlayers {

    @Override //Coins or //Coins bal [player]
    public void execute(CommandSender sendi, String label, String[] args) {
        if (args.length == 2 && getPl().getPerms().getBalOther(sendi)) {
            String pName = args[1];
            Player p = Bukkit.getPlayer(pName);
            if (p != null)
                getPl().getMessages().getBalanceOther(sendi, getPl().getEconomy().getBalance(p), p.getName());
            else
                getPl().getMessages().getFailName(sendi, pName);
        } else {
            getPl().getMessages().getBalance(sendi, getPl().getEconomy().getBalance((Player) sendi));
        }
    }

    @Override
    public List<String> tabPlayers(CommandSender sendi, String arg) {
        List<String> list = new ArrayList<>();
        if (getPl().getPerms().getBalOther(sendi))
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getName().toUpperCase().startsWith(arg.toUpperCase()))
                    list.add(p.getName());
            }
        return list;
    }

    @Override
    public boolean hasPerm(CommandSender sendi) {
        return true;
    }

    @Override
    public boolean allowConsole() {
        return false;
    }

    @Override
    public String getHelp() {
        return getPl().getMessages().getMessagesHelp().getBalance();
    }
}
