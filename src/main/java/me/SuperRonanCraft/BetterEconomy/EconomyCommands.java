package me.SuperRonanCraft.BetterEconomy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EconomyCommands {

    public void onCommand(CommandSender sendi, Command command, String label, String[] args) {
        if (!(sendi instanceof Player)) return;
        Player p = (Player) sendi;
        System.out.println(getPl().economyImplementater);
        p.sendMessage("Balance " + getPl().economyImplementater.getBalance(p));
    }

    private BetterEconomy getPl() {
        return BetterEconomy.getInstance;
    }
}
