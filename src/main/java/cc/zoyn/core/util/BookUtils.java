package cc.zoyn.core.util;

import cc.zoyn.core.dto.Book;
import cc.zoyn.core.dto.Page;
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
 * <br />
 * Easy to open a book with the specified json
 * <br />
 * 简易的打开一个带有特定json的书
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
     * <p>
     * 打开一个虚拟的书<br/>
     * open a virtual book
     * </p>
     *
     * @param item   书
     * @param player 玩家
     * @return 布尔值[true成功/false失败]
     */
    public static boolean openBook(Player player, ItemStack item) {
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

    /**
     * 打开一个虚拟的书
     * <br />
     * open a virtual book
     *
     * @param player player object
     * @param book   a {@link Book} object
     * @return true mean open success / false mean open fail
     */
    public static boolean openBook(Player player, Book book) {
        return openBook(player, book.getItem());
    }

    /**
     * 以JSON格式来设置书的页面
     * <br />
     * use json to set book's page
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
     * <br />
     * use {@link Page} to set book's page
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