package master.com.mEssentials.commands.general;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.command.CommandSender;

public class ScoreboardCommand implements MECommand {
    private final MEssentials plugin = org.bukkit.plugin.java.JavaPlugin.getPlugin(MEssentials.class);

    @Override public String getName() { return "scoreboard"; }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(plugin.getPrefix() + "§7Scoreboard display toggled.");
    }
}
