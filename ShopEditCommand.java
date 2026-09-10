package master.com.mEssentials.commands.admin;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopEditCommand implements MECommand {
    private final MEssentials plugin;
    public ShopEditCommand(MEssentials plugin) { this.plugin = plugin; }

    @Override public String getName() { return "shopedit"; }
    @Override public String getPermission() { return "messentials.admin"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;

        Inventory manager = Bukkit.createInventory(null, 27, "§0Shop Category Manager");

        ConfigurationSection categories = plugin.getConfig().getConfigurationSection("shop.categories");
        if (categories != null) {
            for (String catName : categories.getKeys(false)) {
                ItemStack icon = plugin.getConfig().getItemStack("shop.categories." + catName + ".icon");
                if (icon == null) icon = new ItemStack(Material.BOOK);

                ItemMeta meta = icon.getItemMeta();
                meta.setDisplayName("§b§l" + catName);
                meta.setLore(java.util.List.of("§7Left-Click to edit items", "§7Right-Click to delete"));
                icon.setItemMeta(meta);
                manager.addItem(icon);
            }
        }

        ItemStack create = new ItemStack(Material.NETHER_STAR);
        ItemMeta cMeta = create.getItemMeta();
        cMeta.setDisplayName("§a§l[+] Create New Category");
        create.setItemMeta(cMeta);
        manager.setItem(26, create);

        p.openInventory(manager);
    }
}