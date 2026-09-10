package master.com.mEssentials.listeners;

import master.com.mEssentials.MEssentials;
import org.bukkit.Bukkit;
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

public class GUIEditorListener implements Listener {
    private final MEssentials plugin;
    private final Map<UUID, Integer> dashCommandInput = new HashMap<>();

    public GUIEditorListener(MEssentials plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEditorClick(InventoryClickEvent event) {
        String title = event.getView().getTitle();
        Player p = (Player) event.getWhoClicked();

        if (title.equals("§6Editing Dashboard...")) {
            if (event.getClick().isRightClick()) {
                event.setCancelled(true);
                int slot = event.getRawSlot();
                if (event.getCurrentItem() == null) return;

                p.closeInventory();
                p.sendMessage("§b§lCOMMAND §7Type the command (no /) for this slot in chat:");
                dashCommandInput.put(p.getUniqueId(), slot);
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();
        if (dashCommandInput.containsKey(p.getUniqueId())) {
            event.setCancelled(true);
            int slot = dashCommandInput.remove(p.getUniqueId());
            String cmd = event.getMessage();

            plugin.getDataManager().getGui().set("dashboard." + slot + ".command", cmd);
            plugin.getDataManager().saveGui();

            p.sendMessage("§a§lSUCCESS §7Command set to: §f/" + cmd);

            Bukkit.getScheduler().runTask(plugin, () -> p.performCommand("dashboardedit"));
        }
    }

    @EventHandler
    public void onSave(InventoryCloseEvent event) {
        String title = event.getView().getTitle();
        if (title.equals("§6Editing Dashboard...")) {
            for (int i = 0; i < event.getInventory().getSize(); i++) {
                ItemStack item = event.getInventory().getItem(i);
                if (item != null && item.getType() != Material.AIR) {
                    plugin.getDataManager().getGui().set("dashboard." + i + ".item", item);
                } else {
                    plugin.getDataManager().getGui().set("dashboard." + i + ".item", null);
                }
            }
            plugin.getDataManager().saveGui();
            event.getPlayer().sendMessage("§a§lSAVED §7Dashboard layout updated.");
        }
    }
}