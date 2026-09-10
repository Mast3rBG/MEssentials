package master.com.mEssentials;

import master.com.mEssentials.CurrencyManager.CurrencyType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import java.util.List;

public class ScoreboardManager {
    private final MEssentials plugin;

    public ScoreboardManager(MEssentials plugin) {
        this.plugin = plugin;
    }

    public void showScoreboard(Player player) {
        Bukkit.getScoreboardManager();
        org.bukkit.scoreboard.Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        String title = plugin.getConfig().getString("commands.scoreboard.title", "&b&lME&f&lSSENTIALS");
        Objective obj = board.registerNewObjective("mEssentials", "dummy",
                ChatColor.translateAlternateColorCodes('&', title));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        CurrencyManager cm = plugin.getCurrencyManager();

        double coins  = cm.getBalance(player, CurrencyType.COINS);
        double tokens = cm.getBalance(player, CurrencyType.TOKENS);
        double gems   = cm.getBalance(player, CurrencyType.GEMS);

        String sCoins  = cm.getSymbol(CurrencyType.COINS);
        String sTokens = cm.getSymbol(CurrencyType.TOKENS);
        String sGems   = cm.getSymbol(CurrencyType.GEMS);

        String legacySymbol = plugin.getConfig().getString("settings.currency-symbol", "$");

        List<String> lines = plugin.getConfig().getStringList("commands.scoreboard.lines");
        int scoreValue = lines.size();

        for (String line : lines) {
            String formatted = line
                    .replace("%player%",          player.getName())
                    .replace("%ping%",             String.valueOf(player.getPing()))
                    .replace("%symbol%",           legacySymbol)
                    .replace("%balance%",          CurrencyManager.formatAmt(coins))
                    .replace("%coins%",            CurrencyManager.formatAmt(coins))
                    .replace("%coins_symbol%",     sCoins)
                    .replace("%tokens%",           CurrencyManager.formatAmt(tokens))
                    .replace("%tokens_symbol%",    sTokens)
                    .replace("%gems%",             CurrencyManager.formatAmt(gems))
                    .replace("%gems_symbol%",      sGems);

            Score lineScore = obj.getScore(ChatColor.translateAlternateColorCodes('&', formatted));
            lineScore.setScore(scoreValue--);
        }

        player.setScoreboard(board);
    }
}
