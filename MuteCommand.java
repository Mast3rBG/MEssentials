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

public class MuteCommand implements MECommand {
    private final MEssentials plugin;
    public MuteCommand(MEssentials plugin) { this.plugin = plugin; }

    @Override public String getName() { return "mute"; }
    @Override public String getPermission() { return "messentials.mute"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("§cUsage: /mute <player> <duration> <reason>");
            return;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        String dur = args[1];
        long expiry = TimeUtil.parseTime(dur);
        String reason = String.join(" ", java.util.Arrays.copyOfRange(args, 2, args.length));

        String path = "mutes." + target.getUniqueId();
        plugin.getDataManager().getConfig().set(path + ".reason", reason);
        plugin.getDataManager().getConfig().set(path + ".expiry", expiry);

        saveToHistory(target.getUniqueId(), "MUTE", sender.getName(), reason, dur, expiry);
        plugin.getDataManager().saveConfig();

        Bukkit.broadcast("§8[§bPunish§8] §f" + target.getName() + " §7was muted by §b" + sender.getName() + " §7for §f" + reason, "messentials.staff");

        if (target.isOnline() && target.getPlayer() != null) {
            List<String> muteDesign = plugin.getConfig().getStringList("punishment-designs.mute-notify");
            for (String line : muteDesign) {
                target.getPlayer().sendMessage(format(line, target.getName(), sender.getName(), reason, dur));
            }
        }
        sender.sendMessage("§aYou muted " + target.getName());
    }

    private void saveToHistory(UUID uuid, String type, String staff, String reason, String duration, long expiry) {
        String id = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        String p = "history." + uuid + "." + id;
        plugin.getDataManager().getConfig().set(p + ".type", type);
        plugin.getDataManager().getConfig().set(p + ".staff", staff);
        plugin.getDataManager().getConfig().set(p + ".reason", reason);
        plugin.getDataManager().getConfig().set(p + ".date", System.currentTimeMillis());
        plugin.getDataManager().getConfig().set(p + ".duration", duration);
        plugin.getDataManager().getConfig().set(p + ".expiry", expiry);
    }

    private String format(String msg, String target, String staff, String reason, String dur) {
        return ChatColor.translateAlternateColorCodes('&', msg
                .replace("{target}", target).replace("{staff}", staff)
                .replace("{reason}", reason).replace("{duration}", dur));
    }
}