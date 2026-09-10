package master.com.mEssentials.commands.general;

import master.com.mEssentials.commands.MECommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HelpCommand implements MECommand {
    @Override public String getName() { return "mehelp"; }
    @Override public String getPermission() { return null; }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(ChatColor.AQUA + "§m-------§r" + ChatColor.AQUA + " §lMEssentials Help Guide §m-------");

        sender.sendMessage(ChatColor.GOLD + "§l> General & Social");
        sender.sendMessage("§f/rules, /mehelp, /afk, /ping, /playtime, /seen, /realname, /list, /suicide");

        sender.sendMessage(ChatColor.GOLD + "§l> Teleportation");
        sender.sendMessage("§f/spawn, /setspawn, /home, /sethome, /delhome, /homes, /rtp, /back");
        sender.sendMessage("§f/tpa, /tpahere, /tpaccept, /tpdeny, /tpcancel, /tptoggle");

        sender.sendMessage(ChatColor.GOLD + "§l> Economy");
        sender.sendMessage("§f/bal, /pay, /eco, /baltop, /shop, /ah, /sell, /sellall, /sellwand");

        sender.sendMessage(ChatColor.GOLD + "§l> Tools & Utility");
        sender.sendMessage("§f/heal, /feed, /fly, /god, /hat, /nick, /speed, /skull, /rename, /coords");
        sender.sendMessage("§f/repair, /fix, /enderchest, /workbench, /vault, /msg, /reply, /togglepm");

        sender.sendMessage(ChatColor.GOLD + "§l> Virtual Blocks");
        sender.sendMessage("§f/anvil, /grindstone, /cartography, /loom, /stonecutter");

        sender.sendMessage(ChatColor.GOLD + "§l> World Control");
        sender.sendMessage("§f/day, /night, /sun, /rain, /ptime, /pweather, /top, /jump, /gamemode, /gm");

        sender.sendMessage(ChatColor.GOLD + "§l> Staff & Admin");
        sender.sendMessage("§f/invsee, /tp, /tphere, /tpall, /kill, /clearinv, /sudo, /broadcast, /kick");
        sender.sendMessage("§f/staffchat, /staffmode, /vanish, /freeze, /maintenance, /antilag, /whois");
        sender.sendMessage("§f/viewechest, /adminviewvault, /scoreboard, /dashboard, /alt, /gc, /memory, /essentials");

        sender.sendMessage(ChatColor.GOLD + "§l> Punishments");
        sender.sendMessage("§f/mute, /unmute, /ban, /unban, /permmute, /permban, /ipmute, /ipban");

        sender.sendMessage(ChatColor.GOLD + "§l> Fun & Troll");
        sender.sendMessage("§f/burn, /smite, /rocket, /fakeop, /fakedeop, /launch, /slap, /explode");

        sender.sendMessage(ChatColor.GOLD + "§l> Kits");
        sender.sendMessage("§f/kit, /kits, /createkit, /delkit");

        sender.sendMessage(ChatColor.AQUA + "§m---------------------------------");
    }
}