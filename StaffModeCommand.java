package master.com.mEssentials.commands.admin;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StaffModeCommand implements MECommand {
    private final MEssentials plugin;

    public StaffModeCommand(MEssentials plugin) { this.plugin = plugin; }

    @Override public String getName() { return "staffmode"; }
    @Override public String getPermission() { return "messentials.staff"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;

        if (p.getGameMode() == GameMode.CREATIVE) {
            p.setGameMode(GameMode.SURVIVAL);
            p.getInventory().clear();
            Bukkit.getOnlinePlayers().forEach(online -> online.showPlayer(plugin, p));
            p.sendMessage("§bStaff Mode §cDisabled");
        } else {
            p.setGameMode(GameMode.CREATIVE);
            giveStaffItems(p);
            Bukkit.getOnlinePlayers().forEach(online -> {
                if (!online.hasPermission("messentials.staff")) online.hidePlayer(plugin, p);
            });
            p.sendMessage("§bStaff Mode §aEnabled §7(Vanish Active)");
        }
    }

    private void giveStaffItems(Player p) {
        p.getInventory().clear();
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta meta = compass.getItemMeta();
        meta.setDisplayName("§bRandom TP §7(Right Click)");
        compass.setItemMeta(meta);
        p.getInventory().setItem(0, compass);

        ItemStack ice = new ItemStack(Material.ICE);
        ItemMeta iceMeta = ice.getItemMeta();
        iceMeta.setDisplayName("§bFreeze Player §7(Left Click)");
        ice.setItemMeta(iceMeta);
        p.getInventory().setItem(1, ice);
    }
}