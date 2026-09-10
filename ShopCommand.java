package master.com.mEssentials.commands.economy;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopCommand implements MECommand {
    private final MEssentials plugin;
    public ShopCommand(MEssentials plugin) { this.plugin = plugin; }

    @Override public String getName() { return "shop"; }
    @Override public String getPermission() { return ""; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;

        Inventory inv = Bukkit.createInventory(null, 27, "§8Server Shop");
        ConfigurationSection cats = plugin.getConfig().getConfigurationSection("shop.categories");

        if (cats != null) {
            for (String name : cats.getKeys(false)) {
                ItemStack icon = plugin.getConfig().getItemStack("shop.categories." + name + ".icon").clone();
                ItemMeta m = icon.getItemMeta();
                m.setDisplayName("§b§l" + name);
                icon.setItemMeta(m);
                inv.addItem(icon);
            }
        }
        p.openInventory(inv);
    }
}