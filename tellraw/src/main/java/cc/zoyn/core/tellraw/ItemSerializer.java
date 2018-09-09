package cc.zoyn.core.tellraw;

import cc.zoyn.core.util.nms.NMSUtils;
import cc.zoyn.core.util.nms.nbt.NBTUtils;
import cc.zoyn.core.util.reflect.ReflectionUtils;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

public class ItemSerializer {

    private static Method SAVE_NBT;
    private static Method MOJANGSON_TO_NBT;

    static {
        try {
            SAVE_NBT = ReflectionUtils.getMethod(NMSUtils.getNMSClass("ItemStack"), "save", NMSUtils.getNMSClass("NBTTagCompound"));
            MOJANGSON_TO_NBT = ReflectionUtils.getMethod(NMSUtils.getNMSClass("MojangsonParser"), "parse", String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Prevent accidental construction
    private ItemSerializer() {
    }

    /**
     * Create the item mojangson data
     *
     * @param itemStack the item
     * @return {@link String}
     */
    public static String getItemStackJson(ItemStack itemStack) {
        Object nmsItem = NMSUtils.getNMSItem(itemStack);
        Object savedTag = null;
        try {
            savedTag = ReflectionUtils.invokeMethod(SAVE_NBT, nmsItem, NBTUtils.newNBTTagCompound());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (savedTag != null) {
            return savedTag.toString();
        } else {
            return "null";
        }
    }

    /**
     * Load a item by using mojangson data
     *
     * @param mojangson the mojangson data
     * @return {@link ItemStack}
     */
    public static ItemStack loadItemStackJson(String mojangson) {
        ItemStack itemStack = null;
        try {
            Object nbtTag = ReflectionUtils.invokeMethod(MOJANGSON_TO_NBT, null, mojangson);
            Object nmsItem = NBTUtils.newNMSItemStack(nbtTag);
            itemStack = (ItemStack) NMSUtils.getBukkitItem(nmsItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemStack;
    }

}
