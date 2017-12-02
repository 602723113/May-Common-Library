package cc.zoyn.core.util.nms;

import cc.zoyn.core.util.ReflectionUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

/**
 * NMS工具类
 *
 * @author Zoyn
 * @since 2017/4/26
 */
public final class NMSUtils {

    private static Class<?> chatSerializer;
    private static Class<?> enumTitleAction;
    private static Class<?> packetPlayOutTitle;
    private static Class<?> packetPlayOutChat;
    private static String version;
    private static Method asNMSCopyMethod;


    private NMSUtils() {
    }

    static {
        //org.bukkit.craftbukkit.vX_XX_RX;
        version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        chatSerializer = version.contains("v1_8_R1") ? getNMSClass("ChatSerializer") : getNMSClass("IChatBaseComponent$ChatSerializer");
        enumTitleAction = version.contains("v1_8_R1") ? getNMSClass("EnumTitleAction") : getNMSClass("PacketPlayOutTitle$EnumTitleAction");
        packetPlayOutTitle = getNMSClass("PacketPlayOutTitle");
        packetPlayOutChat = getNMSClass("PacketPlayOutChat");
    }

    /**
     * 取PacketPlayOutTitle类
     *
     * @return {@link Class}
     */
    public static Class<?> getPacketPlayOutTitleClass() {
        return packetPlayOutTitle;
    }

    /**
     * 取PacketPlayOutChat类
     *
     * @return {@link Class}
     */
    public static Class<?> getPacketPlayOutChatClass() {
        return packetPlayOutChat;
    }

    /**
     * 取EnumTitleAction类
     *
     * @return {@link Class}
     */
    public static Class<?> getEnumTitleActionClass() {
        return enumTitleAction;
    }

    /**
     * 取chatSerializer类
     *
     * @return {@link Class}
     */
    public static Class<?> getChatSerializerClass() {
        return chatSerializer;
    }

    /**
     * 取服务器版本 如v1_10_R1
     *
     * @return 服务器版本
     */
    public static String getVersion() {
        return version;
    }

    public static Class<?> getOBCClass(String className) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + NMSUtils.getVersion() + "." + className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取物品的NMS对象
     *
     * @param is
     * @return {@link Object}
     */
    public static Object getNMSItem(ItemStack is) {
        if (asNMSCopyMethod == null) {
            Class craftItemStack = NMSUtils.getOBCClass("inventory.CraftItemStack");
            Validate.notNull(craftItemStack);

            try {
                asNMSCopyMethod = ReflectionUtils.getMethod(ReflectionUtils.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack"), "asNMSCopy", ItemStack.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 取对应的NMS下的类
     *
     * @param className 类名
     * @return {@link Class}
     */
    public static Class<?> getNMSClass(String className) {
        try {
            return Class.forName("net.minecraft.server." + version + "." + className);
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }
        return null;
    }

    /**
     * 给一名玩家发送NMS数据包
     *
     * @param player 玩家
     * @param packet 数据包对象
     */
    public static void sendPacket(Player player, Object packet) {
        //取NMS实例
        Object entityPlayer = getNMSPlayer(player);
        try {
            //取其playerConnection实例
            Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
            //调用方法sendPacket()
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }
    }

    /**
     * 取玩家NMS对象也就是EntityPlayer类的实例
     *
     * @param player 玩家
     * @return 玩家的NMS对象
     */
    public static Object getNMSPlayer(Player player) {
        try {
            //取Player的实现类并调用其方法getHandle
            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
            return entityPlayer;
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }
        return player;
    }
}
