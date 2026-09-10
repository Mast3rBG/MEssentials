package master.com.mEssentials.commands.general;

import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerDataCommand implements MECommand {
    private final String type;

    public PlayerDataCommand(String type) { this.type = type; }

    @Override public String getName() { return type; }
    @Override public String getPermission() { return "messentials." + type; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) return;

        switch (type.toLowerCase()) {
            case "suicide" -> {
                player.setHealth(0);
                player.sendMessage("§7You took the easy way out.");
            }
            case "invclear" -> {
                player.getInventory().clear();
                player.sendMessage("§aYour inventory has been cleared.");
            }
            case "skull" -> {
                ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta meta = (SkullMeta) skull.getItemMeta();
                meta.setOwningPlayer(args.length > 0 ? Bukkit.getOfflinePlayer(args[0]) : player);
                skull.setItemMeta(meta);
                player.getInventory().addItem(skull);
                player.sendMessage("§aYou received a player head!");
            }
            case "whois" -> {
                if (args.length == 0) return;
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) return;
                sender.sendMessage("§b§lWHOIS: " + target.getName());
                sender.sendMessage("§7IP: §f" + target.getAddress().getHostString());
                sender.sendMessage("§7Gamemode: §f" + target.getGameMode());
                sender.sendMessage("§7Fly: §f" + (target.getAllowFlight() ? "Enabled" : "Disabled"));
            }
        }
    }
}