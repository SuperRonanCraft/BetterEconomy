package me.SuperRonanCraft.BetterEconomy.events.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdRemove implements EconomyCommand, EconomyCommandHelpable {

    @Override //Coins Remove [player]
    public void execute(CommandSender sendi, String label, String[] args) {
        getPl().getMessages().getBalance(sendi, getPl().getEconomy().getBalance((Player) sendi));
    }

    @Override
    public boolean hasPerm(CommandSender sendi) {
        return true;
    }

    @Override
    public String getHelp() {
        return getPl().getMessages().getMessagesHelp().getRemove();
    }
}
