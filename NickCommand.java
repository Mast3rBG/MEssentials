package master.com.mEssentials.commands.general;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickCommand implements MECommand {
    private final MEssentials plugin;
    public NickCommand(MEssentials plugin) { this.plugin = plugin; }

    @Override public String getName() { return "nick"; }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;
        if (args.length == 0) {
            p.setDisplayName(p.getName());
            p.sendMessage(plugin.getPrefix() + "§aNickname cleared!");
            return;
        }

        String nick = ChatColor.translateAlternateColorCodes('&', args[0]);
        p.setDisplayName(nick + "§r");
        p.sendMessage(plugin.getPrefix() + "§aYour nickname is now: " + nick);
    }
}