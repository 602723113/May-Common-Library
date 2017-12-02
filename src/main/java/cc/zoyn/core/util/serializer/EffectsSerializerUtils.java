package cc.zoyn.core.util.serializer;

import com.google.common.collect.Lists;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * 药水序列化工具类
 *
 * @author Zoyn
 * @since 2017/8/2
 */
public class EffectsSerializerUtils {

    /**
     * 将药水效果序列化为Base64数据
     *
     * @param paramCollection 药水数据
     * @return Base64数据
     */
    public static String potionEffectsToBase64(Collection<PotionEffect> paramCollection) {
        try {
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream localBukkitObjectOutputStream = new BukkitObjectOutputStream(localByteArrayOutputStream);

            localBukkitObjectOutputStream.writeInt(paramCollection.toArray().length);
            for (int i = 0; i < paramCollection.toArray().length; i++) {
                localBukkitObjectOutputStream.writeObject(paramCollection.toArray()[i]);
            }
            localBukkitObjectOutputStream.close();
            return Base64Coder.encodeLines(localByteArrayOutputStream.toByteArray());
        } catch (Exception localException) {
            throw new IllegalStateException("在保存药水效果时发生错误!", localException);
        }
    }

    /**
     * 将Base64数据反序列化为药水
     *
     * @param paramString 药水Base64数据
     * @return 药水集合
     */
    public static Collection<PotionEffect> potionEffectsFromBase64(String paramString) {
        try {
            ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(
                    Base64Coder.decodeLines(paramString));
            BukkitObjectInputStream localBukkitObjectInputStream = new BukkitObjectInputStream(
                    localByteArrayInputStream);

            PotionEffect[] arrayOfPotionEffect = new PotionEffect[localBukkitObjectInputStream.readInt()];
            List<PotionEffect> localArrayList = Lists.newArrayList();
            for (int i = 0; i < arrayOfPotionEffect.length; i++) {
                localArrayList.add((PotionEffect) localBukkitObjectInputStream.readObject());
            }
            localBukkitObjectInputStream.close();
            return localArrayList;
        } catch (IOException | ClassNotFoundException localIOException) {
            localIOException.printStackTrace();
        }
        return null;
    }
}
