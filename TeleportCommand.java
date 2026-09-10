package master.com.mEssentials.commands.admin;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand implements MECommand {
    private final MEssentials plugin;
    public TeleportCommand(MEssentials plugin) { this.plugin = plugin; }

    @Override public String getName() { return "tp"; }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;
        if (args.length == 0) {
            p.sendMessage("§cUsage: /tp <player> [target]");
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            p.sendMessage("§cPlayer not found.");
            return;
        }

        p.teleport(target.getLocation());
        p.sendMessage("§aTeleported to §f" + target.getName());
    }
}