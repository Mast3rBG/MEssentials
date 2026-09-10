package master.com.mEssentials.commands.general;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.command.CommandSender;

public class RulesCommand implements MECommand {
    private final MEssentials plugin;

    public RulesCommand(MEssentials plugin) { this.plugin = plugin; }

    @Override public String getName() { return "rules"; }
    @Override public String getPermission() { return ""; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage("§8§m-------§r §b§lServer Rules §8§m-------");
        for (String rule : plugin.getConfig().getStringList("messages.rules")) {
            sender.sendMessage("§8» §7" + rule.replace("&", "§"));
        }
    }
}