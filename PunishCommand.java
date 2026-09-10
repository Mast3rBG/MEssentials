package master.com.mEssentials.commands.admin;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PunishCommand implements MECommand {
    private final MEssentials plugin;
    private final String type;

    public PunishCommand(MEssentials plugin, String type) {
        this.plugin = plugin;
        this.type = type;
    }

    @Override public String getName() { return type; }
    @Override public String getPermission() { return "messentials.admin"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§cUsage: /" + type + " <player> <reason>");
            return;
        }

        String targetName = args[0];
        Player target = Bukkit.getPlayer(targetName);
        String reason = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));
        String banner = sender.getName();

        switch (type.toLowerCase()) {
            case "permban":
                Bukkit.getBanList(BanList.Type.NAME).addBan(targetName, "§c§lBANNED\n§7Reason: §f" + reason, null, banner);
                if (target != null) target.kickPlayer("§cYou are permanently banned!\n§7Reason: " + reason);
                break;

            case "ipban":
                String ip = (target != null) ? target.getAddress().getAddress().getHostAddress() : targetName;
                Bukkit.getBanList(BanList.Type.IP).addBan(ip, "§c§lIP BANNED\n§7Reason: §f" + reason, null, banner);
                if (target != null) target.kickPlayer("§cYour IP has been banned!\n§7Reason: " + reason);
                break;

            case "permmute":
                plugin.getConfig().set("mutes." + targetName.toLowerCase(), "PERMANENT");
                plugin.saveConfig();
                if (target != null) target.sendMessage("§cYou have been permanently muted for: " + reason);
                break;

            case "ipmute":
                String targetIP = (target != null) ? target.getAddress().getAddress().getHostAddress() : targetName;
                plugin.getConfig().set("ipmutes." + targetIP, true);
                plugin.saveConfig();
                break;
        }

        Bukkit.broadcastMessage("§8[§bPunish§8] §f" + targetName + " §7was §b" + type.toUpperCase() + " §7by §b" + banner);
    }
}