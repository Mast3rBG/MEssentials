package master.com.mEssentials;

import master.com.mEssentials.commands.*;
import master.com.mEssentials.commands.admin.*;
import master.com.mEssentials.commands.economy.*;
import master.com.mEssentials.commands.general.*;
import master.com.mEssentials.commands.tools.*;
import master.com.mEssentials.listeners.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.security.MessageDigest;
import java.util.*;

public final class MEssentials extends JavaPlugin {

    private final Map<UUID, TpaRequest> tpaRequests = new HashMap<>();
    private final Map<UUID, Location> lastLocation = new HashMap<>();
    private final Map<UUID, UUID> lastMessaged = new HashMap<>();
    private final List<UUID> frozenPlayers = new ArrayList<>();
    private final List<UUID> mutedPlayers = new ArrayList<>();
    private final List<UUID> vanishedPlayers = new ArrayList<>();
    private final List<String> ipMutedPlayers = new ArrayList<>();
    private final Map<String, MECommand> commands = new HashMap<>();

    private ScoreboardManager sbManager;
    private StaffManager smManager;
    private DataManager dataManager;
    private CurrencyManager currencyManager;

    public List<UUID> getFrozenPlayers() { return frozenPlayers; }
    public List<UUID> getMutedPlayers() { return mutedPlayers; }
    public List<UUID> getVanishedPlayers() { return vanishedPlayers; }
    public Map<UUID, TpaRequest> getTpaRequests() { return tpaRequests; }
    public Map<UUID, Location> getLastLocation() { return lastLocation; }
    public Map<UUID, UUID> getLastMessaged() { return lastMessaged; }
    public Collection<String> getIpmutedPlayers() { return ipMutedPlayers; }
    public ScoreboardManager getScoreboardManager() { return sbManager; }
    public StaffManager getStaffManager() { return smManager; }
    public DataManager getDataManager() { return dataManager; }
    public CurrencyManager getCurrencyManager() { return currencyManager; }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.dataManager     = new DataManager(this);
        this.currencyManager = new CurrencyManager(this);
        this.sbManager       = new ScoreboardManager(this);
        this.smManager       = new StaffManager(this);

        String channel = getConfig().getString("settings.proxy-channel", "velocity:main");

        register(new InvseeCommand(this));
        register(new TeleportCommand(this));
        register(new MOTDCommand());
        register(new MaintenanceCommand());
        register(new AntiLagCommand(this));
        register(new DashboardCommand(this));
        register(new GamemodeCommand());
        register(new HistoryCommand(this));
        register(new WarnCommand(this));
        register(new ScoreboardCommand());
        register(new MuteCommand(this));
        register(new BanCommand(this));
        register(new FreezeCommand(this));
        register(new AltCommand(this));
        register(new VanishCommand(this));
        register(new SpeedCommand());
        register(new StaffChatCommand(this));
        register(new StaffModeCommand(this));
        register(new DashboardEditCommand(this));
        register(new ShopEditCommand(this));
        register(new ChatUtilCommand(this, "broadcast"));
        register(new ChatUtilCommand(this, "clearchat"));
        register(new ChatUtilCommand(this, "mutechat"));
        register(new ChatUtilCommand(this, "sudo"));
        register(new StaffUtilCommand(this, "tpall"));
        register(new StaffUtilCommand(this, "kill"));
        register(new StaffUtilCommand(this, "clearinv"));
        register(new StaffUtilCommand(this, "kick"));
        register(new StaffUtilCommand(this, "tphere"));

        register(new PunishCommand(this, "permban"));
        register(new PunishCommand(this, "ipban"));
        register(new PunishCommand(this, "permmute"));
        register(new PunishCommand(this, "ipmute"));
        register(new UnpunishCommand(this, "unban"));
        register(new UnpunishCommand(this, "unmute"));

        register(new TpaCommand(this));
        register(new TpaHereCommand(this));
        register(new TpAcceptCommand(this));
        register(new TeleportResponse(this, "tpdeny"));
        register(new TeleportResponse(this, "tpcancel"));
        register(new SpawnCommand(this, false));
        register(new SpawnCommand(this, true));
        register(new BackCommand(this));
        register(new RTPCommand());
        register(new WarpCommand(this, "warp"));
        register(new WarpCommand(this, "setwarp"));
        register(new WarpCommand(this, "delwarp"));
        register(new WarpCommand(this, "warps"));
        register(new HomeCommand(this, "home"));
        register(new HomeCommand(this, "sethome"));
        register(new HomeCommand(this, "delhome"));
        register(new MovementCommand("top"));
        register(new MovementCommand("jump"));

        register(new HelpCommand());
        register(new RulesCommand(this));
        register(new SocialInfoCommand(this, "ping"));
        register(new SocialInfoCommand(this, "playtime"));
        register(new SocialInfoCommand(this, "seen"));
        register(new SocialInfoCommand(this, "realname"));
        register(new AFKCommand(this));
        register(new MessageCommand(this, false));
        register(new MessageCommand(this, true ));
        register(new SystemUtilCommand(this, "list"));
        register(new PlayerDataCommand("suicide"));
        register(new PlayerDataCommand("whois"));
        register(new LanguageCommand(this));

        register(new BalCommand(this));
        register(new EcoCommand(this));
        register(new PayCommand());
        register(new ShopCommand(this));
        register(new AHCommand(this));
        register(new SellCommand(this));
        register(new SellWandCommand(this));
        register(new SocialInfoCommand(this, "baltop"));

        register(new SystemUtilCommand(this, "gc"));
        register(new SystemUtilCommand(this, "memory"));
        register(new SystemUtilCommand(this, "essentials"));
        register(new PlayerDataCommand("skull"));
        register(new InventoryViewCommand("viewechest"));
        register(new InventoryViewCommand("adminviewvault"));
        register(new InventoryViewCommand("coords"));
        register(new ToggleCommand("tptoggle"));
        register(new ToggleCommand("togglepm"));
        register(new HealCommand(this));
        register(new FeedCommand());
        register(new FlyCommand());
        register(new GodCommand());
        register(new HatCommand());
        register(new NickCommand(this));
        register(new ItemToolCommand("repair"));
        register(new ItemToolCommand("rename"));
        register(new VaultCommand(this));
        register(new WorkbenchCommand(this));
        register(new EnderChestCommand());
        register(new VirtualBlockCommand("anvil"));
        register(new VirtualBlockCommand("grindstone"));
        register(new VirtualBlockCommand("cartography"));
        register(new VirtualBlockCommand("loom"));
        register(new VirtualBlockCommand("stonecutter"));
        register(new VelocityCommand(this, "lifesteal", "lifesteal"));
        register(new VelocityCommand(this, "lobby", "lobby"));

        register(new WorldControlCommand(this, "day"));
        register(new WorldControlCommand(this, "night"));
        register(new WorldControlCommand(this, "sun"));
        register(new WorldControlCommand(this, "rain"));
        register(new WorldControlCommand(this, "ptime"));
        register(new WorldControlCommand(this, "pweather"));
        register(new FunCommand("burn"));
        register(new FunCommand("smite"));
        register(new FunCommand("rocket"));
        register(new TrollCommand("fakeop"));
        register(new TrollCommand("fakedeop"));
        register(new TrollCommand("launch"));
        register(new TrollCommand("slap"));
        register(new TrollCommand("explode"));
        register(new KitCommand(this, "kit"));
        register(new KitCommand(this,"kits"));
        register(new KitCommand(this,"createkit"));
        register(new KitCommand(this,"delkit"));
        register(new MiscUtilCommand("near"));

        getServer().getPluginManager().registerEvents(new CurrencyRedeemListener(this), this);
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new ShopListener(this), this);
        getServer().getPluginManager().registerEvents(new FeatureListener(this), this);
        getServer().getPluginManager().registerEvents(new ConnectionListener(this), this);
        getServer().getPluginManager().registerEvents(new MenuListener(this), this);
        getServer().getPluginManager().registerEvents(new GUIEditorListener(this), this);
        getServer().getPluginManager().registerEvents(new GrandGuiListener(this), this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, channel);

        getLogger().info("MEssentials Enabled! Proxy Channel: " + channel);
    }

    private void register(MECommand cmd) {
        String name = cmd.getName().toLowerCase();
        if (!commands.containsKey(name)) {
            commands.put(name, cmd);
            if (getCommand(name) != null) {
                getCommand(name).setExecutor(this);
                if (cmd instanceof TabCompleter tc) {
                    getCommand(name).setTabCompleter(tc);
                }
                for (String alias : getCommand(name).getAliases()) {
                    commands.putIfAbsent(alias.toLowerCase(), cmd);
                }
            }
        }
    }

    public String hashIP(String ip) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(ip.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            return "HASH_ERROR";
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        MECommand cmd = commands.get(command.getName().toLowerCase());
        if (cmd != null) {
            if (!getConfig().getBoolean("commands." + cmd.getName() + ".enabled", true)) {
                sender.sendMessage("§cThis command is currently disabled.");
                return true;
            }
            if (cmd.getPermission() != null && !cmd.getPermission().isEmpty() && !sender.hasPermission(cmd.getPermission())) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.no-permission", "&cNo permission!")));
                return true;
            }
            cmd.execute(sender, args);
        }
        return true;
    }

    public String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString("settings.prefix", "&8[&bME&8] &r"));
    }

    public <K> Map<K, Long> getBaltopCooldown() {
        return Map.of();
    }
}