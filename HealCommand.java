package master.com.mEssentials.commands.tools;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements MECommand {
    private final MEssentials plugin;
    public HealCommand(MEssentials plugin) { this.plugin = plugin; }

    @Override public String getName() { return "heal"; }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;

        AttributeInstance healthAttr = p.getAttribute(Attribute.MAX_HEALTH);

        if (healthAttr != null) {
            p.setHealth(healthAttr.getValue());
            p.setFoodLevel(20);
            p.setFireTicks(0);
            p.sendMessage("§a§lHEAL §7You have been restored!");
        }
    }
}