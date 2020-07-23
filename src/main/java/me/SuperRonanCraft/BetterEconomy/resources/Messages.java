package me.SuperRonanCraft.BetterEconomy.resources;

import me.SuperRonanCraft.BetterEconomy.BetterEconomy;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Messages {

    private final String preM = "Messages.";

    public void errorConsole() {
        getPl().getLogger().severe("Console cannot do this!");
    }

    public void getBalance(CommandSender sendi, Double bal) {
        sms(sendi, getLang().getString(preM + "Balance").replace("{0}", String.valueOf(bal)));
    }

//PROCESSING
    public void sms(CommandSender sendi, String msg) {
        if (!msg.equals(""))
            sendi.sendMessage(colorPre(msg));
    }

    private String getPrefix() {
        return getLang().getString(preM + "Prefix");
    }

    public String colorPre(String str) {
        return color(getPrefix() + str);
    }

    public String color(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    private FileLangs getLang() {
        return getPl().getFiles().getLang();
    }

    private BetterEconomy getPl() {
        return BetterEconomy.getInstance();
    }
}
