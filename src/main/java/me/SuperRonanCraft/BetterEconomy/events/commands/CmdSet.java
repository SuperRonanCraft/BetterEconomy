package me.SuperRonanCraft.BetterEconomy.events.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdSet implements EconomyCommand, EconomyCommandHelpable, EconomyCommandTabPlayers {

    @Override //Coins set [player] [amount]
    public void execute(CommandSender sendi, String label, String[] args) {
        if (args.length >= 3) {
            String pName = args[1];
            Player p = Bukkit.getPlayer(pName);
            if (p != null) {
                try {
                    double amt = Double.parseDouble(args[2]);
                    double current = getPl().getEconomy().getBalance(p);
                    if (current > amt) {
                        double deduct = amt - current;
                        getPl().getEconomy().withdrawPlayer(p, deduct);
                    } else if (current < amt) {
                        double add = current - amt;
                        getPl().getEconomy().depositPlayer(p, add);
                    }
                    getPl().getMessages().getSuccessSet(sendi, p.getName(), String.valueOf(getPl().getEconomy().getBalance(p)));
                } catch (NumberFormatException e) {
                    getPl().getMessages().getFailNumber(sendi);
                }
            } else {
                getPl().getMessages().getFailName(sendi, pName);
            }
        } else
            usage(sendi, label);
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
