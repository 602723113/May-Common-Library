package cc.zoyn.core.util.nbt;

import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class TagCompound {
    private Object nbtTagCompound;

    private Map<String, Object> data = Maps.newHashMap();

    public TagCompound() {
        this.nbtTagCompound = NBTUtils.newNBTTagCompound();
    }

    public TagCompound(Object nbtTagCompound) {
        this.nbtTagCompound = nbtTagCompound;
    }

    public Object getNBTTagCompound() {
        return nbtTagCompound;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public TagCompound put(String key, Object value) {
        data.put(key, value);
        return this;
    }

    public TagCompound putListCompound(String key, List<TagCompound> value) {
        data.put(key, value);
        return this;
    }

    public TagCompound putCompound(String key, TagCompound value) {
        data.put(key, value.build());
        return this;
    }

    public TagCompound putInt(String key, int value) {
        data.put(key, value);
        return this;
    }

    public TagCompound putString(String key, String value) {
        data.put(key, value);
        return this;
    }

    public TagCompound putShort(String key, short value) {
        data.put(key, value);
        return this;
    }

    public TagCompound putLong(String key, long value) {
        data.put(key, value);
        return this;
    }

    public TagCompound putDouble(String key, double value) {
        data.put(key, value);
        return this;
    }

    public TagCompound putByte(String key, byte value) {
        data.put(key, value);
        return this;
    }

    public TagCompound putBoolean(String key, boolean value) {
        data.put(key, value);
        return this;
    }

    public TagCompound putIntArray(String key, int[] value) {
        data.put(key, value);
        return this;
    }

    public TagCompound putByteArray(String key, byte[] value) {
        data.put(key, value);
        return this;
    }

    public Object build() {
        data.forEach((key, value) -> {
            try {
                if (value.getClass().equals(NBTUtils.NBT_TAG_COMPOUND)) {
                    NBTUtils.setTagCompound(nbtTagCompound, key, value);
                } else if (value instanceof List) {
                    NBTUtils.setListTagCompound(nbtTagCompound, key, (List<TagCompound>) value);
                } else if (value instanceof Integer) {
                    NBTUtils.setInt(nbtTagCompound, key, (int) value);
                } else if (value instanceof String) {
                    NBTUtils.setString(nbtTagCompound, key, (String) value);
                } else if (value instanceof Short) {
                    NBTUtils.setShort(nbtTagCompound, key, (short) value);
                } else if (value instanceof Long) {
                    NBTUtils.setLong(nbtTagCompound, key, (long) value);
                } else if (value instanceof Double) {
                    NBTUtils.setDouble(nbtTagCompound, key, (double) value);
                } else if (value instanceof Float) {
                    NBTUtils.setFloat(nbtTagCompound, key, (float) value);
                } else if (value instanceof Byte) {
                    NBTUtils.setByte(nbtTagCompound, key, (byte) value);
                } else if (value instanceof Boolean) {
                    NBTUtils.setBoolean(nbtTagCompound, key, (boolean) value);
                } else if (value instanceof int[]) {
                    NBTUtils.setIntArray(nbtTagCompound, key, (int[]) value);
                } else if (value instanceof byte[]) {
                    NBTUtils.setByteArray(nbtTagCompound, key, (byte[]) value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return nbtTagCompound;
    }


}
