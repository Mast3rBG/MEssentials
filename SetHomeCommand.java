package master.com.mEssentials.commands.general;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;

public class SetHomeCommand implements MECommand {
    private final MEssentials plugin;
    public SetHomeCommand(MEssentials plugin) { this.plugin = plugin; }

    @Override public String getName() { return "sethome"; }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;

        String homeName = (args.length > 0) ? args[0].toLowerCase() : "home";
        FileConfiguration config = plugin.getConfig();
        String path = "data.homes." + p.getUniqueId() + "." + homeName;

        config.set(path + ".world", p.getWorld().getName());
        config.set(path + ".x", p.getLocation().getX());
        config.set(path + ".y", p.getLocation().getY());
        config.set(path + ".z", p.getLocation().getZ());
        config.set(path + ".yaw", p.getLocation().getYaw());
        config.set(path + ".pitch", p.getLocation().getPitch());

        plugin.saveConfig();
        p.sendMessage(plugin.getPrefix() + "§aHome '§f" + homeName + "§a' has been set!");
    }
}