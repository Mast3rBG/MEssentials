package master.com.mEssentials.commands.admin;

import master.com.mEssentials.commands.MECommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpeedCommand implements MECommand {
    @Override public String getName() { return "speed"; }
    @Override public String getPermission() { return "messentials.speed"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;
        if (args.length < 1) {
            p.sendMessage("§cUsage: /speed <1-10>");
            return;
        }

        try {
            float speed = Float.parseFloat(args[0]) / 10;
            if (speed > 1.0f) speed = 1.0f;
            if (speed < 0.0f) speed = 0.1f;

            if (p.isFlying()) {
                p.setFlySpeed(speed);
                p.sendMessage("§bFly speed set to " + args[0]);
            } else {
                p.setWalkSpeed(speed);
                p.sendMessage("§bWalk speed set to " + args[0]);
            }
        } catch (NumberFormatException e) {
            p.sendMessage("§cPlease enter a valid number.");
        }
    }
}