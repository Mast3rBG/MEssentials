package master.com.mEssentials.commands.admin;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvseeCommand implements MECommand {
    private final MEssentials plugin;
    public InvseeCommand(MEssentials plugin) { this.plugin = plugin; }

    @Override public String getName() { return "invsee"; }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player admin)) return;
        if (args.length == 0) {
            admin.sendMessage("§cUsage: /invsee <player>");
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            admin.sendMessage("§cPlayer not found.");
            return;
        }

        admin.openInventory(target.getInventory());
        admin.sendMessage("§aViewing §f" + target.getName() + "'s §ainventory.");
    }
}
