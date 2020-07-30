package me.SuperRonanCraft.BetterEconomy.resources.files.lang;

import me.SuperRonanCraft.BetterEconomy.BetterEconomy;
import me.SuperRonanCraft.BetterEconomy.resources.files.FileLangs;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Messages {

    private final String preM = "Messages.";
    private final MessagesHelp messagesHelp = new MessagesHelp(this);
    private final MessagesUsage messagesUsage = new MessagesUsage(this);

    public MessagesHelp getMessagesHelp() {
        return messagesHelp;
    }

    public MessagesUsage getMessagesUsage() {
        return messagesUsage;
    }

//MESSAGES
    public void errorConsole(String label) {
        getPl().getLogger().severe("Console cannot do this! Type '/{0} help'".replace("{0}", label));
    }

    public void getBalance(CommandSender sendi, Double bal) {
        sms(sendi, getLang().getString(preM + "Balance.Self").replace("{0}", String.valueOf(bal)));
    }

    public void getBalanceOther(CommandSender sendi, Double bal, String p) {
        sms(sendi, getLang().getString(preM + "Balance.Other").replace("{1}", String.valueOf(bal)).replace("{0}", p));
    }

    public String listTopPrefix() {
        return getLang().getString(preM + "Top.Prefix");
    }

    public String listTopPlayer() {
        return getLang().getString(preM + "Top.Player");
    }

    public void getReload(CommandSender sendi) {
        sms(sendi, getLang().getString(preM + "Reload"));
    }

    public void getSuccessAdd(CommandSender sendi, String p, String amt) {
        sms(sendi, getLang().getString(preM + "Success.Add").replace("{0}", amt).replace("{1}", p));
    }

    public void getSuccessRemove(CommandSender sendi, String p, String amt) {
        sms(sendi, getLang().getString(preM + "Success.Remove").replace("{0}", amt).replace("{1}", p));
    }

    public void getSuccessSet(CommandSender sendi, String p, String amt) {
        sms(sendi, getLang().getString(preM + "Success.Set").replace("{0}", amt).replace("{1}", p));
    }

    public void getFailName(CommandSender sendi, String p) {
        sms(sendi, getLang().getString(preM + "Fail.Name").replace("{0}", p));
    }

    public void getFailNumber(CommandSender sendi) {
        sms(sendi, getLang().getString(preM + "Fail.Number"));
    }

    //PROCESSING
    public void sms(CommandSender sendi, String msg) {
        if (!msg.equals(""))
            sendi.sendMessage(colorPre(msg));
    }

    public void sms(CommandSender sendi, List<String> msg) {
        if (msg != null && !msg.isEmpty())
            try {
                msg.forEach(s -> msg.set(msg.indexOf(s), colorPre(s)));
                sendi.sendMessage(msg.toArray(new String[0]));
            } catch (NullPointerException e) {
                sendi.sendMessage(colorPre("&cWhoops! Seems like the server owner didn't update their " +
                        "messages file! Please contact an admin for help!"));
            }
    }

    private String getPrefix() {
        return getLang().getString(preM + "Prefix");
    }

    public String colorPre(String str) {
        return color(str.replace("%prefix%", getPrefix()));
    }

    public String color(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    FileLangs getLang() {
        return getPl().getFiles().getLang();
    }

    private BetterEconomy getPl() {
        return BetterEconomy.getInstance();
    }
}
