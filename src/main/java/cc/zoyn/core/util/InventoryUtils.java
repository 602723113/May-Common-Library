package cc.zoyn.core.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
     * 检查玩家是否拥有某物品
     *
     * @param player 玩家
     * @param is     物品(ItemStack类型)
     * @return Boolean
     */
    public static boolean hasItem(Player player, ItemStack is) {
        Inventory inventory = player.getInventory();
        ItemStack[] invItem = inventory.getContents();
        int i = 0;
        if (i < invItem.length) {
            if (invItem[i].equals(is)) {
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
