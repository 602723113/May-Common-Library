package cc.zoyn.core.util;

import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * 物品栏工具类
 *
 * @author Zoyn
 * @since 2016/?/?
 */
public final class InventoryUtils {

    // Prevent accidental construction
    private InventoryUtils() {
    }

    /**
     * 检查一个容器是否为空
     *
     * @param inventory 给定的容器
     * @return 当容器为空时返回true
     */
    public static boolean isEmpty(Inventory inventory) {
        List<ItemStack> itemStacks = Lists.newArrayList(inventory.getContents());
        for (ItemStack itemStack : itemStacks) {
            if (itemStack == null || itemStack.getType().equals(Material.AIR)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查玩家是否拥有某物品
     *
     * @param player    玩家
     * @param itemStack 物品
     * @return Boolean
     */
    public static boolean hasItem(Player player, ItemStack itemStack) {
        Inventory inventory = player.getInventory();
        ItemStack[] invItem = inventory.getContents();
        for (ItemStack item : invItem) {
            if (item.equals(itemStack)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 安全的给予物品
     *
     * @param player 玩家
     * @param is     物品
     */
    public static void safeAddItem(Player player, ItemStack is) {
        if (is == null || is.getType() == Material.AIR) {
            return;
        }
        player.getWorld().dropItem(player.getLocation(), is);
    }

    /**
     * 给玩家物品并设置数量
     *
     * @param player 玩家
     * @param is     物品
     * @param amount 数量
     */
    public static void giveItemAndAmount(Player player, ItemStack is, int amount) {
        if (is == null || is.getType() == Material.AIR) {
            return;
        }
        is.setAmount(amount);
        safeAddItem(player, is);
    }

    /**
     * 设置玩家主手中物品数量
     *
     * @param player 玩家
     * @param amount 数量
     */
    public static void setItemInHandAmount(Player player, int amount) {
        if (player.getInventory().getItemInMainHand() == null
                || player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            return;
        }
        ItemStack is = player.getInventory().getItemInMainHand();
        is.setAmount(amount);
    }
}
