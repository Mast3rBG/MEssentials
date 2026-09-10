package master.com.mEssentials.commands.admin;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffUtilCommand implements MECommand {
    private final MEssentials plugin;
    private final String type;

    public StaffUtilCommand(MEssentials plugin, String type) {
        this.plugin = plugin;
        this.type = type;
    }

    @Override public String getName() { return type; }
    @Override public String getPermission() { return "messentials.staff." + type; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        switch (type.toLowerCase()) {
            case "tpall" -> {
                if (!(sender instanceof Player p)) return;
                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.teleport(p.getLocation());
                }
                Bukkit.broadcastMessage(plugin.getPrefix() + "§7All players have been teleported to §b" + p.getName());
            }
            case "kill" -> {
                if (args.length == 0) return;
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    target.setHealth(0);
                    sender.sendMessage("§aKilled " + target.getName());
                }
            }
            case "clearinv" -> {
                Player target = args.length > 0 ? Bukkit.getPlayer(args[0]) : (sender instanceof Player ? (Player) sender : null);
                if (target != null) {
                    target.getInventory().clear();
                    sender.sendMessage("§aInventory cleared for " + target.getName());
                }
            }
        }
    }
}