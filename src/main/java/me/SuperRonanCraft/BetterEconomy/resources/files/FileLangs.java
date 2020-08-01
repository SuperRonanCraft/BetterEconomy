package me.SuperRonanCraft.BetterEconomy.resources.files;

import me.SuperRonanCraft.BetterEconomy.BetterEconomy;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class FileLangs {
    private YamlConfiguration config = new YamlConfiguration();

    public String getString(String path) {
        if (config.isString(path))
            return config.getString(path);
        return "SOMETHING WENT WRONG";
    }

    @SuppressWarnings("all")
    public List<String> getStringList(String path) {
        if (config.isList(path))
            return config.getStringList(path);
        return Arrays.asList("SOMETHING WENT WRONG!");
    }

    public boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    @SuppressWarnings("all")
    public void load() {
        BetterEconomy pl = BetterEconomy.getInstance();
        String fileName = "lang" + File.separator + pl.getFiles().getType(FileBasics.FileType.CONFIG).getString("Language-File");
        File file = new File(pl.getDataFolder(), fileName);
        if (!file.exists())
            pl.saveResource(fileName, false);
        try {
            config.load(file);
            InputStream defConfigStream = pl.getResource(fileName);
            if (defConfigStream == null)
                defConfigStream = pl.getResource(fileName.replace(File.separator, "/"));
            if (defConfigStream != null) {
                config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream)));
                config.options().copyDefaults(true);
            }
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        generateDefaults(pl);
    }

    private String[] defaultLangs = {"en.yml"};

    private void generateDefaults(BetterEconomy pl) {
        //Generate all Langs
        for (String yaml : defaultLangs) {
            if (yaml.equals(defaultLangs[0]) && config.getName().equals(defaultLangs[0]))
                continue;
            File f = new File(pl.getDataFolder(), "lang" + File.separator + yaml);
            if (!f.exists())
                pl.saveResource("lang" + File.separator + f.getName(), false);
        }
    }
}