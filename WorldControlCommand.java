package master.com.mEssentials.commands.admin;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldControlCommand implements MECommand {
    private final MEssentials plugin;
    private final String type;

    public WorldControlCommand(MEssentials plugin, String type) {
        this.plugin = plugin;
        this.type = type;
    }

    @Override public String getName() { return type; }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;
        String prefix = plugin.getPrefix();

        switch (type.toLowerCase()) {
            case "day" -> {
                p.getWorld().setTime(1000);
                p.sendMessage(prefix + "§eTime set to Day.");
            }
            case "night" -> {
                p.getWorld().setTime(13000);
                p.sendMessage(prefix + "§bTime set to Night.");
            }
            case "sun" -> {
                p.getWorld().setStorm(false);
                p.getWorld().setThundering(false);
                p.sendMessage(prefix + "§6Weather cleared.");
            }
            case "rain" -> {
                p.getWorld().setStorm(true);
                p.sendMessage(prefix + "§9Weather set to Rain.");
            }
        }
    }
}