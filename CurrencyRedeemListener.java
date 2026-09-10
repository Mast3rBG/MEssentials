package master.com.mEssentials.listeners;

import master.com.mEssentials.CurrencyManager;
import master.com.mEssentials.MEssentials;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class CurrencyRedeemListener implements Listener {

    private final MEssentials plugin;

    public CurrencyRedeemListener(MEssentials plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPickup(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player p)) return;

        ItemStack item = event.getItem().getItemStack();
        CurrencyManager cm = plugin.getCurrencyManager();

        if (cm.tryRedeem(p, item)) {
            event.setCancelled(true);
            event.getItem().remove();
        }
    }
}
