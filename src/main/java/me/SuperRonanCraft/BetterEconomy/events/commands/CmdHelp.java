package me.SuperRonanCraft.BetterEconomy.events.commands;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class CmdHelp implements EconomyCommand {

    @Override
    public void execute(CommandSender sendi, String label, String[] args) {
        List<String> list = new ArrayList<>();
        list.add(getPl().getMessages().getMessagesHelp().getPrefix());
        for (EconomyCommandTypes type : EconomyCommandTypes.values())
            if (type.get() instanceof EconomyCommandHelpable)
                list.add(((EconomyCommandHelpable) type.get()).getHelp().replace("{0}", label));
        getPl().getMessages().sms(sendi, list);
    }

    @Override
    public boolean hasPerm(CommandSender sendi) {
        return true;
    }
}
