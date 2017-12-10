package cc.zoyn.core.util.serializer;

import cc.zoyn.core.util.nms.NMSUtils;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 物品序列化工具类
 *
 * @author Zoyn
 * @version 1.1 更新 2017/8/2 更新内容: 修复兼容1.11与1.12的问题
 * @since 2017/?/?
 */
public final class ItemSerializerUtils {

    private static Method WRITE_NBT;
    private static Method READ_NBT;

    // Prevent accidental construction
    private ItemSerializerUtils() {
    }

    /**
     * 将物品序列化为Base64数据
     *
     * @param items 物品
     * @return 物品的Base64数据
     */
    public static String toBase64(ItemStack[] items) {
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
        Object localNBTTagList = null;
        try {
            localNBTTagList = NMSUtils.getNMSClass("NBTTagList").getConstructor().newInstance();
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }
        try {
            for (int i = 0; i < items.length; i++) {
                // CraftBukkit对象
                Object localCraftItemStack = Class
                        .forName("org.bukkit.craftbukkit." + NMSUtils.getVersion() + ".inventory.CraftItemStack")
                        .getMethod("asCraftCopy", ItemStack.class).invoke(items[i], items[i]);
                // nbt
                Object localNBTTagCompound = NMSUtils.getNMSClass("NBTTagCompound").getConstructor().newInstance();
                if (localCraftItemStack != null) {
                    try {
                        Object nmsItem = NMSUtils.getNMSItem(items[i]);
                        nmsItem.getClass().getMethod("save", NMSUtils.getNMSClass("NBTTagCompound")).invoke(nmsItem,
                                localNBTTagCompound);
                    } catch (NullPointerException localNullPointerException) {
                        System.out.println("错误: " + localNullPointerException.getMessage());
                    }
                }
                localNBTTagList.getClass().getMethod("add", NMSUtils.getNMSClass("NBTBase")).invoke(localNBTTagList,
                        localNBTTagCompound);
            }
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }

        if (WRITE_NBT == null) {
            try {
                WRITE_NBT = NMSUtils.getNMSClass("NBTCompressedStreamTools").getDeclaredMethod("a",
                        NMSUtils.getNMSClass("NBTBase"), DataOutput.class);
                WRITE_NBT.setAccessible(true);
            } catch (Exception localException1) {
                throw new IllegalStateException("未找到写入方法", localException1);
            }
        }
        try {
            WRITE_NBT.invoke(null, localNBTTagList, localDataOutputStream);
        } catch (Exception localException2) {
            throw new IllegalArgumentException("无法写入" + localNBTTagList + "至" + localDataOutputStream, localException2);
        }
        return Base64Coder.encodeLines(localByteArrayOutputStream.toByteArray());
    }

    /**
     * 将Base64数据反序列化为物品
     *
     * @param paramString Base64数据
     * @return 物品数组
     */
    public static ItemStack[] fromBase64(String paramString) {
        ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(Base64Coder.decodeLines(paramString));
        Object localNBTTagList = readNbt(new DataInputStream(localByteArrayInputStream)); // NBTTagList
        ItemStack[] arrayOfItemStack;
        try {
            arrayOfItemStack = new ItemStack[(int) localNBTTagList.getClass().getMethod("size").invoke(localNBTTagList)];

            for (int i = 0; i < arrayOfItemStack.length; i++) {
                Object localNBTTagCompound = localNBTTagList.getClass().getMethod("get", Integer.TYPE).invoke(localNBTTagList, i);

                //判断NBT是否不为空
                if (!(boolean) localNBTTagCompound.getClass().getMethod("isEmpty").invoke(localNBTTagCompound)) {
                    String version = NMSUtils.getVersion();
                    //子版本 如 v1_10_R1 中的10
                    int subVersion = Integer.valueOf(version.split("_")[1]);
                    /*
                     * 1.11版本及以上删除了createStack方法所以只能使用其构造方法来创建
					 */
                    if (subVersion >= 11) {
                        //构造器
                        Constructor<?> constructor = NMSUtils.getNMSClass("ItemStack").getConstructor(NMSUtils.getNMSClass("NBTTagCompound"));
                        Object nmsItem = constructor.newInstance(localNBTTagCompound);
                        arrayOfItemStack[i] = (ItemStack) NMSUtils.getOBCClass("inventory.CraftItemStack").getMethod("asCraftMirror", NMSUtils.getNMSClass("ItemStack")).invoke(nmsItem, nmsItem);
                    } else {
                        arrayOfItemStack[i] = (ItemStack) Class
                                .forName("org.bukkit.craftbukkit." + NMSUtils.getVersion() + ".inventory.CraftItemStack")
                                .getMethod("asCraftMirror", NMSUtils.getNMSClass("ItemStack"))
                                .invoke(localNBTTagCompound, NMSUtils.getNMSClass("ItemStack")
                                        .getMethod("createStack", NMSUtils.getNMSClass("NBTTagCompound"))
                                        .invoke(localNBTTagCompound, localNBTTagCompound));
                    }
                }
            }

            return arrayOfItemStack;
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }
        return null;
    }

    /**
     * 读取NBT
     *
     * @param paramDataInput 数据输入流
     */
    private static Object readNbt(DataInput paramDataInput) {
        if (READ_NBT == null) {
            try {
                READ_NBT = NMSUtils.getNMSClass("NBTCompressedStreamTools").getDeclaredMethod("a",
                        DataInput.class, Integer.TYPE, NMSUtils.getNMSClass("NBTReadLimiter"));
                READ_NBT.setAccessible(true);
            } catch (Exception localException1) {
                throw new IllegalStateException("未找到方法.", localException1);
            }
        }
        try {
            Object limiter = NMSUtils.getNMSClass("NBTReadLimiter").getConstructor(Long.TYPE)
                    .newInstance(9223372036854775807L);
            return READ_NBT.invoke(null, new Object[]{paramDataInput, Integer.valueOf(0), limiter});
        } catch (Exception localException2) {
            throw new IllegalArgumentException("无法从该位置读取数据" + paramDataInput, localException2);
        }
    }
}
