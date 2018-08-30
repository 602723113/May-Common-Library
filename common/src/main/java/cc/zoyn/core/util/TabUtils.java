package cc.zoyn.core.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.apache.commons.lang3.Validate;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;

public final class TabUtils {

    // Prevent accidental construction
    private TabUtils() {

    }

    /**
     * 更改一个玩家的Tab列表
     * <p>
     * change a player's tab
     *
     * @param player player
     * @param head   tab's head
     * @param foot   tab's foot
     */
    public static void setTab(@Nullable Player player, @Nullable String head, @Nullable String foot) {
        Validate.notNull(player);

        String translatedHead = "";
        String translatedFoot = "";
        if (head != null) {
            translatedHead = ChatColor.translateAlternateColorCodes('&', head);
        }
        if (foot != null) {
            translatedFoot = ChatColor.translateAlternateColorCodes('&', foot);
        }

        // get ProtocolManager instance
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
        packet.getChatComponents()
                .write(0, WrappedChatComponent.fromText(translatedHead))
                .write(1, WrappedChatComponent.fromText(translatedFoot));
        try {
            protocolManager.sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
