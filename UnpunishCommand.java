package master.com.mEssentials.commands.admin;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class UnpunishCommand implements MECommand {
    private final MEssentials plugin;
    private final String type;

    public UnpunishCommand(MEssentials plugin, String type) {
        this.plugin = plugin;
        this.type = type;
    }

    @Override public String getName() { return type; }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§cUsage: /" + type + " <player/IP>");
            return;
        }

        String prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("settings.prefix"));
        String targetName = args[0];

        if (type.equalsIgnoreCase("unban")) {
            Bukkit.getBanList(org.bukkit.BanList.Type.NAME).pardon(targetName);
            Bukkit.getBanList(org.bukkit.BanList.Type.IP).pardon(targetName);

            sender.sendMessage(prefix + "§aUnbanned §f" + targetName);
        } else if (type.equalsIgnoreCase("unmute")) {
            OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(targetName);

            plugin.getMutedPlayers().remove(offlineTarget.getUniqueId());
            plugin.getIpmutedPlayers().remove(targetName);

            sender.sendMessage(prefix + "§aUnmuted §f" + targetName);
        }
    }
}