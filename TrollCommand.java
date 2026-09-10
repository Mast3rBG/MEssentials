package master.com.mEssentials.commands.admin;

import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TrollCommand implements MECommand {
    private final String type;

    public TrollCommand(String type) { this.type = type; }

    @Override public String getName() { return type; }
    @Override public String getPermission() { return "messentials.admin.troll"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) { sender.sendMessage("§cUsage: /" + type + " <player>"); return; }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) { sender.sendMessage("§cPlayer not found."); return; }

        switch (type.toLowerCase()) {
            case "fakeop" -> target.sendMessage(ChatColor.WHITE + "§7§o[Server: Made " + target.getName() + " a server operator]");
            case "fakedeop" -> target.sendMessage(ChatColor.WHITE + "§7§o[Server: Made " + target.getName() + " no longer a server operator]");
            case "launch" -> target.setVelocity(target.getVelocity().setY(5));
            case "slap" -> target.setVelocity(target.getVelocity().setX(2));
            case "explode" -> target.getWorld().createExplosion(target.getLocation(), 0F, false);
        }
        sender.sendMessage("§aTrolled " + target.getName() + " with " + type + "!");
    }
}