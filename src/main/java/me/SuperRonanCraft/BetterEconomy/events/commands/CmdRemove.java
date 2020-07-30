package me.SuperRonanCraft.BetterEconomy.events.commands;

import me.SuperRonanCraft.BetterEconomy.resources.data.DatabasePlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdRemove implements EconomyCommand, EconomyCommandHelpable, EconomyCommandTabPlayers {

    @Override //Coins remove [player] [amount]
    public void execute(CommandSender sendi, String label, String[] args) {
        if (args.length < 3) {
            usage(sendi, label);
            return;
        }
        double amt;
        try {
            amt = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            getPl().getMessages().getFailNumber(sendi);
            return;
        }
        String pName = args[1];
        Player p = Bukkit.getPlayer(pName);
        if (p != null) {
            getPl().getEconomy().withdrawPlayer(p, amt);
            getPl().getMessages().getSuccessRemove(sendi, p.getName(), String.valueOf(amt));
        } else {
            DatabasePlayer pInfo = getPl().getSystems().getDatabasePlayer(sendi, args[1]);
            if (pInfo != null) { //Only one player found
                double newamt = Math.max(pInfo.balance - amt, 0.0);
                getPl().getDatabase().playerSetBalance(pInfo.id, newamt);
                getPl().getMessages().getSuccessRemove(sendi, pInfo.name, String.valueOf(newamt));
            }
        }
    }

    private void usage(CommandSender sendi, String label) {
        getPl().getMessages().getMessagesUsage().getRemove(sendi, label);
    }

    @Override
    public boolean hasPerm(CommandSender sendi) {
        return getPl().getPerms().getRemove(sendi);
    }

    @Override
    public String getHelp() {
        return getPl().getMessages().getMessagesHelp().getRemove();
    }
}
