package master.com.mEssentials.commands.general;

import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerControlCommand implements MECommand {
    private final String type;

    public PlayerControlCommand(String type) {
        this.type = type;
    }

    @Override public String getName() { return type; }
    @Override public String getPermission() { return "messentials.admin"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;

        if (type.equalsIgnoreCase("gm")) {
            if (args.length < 1) {
                p.sendMessage("§cUsage: /gm <0/1/2/3>");
                return;
            }


            Player target = (args.length > 1) ? Bukkit.getPlayer(args[1]) : p;
            if (target == null) {
                p.sendMessage("§cPlayer not found.");
                return;
            }

            GameMode gm = matchGameMode(args[0]);
            if (gm == null) {
                p.sendMessage("§cInvalid GameMode!");
                return;
            }

            target.setGameMode(gm);
            target.sendMessage("§aYour gamemode has been set to §f" + gm.name());
            if (target != p) p.sendMessage("§aSet §f" + target.getName() + "'s §agamemode to §f" + gm.name());
        }
    }

    private GameMode matchGameMode(String input) {
        return switch (input.toLowerCase()) {
            case "0", "survival", "s" -> GameMode.SURVIVAL;
            case "1", "creative", "c" -> GameMode.CREATIVE;
            case "2", "adventure", "a" -> GameMode.ADVENTURE;
            case "3", "spectator", "sp" -> GameMode.SPECTATOR;
            default -> null;
        };
    }
}