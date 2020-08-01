package me.SuperRonanCraft.BetterEconomy.events.commands;

import org.bukkit.command.CommandSender;

public class CmdVersion implements EconomyCommand, EconomyCommandHelpable {

    @Override
    public void execute(CommandSender sendi, String label, String[] args) {
        getPl().getMessages().sms(sendi, "%prefix% &aVersion &e#" + getPl().getDescription().getVersion());
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
