package master.com.mEssentials.commands.admin;

import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InventoryViewCommand implements MECommand {
    private final String type;

    public InventoryViewCommand(String type) { this.type = type; }

    @Override public String getName() { return type; }
    @Override public String getPermission() { return "messentials.staff"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) return;

        if (type.equals("viewechest")) {
            if (args.length == 0) return;
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) player.openInventory(target.getEnderChest());
        }
        else if (type.equals("coords")) {
            player.sendMessage("§bCurrent Location: §fX: " + player.getLocation().getBlockX() +
                    " Y: " + player.getLocation().getBlockY() +
                    " Z: " + player.getLocation().getBlockZ());
        }
    }
}