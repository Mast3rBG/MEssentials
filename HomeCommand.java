package master.com.mEssentials.commands.general;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand implements MECommand {
    private final MEssentials plugin;
    private final String type;

    public HomeCommand(MEssentials plugin, String type) {
        this.plugin = plugin;
        this.type = type;
    }

    @Override public String getName() { return type; }
    @Override public String getPermission() { return ""; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;
        String homeName = (args.length > 0) ? args[0].toLowerCase() : "home";
        String path = "players." + p.getUniqueId() + ".homes." + homeName;

        switch (type) {
            case "sethome" -> {
                plugin.getConfig().set(path, p.getLocation());
                plugin.saveConfig();
                p.sendMessage(plugin.getPrefix() + "§aHome §f" + homeName + " §aset!");
            }
            case "home" -> {
                Location loc = plugin.getConfig().getLocation(path);
                if (loc == null) {
                    p.sendMessage("§cHome '" + homeName + "' not found!");
                    return;
                }
                p.teleport(loc);
                p.sendMessage(plugin.getPrefix() + "§7Teleporting §bHome§7.");
            }
            case "delhome" -> {
                plugin.getConfig().set(path, null);
                plugin.saveConfig();
                p.sendMessage(plugin.getPrefix() + "§cHome §f" + homeName + " §cremoved.");
            }
        }
    }
}