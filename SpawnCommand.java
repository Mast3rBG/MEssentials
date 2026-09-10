package master.com.mEssentials.commands.general;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements MECommand {
    private final MEssentials plugin;
    private final boolean isSet;

    public SpawnCommand(MEssentials plugin, boolean b) {
        this.plugin = plugin;
        this.isSet = b;
    }

    @Override
    public String getName() {
        return isSet ? "setspawn" : "spawn";
    }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;

        if (isSet) {
            Location loc = p.getLocation();
            plugin.getConfig().set("data.spawn.world", loc.getWorld().getName());
            plugin.getConfig().set("data.spawn.x", loc.getX());
            plugin.getConfig().set("data.spawn.y", loc.getY());
            plugin.getConfig().set("data.spawn.z", loc.getZ());
            plugin.getConfig().set("data.spawn.yaw", (double) loc.getYaw());
            plugin.getConfig().set("data.spawn.pitch", (double) loc.getPitch());
            plugin.saveConfig();
            p.sendMessage(plugin.getPrefix() + "§aServer spawn has been set!");
        } else {
            if (!plugin.getConfig().contains("data.spawn.world")) {
                p.sendMessage("§cSpawn has not been set yet!");
                return;
            }
            World world = Bukkit.getWorld(plugin.getConfig().getString("data.spawn.world"));
            if (world == null) {
                p.sendMessage("§cSpawn world not found!");
                return;
            }
            double x = plugin.getConfig().getDouble("data.spawn.x");
            double y = plugin.getConfig().getDouble("data.spawn.y");
            double z = plugin.getConfig().getDouble("data.spawn.z");
            float yaw = (float) plugin.getConfig().getDouble("data.spawn.yaw");
            float pitch = (float) plugin.getConfig().getDouble("data.spawn.pitch");

            p.teleport(new Location(world, x, y, z, yaw, pitch));
            p.sendMessage(plugin.getPrefix() + "§7Teleporting to §bSpawn§7.");
        }
    }
}