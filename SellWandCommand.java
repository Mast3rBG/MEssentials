package master.com.mEssentials.commands.economy;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.List;

public class SellWandCommand implements MECommand {
    private final MEssentials plugin;
    public SellWandCommand(MEssentials plugin) { this.plugin = plugin; }

    @Override public String getName() { return "sellwand"; }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;

        ItemStack wand = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = wand.getItemMeta();
        meta.setDisplayName("§6§lSELL WAND");
        List<String> lore = new ArrayList<>();
        lore.add("§7Right-click a chest to sell contents!");
        lore.add("§eMultiplier: §f1.5x");
        meta.setLore(lore);
        wand.setItemMeta(meta);

        p.getInventory().addItem(wand);
        p.sendMessage("§e§lWAND §7You received a Sell Wand.");
    }
}