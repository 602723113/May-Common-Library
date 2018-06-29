package cc.zoyn.core.util.nms.nbt.data;

import cc.zoyn.core.util.nms.nbt.TagBase;
import cc.zoyn.core.util.nms.nbt.TagType;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author Zoyn
 * @since 2017-12-02
 */
@NoArgsConstructor
public class TagCompound extends TagBase<Map<String, TagBase>> {

    private Map<String, TagBase> map = Maps.newLinkedHashMap();
    private static final Gson PRETTY_PRINTING_GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Gson NORMAL_GSON = new GsonBuilder().create();

    public Map<String, TagBase> getValue() {
        return map;
    }

    public TagCompound put(String key, TagBase base) {
        map.put(key, base);
        return this;
    }

    public TagCompound putInt(String key, int value) {
        put(key, new TagInt(value));
        return this;
    }

    public TagCompound putByte(String key, byte value) {
        put(key, new TagByte(value));
        return this;
    }

    public TagCompound putLong(String key, long value) {
        put(key, new TagLong(value));
        return this;
    }

    public TagCompound putShort(String key, short value) {
        put(key, new TagShort(value));
        return this;
    }

    public TagCompound putFloat(String key, float value) {
        put(key, new TagFloat(value));
        return this;
    }

    public TagCompound putDouble(String key, double value) {
        put(key, new TagDouble(value));
        return this;
    }

    public TagCompound putString(String key, String value) {
        put(key, new TagString(value));
        return this;
    }

    public TagCompound putBoolean(String key, boolean value) {
        putByte(key, (byte) (value ? 1 : 0));
        return this;
    }

    public TagCompound putCompound(String key, TagCompound tag) {
        put(key, tag);
        return this;
    }

    public TagCompound putCompoundList(String key, List<TagCompound> list) {
        put(key, new TagList<>(TagType.COMPOUND, list));
        return this;
    }

    @Override
    public String toString() {
        Map<String, String> toStringMap = Maps.newHashMap();
        map.forEach((s, tagBase) -> {
            if (tagBase instanceof TagCompound) {
                TagCompound compound = (TagCompound) tagBase;
                toStringMap.put(s, NORMAL_GSON.toJson(compound.getValue()));
                return;
            }
            toStringMap.put(s, tagBase.getValue().toString());
        });
        return PRETTY_PRINTING_GSON.toJson(toStringMap);
    }
}
