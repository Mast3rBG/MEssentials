package master.com.mEssentials.commands.general;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EChestCommand implements MECommand {
    private final MEssentials plugin;
    public EChestCommand(MEssentials plugin) { this.plugin = plugin; }

    @Override public String getName() { return "enderchest"; }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player p) {
            p.openInventory(p.getEnderChest());
            p.sendMessage("§aOpening Ender Chest...");
        }
    }
}