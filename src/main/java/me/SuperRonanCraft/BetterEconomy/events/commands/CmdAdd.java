package me.SuperRonanCraft.BetterEconomy.events.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdAdd implements EconomyCommand, EconomyCommandHelpable {

    @Override //Coins Add [player] [amount]
    public void execute(CommandSender sendi, String label, String[] args) {
        if (args.length >= 3) {
            Player p = Bukkit.getPlayer(args[1]);
            if (p != null)
                getPl().getEconomy().depositPlayer(p, Integer.parseInt(args[2]));
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
