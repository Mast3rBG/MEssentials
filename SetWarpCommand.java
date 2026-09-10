package master.com.mEssentials.commands.tools;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWarpCommand implements MECommand {
    private final MEssentials plugin;
    public SetWarpCommand(MEssentials plugin) { this.plugin = plugin; }

    @Override public String getName() { return "setwarp"; }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;
        if (args.length == 0) { p.sendMessage("§cUsage: /setwarp <name>"); return; }

        String name = args[0].toLowerCase();
        Location l = p.getLocation();
        String path = "data.warps." + name;

        plugin.getConfig().set(path + ".world", l.getWorld().getName());
        plugin.getConfig().set(path + ".x", l.getX());
        plugin.getConfig().set(path + ".y", l.getY());
        plugin.getConfig().set(path + ".z", l.getZ());
        plugin.getConfig().set(path + ".yaw", l.getYaw());
        plugin.getConfig().set(path + ".pitch", l.getPitch());

        plugin.saveConfig();
        p.sendMessage(plugin.getPrefix() + "§aWarp '§f" + name + "§a' set!");
    }
}