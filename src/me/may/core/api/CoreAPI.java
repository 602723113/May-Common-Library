package me.may.core.api;

import me.may.core.dto.Tellraw;
import me.may.core.service.CoreService;
import me.may.core.service.CoreServiceImpl;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * 核心API
 *
 * @author May_Speed
 */
public class CoreAPI {

    private static CoreService coreService;

    static {
        coreService = new CoreServiceImpl();
    }

    /**
     * 给一名玩家发送Tellraw
     *
     * @param tellraw Tellraw的对象
     * @param player  玩家
     */
    public static void sendTellraw(Tellraw tellraw, Player player) {
        coreService.sendTellraw(tellraw, player);
    }

    /**
     * 创建一个Tellraw对象
     *
     * @param message 信息
     * @return {@link Tellraw}
     */
    public static Tellraw createTellraw(String message) {
        return coreService.createTellraw(message);
    }

    /**
     * 设置一个物品的无法破坏
     *
     * @param is          物品
     * @param unbreakable 无法破坏值[布尔]
     */
    public static void setItemUnbreakable(ItemStack is, boolean unbreakable) {
        coreService.setItemUnbreakable(is, unbreakable);
    }

    /**
     * 设置一个物品发光
     *
     * @param is 物品
     * @return {@link ItemStack}
     */
    public static ItemStack setItemGlow(ItemStack is) {
        return coreService.setItemGlow(is);
    }

    /**
     * 设置玩家的Tab 可以使用\n换行字符
     *
     * @param player 玩家
     * @param head   Tab首
     * @param foot   Tab尾
     */
    public static void setPlayerTab(Player player, String head, String foot) {
        coreService.setPlayerTab(player, head, foot);
    }

    /**
     * 给玩家发送Title
     *
     * @param player   玩家
     * @param fadeIn   淡入时间
     * @param stay     停留时间
     * @param fadeOut  淡出时间
     * @param title    标题[可用Null]
     * @param subtitle 副标题[可用Null]
     */
    public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
        coreService.sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
    }

    /**
     * 给玩家发送一条ActionBar
     *
     * @param player 玩家
     * @param msg    信息
     */
    public static void sendActionBar(Player player, String msg) {
        coreService.sendActionBar(player, msg);
    }

    /**
     * 取该坐标附近的所有实体
     *
     * @param loc    坐标
     * @param radius 半径
     * @return {@link Entity}
     */
    public static Entity[] getNearbyEntitiesArrays(Location loc, double radius) {
        return coreService.getNearbyEntitiesArrays(loc, radius);
    }
}