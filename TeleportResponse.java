package master.com.mEssentials.commands.general;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportResponse implements MECommand {
    private final MEssentials plugin;
    private final String type;

    public TeleportResponse(MEssentials plugin, String type) {
        this.plugin = plugin;
        this.type = type;
    }

    @Override public String getName() { return type; }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) return;

        if (type.equalsIgnoreCase("tpdeny")) {
            if (plugin.getTpaRequests().remove(player.getUniqueId()) != null) {
                player.sendMessage("§cTeleport request denied.");
            } else {
                player.sendMessage("§cNo pending requests to deny.");
            }
        } else if (type.equalsIgnoreCase("tpcancel")) {
            plugin.getTpaRequests().values().removeIf(req -> req.getSender().equals(player.getUniqueId()));
            player.sendMessage("§7Pending requests cancelled.");
        }
    }
}