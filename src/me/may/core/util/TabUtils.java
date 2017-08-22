package me.may.core.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import me.may.core.Core;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class TabUtils {

    /**
     * 更改一个玩家的Tab列表
     *
     * @param player 玩家
     * @param head   Tab顶部
     * @param foot   Tab底部
     */
    public static void setTab(Player player, String head, String foot) {
        if (head == null) {
            head = "";
        }
        head = ChatColor.translateAlternateColorCodes('&', head);
        if (foot == null) {
            foot = "";
        }
        foot = ChatColor.translateAlternateColorCodes('&', foot);
        ProtocolManager pm = Core.getInstance().getProtocolManager();
        PacketContainer packet = pm.createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
        packet.getChatComponents()
                .write(0, WrappedChatComponent.fromText(head))
                .write(1, WrappedChatComponent.fromText(foot))
        ;
        try {
            pm.sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
