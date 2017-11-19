package cc.zoyn.core.util;

import cc.zoyn.core.dto.SlotTypeEnum;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

public class NBTUtils {

    private static Method setTag;
    private static Method set;

    static {
        try {
            setTag = NMSUtils.getNMSClass("ItemStack").getMethod("setTag", NMSUtils.getNMSClass("NBTTagCompound"));
            set = NMSUtils.getNMSClass("NBTTagCompound").getMethod("set", new Class[]{String.class, NMSUtils.getNMSClass("NBTBase")});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // /**
    // * 各类属性
    // */
    // private static String attackDamage = "generic.attackDamage";
    // private static String attackSpeed = "generic.attackSpeed";
    // private static String maxHealth = "generic.maxHealth";
    // private static String moveMentSpeed = "generic.movementSpeed";
    // private static String armor = "generic.armor";
    // private static String luck = "generic.luck";

    public static Object nbtTagString(String str) {
        try {
            return NMSUtils.getNMSClass("NBTTagString").getConstructor(String.class).newInstance(str);
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }
        return null;
    }

    public static Object nbtTagInt(int num) {
        try {
            return NMSUtils.getNMSClass("NBTTagInt").getConstructor(Integer.TYPE).newInstance(num);
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }
        return null;
    }

    public static Object nbtTagDouble(double num) {
        try {
            return NMSUtils.getNMSClass("NBTTagDouble").getConstructor(Double.TYPE).newInstance(num);
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }
        return null;
    }

    public static Object nbtTagByte(byte num) {
        try {
            return NMSUtils.getNMSClass("NBTTagByte").getConstructor(Byte.TYPE).newInstance(num);
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }
        return null;
    }

    public static Object nbtTagFloat(float num) {
        try {
            return NMSUtils.getNMSClass("NBTTagFloat").getConstructor(Float.TYPE).newInstance(num);
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }
        return null;
    }

    public static Object getItemNBT(ItemStack is) {
        Object nmsItem = NMSUtils.getNMSItem(is);
        if (nmsItem == null) {
            return null;
        }
        try {
            return nmsItem.getClass().getMethod("getTag").invoke(nmsItem) != null
                    ? nmsItem.getClass().getMethod("getTag").invoke(nmsItem)
                    : NMSUtils.getNMSClass("NBTTagCompound").newInstance();
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }
        return null;
    }

    public static void setUnbreakable(ItemStack is, boolean unbreak) {
        Object nmsItem = NMSUtils.getNMSItem(is);
        Object itemNbt = getItemNBT(is);
        try {
            //NPE检查
            if (set == null) {
                set = NMSUtils.getNMSClass("NBTTagCompound").getMethod("set", new Class[]{String.class, NMSUtils.getNMSClass("NBTBase")});
            }
            set.invoke(itemNbt, new Object[]{"Unbreakable", nbtTagByte((byte) (unbreak ? 1 : 0))});

            //NPE检查
            if (setTag == null) {
                setTag = NMSUtils.getNMSClass("ItemStack").getMethod("setTag", NMSUtils.getNMSClass("NBTTagCompound"));
            }

            setTag.invoke(nmsItem, itemNbt);
            NMSUtils.getOBCClass("inventory.CraftItemStack").getMethod("asBukkitCopy", NMSUtils.getNMSClass("ItemStack")).invoke(nmsItem, nmsItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取武器的Attribute数据
     *
     * @param is 物品
     * @return NBTTagCompound的实例
     */
    public static Object getAttribute(ItemStack is) {
        Object nmsItem = NMSUtils.getNMSItem(is);
        // 判断是否有无Tag数据
        try {
            if (nmsItem.getClass().getMethod("getTag").invoke(nmsItem) != null) {
                return nmsItem.getClass().getMethod("getTag").invoke(nmsItem);
            } else {
                // 如果没有Tag数据则实例化个NBTTagCompound的实例
                return NMSUtils.getNMSClass("NBTTagCompound").newInstance();
            }
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }

        return null;
    }

    // public static ItemStack setUnbreakable(ItemStack is, boolean unbreak) {
    // Object nmsItem = NMSUtil.getNMSItem(is);
    // Object itemNbt = getItemNBT(is);
    // return null;
    // }

    /**
     * 设置物品伤害
     *
     * @param is     物品
     * @param damage 伤害
     * @param slot   位置
     * @return 物品的ItemStack
     */
    public static ItemStack setItemDamage(ItemStack is, int damage, SlotTypeEnum slot) {
        try {
            // 物品的nms对象
            Object nmsItem = is.getClass().getMethod("asNMSCopy", ItemStack.class).invoke(is, is);
            // 物品的nbt对象
            Object itemNbt = nmsItem.getClass().getMethod("getTag").invoke(nmsItem) != null
                    ? nmsItem.getClass().getMethod("getTag").invoke(nmsItem)
                    : NMSUtils.getNMSClass("NBTTagCompound").newInstance();
            // NbtTagList对象
            Object modifiers = NMSUtils.getNMSClass("NBTTagList").getConstructor().newInstance();
            Object damageTag = NMSUtils.getNMSClass("NBTTagCompound").getConstructor().newInstance();
            // 模块数据构造
            Object attackDamage = NMSUtils.getNMSClass("NBTTagString").getConstructor(String.class)
                    .newInstance("generic.attackDamage");
            Object name = NMSUtils.getNMSClass("NBTTagString").getConstructor(String.class).newInstance("Damage");
            Object amount = NMSUtils.getNMSClass("NBTTagInt").getConstructor(Integer.TYPE).newInstance(damage);
            Object operation = NMSUtils.getNMSClass("NBTTagInt").getConstructor(Integer.TYPE).newInstance(0);
            Object uuidLeast = NMSUtils.getNMSClass("NBTTagInt").getConstructor(Integer.TYPE).newInstance(894654);
            Object uuidMost = NMSUtils.getNMSClass("NBTTagInt").getConstructor(Integer.TYPE).newInstance(2872);
            Object slotTag = NMSUtils.getNMSClass("NBTTagString").getConstructor(String.class)
                    .newInstance(slot.toString());
            // 模块数据输入
            damageTag.getClass().getMethod("set", new Class[]{String.class, NMSUtils.getNMSClass("NBTBase")})
                    .invoke(damageTag, new Object[]{"AttributeName", attackDamage});
            damageTag.getClass().getMethod("set", new Class[]{String.class, NMSUtils.getNMSClass("NBTBase")})
                    .invoke(damageTag, new Object[]{"Name", name});
            damageTag.getClass().getMethod("set", new Class[]{String.class, NMSUtils.getNMSClass("NBTBase")})
                    .invoke(damageTag, new Object[]{"Amount", amount});
            damageTag.getClass().getMethod("set", new Class[]{String.class, NMSUtils.getNMSClass("NBTBase")})
                    .invoke(damageTag, new Object[]{"Operation", operation});
            damageTag.getClass().getMethod("set", new Class[]{String.class, NMSUtils.getNMSClass("NBTBase")})
                    .invoke(damageTag, new Object[]{"UUIDLeast", uuidLeast});
            damageTag.getClass().getMethod("set", new Class[]{String.class, NMSUtils.getNMSClass("NBTBase")})
                    .invoke(damageTag, new Object[]{"UUIDMost", uuidMost});
            damageTag.getClass().getMethod("set", new Class[]{String.class, NMSUtils.getNMSClass("NBTBase")})
                    .invoke(damageTag, new Object[]{"Slot", slotTag});
            // NbtTagList数据导入
            modifiers.getClass().getMethod("add", NMSUtils.getNMSClass("NBTBase")).invoke(modifiers, damageTag);
            // 设置该NbtTagList
            itemNbt.getClass().getMethod("set", new Class[]{String.class, NMSUtils.getNMSClass("NBTBase")})
                    .invoke(itemNbt, new Object[]{"AttributeModifiers", modifiers});
            // 保存nbt数据
            nmsItem.getClass().getMethod("setTag", NMSUtils.getNMSClass("NBTTagCompound")).invoke(nmsItem, itemNbt);
            ItemStack bukItem = (ItemStack) is.getClass().getMethod("asBukkitCopy", NMSUtils.getNMSClass("ItemStack"))
                    .invoke(nmsItem, nmsItem);
            return bukItem;
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }
        return is;
    }

}
