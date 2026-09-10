package master.com.mEssentials.commands.admin;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import java.util.List;
import java.util.UUID;

public class WarnCommand implements MECommand {
    private final MEssentials plugin;
    public WarnCommand(MEssentials plugin) { this.plugin = plugin; }

    @Override public String getName() { return "warn"; }
    @Override public String getPermission() { return "messentials.warn"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§cUsage: /warn <player> <reason>");
            return;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        String reason = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));

        saveToHistory(target.getUniqueId(), "WARN", sender.getName(), reason);
        plugin.getDataManager().saveConfig();

        Bukkit.broadcast("§8[§eWarn§8] §f" + target.getName() + " §7was warned by §e" + sender.getName() + " §7for §f" + reason, "messentials.staff");

        if (target.isOnline() && target.getPlayer() != null) {
            List<String> warnDesign = plugin.getConfig().getStringList("punishment-designs.warn-notify");
            for (String line : warnDesign) {
                target.getPlayer().sendMessage(format(line, target.getName(), sender.getName(), reason));
            }
        }
        sender.sendMessage("§eYou warned " + target.getName());
    }

    private void saveToHistory(UUID uuid, String type, String staff, String reason) {
        String id = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        String p = "history." + uuid + "." + id;
        plugin.getDataManager().getConfig().set(p + ".type", type);
        plugin.getDataManager().getConfig().set(p + ".staff", staff);
        plugin.getDataManager().getConfig().set(p + ".reason", reason);
        plugin.getDataManager().getConfig().set(p + ".date", System.currentTimeMillis());
        plugin.getDataManager().getConfig().set(p + ".duration", "N/A");
        plugin.getDataManager().getConfig().set(p + ".expiry", -1L);
    }

    private String format(String msg, String target, String staff, String reason) {
        return ChatColor.translateAlternateColorCodes('&', msg
                .replace("{target}", target).replace("{staff}", staff)
                .replace("{reason}", reason));
    }
}