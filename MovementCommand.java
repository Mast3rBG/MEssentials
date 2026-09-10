package master.com.mEssentials.commands.general;

import master.com.mEssentials.commands.MECommand;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MovementCommand implements MECommand {
    private final String type;
    public MovementCommand(String type) { this.type = type; }

    @Override public String getName() { return type; }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;

        if (type.equalsIgnoreCase("top")) {
            p.teleport(p.getWorld().getHighestBlockAt(p.getLocation()).getLocation().add(0, 1, 0));
            p.sendMessage("§aTeleported to the surface!");
        } else if (type.equalsIgnoreCase("jump")) {
            Block target = p.getTargetBlockExact(100);
            if (target != null) {
                p.teleport(target.getLocation().add(0.5, 1, 0.5));
                p.sendMessage("§bJump!");
            }
        }
    }
}