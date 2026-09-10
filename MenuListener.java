package master.com.mEssentials.listeners;

import master.com.mEssentials.MEssentials;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MenuListener implements Listener {
    private final MEssentials plugin;
    public MenuListener(MEssentials plugin) { this.plugin = plugin; }

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        if (event.getCurrentItem() == null) return;
        Player p = (Player) event.getWhoClicked();

        if (title.equals("§8Server Shop")) {
            event.setCancelled(true);
            int slot = event.getRawSlot();

            if (plugin.getConfig().contains("shop.items." + slot)) {
                double price = plugin.getConfig().getDouble("shop.items." + slot + ".price");
                double balance = plugin.getDataManager().getConfig().getDouble("players." + p.getUniqueId() + ".balance", 0.0);

                if (balance >= price) {
                    plugin.getDataManager().getConfig().set("players." + p.getUniqueId() + ".balance", balance - price);
                    plugin.getDataManager().saveConfig();

                    ItemStack item = plugin.getConfig().getItemStack("shop.items." + slot + ".item").clone();
                    p.getInventory().addItem(item);

                    p.sendMessage("§a§lPURCHASE §7You bought §f" + item.getType().name() + " §7for §e$" + price);
                    plugin.getScoreboardManager().showScoreboard(p);
                } else {
                    p.sendMessage("§c§lERROR §7You can't afford this! Need §e$" + (price - balance) + " §7more.");
                }
            }
        }

        if (title.equals("§8Dashboard")) {
            event.setCancelled(true);
            if (event.getCurrentItem().getType() == org.bukkit.Material.COMPASS) {
                p.performCommand("spawn");
                p.closeInventory();
            }
        }


        if (title.equals("§8Server Shop")) {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if (item == null || item.getType() == Material.AIR) return;

            p.sendMessage("§aProcessing purchase for " + item.getType().name());
        }
    }
}