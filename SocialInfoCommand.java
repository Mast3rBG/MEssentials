package master.com.mEssentials.commands.general;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.*;

public class SocialInfoCommand implements MECommand {
    private final MEssentials plugin;
    private final String type;

    public SocialInfoCommand(MEssentials plugin, String type) {
        this.plugin = plugin;
        this.type = type;
    }

    @Override public String getName() { return type; }
    @Override public String getPermission() { return ""; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (type.equalsIgnoreCase("baltop")) {
            showBalTop(sender);
            return;
        }

        if (type.equalsIgnoreCase("alts")) {
            if (args.length == 0) { sender.sendMessage("§cUsage: /alts <player>"); return; }
            showAlts(sender, args[0]);
            return;
        }
    }

    private void showBalTop(CommandSender sender) {
        ConfigurationSection section = plugin.getDataManager().getConfig().getConfigurationSection("players");
        if (section == null) { sender.sendMessage("§cNo player data found."); return; }

        Map<String, Double> balances = new HashMap<>();
        for (String uuidStr : section.getKeys(false)) {
            double bal = section.getDouble(uuidStr + ".balance", 0.0);
            OfflinePlayer op = Bukkit.getOfflinePlayer(UUID.fromString(uuidStr));
            if (op.getName() != null) balances.put(op.getName(), bal);
        }

        List<Map.Entry<String, Double>> sorted = new ArrayList<>(balances.entrySet());
        sorted.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        sender.sendMessage("§8§m-------§r §b§lTop 10 Balances §8§m-------");
        for (int i = 0; i < Math.min(10, sorted.size()); i++) {
            sender.sendMessage("§f" + (i + 1) + ". §b" + sorted.get(i).getKey() + " §7- §a$" + String.format("%.2f", sorted.get(i).getValue()));
        }
    }

    private void showAlts(CommandSender sender, String targetName) {
        OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);
        String hash = plugin.getDataManager().getConfig().getString("players." + target.getUniqueId() + ".last-ip-hash");

        if (hash == null) { sender.sendMessage("§cNo IP history found for " + targetName); return; }

        List<String> alts = plugin.getDataManager().getConfig().getStringList("ips." + hash + ".accounts");
        sender.sendMessage("§8§m-------§r §bAlts for §f" + targetName + " §8§m-------");
        for (String name : alts) {
            String color = name.equalsIgnoreCase(targetName) ? "§b" : "§7";
            sender.sendMessage(" §8» " + color + name);
        }
    }
}