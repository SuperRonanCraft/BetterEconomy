package me.SuperRonanCraft.BetterEconomy.events.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdAdd implements EconomyCommand, EconomyCommandHelpable {

    @Override //Coins Add [player] [amount]
    public void execute(CommandSender sendi, String label, String[] args) {
        if (args.length >= 3) {
            String pName = args[1];
            OfflinePlayer offlineP = Bukkit.getPlayer(pName);
            if (offlineP == null)
                for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
                    if (p.getName().equalsIgnoreCase(pName)) {
                        offlineP = p;
                        break;
                    }
                }
            if (offlineP != null) {
                try {
                    double amt = Double.parseDouble(args[2]);
                    getPl().getEconomy().depositPlayer(offlineP, amt);
                    getPl().getMessages().getSuccessAdd(sendi, offlineP.getName(), String.valueOf(amt));
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
        getPl().getMessages().getMessagesUsage().getAdd(sendi, label);
    }

    @Override
    public boolean hasPerm(CommandSender sendi) {
        return true;
    }

    @Override
    public String getHelp() {
        return getPl().getMessages().getMessagesHelp().getAdd();
    }
}
