package master.com.mEssentials.commands.admin;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.TimeUtil;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import java.util.List;
import java.util.UUID;

public class BanCommand implements MECommand {

    private final MEssentials plugin;

    public BanCommand(MEssentials plugin) {
        this.plugin = plugin;
    }

    @Override public String getName() { return "ban"; }
    @Override public String getPermission() { return "messentials.ban"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("§cUsage: /ban <player> <duration: 1d/perm> <reason>");
            return;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        String durationStr = args[1];
        long expiry = TimeUtil.parseTime(durationStr);
        String reason = String.join(" ", java.util.Arrays.copyOfRange(args, 2, args.length));

        String activePath = "bans." + target.getUniqueId();
        plugin.getDataManager().getConfig().set(activePath + ".reason", reason);
        plugin.getDataManager().getConfig().set(activePath + ".expiry", expiry);
        plugin.getDataManager().getConfig().set(activePath + ".staff", sender.getName());

        saveToHistory(target.getUniqueId(), "BAN", sender.getName(), reason, durationStr, expiry);

        plugin.getDataManager().saveConfig();

        List<String> broadcastLines = plugin.getConfig().getStringList("punishment-designs.broadcast");
        for (String line : broadcastLines) {
            Bukkit.broadcastMessage(format(line, target.getName(), sender.getName(), reason, durationStr));
        }

        if (target.isOnline() && target.getPlayer() != null) {
            List<String> kickLines = plugin.getConfig().getStringList("punishment-designs.ban-screen");
            StringBuilder sb = new StringBuilder();
            for (String line : kickLines) {
                sb.append(format(line, target.getName(), sender.getName(), reason, durationStr)).append("\n");
            }
            target.getPlayer().kickPlayer(sb.toString());
        }
    }

    private void saveToHistory(UUID uuid, String type, String staff, String reason, String duration, long expiry) {
        String caseID = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        String path = "history." + uuid + "." + caseID;

        plugin.getDataManager().getConfig().set(path + ".type", type);
        plugin.getDataManager().getConfig().set(path + ".staff", staff);
        plugin.getDataManager().getConfig().set(path + ".reason", reason);
        plugin.getDataManager().getConfig().set(path + ".date", System.currentTimeMillis());
        plugin.getDataManager().getConfig().set(path + ".duration", duration);
        plugin.getDataManager().getConfig().set(path + ".expiry", expiry);
    }

    private String format(String msg, String target, String staff, String reason, String dur) {
        return ChatColor.translateAlternateColorCodes('&', msg
                .replace("{target}", target != null ? target : "Unknown")
                .replace("{staff}", staff)
                .replace("{reason}", reason)
                .replace("{duration}", dur)
                .replace("{type}", "BAN"));
    }
}