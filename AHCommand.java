package master.com.mEssentials.commands.economy;
import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AHCommand implements MECommand {
    private final MEssentials plugin;
    public AHCommand(MEssentials plugin) { this.plugin = plugin; }

    @Override public String getName() { return "ah"; }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;

        if (args.length >= 2 && args[0].equalsIgnoreCase("sell")) {
            try {
                double price = Double.parseDouble(args[1]);
                ItemStack itemToSell = p.getInventory().getItemInMainHand();

                if (itemToSell.getType() == Material.AIR) {
                    p.sendMessage("§cHold an item to sell on the AH!");
                    return;
                }

                p.sendMessage("§aListing " + itemToSell.getType().name() + " for §e$" + price);
                p.getInventory().setItemInMainHand(null);
                return;
            } catch (NumberFormatException e) {
                p.sendMessage("§cUsage: /ah sell <price>");
                return;
            }
        }

        Inventory ahGui = Bukkit.createInventory(null, 54, "§8Auction House");
        p.openInventory(ahGui);
    }
}