package me.SuperRonanCraft.BetterEconomy.events.commands;

import me.SuperRonanCraft.BetterEconomy.resources.data.DatabasePlayer;
import me.SuperRonanCraft.BetterEconomy.resources.files.FileBasics;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CmdTop implements EconomyCommand, EconomyCommandHelpable {

    private List<String> cacheTop = new ArrayList<>();
    private long cooldownGoal = 0;
    private final int max, cooldown;

    CmdTop() {
        max = getPl().getFiles().getType(FileBasics.FileType.CONFIG).getInt("Top.Amount"); //Max amount of top players
        cooldown = getPl().getFiles().getType(FileBasics.FileType.CONFIG).getInt("Top.Cooldown"); //In Minutes
    }

    @Override //Coins top
    public void execute(CommandSender sendi, String label, String[] args) {
        List<String> message;
        if (cooldownGoal != 0 && System.currentTimeMillis() > cooldownGoal)
            cacheTop.clear();
        if (cacheTop.isEmpty()) {
            message = new ArrayList<>();
            String prefix = getPl().getMessages().listTopPrefix(String.valueOf(max));
            message.add(prefix);
            List<DatabasePlayer> topPlayers = getPl().getDatabase().getTop(max);
            int index = 0;
            for (DatabasePlayer pInfo : topPlayers) {
                //0 = index, 1 = player, 2 = balance
                index++;
                message.add(getPl().getMessages().listTopPlayer(String.valueOf(index), pInfo.name, String.valueOf(pInfo.balance)));
            }
            if (topPlayers.isEmpty())
                message.add(getPl().getMessages().listTopNone());
            cacheTop = message;
            cooldownGoal = System.currentTimeMillis() + (cooldown * 1000);
        } else {
            message = cacheTop;
            long waiting = (cooldownGoal - System.currentTimeMillis()) / 1000;
            debug("Top list shown from cache! Waiting " + waiting + " more seconds");
        }
        getPl().getMessages().sms(sendi, message);
    }

    @Override
    public boolean hasPerm(CommandSender sendi) {
        return true;
    }

    @Override
    public boolean allowConsole() {
        return true;
    }

    @Override
    public String getHelp() {
        return getPl().getMessages().getMessagesHelp().getTop();
    }
}
