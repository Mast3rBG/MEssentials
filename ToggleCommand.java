package master.com.mEssentials.commands.general;

import master.com.mEssentials.commands.MECommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleCommand implements MECommand {
    private final String type;

    public ToggleCommand(String type) { this.type = type; }

    @Override public String getName() { return type; }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage("§b" + type + " §7has been toggled.");
    }
}