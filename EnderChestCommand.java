package master.com.mEssentials.commands.tools;

import master.com.mEssentials.commands.MECommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnderChestCommand implements MECommand {
    @Override public String getName() { return "enderchest"; }
    @Override public String getPermission() { return "messentials.enderchest"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;
        p.openInventory(p.getEnderChest());
        p.sendMessage("§dOpening Ender Chest...");
    }
}