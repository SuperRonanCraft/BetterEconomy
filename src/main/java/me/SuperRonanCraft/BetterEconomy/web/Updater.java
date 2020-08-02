package me.SuperRonanCraft.BetterEconomy.web;

import me.SuperRonanCraft.BetterEconomy.BetterEconomy;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Updater {

    public static String updatedVersion;

    public Updater(BetterEconomy pl) {
        try {
            URLConnection con = new URL(getUrl() + project()).openConnection();
            updatedVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
        } catch (Exception ex) {
            pl.getLogger().severe("Failed to check for an update on spigot! Are you online?");
            updatedVersion = pl.getDescription().getVersion();
        }
    }

    private String getUrl() {
        return "https://api.spigotmc.org/legacy/update.php?resource=";
    }

    private String project() {
        return "82196";
    }
}