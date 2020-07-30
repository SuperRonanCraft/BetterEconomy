package me.SuperRonanCraft.BetterEconomy.resources;

import me.SuperRonanCraft.BetterEconomy.resources.softdepends.DependsPermissions;
import org.bukkit.command.CommandSender;

public class Permissions {

    private final static String pre = "bettereconomy.";
    private final DependsPermissions perm = new DependsPermissions();

    public void register() {
        perm.register(); //Vault registration
    }
//PERMISSIONS

    //Reload plugin
    public boolean getReload(CommandSender sendi) {
        return perm(pre + "reload", sendi);
    }

    //Send money to other
    public boolean getSend(CommandSender sendi) {
        return perm(pre + "send", sendi);
    }

    //Set a balance
    public boolean getSet(CommandSender sendi) {
        return perm(pre + "set", sendi);
    }

    //Add to a balance
    public boolean getAdd(CommandSender sendi) {
        return perm(pre + "add", sendi);
    }

    //Get balance of other
    public boolean getBalOther(CommandSender sendi) {
        return perm(pre + "bal.other", sendi);
    }

    //Remove from a balance
    public boolean getRemove(CommandSender sendi) {
        return perm(pre + "remove", sendi);
    }

//PROCESSING
    private boolean perm(String str, CommandSender sendi) {
        return perm.hasPerm(str, sendi);
    }
}
