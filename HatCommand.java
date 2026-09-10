package master.com.mEssentials.commands.tools;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class HatCommand implements MECommand {
    private final MEssentials plugin = JavaPlugin.getPlugin(MEssentials.class);

    @Override public String getName() { return "hat"; }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player p) {
            ItemStack hand = p.getInventory().getItemInMainHand();
            if (hand.getType() == Material.AIR) {
                p.sendMessage("§cHold something first!");
                return;
            }

            ItemStack head = p.getInventory().getHelmet();
            p.getInventory().setHelmet(hand);
            p.getInventory().setItemInMainHand(head);

            String msg = plugin.getConfig().getString("commands.hat.msg", "&aNice hat!");
            p.sendMessage(plugin.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
        }
    }
}