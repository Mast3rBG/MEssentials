package master.com.mEssentials.commands;
import master.com.mEssentials.MEssentials;
import  org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public interface MECommand {
    String getName();
    String getPermission();

    default boolean isEnabled() {
        MEssentials plugin = JavaPlugin.getPlugin(MEssentials.class);
        if (getName().equalsIgnoreCase("mehelp")) return true;
        return plugin.getConfig().getBoolean("commands." + getName() + ".enabled", true);
    }

    void execute(CommandSender sender, String[] args);
}