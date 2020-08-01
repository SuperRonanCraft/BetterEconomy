package me.SuperRonanCraft.BetterEconomy.resources.files;

import me.SuperRonanCraft.BetterEconomy.BetterEconomy;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileBasics {

    List<FileType> types = new ArrayList<>();

    void load() {
        types.clear();
        for (FileType type : FileType.values()) {
            type.load();
            types.add(type);
        }
    }

    public enum FileType {
        CONFIG("config");

        private String fileName;
        private YamlConfiguration config = new YamlConfiguration();

        FileType(String str) {
            this.fileName = str + ".yml";
        }

        //PUBLIC
        public String getString(String path) {
            if (config.isString(path))
                return config.getString(path);
            return "SOMETHING WENT WRONG";
        }

        public boolean getBoolean(String path) {
            return config.getBoolean(path);
        }

        public int getInt(String path) {
            return config.getInt(path);
        }

        @SuppressWarnings("all")
        public List<String> getStringList(String path) {
            if (config.isList(path))
                return config.getStringList(path);
            return new ArrayList<>();
        }

        public ConfigurationSection getConfigurationSection(String path) {
            return config.getConfigurationSection(path);
        }

        public boolean isString(String path) {
            return config.isString(path);
        }

        public boolean isList(String path) {
            return config.isList(path);
        }

        public List<Map<?, ?>> getMapList(String path) {
            return config.getMapList(path);
        }

        public YamlConfiguration getFile() {
            return config;
        }

        public void setValue(String path, Object value) {
            config.set(path, value);
        }

        //PROCCESSING
        private void load() {
            BetterEconomy pl = BetterEconomy.getInstance();
            File file = new File(pl.getDataFolder(), fileName);
            if (!file.exists())
                pl.saveResource(fileName, false);
            try {
                config.load(file);
                final InputStream defConfigStream = pl.getResource(fileName);
                if (defConfigStream != null) {
                    config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream)));
                    config.options().copyDefaults(true);
                }
                config.save(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
