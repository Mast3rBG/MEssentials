package master.com.mEssentials;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlaceholderUtil {
    public static String setPlaceholders(Player p, String text) {
        return text.replace("%player%", p.getName())
                .replace("%ping%", String.valueOf(p.getPing()))
                .replace("%online%", String.valueOf(Bukkit.getOnlinePlayers().size()))
                .replace("%max%", String.valueOf(Bukkit.getMaxPlayers()))
                .replace("%balance%", "0");
    }
}