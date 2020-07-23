package me.SuperRonanCraft.BetterEconomy.events;

import me.SuperRonanCraft.BetterEconomy.BetterEconomy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands {

    public void onCommand(CommandSender sendi, Command command, String label, String[] args) {
        if (!(sendi instanceof Player)) {
            getPl().getMessages().errorConsole();
            return;
        }
        getPl().getMessages().getBalance(sendi,getPl().economyImplementater.getBalance((Player) sendi));
    }

    private BetterEconomy getPl() {
        return BetterEconomy.getInstance();
    }
}
