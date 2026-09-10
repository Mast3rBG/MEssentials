package master.com.mEssentials;

import master.com.mEssentials.commands.MECommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CommandManager implements CommandExecutor {

    private final MEssentials plugin;
    private final Map<String, MECommand> commands = new HashMap<>();

    public CommandManager(MEssentials plugin) {
        this.plugin = plugin;
    }

    public void registerCommand(MECommand meCommand) {
        commands.put(meCommand.getName().toLowerCase(), meCommand);
        plugin.getCommand(meCommand.getName()).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        MECommand meCmd = commands.get(command.getName().toLowerCase());

        if (meCmd != null) {
            if (!plugin.getConfig().getBoolean("commands." + meCmd.getName() + ".enabled", true)) {
                sender.sendMessage(ChatColor.RED + "This command is currently disabled.");
                return true;
            }

            if (meCmd.getPermission() != null && !sender.hasPermission(meCmd.getPermission())) {
                String noPerm = plugin.getConfig().getString("messages.no-permission", "&cNo permission!");
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', noPerm));
                return true;
            }

            meCmd.execute(sender, args);
            return true;
        }
        return false;
    }
}
