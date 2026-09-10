package master.com.mEssentials.listeners;

import master.com.mEssentials.MEssentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GrandGuiListener implements Listener {
    private final MEssentials plugin;
    private final Map<UUID, String> inputType = new HashMap<>();

    public GrandGuiListener(MEssentials plugin) { this.plugin = plugin; }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        String rawTitle = event.getView().getTitle();
        String title = ChatColor.stripColor(rawTitle);
        Player p = (Player) event.getWhoClicked();
        int slot = event.getRawSlot();

        if (title.contains("Shop") || title.contains("Dashboard") || title.contains("Settings:")) {
            if (!title.startsWith("Editing")) {
                event.setCancelled(true);
            }
        }

        if (title.equals("Server Shop")) {
            event.setCancelled(true);
            if (plugin.getDataManager().getGui().contains("shop." + slot)) {
                double price = plugin.getDataManager().getGui().getDouble("shop." + slot + ".price");
                double bal = plugin.getDataManager().getConfig().getDouble("players." + p.getUniqueId() + ".balance", 0.0);

                if (bal >= price) {
                    plugin.getDataManager().getConfig().set("players." + p.getUniqueId() + ".balance", bal - price);
                    plugin.getDataManager().saveConfig();
                    p.getInventory().addItem(plugin.getDataManager().getGui().getItemStack("shop." + slot + ".item").clone());
                    p.sendMessage("§a§lPURCHASE §7Bought for §e$" + price);
                } else {
                    p.sendMessage("§c§lERROR §7You need §e$" + (price - bal) + " §7more.");
                }
            }
        }

        if (title.equals("Dashboard")) {
            event.setCancelled(true);
            String cmd = plugin.getDataManager().getGui().getString("dashboard." + slot + ".command");
            if (cmd != null) {
                p.closeInventory();
                p.performCommand(cmd.replace("/", ""));
            }
        }

        if (title.startsWith("Editing")) {
            if (event.getClick().isRightClick()) {
                event.setCancelled(true);
                openSettings(p, slot, title.contains("Shop") ? "Shop" : "Dashboard");
            }
        }

        if (title.startsWith("Settings:")) {
            event.setCancelled(true);
            String[] parts = title.split(" ");
            int targetSlot = Integer.parseInt(parts[2]);
            if (event.getCurrentItem().getType() == Material.PAPER) {
                p.closeInventory();
                p.sendMessage("§b§lINPUT §7Type the value in chat:");
                inputType.put(p.getUniqueId(), (title.contains("Shop") ? "PRICE:" : "CMD:") + targetSlot);
            }
        }
    }

    private void openSettings(Player p, int slot, String type) {
        org.bukkit.inventory.Inventory inv = Bukkit.createInventory(null, 9, "§bSettings: " + slot + " (" + type + ")");
        ItemStack paper = new ItemStack(Material.PAPER);
        org.bukkit.inventory.meta.ItemMeta m = paper.getItemMeta();
        m.setDisplayName(type.equals("Shop") ? "§eSet Price" : "§eSet Command");
        paper.setItemMeta(m);
        inv.setItem(4, paper);
        p.openInventory(inv);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();
        if (!inputType.containsKey(p.getUniqueId())) return;

        event.setCancelled(true);
        String[] data = inputType.remove(p.getUniqueId()).split(":");
        String type = data[0];
        int slot = Integer.parseInt(data[1]);

        if (type.equals("PRICE")) {
            try {
                double price = Double.parseDouble(event.getMessage());
                plugin.getDataManager().getGui().set("shop." + slot + ".price", price);
                p.sendMessage("§aPrice set to §e$" + price);
            } catch (Exception e) { p.sendMessage("§cInvalid price."); }
        } else {
            plugin.getDataManager().getGui().set("dashboard." + slot + ".command", event.getMessage());
            p.sendMessage("§aCommand set to §f/" + event.getMessage());
        }
        plugin.getDataManager().saveGui();
    }

    @EventHandler
    public void onSave(InventoryCloseEvent event) {
        String title = ChatColor.stripColor(event.getView().getTitle());
        if (title.startsWith("Editing")) {
            String path = title.contains("Shop") ? "shop." : "dashboard.";
            for (int i = 0; i < event.getInventory().getSize(); i++) {
                ItemStack item = event.getInventory().getItem(i);
                if (item != null) plugin.getDataManager().getGui().set(path + i + ".item", item);
                else plugin.getDataManager().getGui().set(path + i, null);
            }
            plugin.getDataManager().saveGui();
            event.getPlayer().sendMessage("§a§lSAVED §7Layout updated.");
        }
    }
}