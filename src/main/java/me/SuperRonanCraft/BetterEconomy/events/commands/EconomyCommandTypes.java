package me.SuperRonanCraft.BetterEconomy.events.commands;

public enum EconomyCommandTypes {
    BALANCE(new CmdBalance()), //Keep First for `help` command
    HELP(new CmdHelp()), //Keep second for `help` command
    //Order of how to show in `help` command
    ADD(new CmdAdd()),
    REMOVE(new CmdRemove()),
    SET(new CmdSet()),
    RELOAD(new CmdReload());

    private EconomyCommand cmd;

    EconomyCommandTypes(EconomyCommand cmd) {
        this.cmd = cmd;
    }

    public EconomyCommand get() {
        return this.cmd;
    }
}
