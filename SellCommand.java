package master.com.mEssentials.commands.economy;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SellCommand implements MECommand {
    private final MEssentials plugin;

    public SellCommand(MEssentials plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() { return "sell"; }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;

        double totalSold = 0;
        int itemsCount = 0;

        for (int i = 0; i < p.getInventory().getSize(); i++) {
            ItemStack itemStack = p.getInventory().getItem(i);

            if (itemStack == null || itemStack.getType() == Material.AIR) continue;

            String path = "prices." + itemStack.getType().name();
            double price = plugin.getConfig().getDouble(path, 0);

            if (price > 0) {
                totalSold += (price * itemStack.getAmount());
                itemsCount += itemStack.getAmount();

                p.getInventory().setItem(i, null);
            }
        }

        if (totalSold > 0) {
            String symbol = plugin.getConfig().getString("settings.currency-symbol", "$");
            p.sendMessage("§a§lSELL §7You sold §f" + itemsCount + " §7items for §e" + symbol + totalSold);

        } else {
            p.sendMessage("§cYou don't have any sellable items in your inventory!");
        }
    }
}