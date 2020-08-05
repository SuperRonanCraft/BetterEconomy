package me.SuperRonanCraft.BetterEconomy.events.commands;

public enum EconomyCommandTypes {
    BAL(new CmdBalance(), false), //Keep First for `help` command
    HELP(new CmdHelp(), false), //Keep second for `help` command
    //Order of how to show in `help` command
    TOP(new CmdTop()),
    PAY(new CmdPay()),
    ADD(new CmdAdd()),
    REMOVE(new CmdRemove()),
    SET(new CmdSet()),
    RELOAD(new CmdReload(), false),
    VERSION(new CmdVersion(), false);

    private final EconomyCommand cmd;
    public boolean sync = false;

    EconomyCommandTypes(EconomyCommand cmd) {
        this.cmd = cmd;
    }

    EconomyCommandTypes(EconomyCommand cmd, boolean sync) {
        this.cmd = cmd;
        this.sync = sync;
    }

    public EconomyCommand get() {
        return this.cmd;
    }
}
