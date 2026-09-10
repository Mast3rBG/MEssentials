package master.com.mEssentials.commands.general;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class AFKCommand implements MECommand {
    private final MEssentials plugin;
    private final ArrayList<UUID> afkPlayer = new ArrayList<>();

    public AFKCommand(MEssentials plugin) { this.plugin = plugin; }

    @Override public String getName() { return "afk"; }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;

        if (afkPlayer.contains(p.getUniqueId())) {
            afkPlayer.remove(p.getUniqueId());
            Bukkit.broadcastMessage("§8[§6AFK§8] §f" + p.getName() + "§7 is no longer AFK.");
            p.setPlayerListName(p.getName());
        } else {
            afkPlayer.add(p.getUniqueId());
            Bukkit.broadcastMessage("§8[§6AFK§8] §f" + p.getName() + "§7 is now AFk.");
            p.setPlayerListName("&7[AFK] " + p.getName());
        }
    }
}
