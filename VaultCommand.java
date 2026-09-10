package master.com.mEssentials.commands.general;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import java.io.File;
import java.util.List;

public class VaultCommand implements MECommand {
    private final MEssentials plugin;

    public VaultCommand(MEssentials plugin) {
        this.plugin = plugin;
    }

    @Override public String getName() { return "vault"; }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;

        Inventory vault = Bukkit.createInventory(p, 54, "§8Private Vault");

        File file = new File(plugin.getDataFolder() + "/vaults/", p.getUniqueId() + ".yml");
        if (file.exists()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            List<ItemStack> items = (List<ItemStack>) config.getList("items");
            if (items != null) vault.setContents(items.toArray(new ItemStack[0]));
        }

        p.openInventory(vault);
        p.sendMessage("§6§lVAULT §7Opening storage...");
    }
}