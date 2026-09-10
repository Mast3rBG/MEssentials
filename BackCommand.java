package master.com.mEssentials.commands.general;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BackCommand implements MECommand {
    private final MEssentials plugin;
    public BackCommand(MEssentials plugin) { this.plugin = plugin; }

    @Override public String getName() { return "back"; }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;

        Location last = plugin.getLastLocation().get(p.getUniqueId());
        if (last == null) {
            p.sendMessage("§cYou have no previous location to return to.");
            return;
        }

        p.teleport(last);
        p.sendMessage(plugin.getPrefix() + "§7Teleported to your previous location.");
    }
}