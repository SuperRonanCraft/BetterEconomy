package me.SuperRonanCraft.BetterEconomy.resources.files.lang;

public class MessagesHelp {

    private static String pre = "Help.";
    private Messages pl;

    public MessagesHelp(Messages pl) {
        this.pl = pl;
    }

    public String getPrefix() {
        return pl.getLang().getString(pre + "Prefix");
    }

    public String getBalance() {
        return pl.getLang().getString(pre + "Balance");
    }

    public String getReload() {
        return pl.getLang().getString(pre + "Reload");
    }

    public String getAdd() {
        return pl.getLang().getString(pre + "Add");
    }

    public String getRemove() {
        return pl.getLang().getString(pre + "Remove");
    }

    public String getSet() {
        return pl.getLang().getString(pre + "Set");
    }
}
