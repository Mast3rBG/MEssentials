package master.com.mEssentials.commands.general;

import java.util.UUID;

public class TpaRequest {
    private final UUID sender;
    private final boolean isHere;

    public TpaRequest(UUID sender, boolean isHere) {
        this.sender = sender;
        this.isHere = isHere;
    }

    public UUID getSender() { return sender; }
    public boolean isHere() { return isHere; }
}