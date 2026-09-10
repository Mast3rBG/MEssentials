package master.com.mEssentials.commands.general;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DashboardCommand implements MECommand {
    private final MEssentials plugin;
    public DashboardCommand(MEssentials plugin) { this.plugin = plugin; }

    @Override public String getName() { return "dashboard"; }
    @Override public String getPermission() { return ""; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;
        Inventory inv = Bukkit.createInventory(null, 27, "§8Server Dashboard");

        if (plugin.getDataManager().getGui().getConfigurationSection("dashboard") != null) {
            for (String key : plugin.getDataManager().getGui().getConfigurationSection("dashboard").getKeys(false)) {
                inv.setItem(Integer.parseInt(key), plugin.getDataManager().getGui().getItemStack("dashboard." + key + ".item"));
            }
        }
        p.openInventory(inv);
    }
}