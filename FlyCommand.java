package master.com.mEssentials.commands.tools;

import master.com.mEssentials.commands.MECommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.eclipse.aether.util.listener.ChainedTransferListener;

public class FlyCommand  implements MECommand {
    public String getName() {return "fly"; }
    public String getPermission() {return "proessentials.fly"; }

    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player p){
            p.sendMessage(ChatColor.GOLD +"Fly: " + ChatColor.WHITE + "speed" );
        }
    }
}
