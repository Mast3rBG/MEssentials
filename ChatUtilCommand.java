package master.com.mEssentials.commands.admin;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatUtilCommand implements MECommand {
    private final MEssentials plugin;
    private final String type;
    public static boolean chatMuted = false;

    public ChatUtilCommand(MEssentials plugin, String type) {
        this.plugin = plugin;
        this.type = type;
    }

    @Override public String getName() { return type; }
    @Override public String getPermission() { return "messentials.admin." + type; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        switch (type.toLowerCase()) {
            case "broadcast" -> {
                if (args.length == 0) return;
                String msg = ChatColor.translateAlternateColorCodes('&', String.join(" ", args));
                Bukkit.broadcastMessage("§8[§b§lANNOUNCEMENT§8] §f" + msg);
            }
            case "clearchat" -> {
                for (int i = 0; i < 100; i++) Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage(plugin.getPrefix() + "§7Chat has been cleared by §b" + sender.getName());
            }
            case "mutechat" -> {
                chatMuted = !chatMuted;
                Bukkit.broadcastMessage(plugin.getPrefix() + "§7Global chat is now " + (chatMuted ? "§cMUTED" : "§aENABLED"));
            }
            case "sudo" -> {
                if (args.length < 2) return;
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) return;
                String cmd = String.join(" ", args).replaceFirst(args[0], "").trim();
                target.chat(cmd.startsWith("/") ? cmd : cmd);
            }
        }
    }
}