package master.com.mEssentials.commands.tools;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.List;

public class KitCommand implements MECommand {
    private final MEssentials plugin;
    private final String type;

    public KitCommand(MEssentials plugin, String type) {
        this.plugin = plugin;
        this.type = type;
    }

    @Override public String getName() { return type; }
    @Override public String getPermission() { return type.equals("kit") ? "" : "messentials.admin"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;
        if (args.length < 1) {
            p.sendMessage("§cUsage: /" + type + " <name>");
            return;
        }

        String kitName = args[0].toLowerCase();

        if (type.equals("createkit")) {
            plugin.getConfig().set("kits." + kitName + ".items", p.getInventory().getContents());
            plugin.saveConfig();
            p.sendMessage(plugin.getPrefix() + "§aKit §f" + kitName + " §ahas been created!");
        }
        else if (type.equals("delkit")) {
            plugin.getConfig().set("kits." + kitName, null);
            plugin.saveConfig();
            p.sendMessage(plugin.getPrefix() + "§cKit §f" + kitName + " §cremoved.");
        }
        else if (type.equals("kit")) {
            if (!plugin.getConfig().contains("kits." + kitName)) {
                p.sendMessage("§cKit not found.");
                return;
            }
            ItemStack[] items = ((List<ItemStack>) plugin.getConfig().get("kits." + kitName + ".items")).toArray(new ItemStack[0]);
            p.getInventory().addItem(items);
            p.sendMessage(plugin.getPrefix() + "§7You received kit §b" + kitName + "§7.");
        }
    }
}