package master.com.mEssentials.commands.admin;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatCommand implements MECommand {
    private final MEssentials plugin;
    public StaffChatCommand(MEssentials plugin) { this.plugin = plugin; }

    @Override public String getName() { return "staffchat"; }
    @Override public String getPermission() { return "messentials.staff"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§cUsage: /sc <message>");
            return;
        }
        String message = String.join(" ", args);
        String format = "§8[§bStaffChat§8] §f" + sender.getName() + "§7: §b" + message;

        Bukkit.getOnlinePlayers().stream()
                .filter(p -> p.hasPermission("messentials.staff"))
                .forEach(p -> p.sendMessage(format));

        Bukkit.getConsoleSender().sendMessage(format);
    }
}