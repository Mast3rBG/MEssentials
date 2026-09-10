package master.com.mEssentials.commands.general;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.Set;

public class WarpCommand implements MECommand {
    private final MEssentials plugin;
    private final String type;

    public WarpCommand(MEssentials plugin, String type) {
        this.plugin = plugin;
        this.type = type;
    }

    @Override public String getName() { return type; }
    @Override public String getPermission() { return (type.equals("setwarp") || type.equals("delwarp")) ? "messentials.admin" : ""; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;

        if (type.equals("warps")) {
            if (plugin.getConfig().getConfigurationSection("warps") == null) {
                p.sendMessage("§cNo warps set.");
                return;
            }
            Set<String> warps = plugin.getConfig().getConfigurationSection("warps").getKeys(false);
            p.sendMessage("§bWarps: §f" + String.join(", ", warps));
            return;
        }

        if (args.length < 1) {
            p.sendMessage("§cUsage: /" + type + " <name>");
            return;
        }
        String name = args[0].toLowerCase();

        switch (type) {
            case "setwarp" -> {
                plugin.getConfig().set("warps." + name, p.getLocation());
                plugin.saveConfig();
                p.sendMessage("§aWarp §f" + name + " §aset!");
            }
            case "delwarp" -> {
                plugin.getConfig().set("warps." + name, null);
                plugin.saveConfig();
                p.sendMessage("§cWarp §f" + name + " §cremoved.");
            }
            case "warp" -> {
                Location loc = plugin.getConfig().getLocation("warps." + name);
                if (loc == null) {
                    p.sendMessage("§cWarp not found.");
                    return;
                }
                p.teleport(loc);
                p.sendMessage("§7Teleporting to §b" + name);
            }
        }
    }
}