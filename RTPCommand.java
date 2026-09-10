package master.com.mEssentials.commands.general;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.Random;

public class RTPCommand implements MECommand {
    @Override public String getName() { return "rtp"; }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;
        Random r = new Random();
        int x = r.nextInt(5000) - 2500;
        int z = r.nextInt(5000) - 2500;
        int y = p.getWorld().getHighestBlockYAt(x, z);

        p.teleport(new Location(p.getWorld(), x, y + 1, z));
        p.sendMessage("§7§oFinding a safe spot... §aTeleported!");
    }
}