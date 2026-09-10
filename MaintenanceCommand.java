package master.com.mEssentials.commands.admin;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class MaintenanceCommand implements MECommand {
    private final MEssentials plugin = JavaPlugin.getPlugin(MEssentials.class);

    @Override public String getName() { return "maintenance"; }
    @Override public String getPermission() { return "proessentials.admin.maintenance"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        boolean currentState = plugin.getConfig().getBoolean("settings.maintenance-mode", false);
        plugin.getConfig().set("settings.maintenance-mode", !currentState);
        plugin.saveConfig();

        String status = !currentState ? "§aEnabled" : "§cDisabled";
        sender.sendMessage(plugin.getPrefix() + "Maintenance mode is now " + status);
    }
}