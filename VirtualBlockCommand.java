package master.com.mEssentials.commands.tools;

import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public class VirtualBlockCommand implements MECommand {
    private final String type;
    public VirtualBlockCommand(String type) { this.type = type; }

    @Override public String getName() { return type; }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;

        switch (type.toLowerCase()) {
            case "anvil" -> p.openAnvil(null, true);
            case "grindstone" -> p.openInventory(Bukkit.createInventory(p, InventoryType.GRINDSTONE, "Portable Grindstone"));
            case "cartography" -> p.openInventory(Bukkit.createInventory(p, InventoryType.CARTOGRAPHY, "Portable Cartography"));
            case "loom" -> p.openInventory(Bukkit.createInventory(p, InventoryType.LOOM, "Portable Loom"));
            case "stonecutter" -> p.openInventory(Bukkit.createInventory(p, InventoryType.STONECUTTER, "Portable Stonecutter"));
        }
        p.sendMessage("§aOpened portable " + type + ".");
    }
}