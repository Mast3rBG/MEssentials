package master.com.mEssentials.listeners;

import master.com.mEssentials.MEssentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ShopListener implements Listener {
    private final MEssentials plugin;
    public ShopListener(MEssentials plugin) { this.plugin = plugin; }

    @EventHandler
    public void onShopClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        Player p = (Player) event.getWhoClicked();
        if (event.getCurrentItem() == null) return;

        if (title.equals("§8Server Shop")) {
            event.setCancelled(true);
            String cat = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
            openCategory(p, cat);
        }

        if (title.startsWith("§0Buying: ")) {
            event.setCancelled(true);
            String cat = title.replace("§0Buying: ", "");
            int slot = event.getRawSlot();

            double price = plugin.getConfig().getDouble("shop.categories." + cat + ".items." + slot + ".price", 0);
            double bal = plugin.getDataManager().getConfig().getDouble("players." + p.getUniqueId() + ".balance", 0.0);

            if (bal >= price) {
                plugin.getDataManager().getConfig().set("players." + p.getUniqueId() + ".balance", bal - price);
                plugin.getDataManager().saveConfig();
                p.getInventory().addItem(plugin.getConfig().getItemStack("shop.categories." + cat + ".items." + slot + ".item").clone());
                p.sendMessage("§aPurchased for §e$" + price);
            } else {
                p.sendMessage("§cYou can't afford this!");
            }
        }
    }

    private void openCategory(Player p, String cat) {
        Inventory inv = Bukkit.createInventory(null, 54, "§0Buying: " + cat);
        ConfigurationSection items = plugin.getConfig().getConfigurationSection("shop.categories." + cat + ".items");
        if (items != null) {
            for (String key : items.getKeys(false)) {
                inv.setItem(Integer.parseInt(key), plugin.getConfig().getItemStack("shop.categories." + cat + ".items." + key + ".item"));
            }
        }
        p.openInventory(inv);
    }
}