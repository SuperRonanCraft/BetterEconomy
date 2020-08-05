package me.SuperRonanCraft.BetterEconomy.resources.data;

import java.util.UUID;

public class DatabasePlayer {
    public String name;
    public UUID id;
    public double balance;

    public DatabasePlayer(String name, UUID id, double balance) {
        this.name = name;
        this.id = id;
        this.balance = balance;
    }
}
