package me.SuperRonanCraft.BetterEconomy.resources.files.lang;

import org.bukkit.command.CommandSender;

public class MessagesUsage {

    private static String pre = "Usage.";
    private Messages pl;

    public MessagesUsage(Messages pl) {
        this.pl = pl;
    }

    public void getSet(CommandSender sendi, String label) {
        pl.sms(sendi, pl.getLang().getString(pre + "Set").replace("{0}", label));
    }

    public void getRemove(CommandSender sendi, String label) {
        pl.sms(sendi, pl.getLang().getString(pre + "Remove").replace("{0}", label));
    }

    public void getAdd(CommandSender sendi, String label) {
         pl.sms(sendi, pl.getLang().getString(pre + "Add").replace("{0}", label));
    }
}
