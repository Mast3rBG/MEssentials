package master.com.mEssentials.commands.general;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SocialCommand implements MECommand {
    private final MEssentials plugin;
    private final String type;

    public SocialCommand(MEssentials plugin, String type) {
        this.plugin = plugin;
        this.type = type;
    }

    @Override public String getName() { return type; }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (type.equalsIgnoreCase("ping")) {
            Player target = (args.length > 0) ? Bukkit.getPlayer(args[0]) : (sender instanceof Player ? (Player) sender : null);
            if (target != null) sender.sendMessage("§b" + target.getName() + "'s Ping: §f" + target.getPing() + "ms");
            return;
        }

        if (type.equalsIgnoreCase("playtime")) {
            Player target = (args.length > 0) ? Bukkit.getPlayer(args[0]) : (sender instanceof Player ? (Player) sender : null);
            if (target != null) {
                long ticks = target.getStatistic(Statistic.PLAY_ONE_MINUTE);
                long hours = ticks / 72000;
                sender.sendMessage("§b" + target.getName() + "'s Playtime: §f" + hours + " hours");
            }
        }
    }
}