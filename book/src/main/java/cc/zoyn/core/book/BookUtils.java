package cc.zoyn.core.book;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import static cc.zoyn.core.util.nms.NMSUtils.*;
import static cc.zoyn.core.util.reflect.ReflectionUtils.getFieldByFieldName;
import static cc.zoyn.core.util.reflect.ReflectionUtils.getMethod;

/**
 * 书本 - 工具类
 * <p>
 * 简易的打开一个带有特定json的书
 * <p>
 * Easy to open a book with the specified json
 *
 * @author Zoyn
 * @since 2017/?/?
 */
public final class BookUtils {

    private static boolean initialised = false;
    private static Method getHandle;
    private static Method openBook;

    static {
        try {
            getHandle = getMethod(getOBCClass("CraftPlayer"), "getHandle");
            openBook = getMethod(getNMSClass("EntityPlayer"), "a", getNMSClass("ItemStack"), getNMSClass("EnumHand"));

            initialised = true;
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            Bukkit.getServer().getLogger().warning("Cannot force open book!");
            initialised = false;
        }
    }

    // Prevent accidental construction
    private BookUtils() {
    }

    public static boolean isInitialised() {
        return initialised;
    }

    /**
     * 打开一个虚拟的书
     * <p>
     * open a virtual book
     *
     * @param item   给定的书
     * @param player 给定的玩家
     * @return return true if open book successfully
     */
    public static boolean openBook(Player player, ItemStack item) {
        if (!initialised) {
            return false;
        }
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

    /**
     * 打开一个虚拟的书
     * <p>
     * open a virtual book
     *
     * @param player the player
     * @param book   a {@link Book} object
     * @return return true if open book successfully
     */
    public static boolean openBook(Player player, Book book) {
        return openBook(player, book.getItem());
    }

    /**
     * 以JSON格式来设置书的页面
     * <p>
     * use json to set the book pages
     *
     * @param metadata book's meta
     * @param pages    JSON lists
     */
    @SuppressWarnings("unchecked")
    public static void setBookPagesAsJson(BookMeta metadata, List<String> pages) {
        List<Object> p;
        Object page;
        try {
            p = (List<Object>) getFieldByFieldName(getOBCClass("CraftMetaBook"), "pages").get(metadata);
            for (String text : pages) {
                page = stringToIChatBaseComponent(text);
                p.add(page);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用{@link Page}来设置书的页面
     * <p>
     * use {@link Page} to set the book page
     *
     * @param metadata book's meta
     * @param pages    {@link Page} lists
     */
    @SuppressWarnings("unchecked")
    public static void setPagesAsPage(BookMeta metadata, List<Page> pages) {
        setBookPagesAsJson(metadata, pages.stream()
                .map(Page::toJsonString)
                .collect(Collectors.toList()));
    }

    private static void sendPacket(ItemStack itemStack, Player p) throws ReflectiveOperationException {
        Object entityplayer = getHandle.invoke(p);
        Class<?> enumHand = getNMSClass("EnumHand");
        Object[] enumArray = new Object[0];
        if (enumHand != null) {
            enumArray = enumHand.getEnumConstants();
        }
        openBook.invoke(entityplayer, getNMSItem(itemStack), enumArray[0]);
    }
}