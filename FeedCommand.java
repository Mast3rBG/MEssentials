package master.com.mEssentials.commands.tools;

import master.com.mEssentials.commands.MECommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCommand implements MECommand {
    @Override
    public String getName() { return "feed"; }
    @Override
    public String getPermission() { return "proessentials.feed"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player p) {
            p.setFoodLevel(20);
            p.setSaturation(5.0f);
            p.sendMessage(ChatColor.YELLOW + "Your hunger has been satisfied!");
        }
    }
}