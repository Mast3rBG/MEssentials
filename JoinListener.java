package master.com.mEssentials.listeners;

import master.com.mEssentials.MEssentials;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class JoinListener implements Listener {
    private final MEssentials plugin;

    public JoinListener(MEssentials plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (plugin.getConfig().getBoolean("general.motd.enabled", true)) {
            List<String> motdLines = plugin.getConfig().getStringList("general.motd.message");
            for (String line : motdLines) {
                String formatted = line.replace("%player%", player.getName());
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', formatted));
            }
        }

        String rawIp = player.getAddress().getAddress().getHostAddress();
        String hashedIp = plugin.hashIP(rawIp);

        FileConfiguration data = plugin.getDataManager().getConfig();

        data.set("players." + player.getUniqueId() + ".last-ip-hash", hashedIp);

        List<String> accounts = data.getStringList("ips." + hashedIp + ".accounts");
        if (!accounts.contains(player.getName())) {
            accounts.add(player.getName());
            data.set("ips." + hashedIp + ".accounts", accounts);
        }

        plugin.getDataManager().saveConfig();
    }
}