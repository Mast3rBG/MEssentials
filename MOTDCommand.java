package master.com.mEssentials.commands.general;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
public class MOTDCommand implements MECommand {
    private final MEssentials plugin = JavaPlugin.getPlugin(MEssentials.class);

    @Override public String getName() { return "motd"; }
    @Override public String getPermission() { return "proessentials.admin.motd"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            String motd = plugin.getConfig().getString("general.motd", "&bWelcome!");
            sender.sendMessage(plugin.getPrefix() + ChatColor.translateAlternateColorCodes('&', motd));
        } else {
            StringBuilder sb = new StringBuilder();
            for (String arg : args) sb.append(arg).append(" ");
            plugin.getConfig().set("general.motd", sb.toString().trim());
            plugin.saveConfig();
            sender.sendMessage(plugin.getPrefix() + "§aMOTD updated in config!");
        }
    }
}