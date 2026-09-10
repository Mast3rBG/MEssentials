package master.com.mEssentials.commands.tools;

import master.com.mEssentials.commands.MECommand;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemToolCommand implements MECommand {
    private final String type;
    public ItemToolCommand(String type) { this.type = type; }

    @Override public String getName() { return type; }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;
        ItemStack item = p.getInventory().getItemInMainHand();

        if (type.equalsIgnoreCase("repair") || type.equalsIgnoreCase("fix")) {
            if (item.getType() == Material.AIR) return;
            ItemMeta meta = item.getItemMeta();
            if (meta instanceof Damageable d) {
                d.setDamage(0);
                item.setItemMeta(meta);
                p.sendMessage("§aItem repaired!");
            }
        } else if (type.equalsIgnoreCase("rename")) {
            if (args.length == 0) return;
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(String.join(" ", args).replace("&", "§"));
                item.setItemMeta(meta);
                p.sendMessage("§aItem renamed!");
            }
        }
    }
}