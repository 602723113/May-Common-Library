package cc.zoyn.core.util.nms.nbt;

import cc.zoyn.core.util.nms.nbt.data.*;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author Zoyn
 * @since 2017-12-02
 */
public class TagCompound extends TagBase<Map<String, TagBase>> {

    private Map<String, TagBase> map = Maps.newLinkedHashMap();
    private static final Gson PRETTY_PRINTING_GSON = new GsonBuilder().setPrettyPrinting().create();

    public Map<String, TagBase> getValue() {
        return map;
    }

    public void put(String key, TagBase base) {
        map.put(key, base);
    }

    public void putInt(String key, int value) {
        put(key, new TagInt(value));
    }

    public void putByte(String key, byte value) {
        put(key, new TagByte(value));
    }

    public void putLong(String key, long value) {
        put(key, new TagLong(value));
    }

    public void putShort(String key, short value) {
        put(key, new TagShort(value));
    }

    public void putFloat(String key, float value) {
        put(key, new TagFloat(value));
    }

    public void putDouble(String key, double value) {
        put(key, new TagDouble(value));
    }

    public void putString(String key, String value) {
        put(key, new TagString(value));
    }

    public void putBoolean(String key, boolean value) {
        putByte(key, (byte) (value ? 1 : 0));
    }

    public void putCompound(String key, TagCompound tag) {
        put(key, tag);
    }

    public void putCompoundList(String key, List<TagCompound> list) {
        put(key, new TagList<>(TagType.COMPOUND, list));
    }

    @Override
    public String toString() {
        return PRETTY_PRINTING_GSON.toJson(map);
    }
}
