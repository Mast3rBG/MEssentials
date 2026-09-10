package master.com.mEssentials.commands.tools;

import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements MECommand {

    @Override public String getName() { return "gamemode"; }
    @Override public String getPermission() { return "messentials.admin"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player p)) return;

        if (args.length < 1) {
            p.sendMessage("§cUsage: /gamemode <0|1|2|3|survival|creative|adventure|spectator> [player]");
            return;
        }

        Player target = (args.length > 1) ? Bukkit.getPlayer(args[1]) : p;
        if (target == null) {
            p.sendMessage("§cPlayer not found.");
            return;
        }

        GameMode gm = matchGameMode(args[0]);
        if (gm == null) {
            p.sendMessage("§cInvalid gamemode! Use 0/1/2/3 or survival/creative/adventure/spectator.");
            return;
        }

        target.setGameMode(gm);
        target.sendMessage("§aYour gamemode has been set to §f" + gm.name());
        if (!target.equals(p)) p.sendMessage("§aSet §f" + target.getName() + "§a's gamemode to §f" + gm.name());
    }

    private GameMode matchGameMode(String input) {
        return switch (input.toLowerCase()) {
            case "0", "survival",  "s", "surv" -> GameMode.SURVIVAL;
            case "1", "creative",  "c", "cre"  -> GameMode.CREATIVE;
            case "2", "adventure", "a", "adv"  -> GameMode.ADVENTURE;
            case "3", "spectator", "sp","spec"  -> GameMode.SPECTATOR;
            default -> null;
        };
    }
}
