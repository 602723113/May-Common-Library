package cc.zoyn.core.util.serializer;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class BukkitObjectSerializerUtils {

    // Prevent accidental construction
    private BukkitObjectSerializerUtils() {
    }

    /**
     * 单对象序列化为字符串
     *
     * @param object 对象
     * @return 字符串
     * @throws IOException
     */
    public String singleObjectToString(Object object) throws IOException {
        byte[] raw = singleObjectToByteArray(object);

        if (raw != null) {
            return Base64Coder.encodeLines(raw);
        }

        return null;
    }

    /**
     * 单对象序列化为字节数组
     *
     * @param object 对象
     * @return 字节数组
     * @throws IOException
     */
    public byte[] singleObjectToByteArray(Object object) throws IOException {
        if (object instanceof ConfigurationSerializable || object instanceof Serializable) {
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            BukkitObjectOutputStream out = new BukkitObjectOutputStream(buf);

            out.writeObject(object);

            out.close();
            return buf.toByteArray();
        }

        return null;
    }

    /**
     * 集合对象序列化为字符串
     *
     * @param objects 对象
     * @return 字符串
     * @throws IOException
     */
    public String collectionToString(Collection<Object> objects) throws IOException {
        byte[] raw = collectionToByteArray(objects);

        if (raw != null) {
            return Base64Coder.encodeLines(raw);
        }

        return null;
    }

    /**
     * 集合对象序列化为字节数组
     *
     * @param objects 对象
     * @return 字节数组
     * @throws IOException
     */
    public byte[] collectionToByteArray(Collection<Object> objects) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        BukkitObjectOutputStream out = new BukkitObjectOutputStream(buf);
        List<Object> compatible = objects.stream()
                .filter(object -> (object instanceof ConfigurationSerializable || object instanceof Serializable))
                .collect(Collectors.toList());

        out.writeInt(compatible.size());

        for (Object object : compatible) {
            out.writeObject(object);
        }

        out.close();
        return buf.toByteArray();
    }

    /**
     * 字符串反序列化为实例对象
     *
     * @param serialized 字符串
     * @param classOfT   字符串所表示的对象的类
     * @return 实例对象
     * @throws IOException
     */
    public <T> T singleObjectFromString(String serialized, Class<T> classOfT) throws IOException {
        return singleObjectFromByteArray(Base64Coder.decodeLines(serialized), classOfT);
    }

    /**
     * 字节数组反序列化为实例对象
     *
     * @param serialized 字节数组
     * @param classOfT   字节数组所表示的对象的类
     * @return 实例对象
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public <T> T singleObjectFromByteArray(byte[] serialized, Class<T> classOfT) throws IOException {
        ByteArrayInputStream buf = new ByteArrayInputStream(serialized);
        BukkitObjectInputStream in = new BukkitObjectInputStream(buf);
        T object = null;

        try {
            object = (T) in.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        } finally {
            in.close();
        }

        return object;
    }

    /**
     * 字符串反序列化为实例对象集合
     *
     * @param serialized 字符串
     * @param classOfC   集合类型
     * @param classOfT   字符串所表示的对象的类
     * @return 实例对象集合
     * @throws IOException
     */
    public <T, C extends Collection<T>> C collectionFromString(String serialized, Class<C> classOfC, Class<T> classOfT)
            throws IOException {
        return collectionFromByteArray(Base64Coder.decodeLines(serialized), classOfC, classOfT);
    }

    /**
     * 字节数组反序列化为实例对象集合
     *
     * @param serialized 字节数组
     * @param classOfC   集合类型
     * @param classOfT   字节数组所表示的对象的类
     * @return 实例对象集合
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public <T, C extends Collection<T>> C collectionFromByteArray(byte[] serialized, Class<C> classOfC,
                                                                  Class<T> classOfT) throws IOException {
        C objects = null;

        try {
            objects = classOfC.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new IOException("Unable to instantiate collection.", e);
        }

        if (objects != null) {
            ByteArrayInputStream buf = new ByteArrayInputStream(serialized);
            BukkitObjectInputStream in = new BukkitObjectInputStream(buf);

            int count = in.readInt();

            try {
                for (int i = 0; i < count; i++) {
                    objects.add((T) in.readObject());
                }
            } catch (ClassNotFoundException e) {
                throw new IOException("Unable to decode class type.", e);
            } finally {
                in.close();
            }
        }
        return objects;
    }

}