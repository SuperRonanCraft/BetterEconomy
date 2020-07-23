package me.SuperRonanCraft.BetterEconomy.events.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdBalance implements EconomyCommand, EconomyCommandHelpable {

    @Override
    public void execute(CommandSender sendi, String label, String[] args) {
        getPl().getMessages().getBalance(sendi, getPl().economyImplementer.getBalance((Player) sendi));
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
