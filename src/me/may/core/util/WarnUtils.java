package me.may.core.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.WorldBorderAction;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

/**
 * 红屏警告工具类
 *
 * @author May_Speed
 * @since 2017/7/28
 */
public class WarnUtils {

    public static void sendWarn(Player player) {
        // d ---> size
        ProtocolManager pm = ProtocolLibrary.getProtocolManager();
        PacketContainer packet = pm.createPacket(PacketType.Play.Server.WORLD_BORDER);
        packet.getWorldBorderActions().write(0, WorldBorderAction.SET_SIZE);
        WorldBorder wb = player.getWorld().getWorldBorder();
        packet.getDoubles().write(0, wb.getCenter().getX()).write(1, wb.getSize()).write(2, wb.getDamageBuffer())
                .write(3, wb.getCenter().getZ());
        packet.getLongs().write(0, System.currentTimeMillis());
        packet.getIntegers().write(0, wb.getWarningDistance()).write(1, wb.getWarningTime());
        try {
            pm.sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void clearWarn() {

    }
}
