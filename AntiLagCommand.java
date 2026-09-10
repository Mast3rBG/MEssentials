package master.com.mEssentials.commands.admin;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;

public class AntiLagCommand implements MECommand {
    private final MEssentials plugin;
    private boolean active = false;

    public AntiLagCommand(MEssentials plugin) { this.plugin = plugin; }

    @Override public String getName() { return "antilag"; }
    @Override public String getPermission() { return "messentials.admin"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        active = !active;

        if (active) {
            int removed = 0;
            for (World w : Bukkit.getWorlds()) {
                for (Entity e : w.getEntities()) {
                    if (e instanceof Item) {
                        e.remove();
                        removed++;
                    }
                }
            }
            sender.sendMessage(plugin.getPrefix() + "§aAnti-Lag Enabled. §7Cleared §b" + removed + " §7items.");
        } else {
            sender.sendMessage(plugin.getPrefix() + "§cAnti-Lag Disabled.");
        }
    }
}