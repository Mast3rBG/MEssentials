package master.com.mEssentials.commands.general;

import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class MiscUtilCommand implements MECommand {
    private final String type;

    public MiscUtilCommand(String type) { this.type = type; }

    @Override public String getName() { return type; }
    @Override public String getPermission() { return null; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) return;

        if (type.equals("suicide")) {
            player.setHealth(0);
            player.sendMessage("§7You took your own life.");
        } else if (type.equals("near")) {
            player.sendMessage("§bPlayers nearby:");
            for (Entity e : player.getNearbyEntities(100, 100, 100)) {
                if (e instanceof Player p) player.sendMessage("§7- §f" + p.getName() + " §b(" + (int)player.getLocation().distance(p.getLocation()) + "m)");
            }
        }
    }
}