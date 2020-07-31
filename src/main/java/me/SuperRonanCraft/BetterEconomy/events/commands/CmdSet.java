package me.SuperRonanCraft.BetterEconomy.events.commands;

import me.SuperRonanCraft.BetterEconomy.resources.data.DatabasePlayer;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdSet implements EconomyCommand, EconomyCommandHelpable, EconomyCommandTabPlayers, EconomyCommandDoubleGrab {

    @Override //Coins set [player] [amount]
    public void execute(CommandSender sendi, String label, String[] args) {
        if (args.length < 3) {
            usage(sendi, label);
            return;
        }
        double amt;
        try {
            amt = getDouble(sendi, args[2]);
            if (amt < 0) {
                getPl().getMessages().getFailNumber(sendi);
                return;
            }
        } catch (NumberFormatException e) {
            getPl().getMessages().getFailNumber(sendi);
            return;
        }
        String pName = args[1];
        Player p = Bukkit.getPlayer(pName);
        if (p != null) { //Local player
            getPl().getEconomy().setPlayer(p, amt);
            getPl().getMessages().getSuccessSet(sendi, p.getName(), String.valueOf(getPl().getEconomy().getBalance(p)));
        } else { //Look on the mysql
            DatabasePlayer pInfo = getPl().getSystems().getDatabasePlayer(sendi, args[1]);
            if (pInfo != null) //Only one player found
                getPl().getDatabase().playerSetBalance(pInfo.id, amt);
        }
    }

    private void usage(CommandSender sendi, String label) {
        getPl().getMessages().getMessagesUsage().getSet(sendi, label);
    }

    @Override
    public boolean hasPerm(CommandSender sendi) {
        return getPl().getPerms().getSet(sendi);
    }

    @Override
    public String getHelp() {
        return getPl().getMessages().getMessagesHelp().getSet();
    }
}
