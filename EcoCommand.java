package master.com.mEssentials.commands.economy;

import master.com.mEssentials.CurrencyManager;
import master.com.mEssentials.CurrencyManager.CurrencyType;
import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class EcoCommand implements MECommand {
    private final MEssentials plugin;

    public EcoCommand(MEssentials plugin) {
        this.plugin = plugin;
    }

    @Override public String getName() { return "eco"; }
    @Override public String getPermission() { return "messentials.admin"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return;
        }

        String action = args[0].toLowerCase();

        if (action.equals("block")) {
            handleBlock(sender, args);
            return;
        }

        if (args.length < 3) {
            sendHelp(sender);
            return;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
        double amount;
        try {
            amount = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage("§cInvalid amount.");
            return;
        }

        CurrencyType type = CurrencyType.COINS;
        if (args.length >= 4) {
            type = CurrencyType.fromKey(args[3]);
            if (type == null) {
                sender.sendMessage("§cUnknown currency. Use: coins, tokens, gems");
                return;
            }
        }

        CurrencyManager cm = plugin.getCurrencyManager();
        double current = cm.getBalance(target, type);

        switch (action) {
            case "give" -> cm.addBalance(target, type, amount);
            case "take" -> cm.takeBalance(target, type, amount);
            case "set"  -> cm.setBalance(target, type, amount);
            default     -> { sendHelp(sender); return; }
        }

        String symbol = cm.getSymbol(type);
        double newBal  = cm.getBalance(target, type);
        sender.sendMessage(plugin.getPrefix() + "§7Updated §f" + type.displayName + "§7 for §b"
                + target.getName() + "§7: §c" + CurrencyManager.formatAmt(current)
                + " §7→ §a" + symbol + CurrencyManager.formatAmt(newBal));

        Player online = Bukkit.getPlayer(target.getUniqueId());
        if (online != null && plugin.getScoreboardManager() != null)
            plugin.getScoreboardManager().showScoreboard(online);
    }


    private void handleBlock(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("§cUsage: /eco block <player> <amount> [coins|tokens|gems]");
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage("§cPlayer not found or offline.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(args[2]);
            if (amount <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            sender.sendMessage("§cAmount must be a positive number.");
            return;
        }

        CurrencyType type = CurrencyType.COINS;
        if (args.length >= 4) {
            type = CurrencyType.fromKey(args[3]);
            if (type == null) {
                sender.sendMessage("§cUnknown currency. Use: coins, tokens, gems");
                return;
            }
        }

        CurrencyManager cm    = plugin.getCurrencyManager();
        ItemStack       block = cm.createBlock(type, amount);

        Map<Integer, ItemStack> leftover = target.getInventory().addItem(block);
        if (!leftover.isEmpty()) {
            leftover.values().forEach(i -> target.getWorld().dropItemNaturally(target.getLocation(), i));
            target.sendMessage("§eYour inventory was full — the currency block was dropped at your feet.");
        }

        String symbol = cm.getSymbol(type);
        sender.sendMessage(plugin.getPrefix() + "§7Gave §b" + target.getName()
                + "§7 a currency block worth " + symbol + "§a" + CurrencyManager.formatAmt(amount)
                + " " + type.displayName + "§7.");
        target.sendMessage(plugin.getPrefix() + "§7You received a currency block worth "
                + symbol + "§a" + CurrencyManager.formatAmt(amount) + " " + type.displayName
                + "§7. §aPick it up to redeem it!");
    }

    private void sendHelp(CommandSender s) {
        s.sendMessage("§7§m---------§r §b/eco §7§m---------");
        s.sendMessage("§e/eco give|set|take <player> <amount> [coins|tokens|gems]");
        s.sendMessage("§e/eco block <player> <amount> [coins|tokens|gems]");
        s.sendMessage("§7Default currency: §6coins");
    }
}
