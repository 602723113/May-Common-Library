package cc.zoyn.core.util.nms;

import cc.zoyn.core.util.reflect.ReflectionUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static cc.zoyn.core.util.reflect.ReflectionUtils.*;

/**
 * Easy to use NMS
 *
 * @author Zoyn
 * @since 2017/4/26
 */
public final class NMSUtils {

    private static String version;
    private static Field playerConnectionField;
    private static Method sendPacketMethod;
    private static Method asNMSCopyMethod;
    private static Method stringAsIChatBaseComponentMethod;
    private static Method craftBukkitEntityPlayerGetHandleMethod;

    // Prevent accidental construction
    private NMSUtils() {
    }

    static {
        // org.bukkit.craftbukkit.vX_XX_RX;
        version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        // initial
        try {
            playerConnectionField = getFieldByFieldName(getNMSClass("EntityPlayer"), "playerConnection");
            sendPacketMethod = getMethod(getNMSClass("PlayerConnection"), "sendPacket", getNMSClass("Packet"));
            asNMSCopyMethod = getMethod(getOBCClass("inventory.CraftItemStack"), "asNMSCopy", ItemStack.class);
            stringAsIChatBaseComponentMethod = getMethod(getNMSClass("IChatBaseComponent$ChatSerializer"), "a", String.class);
            craftBukkitEntityPlayerGetHandleMethod = getMethod(getOBCClass("entity.CraftPlayer"), "getHandle");
        } catch (NoSuchMethodException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取服务器版本 如 v1_10_R1
     * <br />
     * get the server version, returns a string similar to v1_10_R1
     *
     * @return server version
     */
    public static String getVersion() {
        return version;
    }

    /**
     * 取 org.bukkit.craftbukkit 包下的类对象
     * <br />
     * get org.bukkit.craftbukkit's class object
     *
     * @param className a class's name in the package obc
     * @return {@link Class}
     */
    public static Class<?> getOBCClass(String className) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + NMSUtils.getVersion() + "." + className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取物品的 NMS 对象
     * <br />
     * get a item's nms object
     *
     * @param itemStack a itemStack object
     * @return {@link Object}
     */
    public static Object getNMSItem(ItemStack itemStack) {
        if (asNMSCopyMethod == null) {
            Class craftItemStack = NMSUtils.getOBCClass("inventory.CraftItemStack");
            Validate.notNull(itemStack);

            try {
                //CraftItemStack

                asNMSCopyMethod = getMethod(craftItemStack, "asNMSCopy", ItemStack.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        try {
            return asNMSCopyMethod.invoke(Validate.notNull(itemStack));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 取对应的 NMS 下的类
     * <br />
     * get net.minecraft.server's class object
     *
     * @param className a class's name in the package nms
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
     * 给一名玩家发送 NMS 数据包
     * <br />
     * send a NMS packet to a player
     *
     * @param player player object
     * @param packet packet object
     * @see #getNMSPlayer(Player)
     * @see ReflectionUtils#invokeMethod(Method, Object, Object...)
     */
    public static void sendPacket(Player player, Object packet) {
        Object entityPlayer = getNMSPlayer(player);

        if (playerConnectionField == null) {
            try {
                playerConnectionField = getFieldByFieldName(getNMSClass("EntityPlayer"), "playerConnection");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        if (sendPacketMethod == null) {
            try {
                sendPacketMethod = getMethod(getNMSClass("PlayerConnection"), "sendPacket", getNMSClass("Packet"));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        try {
            // get playerConnection instance
            Object playerConnection = playerConnectionField.get(entityPlayer);
            // invoke method sendPacket()
            invokeMethod(sendPacketMethod, playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取玩家的 NMS 对象
     * <br />
     * get a player's nms object
     *
     * @param player player object
     * @return {@link Object}
     * @see ReflectionUtils#invokeMethod(Method, Object, Object...)
     */
    public static Object getNMSPlayer(Player player) {
        try {
            return invokeMethod(craftBukkitEntityPlayerGetHandleMethod, player);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return player;
    }

    /**
     * 将一行文本转换为 IChatBaseComponent 对象
     * <br/>
     * Convert a text to IChatBaseComponent
     *
     * @param text String object
     * @return {@link Object}
     * @see ReflectionUtils#getMethod(Class, String, Class[])
     */
    public static Object stringToIChatBaseComponent(String text) {
        if (stringAsIChatBaseComponentMethod == null) {
            try {
                // IChatBaseComponent$ChatSerializer
                stringAsIChatBaseComponentMethod = getMethod(getNMSClass("IChatBaseComponent$ChatSerializer"), "a", String.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            return invokeMethod(stringAsIChatBaseComponentMethod, Validate.notNull(text));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
