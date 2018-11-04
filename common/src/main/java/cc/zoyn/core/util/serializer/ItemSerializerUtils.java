package cc.zoyn.core.util.serializer;

import cc.zoyn.core.util.nms.NMSUtils;
import cc.zoyn.core.util.nms.nbt.NBTUtils;
import cc.zoyn.core.util.reflect.ReflectionUtils;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;
import java.lang.reflect.Method;

/**
 * 物品序列化工具类
 *
 * @author Zoyn
 * @version 1.2
 * @since 2017/?/?
 */
public final class ItemSerializerUtils {

    private static Method WRITE_NBT;
    private static Method READ_NBT;

    static {
        try {
            WRITE_NBT = ReflectionUtils.getMethod(NMSUtils.getNMSClass("NBTCompressedStreamTools"), "a", NMSUtils.getNMSClass("NBTBase"), DataOutput.class);
            if (WRITE_NBT != null) {
                WRITE_NBT.setAccessible(true);
            }

            READ_NBT = ReflectionUtils.getMethod(NMSUtils.getNMSClass("NBTCompressedStreamTools"), "a", DataInput.class, Integer.TYPE, NMSUtils.getNMSClass("NBTReadLimiter"));
            if (READ_NBT != null) {
                READ_NBT.setAccessible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        // the nbt list
        Object localNBTTagList = null;
        try {
            localNBTTagList = NBTUtils.newNBTTagList();
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }
        try {
            for (ItemStack item : items) {
                // CraftBukkit对象
                Object localCraftItemStack = NMSUtils.getOBCItem(item);
                // nbt
                Object savedNBTTagCompound = null;
                if (localCraftItemStack != null) {
                    try {
                        Object nmsItem = NMSUtils.getNMSItem(item);
                        // To save the nbt of item
                        savedNBTTagCompound = NBTUtils.saveItemNBT(nmsItem);
                    } catch (NullPointerException localNullPointerException) {
                        System.out.println("错误: " + localNullPointerException.getMessage());
                    }
                }
                // Add the nbt to nbt list
                NBTUtils.nbtTagListAddNBTBase(localNBTTagList, savedNBTTagCompound);
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
            arrayOfItemStack = new ItemStack[NBTUtils.getNBTTagListSize(localNBTTagList)];

            for (int i = 0; i < arrayOfItemStack.length; i++) {
                Object localNBTTagCompound = NBTUtils.getTagCompound(localNBTTagList, i);

                //判断NBT是否不为空
                if (!(boolean) localNBTTagCompound.getClass().getMethod("isEmpty").invoke(localNBTTagCompound)) {
                    Object nmsItem = NBTUtils.newNMSItemStack(localNBTTagCompound);
                    arrayOfItemStack[i] = (ItemStack) NMSUtils.getBukkitItem(nmsItem);
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
                    .newInstance(Long.MAX_VALUE);
            return READ_NBT.invoke(null, paramDataInput, 0, limiter);
        } catch (Exception localException2) {
            throw new IllegalArgumentException("无法从该位置读取数据" + paramDataInput, localException2);
        }
    }
}
