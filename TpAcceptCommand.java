package master.com.mEssentials.commands.general;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpAcceptCommand implements MECommand {
    private final MEssentials plugin;
    public TpAcceptCommand(MEssentials plugin) { this.plugin = plugin; }

    @Override public String getName() { return "tpaccept"; }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) return;

        TpaRequest request = plugin.getTpaRequests().get(player.getUniqueId());

        if (request == null) {
            player.sendMessage("§cYou do not have any pending teleport requests.");
            return;
        }

        Player requester = Bukkit.getPlayer(request.getSender());
        if (requester == null) {
            player.sendMessage("§cThe requester is no longer online.");
            plugin.getTpaRequests().remove(player.getUniqueId());
            return;
        }

        if (request.isHere()) {
            player.teleport(requester.getLocation());
        } else {
            requester.teleport(player.getLocation());
        }

        player.sendMessage("§aTeleport request accepted.");
        requester.sendMessage("§aTeleporting...");
        plugin.getTpaRequests().remove(player.getUniqueId());
    }
}