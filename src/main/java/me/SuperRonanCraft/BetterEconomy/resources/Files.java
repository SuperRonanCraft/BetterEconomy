package me.SuperRonanCraft.BetterEconomy.resources;

public class Files {
    private FileLangs fileLangs = new FileLangs(); //Lang files like lang/en.yml
    private FileBasics fileBasics = new FileBasics(); //Basic files like config.yml

    FileLangs getLang() {
        return fileLangs;
    }

    public FileBasics.FILETYPE getType(FileBasics.FILETYPE type) {
        return fileBasics.types.get(fileBasics.types.indexOf(type));
    }

    public void loadAll() {
        fileBasics.load();
        fileLangs.load();
    }
}

