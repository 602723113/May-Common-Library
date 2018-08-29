package cc.zoyn.core.tellraw;

import cc.zoyn.core.util.NMSUtils;
import cc.zoyn.core.util.nbt.NBTUtils;
import cc.zoyn.core.util.reflect.ReflectionUtils;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

public class ItemSerializer {

    private static Method save;

    static {
        try {
            save = ReflectionUtils.getMethod(NMSUtils.getNMSClass("ItemStack"), "save", NMSUtils.getNMSClass("NBTTagCompound"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the item json data
     *
     * @param itemStack the item
     * @return {@link String}
     */
    public static String getItemStackJson(ItemStack itemStack) {
        Object nmsItem = NMSUtils.getNMSItem(itemStack);
        Object savedTag = null;
        try {
            savedTag = ReflectionUtils.invokeMethod(save, nmsItem, NBTUtils.newNBTTagCompound());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (savedTag != null) {
            return savedTag.toString();
        } else {
            return "null";
        }
    }

}
