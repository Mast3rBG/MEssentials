package master.com.mEssentials;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

public class DataManager {
    private final MEssentials plugin;

    private FileConfiguration dataConfig = null;
    private File dataFile = null;

    private FileConfiguration guiConfig = null;
    private File guiFile = null;

    public DataManager(MEssentials plugin) {
        this.plugin = plugin;
        saveDefaultConfig();
        saveDefaultGui();
    }

    public void reloadConfig() {
        if (dataFile == null) {
            dataFile = new File(plugin.getDataFolder(), "data.yml");
        }
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

    public FileConfiguration getConfig() {
        if (dataConfig == null) reloadConfig();
        return dataConfig;
    }

    public void saveConfig() {
        if (dataConfig == null || dataFile == null) return;
        try {
            getConfig().save(dataFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save data to data.yml!");
        }
    }

    public void saveDefaultConfig() {
        if (dataFile == null) dataFile = new File(plugin.getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            plugin.saveResource("data.yml", false);
        }
    }

    public void reloadGui() {
        if (guiFile == null) {
            guiFile = new File(plugin.getDataFolder(), "gui.yml");
        }
        guiConfig = YamlConfiguration.loadConfiguration(guiFile);
    }

    public FileConfiguration getGui() {
        if (guiConfig == null) reloadGui();
        return guiConfig;
    }

    public void saveGui() {
        if (guiConfig == null || guiFile == null) return;
        try {
            getGui().save(guiFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save layout to gui.yml!");
        }
    }

    public void saveDefaultGui() {
        if (guiFile == null) guiFile = new File(plugin.getDataFolder(), "gui.yml");
        if (!guiFile.exists()) {
            try {
                guiFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}