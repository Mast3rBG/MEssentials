package master.com.mEssentials.commands.general;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import master.com.mEssentials.commands.general.TpaRequest;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpaCommand implements MECommand {
    private final MEssentials plugin;
    public TpaCommand(MEssentials plugin) { this.plugin = plugin; }

    @Override public String getName() { return "tpa"; }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;
        if (args.length == 0) {
            p.sendMessage("§cUsage: /tpa <player>");
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        String prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("settings.prefix"));

        if (target == null || target.equals(p)) {
            p.sendMessage("§cInvalid player.");
            return;
        }

        plugin.getTpaRequests().put(target.getUniqueId(), new TpaRequest(p.getUniqueId(), false));

        p.sendMessage(prefix + "§aSent TPA request to §f" + target.getName());
        target.sendMessage(prefix + "§f" + p.getName() + " §7has requested to teleport to you.");
        target.sendMessage("§7Type §a/tpaccept §7to allow.");
    }
}