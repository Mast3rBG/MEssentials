package master.com.mEssentials.commands.admin;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FreezeCommand implements MECommand {
    private final MEssentials plugin;
    public FreezeCommand(MEssentials plugin) { this.plugin = plugin; }

    @Override public String getName() { return "freeze"; }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) return;
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) return;

        if (plugin.getFrozenPlayers().contains(target.getUniqueId())) {
            plugin.getFrozenPlayers().remove(target.getUniqueId());
            sender.sendMessage("§aUnfrozen " + target.getName());
            target.sendMessage("§aYou have been unfrozen.");
        } else {
            plugin.getFrozenPlayers().add(target.getUniqueId());
            sender.sendMessage("§cFrozen " + target.getName());
            target.sendMessage("§c§lYOU ARE FROZEN. §7Do not log out or you will be banned.");
        }
    }
}