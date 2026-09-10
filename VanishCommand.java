package master.com.mEssentials.commands.admin;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class VanishCommand implements MECommand {
    private final MEssentials plugin;

    public VanishCommand(MEssentials plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "vanish";
    }

    @Override
    public String getPermission() {
        return "messentials.vanish";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cOnly players can vanish!");
            return;
        }
        toggleVanish(p);
    }

    private void toggleVanish(Player p) {
        if (p.hasMetadata("vanished")) {
            p.removeMetadata("vanished", plugin);
            for (Player online : Bukkit.getOnlinePlayers()) {
                online.showPlayer(plugin, p);
            }
            p.sendMessage("§bVanish §cDisabled");
        } else {
            p.setMetadata("vanished", new FixedMetadataValue(plugin, true));
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (!online.hasPermission("messentials.staff")) {
                    online.hidePlayer(plugin, p);
                }
            }
            p.sendMessage("§bVanish §aEnabled");
        }
    }
}