package master.com.mEssentials.commands.admin;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class AltCommand implements MECommand {
    private final MEssentials plugin;

    public AltCommand(MEssentials plugin) {
        this.plugin = plugin;
    }

    @Override public String getName() { return "alts"; }
    @Override public String getPermission() { return "messentials.admin"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§cUsage: /alts <player>");
            return;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        FileConfiguration data = plugin.getDataManager().getConfig();

        String targetHash = data.getString("players." + target.getUniqueId() + ".last-ip-hash");

        if (targetHash == null) {
            sender.sendMessage(plugin.getPrefix() + "§cNo IP history found for §f" + target.getName());
            return;
        }

        List<String> alts = data.getStringList("ips." + targetHash + ".accounts");

        sender.sendMessage("§8§m---------------------------------------");
        sender.sendMessage("§bAlts for §f" + target.getName() + " §7(Hashed: " + targetHash.substring(0, 8) + "...)");

        if (alts.size() <= 1) {
            sender.sendMessage(" §8» §7No other accounts found.");
        } else {
            for (String altName : alts) {
                String color = altName.equalsIgnoreCase(target.getName()) ? "§b" : "§f";
                sender.sendMessage(" §8» " + color + altName);
            }
        }
        sender.sendMessage("§8§m---------------------------------------");
    }
}