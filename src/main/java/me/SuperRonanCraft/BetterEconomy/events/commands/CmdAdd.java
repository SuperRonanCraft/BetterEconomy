package me.SuperRonanCraft.BetterEconomy.events.commands;

import me.SuperRonanCraft.BetterEconomy.resources.data.DatabasePlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CmdAdd implements EconomyCommand, EconomyCommandHelpable, EconomyCommandTabPlayers, EconomyCommandDoubleGrab {

    @Override //Coins add [player] [amount]
    public void execute(CommandSender sendi, String label, String[] args) {
        if (args.length < 3) {
            usage(sendi, label);
            return;
        }
        double amt;
        try {
            amt = getDouble(sendi, args[2]);
        } catch (NumberFormatException e) {
            getPl().getMessages().getFailNumber(sendi);
            return;
        }
        String pName = args[1];
        Player p = Bukkit.getPlayer(pName);
        if (p != null) {
            getPl().getEconomy().depositPlayer(p, amt);
            getPl().getMessages().getSuccessAdd(sendi, p.getName(), String.valueOf(amt));
        } else {
            DatabasePlayer pInfo = getPl().getSystems().getDatabasePlayer(sendi, pName);
            if (pInfo != null) {
                getPl().getDatabase().playerAddBalance(pInfo.id, amt);
                getPl().getMessages().getSuccessAdd(sendi, pInfo.name, String.valueOf(amt));
            } else
                getPl().getMessages().getFailName(sendi, pName);
        }
    }

    private void usage(CommandSender sendi, String label) {
        getPl().getMessages().getMessagesUsage().getAdd(sendi, label);
    }

    @Override
    public boolean hasPerm(CommandSender sendi) {
        return getPl().getPerms().getAdd(sendi);
    }

    @Override
    public String getHelp() {
        return getPl().getMessages().getMessagesHelp().getAdd();
    }
}
