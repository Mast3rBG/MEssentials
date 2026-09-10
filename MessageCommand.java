package master.com.mEssentials.commands.general;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MessageCommand implements MECommand {
    private final MEssentials plugin;
    private final boolean isReply;

    public MessageCommand(MEssentials plugin, boolean isReply) {
        this.plugin = plugin;
        this.isReply = isReply;
    }

    @Override
    public String getName() {
        return isReply ? "reply" : "msg";
    }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;
    }
}