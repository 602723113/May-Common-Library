package cc.zoyn.core.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

/**
 * Title工具类
 *
 * @author Zoyn, D_xiaox
 * @since 2016/12/26
 * <p>
 * update 2017/8/05
 * update 2018/8/02 星空(D_xiaox)
 */
public final class TitleUtils {

    // Prevent accidental construction
    private TitleUtils() {
    }

    /**
     * 给一个玩家发送Title信息 1.8+
     *
     * @param player   发送的玩家
     * @param fadeIn   淡入时间
     * @param stay     停留时间
     * @param fadeOut  淡出时间
     * @param title    主标题
     * @param subTitle 副标题
     */
    public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subTitle) {
        // get protocol manager instance
        ProtocolManager pm = ProtocolLibrary.getProtocolManager();
        PacketContainer packet;
        if (title != null) {
            String translatedTitle = ChatColor.translateAlternateColorCodes('&', title);
            translatedTitle = translatedTitle.replaceAll("%player%", player.getName());
            // create packet
            packet = pm.createPacket(PacketType.Play.Server.TITLE);
            // write datas
            packet.getTitleActions().write(0, EnumWrappers.TitleAction.TITLE); // EnumTitleAction
            packet.getChatComponents().write(0, WrappedChatComponent.fromText(translatedTitle)); // 标题内容
            try {
                pm.sendServerPacket(player, packet, false); // 发送数据包
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        if (subTitle != null) {
            String translatedSubTitle = ChatColor.translateAlternateColorCodes('&', subTitle);
            translatedSubTitle = translatedSubTitle.replaceAll("%player%", player.getName());

            packet = pm.createPacket(PacketType.Play.Server.TITLE);
            packet.getTitleActions().write(0, EnumWrappers.TitleAction.SUBTITLE);
            packet.getChatComponents().write(0, WrappedChatComponent.fromText(translatedSubTitle));
            try {
                pm.sendServerPacket(player, packet, false);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        // 以前无法设置时间, 现在可以了
        packet = pm.createPacket(PacketType.Play.Server.TITLE);
        packet.getTitleActions().write(0, EnumWrappers.TitleAction.TIMES);
        packet.getIntegers()
                .write(0, fadeIn) // ---> c
                .write(1, stay) // ---> d
                .write(2, fadeOut); // ---> e
        try {
            pm.sendServerPacket(player, packet, false);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
