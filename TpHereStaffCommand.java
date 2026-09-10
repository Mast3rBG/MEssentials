package master.com.mEssentials.commands.admin;

import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpHereStaffCommand implements MECommand {
    @Override public String getName() { return "tphere"; }
    @Override public String getPermission() { return "messentials.staff.tphere"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;
        if (args.length == 0) { p.sendMessage("§cUsage: /tphere <player>"); return; }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) { p.sendMessage("§cPlayer not found."); return; }

        target.teleport(p.getLocation());
        p.sendMessage("§7Teleported §b" + target.getName() + " §7to you.");
    }
}