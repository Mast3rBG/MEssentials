package master.com.mEssentials.commands.economy;

import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements MECommand {
    @Override
    public String getName() { return "pay"; }
    @Override
    public String getPermission() { return "proessentials.economy.pay"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) return;

        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Usage: /pay <player> <amount>");
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(ChatColor.RED + "Player not found.");
            return;
        }

        try {
            double amount = Double.parseDouble(args[1]);
            player.sendMessage(ChatColor.GREEN + "You sent $" + amount + " to " + target.getName());
            target.sendMessage(ChatColor.GREEN + "You received $" + amount + " from " + player.getName());
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "Invalid amount.");
        }
    }
}