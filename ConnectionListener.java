package master.com.mEssentials.listeners;

import master.com.mEssentials.MEssentials;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {
    private final MEssentials plugin;

    public ConnectionListener(MEssentials plugin) { this.plugin = plugin; }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        String msg = plugin.getConfig().getString("messages.join", "&8[&a+&8] &7%player%");
        event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', msg.replace("%player%", event.getPlayer().getName())));

        plugin.getScoreboardManager().showScoreboard(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        String msg = plugin.getConfig().getString("messages.quit", "&8[&c-&8] &7%player%");
        event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', msg.replace("%player%", event.getPlayer().getName())));
    }
}