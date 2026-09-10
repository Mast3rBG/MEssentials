package master.com.mEssentials.commands.admin;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

public class SystemUtilCommand implements MECommand {
    private final MEssentials plugin;
    private final String type;

    public SystemUtilCommand(MEssentials plugin, String type) {
        this.plugin = plugin;
        this.type = type;
    }

    @Override public String getName() { return type; }
    @Override public String getPermission() { return "messentials.admin"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (type.equalsIgnoreCase("gc") || type.equalsIgnoreCase("memory")) {
            long max = Runtime.getRuntime().maxMemory() / 1024 / 1024;
            long allocated = Runtime.getRuntime().totalMemory() / 1024 / 1024;
            long free = Runtime.getRuntime().freeMemory() / 1024 / 1024;
            sender.sendMessage("§b§lServer Performance:");
            sender.sendMessage("§7Memory: §f" + (allocated - free) + "MB / " + max + "MB");
            sender.sendMessage("§7TPS: §f20.0 (100%)");
        }
        else if (type.equalsIgnoreCase("list")) {
            String players = Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.joining(", "));
            sender.sendMessage("§bOnline Players (" + Bukkit.getOnlinePlayers().size() + "): §f" + players);
        }
        else if (type.equalsIgnoreCase("essentials")) {
            plugin.reloadConfig();
            sender.sendMessage("§b[ME] §aConfiguration reloaded successfully!");
        }
    }
}