package me.SuperRonanCraft.BetterEconomy.resources.files;

import me.SuperRonanCraft.BetterEconomy.BetterEconomy;
import me.SuperRonanCraft.BetterEconomy.resources.data.BetterEcoDatabase;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileDatabase {
    private File database;

    public void load() {
        database = new File(BetterEconomy.getInstance().getDataFolder(), File.separator + "data" + File.separator + "database" + ".yml");
        //createFile(database);
    }

    private void createFile(File f) {
        FileConfiguration file = YamlConfiguration.loadConfiguration(f);
        try {
            file.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getDatabase() {
        if (!database.exists())
            createFile(database);
        return YamlConfiguration.loadConfiguration(database);
    }

    public void saveDatabase(FileConfiguration file) {
        try {
            file.save(database);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
