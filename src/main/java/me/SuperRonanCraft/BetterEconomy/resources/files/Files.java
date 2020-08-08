package me.SuperRonanCraft.BetterEconomy.resources.files;

public class Files {
    private final FileLangs fileLangs = new FileLangs(); //Lang files like lang/en.yml
    private final FileBasics fileBasics = new FileBasics(); //Basic files like config.yml

    public FileLangs getLang() {
        return fileLangs;
    }

    public FileBasics.FileType getType(FileBasics.FileType type) {
        return fileBasics.types.get(fileBasics.types.indexOf(type));
    }

    public void loadAll() {
        fileBasics.load();
        fileLangs.load();
    }
}

