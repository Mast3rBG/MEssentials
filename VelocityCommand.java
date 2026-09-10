package master.com.mEssentials.commands.tools;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VelocityCommand implements MECommand {

    private final MEssentials plugin;
    private final String commandName;
    private final String serverName;

    public VelocityCommand(MEssentials plugin, String commandName, String serverName) {
        this.plugin      = plugin;
        this.commandName = commandName;
        this.serverName  = serverName;
    }

    @Override public String getName()       { return commandName; }
    @Override public String getPermission() { return ""; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cOnly players can use this command.");
            return;
        }

        String channel = plugin.getConfig().getString("settings.proxy-channel", "velocity:main");

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(serverName);

        p.sendPluginMessage(plugin, channel, out.toByteArray());
        p.sendMessage("§a§lVELOCITY §7Sending you to §f" + serverName + "§7...");
    }
}
