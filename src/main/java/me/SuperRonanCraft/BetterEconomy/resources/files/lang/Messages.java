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

    public String listTopPrefix(String topAmount) {
        return getLang().getString(preM + "Top.Prefix").replace("{0}", topAmount);
    }

    public String listTopPlayer(String index, String player, String amount) {
        return getLang().getString(preM + "Top.Player").replace("{0}", index). replace("{1}", player).replace("{2}", amount);
    }

    public String listTopNone() {
        return getLang().getString(preM + "Top.None");
    }

    //Reload
    public void getReload(CommandSender sendi) {
        sms(sendi, getLang().getString(preM + "Reload"));
    }

    //Add
    public void getSuccessAdd(CommandSender sendi, String p, String amt) {
        sms(sendi, getLang().getString(preM + "Success.Add").replace("{0}", amt).replace("{1}", p));
    }

    //Remove
    public void getSuccessRemove(CommandSender sendi, String p, String amt, String bal) {
        sms(sendi, getLang().getString(preM + "Success.Remove").replace("{0}", amt).replace("{1}", p).replace("{2}", bal));
    }

    //Set
    public void getSuccessSet(CommandSender sendi, String p, String amt) {
        sms(sendi, getLang().getString(preM + "Success.Set").replace("{0}", amt).replace("{1}", p));
    }

    //Pay - Success
    public void getSuccessPay(CommandSender sendi, String amt, String player) {
        sms(sendi, getLang().getString(preM + "Success.Pay").replace("{0}", amt).replace("{1}", player));
    }

    //Player name error
    public void getFailName(CommandSender sendi, String p) {
        sms(sendi, getLang().getString(preM + "Fail.Name").replace("{0}", p));
    }

    //Integer error
    public void getFailNumber(CommandSender sendi) {
        sms(sendi, getLang().getString(preM + "Fail.Number"));
    }

    //Too many similar player names
    public void getFailTooMany(CommandSender sendi, String p, String similar) {
        sms(sendi, getLang().getString(preM + "Fail.TooMany").replace("{0}", p).replace("{1}", similar));
    }

    //Pay - Failed
    public void getFailPay(CommandSender sendi, String player) {
        sms(sendi, getLang().getString(preM + "Fail.Pay").replace("{0}", getPl().getEconomy().currencyNamePlural()).replace("{1}", player));
    }

    //Pay - Failed SELF
    public void getFailSelf(CommandSender sendi) {
        sms(sendi, getLang().getString(preM + "Fail.Self"));
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
