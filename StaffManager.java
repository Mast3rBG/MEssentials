package master.com.mEssentials;

import org.bukkit.entity.Player;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class StaffManager {
    private final Set<UUID> inStaffMode = new HashSet<>();
    private final Set<UUID> inStaffChat = new HashSet<>();

    public StaffManager(MEssentials mEssentials) {
    }

    public void toggleStaffMode(Player player) {
        if (inStaffMode.contains(player.getUniqueId())) {
            inStaffMode.remove(player.getUniqueId());
        } else {
            inStaffMode.add(player.getUniqueId());
        }
    }

    public boolean isInStaffMode(UUID uuid) { return inStaffMode.contains(uuid); }
    public void toggleStaffChat(UUID uuid) {
        if (inStaffChat.contains(uuid)) inStaffChat.remove(uuid);
        else inStaffChat.add(uuid);
    }
    public boolean isInStaffChat(UUID uuid) { return inStaffChat.contains(uuid); }
}