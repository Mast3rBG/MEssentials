package master.com.mEssentials.commands.tools;

import master.com.mEssentials.commands.MECommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GodCommand implements MECommand {
    @Override
    public String getName() { return "god"; }
    @Override
    public String getPermission() { return "proessentials.god"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player p) {
            boolean isGod = p.isInvulnerable();
            p.setInvulnerable(!isGod);
            p.sendMessage(ChatColor.GOLD + "God mode " + (!isGod ? "enabled" : "disabled") + ".");
        }
    }
}