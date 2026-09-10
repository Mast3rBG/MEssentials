package master.com.mEssentials.commands.economy;

import master.com.mEssentials.CurrencyManager;
import master.com.mEssentials.CurrencyManager.CurrencyType;
import master.com.mEssentials.MEssentials;
import master.com.mEssentials.commands.MECommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class BalCommand implements MECommand {
    private final MEssentials plugin;

    public BalCommand(MEssentials plugin) {
        this.plugin = plugin;
    }

    @Override public String getName() { return "bal"; }
    @Override public String getPermission() { return "messentials.economy.bal"; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        OfflinePlayer target = (args.length > 0)
                ? Bukkit.getOfflinePlayer(args[0])
                : (OfflinePlayer) sender;

        CurrencyManager cm = plugin.getCurrencyManager();

        double coins  = cm.getBalance(target, CurrencyType.COINS);
        double tokens = cm.getBalance(target, CurrencyType.TOKENS);
        double gems   = cm.getBalance(target, CurrencyType.GEMS);

        String sCoins  = cm.getSymbol(CurrencyType.COINS);
        String sTokens = cm.getSymbol(CurrencyType.TOKENS);
        String sGems   = cm.getSymbol(CurrencyType.GEMS);

        sender.sendMessage(plugin.getPrefix() + "§7Balance for §b" + target.getName() + "§7:");
        sender.sendMessage("  " + sCoins  + " §6Coins:  §f" + CurrencyManager.formatAmt(coins));
        sender.sendMessage("  " + sTokens + " §bTokens: §f" + CurrencyManager.formatAmt(tokens));
        sender.sendMessage("  " + sGems   + " §dGems:   §f" + CurrencyManager.formatAmt(gems));
    }
}
