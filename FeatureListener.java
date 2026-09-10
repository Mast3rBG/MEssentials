package master.com.mEssentials.listeners;

import master.com.mEssentials.MEssentials;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Bukkit;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FeatureListener implements Listener {
    private final MEssentials plugin;

    public FeatureListener(MEssentials plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onVaultClose(InventoryCloseEvent event) {
        if (!event.getView().getTitle().equals("§8Private Vault")) return;

        Player p = (Player) event.getPlayer();
        File folder = new File(plugin.getDataFolder(), "vaults");
        if (!folder.exists()) folder.mkdirs();

        File file = new File(folder, p.getUniqueId() + ".yml");
        YamlConfiguration config = new YamlConfiguration();
        config.set("items", event.getInventory().getContents());
        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        plugin.getLastLocation().put(e.getPlayer().getUniqueId(), e.getFrom());
    }

    @EventHandler
    public void onWandUse(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock() == null || event.getClickedBlock().getType() != Material.CHEST) return;

        ItemStack item = event.getItem();
        if (item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName().contains("SELL WAND")) {
            event.setCancelled(true);
            Chest chest = (Chest) event.getClickedBlock().getState();
            double total = 0;

            for (ItemStack content : chest.getInventory().getContents()) {
                if (content == null) continue;
                double price = plugin.getConfig().getDouble("prices." + content.getType().name(), 0);
                if (price > 0) {
                    total += (price * content.getAmount());
                    chest.getInventory().remove(content);
                }
            }
            event.getPlayer().sendMessage(total > 0 ? "§aChest sold for §e$" + (total * 1.5) : "§cNo sellable items!");
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        String ip = p.getAddress().getAddress().getHostAddress();

        plugin.getConfig().set("player-ips." + p.getUniqueId(), ip);
        plugin.saveConfig();

        String joinFormat = plugin.getConfig().getString("messages.join-message", "&8[&a+&8] &7%player%");
        String finalMessage = org.bukkit.ChatColor.translateAlternateColorCodes('&', joinFormat.replace("%player%", p.getName()));
        event.setJoinMessage(finalMessage);

        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.hasMetadata("vanished")) {
                if (!p.hasPermission("messentials.staff")) {
                    p.hidePlayer(plugin, online);
                }
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        String quitFormat = plugin.getConfig().getString("messages.quit-message", "&8[&c-&8] &7%player%");
        String finalMessage = org.bukkit.ChatColor.translateAlternateColorCodes('&', quitFormat.replace("%player%", p.getName()));
        event.setQuitMessage(finalMessage);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (plugin.getFrozenPlayers().contains(p.getUniqueId())) {
            if (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getZ() != e.getTo().getZ()) {
                e.setTo(e.getFrom());
                p.sendMessage("§cYou cannot move while frozen!");
            }
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (plugin.getMutedPlayers().contains(uuid) || plugin.getConfig().contains("mutes." + uuid)) {

            long expiry = plugin.getConfig().getLong("mutes." + uuid + ".expiry", -1);
            if (expiry != -1 && System.currentTimeMillis() > expiry) {
                plugin.getConfig().set("mutes." + uuid, null);
                plugin.saveConfig();
                plugin.getMutedPlayers().remove(uuid);
                return;
            }

            event.setCancelled(true);
            player.sendMessage("§c§lERROR §8» §7You are currently muted.");
            player.sendMessage("§fReason: §7" + plugin.getConfig().getString("mutes." + uuid + ".reason", "No reason provided"));
        }
    }

    @EventHandler
    public void onDashboardClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("§8Server Dashboard")) {
            event.setCancelled(true);

            Player p = (Player) event.getWhoClicked();
            ItemStack clicked = event.getCurrentItem();
            if (clicked == null) return;

            if (clicked.getType() == Material.COMPASS) {
                p.closeInventory();
                p.performCommand("spawn");
            }
        }
    }

    @EventHandler
    public void onDashboardMove(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName().contains("Dashboard")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onShopClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("§8Server Shop")) {
            event.setCancelled(true);

            Player p = (Player) event.getWhoClicked();
            ItemStack item = event.getCurrentItem();
            if (item == null || item.getType() == Material.AIR) return;

            double price = plugin.getConfig().getDouble("shop." + item.getType().name() + ".buy", 100.0);
            double balance = plugin.getConfig().getDouble("balances." + p.getUniqueId(), 0.0);

            if (balance >= price) {
                plugin.getConfig().set("balances." + p.getUniqueId(), balance - price);
                p.getInventory().addItem(new ItemStack(item.getType(), 1));
                p.sendMessage("§aBought " + item.getType().name() + " for §e$" + price);
            } else {
                p.sendMessage("§cYou cannot afford this!");
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        String title = event.getView().getTitle();

        if (title.equals("§6Editing Dashboard...")) {
            plugin.getConfig().set("dashboard.items", null);
            for (int i = 0; i < event.getInventory().getSize(); i++) {
                ItemStack item = event.getInventory().getItem(i);
                if (item != null) plugin.getConfig().set("dashboard.items." + i, item);
            }
            plugin.saveConfig();
        }

        if (title.equals("§cEditing Shop...")) {
            plugin.getConfig().set("shop.items", null);
            for (int i = 0; i < event.getInventory().getSize(); i++) {
                ItemStack item = event.getInventory().getItem(i);
                if (item != null) plugin.getConfig().set("shop.items." + i + ".item", item);
            }
            plugin.saveConfig();
        }
    }

    @EventHandler
    public void onSaveLayout(InventoryCloseEvent event) {
        String title = event.getView().getTitle();

        if (title.equals("§6Editing Dashboard...")) {
            plugin.getConfig().set("dashboard.items", null);
            for (int i = 0; i < event.getInventory().getSize(); i++) {
                ItemStack item = event.getInventory().getItem(i);
                if (item != null && item.getType() != Material.AIR) {
                    plugin.getConfig().set("dashboard.items." + i, item);
                }
            }
            plugin.saveConfig();
        }

        if (title.startsWith("§cEditing Shop: ")) {
            String category = title.replace("§cEditing Shop: ", "");
            plugin.getConfig().set("shop." + category, null);
            for (int i = 0; i < event.getInventory().getSize(); i++) {
                ItemStack item = event.getInventory().getItem(i);
                if (item != null && item.getType() != Material.AIR) {
                    plugin.getConfig().set("shop." + category + "." + i + ".item", item);
                    plugin.getConfig().set("shop." + category + "." + i + ".price", 100.0);
                }
            }
            plugin.saveConfig();
        }
    }
}
