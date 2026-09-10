package master.com.mEssentials.commands.general;

import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class FunCommand implements MECommand {
    private final String type;
    public FunCommand(String type) { this.type = type; }

    @Override public String getName() { return type; }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) return;
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) return;

        switch (type.toLowerCase()) {
            case "burn" -> target.setFireTicks(20 * 10);
            case "smite" -> target.getWorld().strikeLightning(target.getLocation());
            case "rocket" -> target.setVelocity(new Vector(0, 5, 0));
        }
        sender.sendMessage("§aApplied " + type + " to " + target.getName());
    }
}