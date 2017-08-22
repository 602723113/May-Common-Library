package me.may.core.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

/**
 * ActionBar - 工具类
 *
 * @author May_Speed
 * @since 2016/?/?
 */
public class ActionBarUtils {

    /**
     * 给一名玩家发送actionbar
     *
     * @param player 玩家
     * @param msg    信息
     */
    public static void sendBar(Player player, String msg) {
        msg = ChatColor.translateAlternateColorCodes('&', msg);
        //获取Pl管理
        ProtocolManager pm = ProtocolLibrary.getProtocolManager();
        PacketContainer packet = pm.createPacket(PacketType.Play.Server.CHAT);
        //nms内封包结构为
        /*	private IChatBaseComponent a;
         *	public BaseComponent[] components; //可以不用填
		 *	private byte b;
		*/
        //依次写入数据
        packet.getChatComponents().write(0, WrappedChatComponent.fromText(msg));
        packet.getBytes().write(0, (byte) 2);

        //发送数据包
        try {
            pm.sendServerPacket(player, packet, false);
        } catch (InvocationTargetException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }
}
