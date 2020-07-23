package me.SuperRonanCraft.BetterEconomy.events.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdReload implements EconomyCommand, EconomyCommandHelpable {

    @Override
    public void execute(CommandSender sendi, String label, String[] args) {
        getPl().load(true);
        getPl().getMessages().getReload(sendi);
    }

    @Override
    public boolean hasPerm(CommandSender sendi) {
        return getPl().getPerms().getReload(sendi);
    }

    @Override
    public String getHelp() {
        return getPl().getMessages().getMessagesHelp().getReload();
    }
}
