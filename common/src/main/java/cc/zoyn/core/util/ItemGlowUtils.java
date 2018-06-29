package cc.zoyn.core.util;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;

/**
 * 用于给予物品发光的特性
 *
 * @author Zoyn
 */
public class ItemGlowUtils extends Enchantment {

    static {
        registerGlow();
    }

    /**
     * 注册附魔
     */
    public static void registerGlow() {

        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }
    }


    public ItemGlowUtils(int id) {
        super(id);
    }

    @Override
    public boolean canEnchantItem(ItemStack is) {
        return true;
    }

    @Override
    public boolean conflictsWith(Enchantment enchantment) {
        return false;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return null;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public int getStartLevel() {
        return 0;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

}
