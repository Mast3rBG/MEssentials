package master.com.mEssentials.commands.general;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import master.com.mEssentials.commands.general.TpaRequest;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpaHereCommand implements MECommand {
    private final MEssentials plugin;
    public TpaHereCommand(MEssentials plugin) { this.plugin = plugin; }

    @Override public String getName() { return "tpahere"; }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;
        if (args.length == 0) {
            p.sendMessage("§cUsage: /tpahere <player>");
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null || target.equals(p)) {
            p.sendMessage("§cInvalid player.");
            return;
        }

        plugin.getTpaRequests().put(target.getUniqueId(), new TpaRequest(p.getUniqueId(), true));

        p.sendMessage("§aSent TPA-Here request to §f" + target.getName());
        target.sendMessage("§f" + p.getName() + " §7wants you to teleport to §bthem§7.");
        target.sendMessage("§7Type §a/tpaccept §7to allow.");
    }
}