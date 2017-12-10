package cc.zoyn.core.util.nms;

import cc.zoyn.core.util.nms.nbt.AttributeType;
import cc.zoyn.core.util.reflect.ReflectionUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

/**
 * Easy to use NMS
 *
 * @author Zoyn
 * @since 2017/4/26
 */
public final class NMSUtils {

    private static String version;
    private static Method asNMSCopyMethod;
    private static Method stringAsIChatBaseComponentMethod;
    private static Method craftBukkitEntityPlayerGetHandleMethod;

    // Prevent accidental construction
    private NMSUtils() {
    }

    static {
        //org.bukkit.craftbukkit.vX_XX_RX;
        version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            stringAsIChatBaseComponentMethod = ReflectionUtils.getMethod(getNMSClass("IChatBaseComponent$ChatSerializer"), "a", String.class);
            craftBukkitEntityPlayerGetHandleMethod = ReflectionUtils.getMethod(getOBCClass("inventory.CraftItemStack"), "getHandle");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取服务器版本 如v1_10_R1
     *
     * @return 服务器版本
     */
    public static String getVersion() {
        return version;
    }

    /**
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
     * 取物品的NMS对象
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
                asNMSCopyMethod = ReflectionUtils.getMethod(craftItemStack, "asNMSCopy", ItemStack.class);
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
     * 取对应的NMS下的类
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
     * 给一名玩家发送NMS数据包
     *
     * @param player player object
     * @param packet packet object
     */
    public static void sendPacket(Player player, Object packet) {
        Object entityPlayer = getNMSPlayer(player);
        try {
            // get playerConnection instance
            Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
            // invoke sendPacket
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取玩家NMS对象也就是EntityPlayer类的实例
     * <br />
     * get a player's nms object
     *
     * @param player player object
     * @return {@link Object}
     */
    public static Object getNMSPlayer(Player player) {
        try {
            return ReflectionUtils.invokeMethod(craftBukkitEntityPlayerGetHandleMethod, player);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return player;
    }

    public static Object stringToIChatBaseComponent(String string) {
        if (stringAsIChatBaseComponentMethod == null) {
            try {
                // IChatBaseComponent$ChatSerializer
                stringAsIChatBaseComponentMethod = ReflectionUtils.getMethod(getNMSClass("IChatBaseComponent$ChatSerializer"), "a", String.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        try {
            return stringAsIChatBaseComponentMethod.invoke(Validate.notNull(string));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
