package me.SuperRonanCraft.BetterEconomy.events.commands;

import me.SuperRonanCraft.BetterEconomy.resources.data.DatabasePlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CmdPay implements EconomyCommand, EconomyCommandHelpable, EconomyCommandTabPlayers, EconomyCommandDoubleGrab {

    @Override //Coins pay [player,player2...] [amount]
    public void execute(CommandSender sendi, String label, String[] args) {
        if (args.length >= 3) {
            double amt;
            try {
                amt = getDouble(sendi, args[2]);
            } catch (NumberFormatException e) {
                getPl().getMessages().getFailNumber(sendi);
                return;
            }
            String[] pNames = args[1].split(",");
            Player payor = (Player) sendi;
            for (String pName : pNames) {
                Player p = Bukkit.getPlayer(pName);
                if (p == payor) {
                    getPl().getMessages().getFailSelf(sendi);
                    continue;
                }
                if (p != null) { //Player is online
                    if (getPl().getEconomy().withdrawPlayer(payor, amt).transactionSuccess()) {
                        getPl().getEconomy().depositPlayer(p, amt);
                        getPl().getMessages().getSuccessPay(sendi, String.valueOf(amt), p.getName());
                    } else
                        getPl().getMessages().getFailPay(sendi, p.getName());
                } else { //Player is offline
                    if (getPl().getEconomy().getBalance(payor) >= amt) { //Has enough to pay out, to not call mysql everytime
                        DatabasePlayer pInfo = getPl().getSystems().getDatabasePlayer(sendi, args[1]);
                        if (pInfo != null) { //Only one player found
                            if (getPl().getEconomy().withdrawPlayer(payor, amt).transactionSuccess()) {
                                if (getPl().getDatabase().playerAddBalance(pInfo.id, amt))
                                    getPl().getMessages().getSuccessPay(sendi, String.valueOf(amt), pInfo.name);
                            } else //Should never be false, but in case
                                getPl().getMessages().getFailPay(sendi, pInfo.name);
                        }
                    } else
                        getPl().getMessages().getFailPay(sendi, args[1]);
                }
            }
        } else if (sendi instanceof Player)
            usage(sendi, label);
    }

    private void usage(CommandSender sendi, String label) {
        getPl().getMessages().getMessagesUsage().getPay(sendi, label);
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
        return getPl().getPerms().getPay(sendi);
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
