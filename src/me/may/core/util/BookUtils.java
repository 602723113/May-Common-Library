package me.may.core.util;

import me.may.core.dto.Book;
import me.may.core.dto.Page;
import me.may.core.util.ReflectionUtils.PackageType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 书本 - 工具类
 *
 * @author May_Speed
 * @since 2017/?/?
 */
public class BookUtils {

    private static boolean initialised = false;
    private static Method getHandle;
    private static Method openBook;

    static {
        try {
            getHandle = ReflectionUtils.getMethod("CraftPlayer", PackageType.CRAFTBUKKIT_ENTITY, "getHandle");
            openBook = ReflectionUtils.getMethod("EntityPlayer", PackageType.MINECRAFT_SERVER, "a",
                    PackageType.MINECRAFT_SERVER.getClass("ItemStack"),
                    PackageType.MINECRAFT_SERVER.getClass("EnumHand"));
            initialised = true;
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            Bukkit.getServer().getLogger().warning("Cannot force open book!");
            initialised = false;
        }
    }

    public static boolean isInitialised() {
        return initialised;
    }

    /**
     * 打开一个虚拟的书
     *
     * @param item   书
     * @param player 玩家
     * @return 布尔值[true成功/false失败]
     */
    public static boolean openBook(Player player, Book book) {
        if (!initialised)
            return false;
        ItemStack held = player.getInventory().getItemInMainHand();
        ItemStack bookItem = book.getItem();
        try {
            player.getInventory().setItemInMainHand(bookItem);
            sendPacket(bookItem, player);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            initialised = false;
        }
        player.getInventory().setItemInMainHand(held);
        return initialised;

    }

    /**
     * 打开一个虚拟的书
     *
     * @param item   书
     * @param player 玩家
     * @return 布尔值[true成功/false失败]
     */
    public static boolean openBook(ItemStack item, Player player) {
        if (!initialised)
            return false;
        ItemStack held = player.getInventory().getItemInMainHand();
        try {
            player.getInventory().setItemInMainHand(item);
            sendPacket(item, player);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            initialised = false;
        }
        player.getInventory().setItemInMainHand(held);
        return initialised;
    }

    private static void sendPacket(ItemStack i, Player p) throws ReflectiveOperationException {
        Object entityplayer = getHandle.invoke(p);
        Class<?> enumHand = PackageType.MINECRAFT_SERVER.getClass("EnumHand");
        Object[] enumArray = enumHand.getEnumConstants();
        openBook.invoke(entityplayer, getItemStack(i), enumArray[0]);
    }

    public static Object getItemStack(ItemStack item) {
        try {
            Method asNMSCopy = ReflectionUtils.getMethod(PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack"),
                    "asNMSCopy", ItemStack.class);
            return asNMSCopy.invoke(PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack"), item);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 以JSON格式来设置书的页面
     *
     * @param metadata 书本的BookMeta
     * @param pages    每页的json集合
     */
    @SuppressWarnings("unchecked")
    public static void setPagesAsJson(BookMeta metadata, List<String> pages) {
        List<Object> p;
        Object page;
        try {
            p = (List<Object>) ReflectionUtils
                    .getField(PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftMetaBook"), true, "pages").get(metadata);
            for (String text : pages) {
                page = ReflectionUtils.invokeMethod(ReflectionUtils.PackageType.MINECRAFT_SERVER
                        .getClass("IChatBaseComponent$ChatSerializer").newInstance(), "a", text);
                p.add(page);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void setPagesAsPage(BookMeta meta, List<Page> pagess) {
        List<Object> p;
        Object page;
        try {
            p = (List<Object>) ReflectionUtils
                    .getField(PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftMetaBook"), true, "pages").get(meta);
            for (Page onePage : pagess) {
                page = ReflectionUtils.invokeMethod(ReflectionUtils.PackageType.MINECRAFT_SERVER
                        .getClass("IChatBaseComponent$ChatSerializer").newInstance(), "a", onePage.toJsonString());
                p.add(page);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}