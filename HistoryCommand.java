package master.com.mEssentials.commands.admin;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class HistoryCommand implements MECommand {
    private final MEssentials plugin;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    public HistoryCommand(MEssentials plugin) {
        this.plugin = plugin;
    }

    @Override public String getName() { return "history"; }
    @Override public String getPermission() { return "messentials.staff"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§cUsage: /history <player>");
            return;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        UUID uuid = target.getUniqueId();
        ConfigurationSection history = plugin.getConfig().getConfigurationSection("punishment_history." + uuid);

        if (history == null || history.getKeys(false).isEmpty()) {
            sender.sendMessage(plugin.getPrefix() + "§7No punishment history found for §f" + target.getName());
            return;
        }

        sender.sendMessage("§8§m---------------------------------------");
        sender.sendMessage("§b§lPunishment History: §f" + target.getName());
        sender.sendMessage("");

        for (String caseID : history.getKeys(false)) {
            ConfigurationSection data = history.getConfigurationSection(caseID);

            String type = data.getString("type", "UNKNOWN");
            String staff = data.getString("staff", "Console");
            String reason = data.getString("reason", "No reason provided");
            long dateMillis = data.getLong("date");
            String duration = data.getString("duration", "Permanent");
            boolean active = data.getBoolean("active", false);

            String status = active ? "§aYES" : "§cNO";
            String dateStr = dateFormat.format(new Date(dateMillis));

            sender.sendMessage("§7ⓘ §lID " + caseID + " §8(§7ACTIVE: " + status + "§8)");
            sender.sendMessage("  §a♦ §7TYPE §f" + type);
            sender.sendMessage("  §c§l§oØ §7OFFENDER §f" + target.getName());
            sender.sendMessage("  §b§l§o⚑ §7PUNISHED BY §f" + staff);
            sender.sendMessage("  §e§l⌚ §7DATE §f" + dateStr);
            sender.sendMessage("  §d§l⏳ §7DURATION §f" + duration);
            sender.sendMessage("  §6§l✎ §7REASON §f" + reason);
            sender.sendMessage("");
        }
        sender.sendMessage("§8§m---------------------------------------");
    }
}