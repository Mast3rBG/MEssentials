package master.com.mEssentials;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

public class CurrencyManager {

    private final MEssentials plugin;

    private final NamespacedKey keyType;
    private final NamespacedKey keyAmount;


    public enum CurrencyType {
        COINS  ("coins",  "§6Coins",  "§6⛃", Material.GOLD_NUGGET),
        TOKENS ("tokens", "§bTokens", "§b★",  Material.EMERALD),
        GEMS   ("gems",   "§dGems",   "§d◆",  Material.AMETHYST_SHARD);

        public final String key;
        public final String displayName;
        public final String fallbackSymbol;
        public final Material fallbackMaterial;

        CurrencyType(String key, String displayName, String fallbackSymbol, Material fallbackMaterial) {
            this.key              = key;
            this.displayName      = displayName;
            this.fallbackSymbol   = fallbackSymbol;
            this.fallbackMaterial = fallbackMaterial;
        }

        public static CurrencyType fromKey(String key) {
            for (CurrencyType t : values())
                if (t.key.equalsIgnoreCase(key)) return t;
            return null;
        }
    }


    public CurrencyManager(MEssentials plugin) {
        this.plugin    = plugin;
        this.keyType   = new NamespacedKey(plugin, "currency_type");
        this.keyAmount = new NamespacedKey(plugin, "currency_amount");
    }


    private String dataPath(OfflinePlayer p, CurrencyType type) {
        String field = (type == CurrencyType.COINS) ? "balance" : type.key;
        return "players." + p.getUniqueId() + "." + field;
    }

    public double getBalance(OfflinePlayer p, CurrencyType type) {
        return plugin.getDataManager().getConfig().getDouble(dataPath(p, type), 0.0);
    }

    public void setBalance(OfflinePlayer p, CurrencyType type, double amount) {
        plugin.getDataManager().getConfig().set(dataPath(p, type), Math.max(0.0, amount));
        plugin.getDataManager().saveConfig();
    }

    public void addBalance(OfflinePlayer p, CurrencyType type, double amount) {
        setBalance(p, type, getBalance(p, type) + amount);
    }

    public void takeBalance(OfflinePlayer p, CurrencyType type, double amount) {
        setBalance(p, type, getBalance(p, type) - amount);
    }


    public String getSymbol(CurrencyType type) {
        return plugin.getConfig().getString("currencies." + type.key + ".symbol", type.fallbackSymbol);
    }

    public Material getMaterial(CurrencyType type) {
        String name = plugin.getConfig().getString("currencies." + type.key + ".material", type.fallbackMaterial.name());
        Material m  = Material.getMaterial(name);
        return (m != null) ? m : type.fallbackMaterial;
    }


    public ItemStack createBlock(CurrencyType type, double amount) {
        String symbol = getSymbol(type);

        ItemStack item = new ItemStack(getMaterial(type), 1);
        ItemMeta  meta = item.getItemMeta();

        meta.setDisplayName(symbol + "§l" + formatAmt(amount) + " " + type.displayName);
        meta.setLore(Arrays.asList(
            "§8[Currency Block]",
            "§7Type:  " + type.displayName,
            "§7Value: " + symbol + "§f" + formatAmt(amount),
            "",
            "§a▶ Auto-redeems when picked up!"
        ));

        meta.getPersistentDataContainer().set(keyType,   PersistentDataType.STRING, type.key);
        meta.getPersistentDataContainer().set(keyAmount, PersistentDataType.DOUBLE,  amount);

        item.setItemMeta(meta);
        return item;
    }

    public boolean tryRedeem(Player p, ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;

        ItemMeta meta    = item.getItemMeta();
        String   typeKey = meta.getPersistentDataContainer().get(keyType,   PersistentDataType.STRING);
        Double   amount  = meta.getPersistentDataContainer().get(keyAmount, PersistentDataType.DOUBLE);

        if (typeKey == null || amount == null) return false;

        CurrencyType type = CurrencyType.fromKey(typeKey);
        if (type == null) return false;

        addBalance(p, type, amount);
        String symbol = getSymbol(type);
        p.sendMessage("§a§lREDEEMED §8» §7You received " + symbol + "§a" + formatAmt(amount)
                + " " + type.displayName + "§7! New balance: " + symbol + "§a" + formatAmt(getBalance(p, type)));

        if (plugin.getScoreboardManager() != null)
            plugin.getScoreboardManager().showScoreboard(p);

        return true;
    }


    public static String formatAmt(double v) {
        return (v == Math.floor(v) && !Double.isInfinite(v))
               ? String.valueOf((long) v)
               : String.format("%.2f", v);
    }

    public NamespacedKey getKeyType()   { return keyType; }
    public NamespacedKey getKeyAmount() { return keyAmount; }
}
