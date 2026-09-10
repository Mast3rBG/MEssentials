package master.com.mEssentials.commands.general;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LanguageCommand implements MECommand {
    private final MEssentials plugin;

    public LanguageCommand(MEssentials plugin) { this.plugin = plugin; }

    @Override public String getName() { return "language"; }
    @Override public String getPermission() { return ""; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;

        if (args.length == 0) {
            p.sendMessage("§8§m-------§r §b§lSelect Language §8§m-------");
            p.sendMessage("§8» §f/lang EN §7(English)");
            p.sendMessage("§8» §f/lang ES §7(Spanish)");
            return;
        }

        String lang = args[0].toUpperCase();
        plugin.getConfig().set("players." + p.getUniqueId() + ".lang", lang);
        plugin.saveConfig();
        p.sendMessage(plugin.getPrefix() + "§7Language set to §b" + lang);
    }
}