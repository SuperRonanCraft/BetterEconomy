package me.SuperRonanCraft.BetterEconomy.events.commands;

public enum EconomyCommandTypes {
    BAL(new CmdBalance()), //Keep First for `help` command
    HELP(new CmdHelp()), //Keep second for `help` command
    //Order of how to show in `help` command
    TOP(new CmdTop()),
    ADD(new CmdAdd()),
    REMOVE(new CmdRemove()),
    SET(new CmdSet()),
    RELOAD(new CmdReload());

    private final EconomyCommand cmd;

    EconomyCommandTypes(EconomyCommand cmd) {
        this.cmd = cmd;
    }

    public EconomyCommand get() {
        return this.cmd;
    }
}
