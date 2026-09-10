package master.com.mEssentials.commands.admin;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DashboardEditCommand implements MECommand {
    private final MEssentials plugin;

    public DashboardEditCommand(MEssentials plugin) {
        this.plugin = plugin;
    }

    @Override public String getName() { return "dashboardedit"; }
    @Override public String getPermission() { return "messentials.admin"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) return;

        Inventory editMenu = Bukkit.createInventory(null, 27, "§6Editing Dashboard...");

        if (plugin.getDataManager().getGui().getConfigurationSection("dashboard") != null) {
            for (String key : plugin.getDataManager().getGui().getConfigurationSection("dashboard").getKeys(false)) {
                try {
                    int slot = Integer.parseInt(key);
                    ItemStack item = plugin.getDataManager().getGui().getItemStack("dashboard." + key + ".item");
                    editMenu.setItem(slot, item);
                } catch (Exception ignored) {}
            }
        }

        player.openInventory(editMenu);
        player.sendMessage("§e§lDASHBOARD §7Arrange items. §6Right-Click §7to set a command. Close to save.");
    }
}