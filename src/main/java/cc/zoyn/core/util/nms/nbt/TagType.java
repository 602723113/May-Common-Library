package cc.zoyn.core.util.nms.nbt;

import cc.zoyn.core.util.nms.nbt.data.*;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * @author Zoyn
 * @since 2017-12-10
 */
@Getter
public enum TagType {
    INT("Int", TagInt.class, Integer.class),
    END("End", null, Void.class),
    BYTE("Byte", TagBase.class, Byte.class),
    LIST("List", TagList.class, List.class),
    LONG("Long", TagLong.class, Long.class),
    SHORT("Short", TagShort.class, Short.class),
    FLOAT("Float", TagFloat.class, Float.class),
    DOUBLE("Double", TagDouble.class, Double.class),
    STRING("String", TagString.class, String.class),
    COMPOUND("Compound", TagCompound.class, Map.class),
    INT_ARRAY("Int_Array", TagIntArray.class, int[].class),
    BYTE_ARRAY("Byte_Array", TagByteArray.class, byte[].class);

    private String name;
    private Class<? extends TagBase> tagClass;
    private Class<?> valueClass;

    <V, T extends TagBase<? extends V>> TagType(String name, Class<T> tagClass, Class<V> valueClass) {
        this.name = name;
        this.tagClass = tagClass;
        this.valueClass = valueClass;
    }

}
