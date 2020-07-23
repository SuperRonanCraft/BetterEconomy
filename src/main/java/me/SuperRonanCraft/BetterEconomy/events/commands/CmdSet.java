package me.SuperRonanCraft.BetterEconomy.events.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdSet implements EconomyCommand, EconomyCommandHelpable {

    @Override //Coins Set [player]
    public void execute(CommandSender sendi, String label, String[] args) {
        getPl().getMessages().getBalance(sendi, getPl().economyImplementer.getBalance((Player) sendi));
    }

    @Override
    public boolean hasPerm(CommandSender sendi) {
        return true;
    }

    @Override
    public String getHelp() {
        return getPl().getMessages().getMessagesHelp().getSet();
    }
}
